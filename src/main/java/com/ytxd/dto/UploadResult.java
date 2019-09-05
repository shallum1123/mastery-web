package com.ytxd.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 上传返回的结果集
 */
@Data
public class UploadResult implements Serializable {
    /**
     * 文件上传后的名称
     */
    private String fileUploadName;
    /**
     * 文件原名称
     */
    private String fileOldName;
    /**
     * 上传的状态码
     */
    private Integer status;
    /**
     * 上传的信息
     */
    private String UploadMessage;
    /**
     * 上传的绝对路径
     */
    private String uploadUrl;
}
