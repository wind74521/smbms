package cn.wind.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * @author Wind
 * @see 2020-02-19 12:43
 * Json格式转换
 */
public class JsonConfig {

    public static String getJson(Object object){
        return JsonConfig.getJson(object,"yyyy-MM-dd HH:mm:ss");
    }

    public static String getJson(Object object,String format){

        ObjectMapper mapper = new ObjectMapper();
        //不使用时间戳
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        mapper.setDateFormat(simpleDateFormat);

        try {
            //转json格式
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
