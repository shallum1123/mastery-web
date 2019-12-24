package com.ytxd.contorller;

import com.ytxd.pojo.Article;
import com.ytxd.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web")
@Api(value = "文章管理的controller", tags = {"文章管理接口"})
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 根据给定内容添加文章对应内容
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/addArticle", method = RequestMethod.POST)
    @ApiOperation(value = "添加文章", notes = "所属类别，文章名和内容不能为空")
    public ResponseEntity addArticle(@ApiParam(name = "文章对象", value = "前台写入的文章") @RequestBody Article article) {

        return new ResponseEntity(articleService.addSelective(article), HttpStatus.OK);
    }

    /**
     * 根据给定条件动态查询该条件对应的文章及文章附件
     * 如果参数是文章主键，则会查询到一条文章和对应的所有附件
     * 如果参数是类别，则只查询该类别对应的所有文章
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/queryArticle", method = RequestMethod.GET)
    @ApiOperation(value = "动态查询文章分页", notes = "文章id，和类别id不能同时为空")
    public ResponseEntity queryArticle(@ApiParam(name = "article", value = "若文章id不为空，则根据文章id查询，类别id不为空则根据文章id查询，否则查询所有") Article article,
                                       @ApiParam(name = "rows", value = "每页总行数") Integer rows,
                                       @ApiParam(name = "  ", value = "当前页数") Integer page) {
        return new ResponseEntity(articleService.queryByCondition(article, rows, page), HttpStatus.OK);
    }

    /**
     * 根据给定字段修改文章的对应字段内容
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/modifyArticle", method = RequestMethod.POST)
    @ApiOperation(value = "修改文章属性", notes = "根据articleId修改文章内容")
    public ResponseEntity modifyArticle(@ApiParam(name = "修改的文章对象", value = "修改不为空的属性值") @RequestBody Article article) {

        return new ResponseEntity(articleService.modifyByPrimaryKeySelective(article), HttpStatus.OK);
    }

    /**
     * 根据id删除文章
     *
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/removeArticle", method = RequestMethod.GET)
    @ApiOperation(value = "删除文章", notes = "根据主键删除文章")
    public ResponseEntity removeArticle(@ApiParam(name = "articleId", value = "文章id") String articleId) {
        if (articleId.isEmpty()) {
            throw new RuntimeException("未选择删除文件异常！");
        }
        return new ResponseEntity(articleService.removeByPrimaryKey(articleId), HttpStatus.OK);
    }

}
