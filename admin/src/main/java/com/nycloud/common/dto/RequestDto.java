package com.nycloud.common.dto;

import lombok.Data;

/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/5/9 0009
 * @modified By:
 * @version: 1.0
 **/
@Data
public class RequestDto {

    private int size = 10;
    private int page = 1;
    private String name;
    private String key;

}
