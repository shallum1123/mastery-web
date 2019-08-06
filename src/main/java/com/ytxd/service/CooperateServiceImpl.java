package com.ytxd.service;

import com.ytxd.common.Response;
import com.ytxd.common.SplitPageUtil;
import com.ytxd.dao.CooperateMapper;
import com.ytxd.pojo.Cooperate;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
public class CooperateServiceImpl implements CooperateService {
    @Autowired
    private CooperateMapper cooperateMapper;

    /**
     * 查询所有数据
     *
     * @return
     */
    @Override
    public Response selectAll(String mohu, Integer rows, Integer page) {

        List<Cooperate> cooperateList = cooperateMapper.selectAll(mohu);
        Response response = new Response();
        response.setResultList(cooperateList);
        //分页
        if (Objects.isNull(page) || page == 0) {
            page = 1;
        }
        if (Objects.isNull(rows) || rows == 0) {
            rows = 15;
        }
        Integer totalPage = SplitPageUtil.getTotalPage(rows, cooperateList);
        response.setTotelPage(totalPage);
        response.setTotalSize(cooperateList.size());
        response.setResultList(SplitPageUtil.getPageContent(rows, page, cooperateList));
        return response;
    }


    /**
     * 提交
     *
     * @param record
     * @return
     */

    @Override
    public int insert(Cooperate record) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        record.setCoopId(uuid);
        Date date = new Date();
        java.sql.Date data1 = new java.sql.Date(date.getTime());
        record.setCreateTime(data1);
        if (record.getName().isEmpty()) {
            throw new RuntimeException("姓名不能为空");
        }
        if (record.getTel().isEmpty() || record.getTel().length() != 11 || record.getTel().matches("^\\d+$")) {
            throw new RuntimeException("电话格式不正确");
        }
        if (record.getEmail().matches("^([a-zA-Z0-9])+([a-zA-Z0-9_.-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
            throw new RuntimeException("邮箱格式不正确");
        }
        return cooperateMapper.insert(record);
    }

    /***
     * 根据主键删除
     * @param coopId
     * @return
     */
    @Override
    public int deleteByPrimaryKey(String coopId) {
        if (null == coopId) {
            throw new RuntimeException("id不能为空");
        }
        return cooperateMapper.deleteByPrimaryKey(coopId);
    }
}
