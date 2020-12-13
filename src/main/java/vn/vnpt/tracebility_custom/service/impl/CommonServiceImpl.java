package vn.vnpt.tracebility_custom.service.impl;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.tracebility_custom.entity.UploadKey;
import vn.vnpt.tracebility_custom.exception.GeneralException;
import vn.vnpt.tracebility_custom.model.response.ActionRP;
import vn.vnpt.tracebility_custom.repository.*;
import vn.vnpt.tracebility_custom.service.CommonService;
import vn.vnpt.tracebility_custom.util.Constant;
import vn.vnpt.tracebility_custom.util.FileUtil;

import java.io.File;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CheckService checkService;

    @Autowired
    private UserAvatarRepository userAvatarRepository;

    @Autowired
    private SlideImageRepository slideImageRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private NewsImageRepository newsImageRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UploadKeyRepository uploadKeyRepository;

    @Override
    public ActionRP uploadFile(String key, MultipartFile[] files) {

        if (!checkService.checkUploadKey(key)) {
            throw new GeneralException(Constant.RESPONSE.CODE.C006, Constant.RESPONSE.MESSAGE.C007);
        }

        if (files.length == 0) {
            throw new GeneralException(Constant.RESPONSE.CODE.C530, Constant.RESPONSE.MESSAGE.C_530);
        }

        UploadKey uploadKey = uploadKeyRepository.findByKey(key);

        String type = uploadKey.getUploadType();
        String path = locationRepository.findByName(type).getAddress() + File.separator + uploadKey.getVal1();

        for (MultipartFile file : files) {
            FileUtil.multipartfileToFile(file, path);
        }

        return new ActionRP(ImmutableMap.builder()
                .put("Success", "200")
                .build());
    }
}
