package com.ytxd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class CooperateReq extends PageSizeCommon {
    /**
     * 模糊查询查询条件
     */
    private String mohu;
}
