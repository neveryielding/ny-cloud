package com.nycloud.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @description: 毫秒转换成年月日时分秒字符串
 * @author: super.wu
 * @date: Created in 2018/6/12 0012
 * @modified By:
 * @version: 1.0
 **/
public class ToStringDateSerializer extends JsonSerializer<Long> {

    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(aLong)).toString());
    }

}
