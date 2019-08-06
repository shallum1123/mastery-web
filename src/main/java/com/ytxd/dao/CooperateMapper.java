package com.ytxd.dao;

import com.ytxd.pojo.Cooperate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperateMapper {
    /**
     * 根据主键删除
     *
     * @param coopId
     * @return
     */
    int deleteByPrimaryKey(String coopId);

    /**
     * 提交
     *
     * @param record
     * @return
     */

    int insert(Cooperate record);


    /**
     * 查询所有数据
     *
     * @return
     */
    List<Cooperate> selectAll(@Param("mohu") String mohu);
}