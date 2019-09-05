package com.ytxd.service;

import com.ytxd.common.Response;
import com.ytxd.pojo.FileList;

import java.util.List;

public interface FileListService {


    /**
     * 根据目录查找
     *
     * @param categoryId
     * @return
     */
    Response selectCategotyId(int categoryId, Integer page, Integer rows);

    /**
     * 添加
     *
     * @param fileList
     * @return
     */
    int insert(FileList fileList);

    /**
     * 添加多个附件接口
     */
    int insertFiles(List<FileList> list);
    /**
     * 根据id删除
     *
     * @param fileId
     * @return
     */
    int deleteByPrimaryKey(String fileId);


}
