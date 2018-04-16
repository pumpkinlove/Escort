package com.miaxis.escort.util;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 一非 on 2018/4/16.
 */

public class StringConverter implements PropertyConverter<String[], String> {

    @Override
    public String[] convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        else {
            List<String> list = Arrays.asList(databaseValue.split(","));
            String[] strings = new String[list.size()];
            list.toArray(strings);
            return strings;
        }
    }

    @Override
    public String convertToDatabaseValue(String[] entityProperty) {
        if(entityProperty==null){
            return null;
        }
        else{
            StringBuilder sb= new StringBuilder();
            for(String link:entityProperty){
                sb.append(link);
                sb.append(",");
            }
            return sb.toString();
        }
    }
}
