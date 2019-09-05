package com.ytxd.contorller;


import com.ytxd.common.Result;
import com.ytxd.pojo.FileList;
import com.ytxd.service.FileListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/file")
@Api(value = "附件表controller",tags = {"附件表接口"})
public class FileListController {
    @Autowired
    private FileListService fileListService;



    /**
     * 根据目录查找
     * @param categoryId
     * @return
     */
    @GetMapping("/selectCategoryId")
    @ApiOperation(value="根据目录查找附件表",notes = "根据目录categoryId查找")
    public ResponseEntity selectCategoryId(@ApiParam(name = "categortId",value="目录categoryId",required = true)Integer categoryId,
                                     @ApiParam(name = "page", value="页数") Integer page,
                                     @ApiParam(name = "rows", value="条数")Integer rows
                                    ){
        return new ResponseEntity(fileListService.selectCategotyId(categoryId,page,rows), HttpStatus.OK);
    }

    /**
     * 添加
     * @param fileList
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
   // @ApiOperation(value="添加一条附件信息",notes = "添加附件对象")
    public ResponseEntity add(@RequestBody FileList fileList){
        try {
            fileListService.insert(fileList);
            return new ResponseEntity(new Result(true, "增加附件成功"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new Result(false, "增加附件失败"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 添加 多个附件
     * @param list
     * @return
     */
    @RequestMapping(value = "/addFiles",method = RequestMethod.POST)
    // @ApiOperation(value="添加多条附件信息",notes = "添加附件对象")
    public ResponseEntity add(@RequestBody List<FileList> list){
        try {
            fileListService.insertFiles(list);
            return new ResponseEntity(new Result(true, "增加附件成功"), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new Result(false, "增加附件失败"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * 根据id删除
     * @param
     * @return
     */
    @GetMapping("/delete")
    @ApiOperation(value="删除一条附件信息",notes = "根据附表id删除")
    public ResponseEntity delete(@ApiParam(name = "fileId",value = "附件id删除",required = true)@RequestParam(value = "fileId") String fileId, HttpSession session){
        try {
            fileListService.deleteByPrimaryKey(fileId);
            return new ResponseEntity(new Result(true, "删除附件成功") , HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(new Result(false, "删除附件失败"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
