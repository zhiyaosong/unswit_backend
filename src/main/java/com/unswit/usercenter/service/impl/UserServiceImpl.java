package com.unswit.usercenter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unswit.usercenter.common.ErrorCode;
import com.unswit.usercenter.dto.UserDTO;
import com.unswit.usercenter.exception.BusinessException;
import com.unswit.usercenter.model.domain.User;
import com.unswit.usercenter.service.UserService;
import com.unswit.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.unswit.usercenter.contant.UserConstant.USER_LOGIN_STATE;
import static com.unswit.usercenter.utils.RedisConstants.LOGIN_USER_KEY;
import static com.unswit.usercenter.utils.RedisConstants.LOGIN_USER_TTL;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "unswit";

    @Override
    public User getUserByToken(String token) {
        System.out.println("拿到token:" + token);
        String tokenKey = LOGIN_USER_KEY + token;
        if (!stringRedisTemplate.hasKey(tokenKey)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        // 将 Redis 里的 Map 再转回 UserDTO
        UserDTO userDTO = BeanUtil.mapToBean(userMap, UserDTO.class, true);
        System.out.println(userDTO);
        // 校验用户是否合法
        Long userId = userDTO.getId();
        User user = userMapper.selectById(userId);
        if (user==null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (user.getIsDelete()==1) {
            throw new BusinessException("用户已被删除",50001,"用户已被删除");
        }
        if (user.getUserStatus()==1) {
            throw new BusinessException("用户被封号",50002,"用户被封号");
        }
        User safetyUser = getSafetyUser(user);
        return safetyUser;
    }

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return
     * success: 新用户id
     * error:
     *      {-2: "账户不是由字母、数字和下划线组成",
     *       -1: "密码和校验密码不相同"，
     *       -3: "数据存入失败"}
     */
    @Override
    public long userRegister(String userName, String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        // 这个在controller层已经校验过了
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 6 || checkPassword.length() < 6) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }

        // 账户只能由字母、数字和下划线组成
        String validPattern = "^[a-zA-Z0-9_]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.matches()) {
            return -2;
        }
        // 密码和校验密码不相同
        // 前端表单自带校验，但是要防止爬虫之类的
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        // userName前面没有检验，所以这里要校验一下。没填默认编程侠
        if (StringUtils.isNotBlank(userName)) {
            user.setUserName(userName);
        }else {
            user.setUserName("编程侠");   // 默认用户名，与用户账户不一样
        }
        user.setAvatarUrl("/img/a.png");   // 这个是前端地址，不要在后端找，默认头像（宝贝的玄凤）
        user.setGender(0);   // 0 代表未知， 1 代表男， 2代表女， 3代表中性

        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -3;
        }
        System.out.println("返回Id");
        return user.getId();
    }

    /**
     *
     * 返回生成的token
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @return 生成的token
     */
    @Override
    public String userLogin(String userAccount, String userPassword) {
        // 1. 校验
        // 有可能从其他地方（非control（/login）里）调用吗？
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 6) {
            return null;
        }
        // 账户只能由字母、数字和下划线组成
        String validPattern = "^[a-zA-Z0-9_]+$";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (!matcher.matches()) {
            return "账户不符合要求";
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        System.out.println("加密后密码"+encryptPassword);
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            // 前端弹窗内容是desc内容
            System.out.println("用户为空");
            return "用户为空";
        }
        //保存用户到Redis
        //1.生成token，生成登陆令牌
        String token = UUID.randomUUID().toString(true);
        //2.将User对象转为Hash存储， userDTO里只有id和userName
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(
                userDTO,
                new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue)->{
                            // setFieldValueEditor优先级高于setIgnoreNullValue，所以这里也要判空
                            if(fieldValue == null){
                                return null;
                            }
                            return fieldValue.toString();
                        }));

        // 将token作为key
        String tokenKey = LOGIN_USER_KEY+token;
        try{
            stringRedisTemplate.opsForHash().putAll( tokenKey,userMap);
            stringRedisTemplate.expire(tokenKey,LOGIN_USER_TTL, TimeUnit.MINUTES);
        } catch(ClassCastException cce){
            // 如果这里还报错，再次打印 Map 结构，以便尽快发现问题
            System.err.println(">> [ERROR] Redis putAll 导致 ClassCastException，map 内容如下：");
            throw cce;
        }

        // 3. 维护用户的 token 列表，只有一个作用：控制单个用户的token数量
        String userSessionsKey = "user_sessions:" + user.getId();
        System.out.println("userSessionsKey:" + userSessionsKey);
        final int MAX_SESSIONS = 3;

        // 把新 token 推到左边（列表头）
        stringRedisTemplate.opsForList().leftPush(userSessionsKey, token);
        // 保证列表只保留最新的 MAX_SESSIONS 个
        stringRedisTemplate.opsForList().trim(userSessionsKey, 0, MAX_SESSIONS - 1);
        // 查出被「踢下线」的多余 token（范围：从第 MAX_SESSIONS 个到末尾）
        List<String> expiredTokens =
                stringRedisTemplate.opsForList().range(userSessionsKey, MAX_SESSIONS, -1);
        System.out.println("expiredTokens:" + expiredTokens);

        if (expiredTokens != null && !expiredTokens.isEmpty()) {
            for (String oldToken : expiredTokens) {
                // 1) 删除旧的会话数据
                String oldTokenKey = LOGIN_USER_KEY + oldToken;
                System.out.println("已删除oldTokenKey:" + oldTokenKey);
                stringRedisTemplate.delete(oldTokenKey);
                // 2) 从列表中移除（防止 trim 后保留）
                stringRedisTemplate.opsForList().remove(userSessionsKey, 0, oldToken);
            }
        }
        // 给 userSessionsKey 同步更新过期时间
        stringRedisTemplate.expire(userSessionsKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        //返回token给客户端
        System.out.println("返回token给客户端");
        //return Result.ok(token);
        return token;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return 返回脱敏后的原始数据
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }

        User safetyUser = new User();
       // User safetyUser = BeanUtil.copyProperties(originUser, User.class);
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     */
    @Override
    public int userLogout(HttpServletRequest request, String token) {
//        // 移除登录态
//        request.getSession().removeAttribute(USER_LOGIN_STATE);
        if (StrUtil.isNotBlank(token)) {
            stringRedisTemplate.delete(LOGIN_USER_KEY + token);
        }
        return 1;
    }

}
