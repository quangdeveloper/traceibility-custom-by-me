package vn.vnpt.tracebility_custom.service;

import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.tracebility_custom.model.response.ActionRP;

public interface CommonService {

    ActionRP uploadFile(String key, MultipartFile[] files);
}
