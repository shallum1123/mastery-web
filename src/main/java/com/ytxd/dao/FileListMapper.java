package com.ytxd.dao;

import com.ytxd.pojo.FileList;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface FileListMapper {
    /**
     * 根据id删除
     *
     * @param fileId
     * @return
     */
    int deleteByPrimaryKey(String fileId);

    /**
     * 添加
     *
     * @param fileList
     * @return
     */
    int insert(FileList fileList);

    /**
     * 根据目录查找
     *
     * @param categoryId
     * @return
     */
    List<FileList> selectCategotyId(Integer categoryId);

    /**
     * 根據id查詢
     *
     * @param fileId
     * @return
     */

    FileList selectFileId(String fileId);
}