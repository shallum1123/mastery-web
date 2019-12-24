package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.google.common.collect.Maps;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.ytxd.dto.UploadResult;

import io.swagger.annotations.ApiParam;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BinaryUploader {

	   // @Value("${uploadFile.url}")
	   // private static String url = "http://192.144.192.2:8888";

//	   @Value("${uploadFile.ip}")
//	   private String ip;
	    private static String ip = "192.144.192.2";

//	    @Value("${uploadFile.port}")
//	    private int port;
	   private static int port = 22;

//	    @Value("${uploadFile.user}")
//	    private String user;
	   private static String user = "root";

//	    @Value("${uploadFile.password}")
//	    private String password;
	   private static String password = "1qaz2wsx!@#$";

	   // @Value("${uploadFile.filepath}")
	   // private static String filePath = "/workspace/images";

	
	public State save(HttpServletRequest request,
			Map<String, Object> conf) {
		// FileItemStream fileStream = null;
		// boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

        // ServletFileUpload upload = new ServletFileUpload(
			// 	new DiskFileItemFactory());
        //
        // if ( isAjaxUpload ) {
        //     upload.setHeaderEncoding( "UTF-8" );
        // }

		try {
			// FileItemIterator iterator = upload.getItemIterator(request);
            //
			// while (iterator.hasNext()) {
			// 	fileStream = iterator.next();
            //
			// 	if (!fileStream.isFormField())
			// 		break;
			// 	fileStream = null;
			// }
            //
			// if (fileStream == null) {
			// 	return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			// }
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
			if(multipartFile==null){
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}

//			String savePath = (String) conf.get("savePath");
//			savePath = PathFormat.format(savePath);
//			//String originFileName = fileStream.getName();
//			String originFileName = multipartFile.getOriginalFilename();
//			String suffix = FileType.getSuffixByFilename(originFileName);
//
//			originFileName = originFileName.substring(0,
//					originFileName.length() - suffix.length());
//			savePath = savePath + suffix;
//
//			long maxSize = ((Long) conf.get("maxSize")).longValue();
//
//			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
//				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
//			}
//
//			savePath = PathFormat.parse(savePath, originFileName);
//
//			//String physicalPath = (String) conf.get("rootPath") + savePath;
//			String basePath=(String) conf.get("basePath");
//			String physicalPath = basePath + savePath;
//
//			//InputStream is = fileStream.openStream();
//			InputStream is = multipartFile.getInputStream();
//			State storageState = StorageManager.saveFileByInputStream(is,
//					physicalPath, maxSize);
//			is.close();
//
//			if (storageState.isSuccess()) {
//				storageState.putInfo("url", PathFormat.format(savePath));
//				storageState.putInfo("type", suffix);
//				storageState.putInfo("original", originFileName + suffix);
//			}
			Map<String, Object> map = uploadImg(multipartFile, conf);
			State storageState = new BaseState();
			storageState.putInfo("url", map.get("url").toString());
			storageState.putInfo("type", map.get("type").toString());
			storageState.putInfo("original", map.get("original").toString());
			storageState.putInfo("state", map.get("state").toString());
			storageState.putInfo("title", map.get("title").toString());
			storageState.putInfo("size", (Long)map.get("size"));
			return storageState;
		 } catch (Exception e) {
		 	return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		} 
		// return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}
	
	
	private  Map<String, Object> uploadImg(MultipartFile file, Map<String, Object> conf) {
		Map<String, Object> uploadResult = Maps.newHashMap();

        if (file == null) {
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

            // String nowTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            String folderName = PathFormat.parse(conf.get("basePath").toString()+conf.get("savePath").toString()); //PathFormat.format("");

            if (port <= 0) {
                session = jsch.getSession(user, ip);
            } else {
                session = jsch.getSession(user, ip, port);
            }

            if (session == null) {
                throw new Exception("session is null");
            }

            session.setPassword(password);

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

//            uploadResult.setFileOldName(realName);
//            uploadResult.setFileUploadName(uploadName);
//            uploadResult.setUploadUrl(this.url + "/images/" + nowTime + "/" + uploadName);
//            uploadResult.setStatus(Integer.valueOf(200));
//            uploadResult.setUploadMessage("上传成功！");
            uploadResult.put("state", "SUCCESS");
            uploadResult.put("url", folderName.replace(conf.get("basePath").toString(), "") + "/" + uploadName);
            uploadResult.put("original", realName);
            uploadResult.put("title", uploadName);
            uploadResult.put("type", suffixName);
            uploadResult.put("size", file.getSize());
        } catch (Exception e) {
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
