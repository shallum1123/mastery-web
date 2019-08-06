package com.ytxd.service;

import com.ytxd.common.Response;
import com.ytxd.pojo.Article;

public interface ArticleService {

    /**
     * 根据条件查询对应文章
     *
     * @return
     */
    Response queryByCondition(Article article, Integer rows, Integer page);

    /**
     * 根据id删除文章
     *
     * @param articleId
     * @return
     */
    int removeByPrimaryKey(String articleId);

    /**
     * 根据选择添加文章
     *
     * @param record
     * @return
     */
    Integer addSelective(Article record);

    /**
     * 根据选择内容去修改文章内容
     *
     * @param record
     * @return
     */
    int modifyByPrimaryKeySelective(Article record);
}
