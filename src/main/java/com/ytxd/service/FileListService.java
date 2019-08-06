package com.ytxd.service;

import com.ytxd.common.Response;
import com.ytxd.pojo.FileList;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface FileListService {


    /**
     * 根据目录查找
     *
     * @param categoryId
     * @return
     */
    Response selectCategotyId(Integer categoryId, Integer page, Integer rows);

    /**
     * 添加
     *
     * @param fileList
     * @return
     */
    int insert(FileList fileList);

    /**
     * 根据id删除
     *
     * @param fileId
     * @return
     */
    int deleteByPrimaryKey(String fileId);


}
