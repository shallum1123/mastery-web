package com.ytxd.service;

import com.ytxd.common.Response;
import com.ytxd.pojo.Cooperate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperateService {
    /**
     * 查询所有数据
     *
     * @return
     */

    Response selectAll(@Param("mohu") String mohu, Integer rows, Integer page);


    /**
     * 提交
     *
     * @param record
     * @return
     */
    int insert(Cooperate record);

    /**
     * 根据主键删除
     *
     * @param coopId
     * @return
     */
    int deleteByPrimaryKey(String coopId);
}
