package com.ytxd.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class PageSizeCommon implements Serializable {
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 每页展示的条数
     */
    private Integer rows;
}
