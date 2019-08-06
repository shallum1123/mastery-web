package com.ytxd.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class UploadDto {
    @Value("${upload.file-path:/workspace/uploadFile}")
    private String filePath;//自定义上传文件绝对路径根目录

    private String fileUrl;//
}
