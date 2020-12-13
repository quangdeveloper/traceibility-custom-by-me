package vn.vnpt.tracebility_custom.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.vnpt.tracebility_custom.entity.Email;
import vn.vnpt.tracebility_custom.entity.FileAttack;
import vn.vnpt.tracebility_custom.service.FileAttackUploadService;
import vn.vnpt.tracebility_custom.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileAttackUploadServiceImpl implements FileAttackUploadService {

    @Value("${app.default.directory.emailAttackment}")
    private String directoryPath;

    @Override
    public FileAttack uploadFile(MultipartFile multipartFile, Email email) {

        String filename = FileUtil.multipartfileToFile(multipartFile, directoryPath);

        return FileAttack.builder()
                .path(filename)
                .email(email)
                .build();
    }

    @Override
    public List<FileAttack> uploadFiles(MultipartFile[] multipartFiles, Email email) {

        List<FileAttack> fileAttackList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            fileAttackList.add(uploadFile(multipartFile, email));
        }

        return  fileAttackList;
    }
}
