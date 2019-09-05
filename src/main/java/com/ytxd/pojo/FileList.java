package com.ytxd.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FileList implements Serializable {

    private String fileId;

    private int categoryId;

    private String businessId;

    private String fileTitle;//附件标题

    private String realName;//上传前的名称

    private String uploadName;//上传后的名称

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

    private int fileSort;//排序

    private String url;//文件存储的绝对路径

    private String spare1;

    private String spare2;

    private String spare3;

}