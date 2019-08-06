package com.ytxd.service;

import com.ytxd.common.Response;
import com.ytxd.common.SplitPageUtil;
import com.ytxd.dao.ArticleMapper;
import com.ytxd.pojo.Article;
import com.ytxd.pojo.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Response queryByCondition(Article article, Integer rows, Integer page) {

        Response response = new Response();
        // articleId and categoryId 不能同时为空
        List<Article> articles = articleMapper.selectByCondition(article);
        if (articles.isEmpty() && Objects.isNull(articles)) {
            throw new RuntimeException("获取文章列表异常");
        }
        //如果文章id不为空，则是查询文章详情
        if (article.getArticleId() != null) {
            //查询当前文章对应的附件
            List<FileList> articleFiles = articleMapper.selectByBusinessId(article.getArticleId());
            if (!articleFiles.isEmpty() && !Objects.isNull(articleFiles)) {
                for (Article ar : articles) {
                    //将查询到的附件赋值给文章的附件属性
                    ar.setFiles(articleFiles);
                }
            }
        }
        response.setResultList(articles);
        //分页
        if (Objects.isNull(page) || page == 0) {
            page = 1;
        }
        if (Objects.isNull(rows) || rows == 0) {
            rows = 15;
        }
        Integer totalPage = SplitPageUtil.getTotalPage(rows, articles);
        response.setTotelPage(totalPage);
        response.setTotalSize(articles.size());
        response.setResultList(SplitPageUtil.getPageContent(rows, page, articles));
        return response;
    }

    @Override
    public int removeByPrimaryKey(String articleId) {
        if (articleId == null) {
            throw new RuntimeException("未选择需要删除的文章异常！");
        }
        return articleMapper.deleteByPrimaryKey(articleId);
    }

    @Override
    public Integer  addSelective(Article article) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        article.setArticleId(uuid);
        Date date = new Date();
        java.sql.Date data1 = new java.sql.Date(date.getTime());
        article.setCreateTime(data1);

        if (article.getCategoryId() == null) {
            throw new RuntimeException("请选择您要添加文章所属的类别！");
        }
        if (article.getArticleTitle() == null) {
            throw new RuntimeException("文章名不能为空");
        }
        if (article.getContent() == null) {
            throw new RuntimeException("请填写文章内容");
        }
        return articleMapper.insertSelective(article);
    }

    @Override
    public int modifyByPrimaryKeySelective(Article article) {
        if (article.getArticleId() == null) {
            throw new RuntimeException("未选择要修改的文章异常");
        }
        /*if(article.getCategoryId() ==null){
            throw new RuntimeException("文章所属的类别不能为空异常！");
        }
        if (article.getArticleTitle() ==null){
            throw new RuntimeException("文章名不能为空异常");
        }
        if(article.getContent() == null){
            throw new RuntimeException("文章内容为空异常");
        }*/
        Date date = new Date();
        java.sql.Date data1 = new java.sql.Date(date.getTime());
        article.setUpdateTime(data1);
        return articleMapper.updateByPrimaryKeySelective(article);
    }
}
