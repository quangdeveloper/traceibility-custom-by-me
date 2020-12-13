package vn.vnpt.tracebility_custom.model.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseConfig {

    private static ResponseConfig instance;

    private Map<String, String> map;

    public static ResponseConfig getInstance() {
        if (instance == null) instance = new ResponseConfig();
        return instance;
    }

    private ResponseConfig() {
        map = new HashMap<>();
        addResponse();
    }

    public String getMess(String code) {
        return map.get(code);
    }

    public void addResponse() {
        map.put("404", "Không tìm thấy tài nguyên");
        map.put("405", "Lỗi 405");
        map.put("406", "Lỗi 406");
        map.put("409", "Tài nguyên đã tồn tại");

    }
}
