package vn.vnpt.tracebility_custom.model.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Map;


@Service
@Slf4j
public class GsonResponse {

    public String toJson(Object obj) {
//        Gson gson = new Gson();
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(obj);
    }

    public String errorSystem(Exception ex, Logger logger) {

        ex.printStackTrace();
        logger.error(ex.getMessage());
        ResponseCode responseCode = new ResponseCode();
        responseCode.setFailSystem();
        return toJson(responseCode);
    }

    public String failInput() {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setCode("011");
        return toJson(responseCode);
    }

    public Map<String, Object> fromJson(String json) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return gson.fromJson(json, type);
    }



}
