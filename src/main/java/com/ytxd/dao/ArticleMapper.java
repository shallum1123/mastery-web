package com.ytxd.dao;

import com.ytxd.pojo.Article;
import com.ytxd.pojo.FileList;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

public interface ArticleMapper {

    /**
     * 根据条件查询对应文章
     *
     * @return
     */
    List<Article> selectByCondition(Article article);

    /**
     * 根据文章主键查询对应的所有附件
     *
     * @param businessId
     * @return
     */
    List<FileList> selectByBusinessId(@Param(value = "businessId") String businessId);

    /**
     * 根据id删除文章
     *
     * @param articleId
     * @return
     */
    int deleteByPrimaryKey(String articleId);

    /**
     * 根据选择添加文章
     *
     * @param article
     * @return
     */
    int insertSelective(Article article);

    /**
     * 根据选择内容去修改文章内容
     *
     * @param article
     * @return
     */
    int updateByPrimaryKeySelective(Article article);

}