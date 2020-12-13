package vn.vnpt.tracebility_custom.service;

import org.springframework.web.multipart.MultipartFile;
import vn.vnpt.tracebility_custom.entity.Email;
import vn.vnpt.tracebility_custom.entity.FileAttack;

import java.util.List;


public interface FileAttackUploadService {

    FileAttack uploadFile(MultipartFile multipartFile, Email email);

    List<FileAttack> uploadFiles(MultipartFile[] multipartFiles, Email email);

}
