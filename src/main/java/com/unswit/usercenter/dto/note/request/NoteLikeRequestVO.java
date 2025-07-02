package com.unswit.usercenter.dto.note.request;

import lombok.Data;

import java.util.List;

@Data
public class NoteLikeRequestVO {
    private String userId;
    private List<Long> noteIds;
}