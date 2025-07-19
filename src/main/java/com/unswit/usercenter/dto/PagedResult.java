package com.unswit.usercenter.dto;

import lombok.Data;

import java.util.List;

@Data
public class PagedResult<T> {
    private List<T> data;
    private long total;
}
