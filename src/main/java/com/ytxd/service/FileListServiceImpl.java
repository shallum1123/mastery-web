package com.ytxd.service;

import com.ytxd.common.Response;
import com.ytxd.common.SplitPageUtil;
import com.ytxd.dao.FileListMapper;
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
public class FileListServiceImpl implements FileListService {
    @Autowired
    private FileListMapper fileListMapper;


    /**
     * 根据目录查找
     *
     * @param categoryId
     * @return
     */
    @Override
    public Response selectCategotyId(Integer categoryId, Integer page, Integer rows) {
        if (categoryId == null) {
            throw new RuntimeException("所属类目不能为空");
        }
        List<FileList> fileLists = fileListMapper.selectCategotyId(categoryId);
        Response response = new Response();
        response.setResultList(fileLists);
        //分页
        if (Objects.isNull(page) || page == 0) {
            page = 1;
        }
        if (Objects.isNull(rows) || rows == 0) {
            rows = 15;
        }

        Integer totalPage = SplitPageUtil.getTotalPage(rows, fileLists);
        response.setTotelPage(totalPage);
        response.setTotalSize(fileLists.size());
        response.setResultList(SplitPageUtil.getPageContent(rows, page, fileLists));

        return response;
    }

    /**
     * 添加
     *
     * @param fileList
     * @return
     */
    @Override
    public int insert(FileList fileList) {
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        //判断附件id是否为空
        if (fileList.getFileId() != "" && fileList.getFileId() != null) {
            //id不为空则是替换 附件
            FileList file = fileListMapper.selectFileId(fileList.getFileId());
            //将原来的创建时间赋值给替换后的附件
            fileList.setCreateTime(file.getCreateTime());
            //修改时间为当前时间
            fileList.setUpdateTime(date);
            //根据id删除附件
            fileListMapper.deleteByPrimaryKey(fileList.getFileId());
        } else {
            fileList.setCreateTime(date);

        }
        //定义附件主键id
        String uuid = UUID.randomUUID().toString().replace("-", "");
        fileList.setFileId(uuid);
        if (fileList.getCategoryId() == null) {
            throw new RuntimeException("请选择您要添加图片所属的类目");
        }

        return fileListMapper.insert(fileList);
    }


    /**
     * 根据id删除
     *
     * @param fileId
     * @return
     */

    @Override
    public int deleteByPrimaryKey(String fileId) {
        if (fileId == null) {
            throw new RuntimeException("fileId不能为空");
        }
        /*String realPath = session.getServletContext().getRealPath("upload");
        File file = new File("realPath");
        if(!file.exists()){
            throw new RuntimeException("您删除的文件名不存在");
        }
        file.delete();*/
        return fileListMapper.deleteByPrimaryKey(fileId);

    }


}
