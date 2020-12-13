package vn.vnpt.tracebility_custom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.vnpt.tracebility_custom.entity.UploadKey;
import vn.vnpt.tracebility_custom.repository.UploadKeyRepository;
import vn.vnpt.tracebility_custom.service.UploadService;
import vn.vnpt.tracebility_custom.util.GenerateCode;

import java.time.LocalDate;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadKeyRepository uploadKeyRepository;

    @Autowired
    private CheckService checkService;

    @Override
    public String progressUpload(Long id, String type) {

        String uploadKey = GenerateCode.genarateUploadKey();
        checkService.setKeyUpload(uploadKey);

        UploadKey upload = UploadKey.builder()
                .createdDate(LocalDate.now())
                .expiredDate(LocalDate.now())
                .keyGen(uploadKey)
                .uploadType(type)
                .maxFile(3L)
                .status("A")
                .val1(id)
                .val2(0L)
                .val3(0L)
                .build();
        uploadKeyRepository.save(upload);
        return uploadKey;
    }

}
