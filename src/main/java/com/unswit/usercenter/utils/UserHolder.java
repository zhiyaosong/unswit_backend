package com.unswit.usercenter.utils;


import com.unswit.usercenter.dto.user.UserSimpleDTO;

public class UserHolder {
    private static final ThreadLocal<UserSimpleDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserSimpleDTO user){
        tl.set(user);
    }

    public static UserSimpleDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
