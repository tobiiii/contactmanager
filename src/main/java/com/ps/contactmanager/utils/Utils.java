package com.ps.contactmanager.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Slf4j
public abstract class Utils {


    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        var mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(df);
        return mapper.writeValueAsString(object);
    }


}
