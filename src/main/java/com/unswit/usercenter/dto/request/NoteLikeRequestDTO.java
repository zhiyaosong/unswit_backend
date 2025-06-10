package com.unswit.usercenter.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class NoteLikeRequestDTO {
    private String userId;
    private List<Long> noteIds;
}