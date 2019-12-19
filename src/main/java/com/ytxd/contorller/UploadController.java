package com.ytxd.contorller;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.ytxd.dto.UploadResult;
import com.ytxd.service.FileListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "附件controller", tags = { "上传接口" })
@RestController
@RequestMapping({ "/upload" })
public class UploadController {
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    FileListService fileListService;

    @Value("${uploadFile.url}")
    private String url;

    @Value("${uploadFile.ip}")
    private String ip;

    @Value("${uploadFile.port}")
    private int port;

    @Value("${uploadFile.user}")
    private String user;

    @Value("${uploadFile.password}")
    private String password;

    @Value("${uploadFile.filepath}")
    private String filePath;

    @RequestMapping({ "/uploadFile" })
    @ApiOperation(value = "上传文件", notes = "MultipartFile为空则是未选择文件")
    public UploadResult uploadImg(@ApiParam(name = "file", value = "选择的文件") @RequestBody MultipartFile file) {
        UploadResult uploadResult = new UploadResult();

        if (file == null) {
            System.out.println("==============" + file);
            throw new RuntimeException("未选择上传文件异常！");
        }

        Session session = null;
        Channel channel = null;

        JSch jsch = new JSch();
        OutputStream outstream = null;
        try {
            String realName = file.getOriginalFilename();

            String suffixName = realName.substring(realName.lastIndexOf("."));

            String uploadName = UUID.randomUUID().toString().replace("-", "") + suffixName;

            String nowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String folderName = this.filePath + "/" + nowTime;

            if (this.port <= 0) {
                session = jsch.getSession(this.user, this.ip);
            } else {
                session = jsch.getSession(this.user, this.ip, this.port);
            }

            if (session == null) {
                throw new Exception("session is null");
            }

            session.setPassword(this.password);

            session.setConfig("StrictHostKeyChecking", "no");

            session.connect(30000);

            channel = session.openChannel("sftp");
            channel.connect(50000);
            ChannelSftp sftp = (ChannelSftp) channel;

            boolean isDir = true;
            try {
                sftp.cd(folderName);
                isDir = true;
            } catch (SftpException e) {
                isDir = false;
            }
            if (!isDir) {
                sftp.mkdir(folderName);
                sftp.cd(folderName);
            }

            File files = File.createTempFile(realName, realName.substring(file.getOriginalFilename().lastIndexOf(".")));
            file.transferTo(files);

            sftp.put(new FileInputStream(files), uploadName);

            uploadResult.setFileOldName(realName);
            uploadResult.setFileUploadName(uploadName);
            uploadResult.setUploadUrl(this.url + "/images/" + nowTime + "/" + uploadName);
            uploadResult.setStatus(Integer.valueOf(200));
            uploadResult.setUploadMessage("上传成功！");
            log.info("{}", "文件上传成功！", Integer.valueOf(200));
        } catch (Exception e) {
            log.error("{}", "文件上传失败，失败理由：", e);
            throw new RuntimeException("上传失败异常", e);
        } finally {
            if (outstream != null) {
                try {
                    outstream.flush();
                    outstream.close();
                } catch (IOException e) {
                    throw new RuntimeException("關閉文件流失敗", e);
                }
            }
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
        return uploadResult;
    }

}