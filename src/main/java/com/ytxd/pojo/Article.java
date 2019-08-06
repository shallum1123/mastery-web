package com.ytxd.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Article implements Serializable {
    private String articleId;//文章id

    private Integer categoryId;//类别id

    private String articleTitle;//文章名称

    private String abbreviation;//简写

    private Date createTime;

    private Date updateTime;

    private String sketch;//简述

    private Integer status;//状态

    private String content;//文章内容

    /**
     * 一篇文章对应的所有附件
     */
    private List<FileList> files;

}