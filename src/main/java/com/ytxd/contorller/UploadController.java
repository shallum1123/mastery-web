package com.ytxd.contorller;

import com.ytxd.dto.UploadDto;
import com.ytxd.dto.UploadResult;
import com.ytxd.service.FileListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Api(value = "附件controller", tags = "上传接口")
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    FileListService fileListService;

    @Value("${upload.url}")
    private String url;

    @RequestMapping("/uploadFile")
    @ApiOperation(value = "上传文件", notes = "MultipartFile为空则是未选择文件")
    public UploadResult uploadImg(@ApiParam(name = "file", value = "选择的文件") @RequestBody MultipartFile file, HttpSession session) {
        //结果集类
        UploadResult uploadResult = new UploadResult();
        //配置类
        UploadDto uploadDto = new UploadDto();
        if (file == null) {
            System.out.println("=============="+file);
            throw new RuntimeException("未选择上传文件异常！");
        }
        try {
            //获取得到原始文件名
            String realName = file.getOriginalFilename();
            //获取文件后缀名
            String suffixName = realName.substring(realName.lastIndexOf("."));
            //定义上传后的文件名
            String uploadName = UUID.randomUUID().toString().replace("-", "") + suffixName;
            //获取当前时间戳
            String nowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //按照时间戳创建不同的文件夹
            String folderName = "/" + nowTime + "/" + uploadName;
            //根据定义的文件路径，确定文件存储的相对路径
            //String uploadUrl = uploadDto.getFilePath() + folderName;
            //动态指定文件上传目录(根据相对路径获取绝对路径)
            String uploadUrl = session.getServletContext().getRealPath(folderName);

            File fileAddress = new File(uploadUrl);
            if (!fileAddress.getParentFile().exists()) {
                fileAddress.getParentFile().mkdir();
            }
            file.transferTo(fileAddress);
            uploadResult.setFileOldName(realName);
            uploadResult.setFileUploadName(uploadName);
            uploadResult.setUploadUrl(url+folderName);
            uploadResult.setStatus(200);
            uploadResult.setUploadMessage("上传成功！");
            log.info("{}", "文件上传成功！", 200);
        } catch (IOException e) {
            log.error("{}", "文件上传失败，失败理由：", e);
            throw new RuntimeException("上传失败异常", e);
        }
        return uploadResult;
    }
}
