package com.ytxd.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author long
 * @category 龙哥自创分页工具类
 */
public class SplitPageUtil {
    /**
     * 根据查询到的条数得到总页数
     *
     * @param list
     * @return
     */
    public static Integer getTotalPage(Integer rows, List list) {
        int totalPage;
        // 判断查到的数据是否为空;
        if (list.size() == 0) {
            return totalPage = 0;
        } else if (list.size() % rows == 0) {
            return totalPage = list.size() / rows;
        } else {
            return totalPage = list.size() / rows + 1;
        }
    }

    /**
     * 根据当前页数获取自己设定的当前页内容
     *
     * @param page
     * @param list
     * @return
     */
    public static List getPageContent(Integer rows, Integer page, List list) {
        /**
         * rows:设定每页条数 page:当前页 list:查询到的所有对象的集合
         */
        int totalPage = 1;
        List newList = new ArrayList();

        // 计算总页数；
        if (list.size() % rows == 0) {
            totalPage = list.size() / rows;
        } else {
            totalPage = list.size() / rows + 1;
        }
        // 判断查到的数据是否为空;
        if (list.size() == 0) {
            // 判断如果当前页数小于总页数，或刚好每页都是rows
        } else if (page < totalPage || list.size() % rows == 0) {
            // 将当前页数的显示内容放到新的集合中
            for (int i = rows * (page - 1); i < rows * page; i++) {
                newList.add(list.get(i));
            }
        } else {// 如果左后页条数不为rows,且当亲页数等于总页数
            // 将剩余的不为rows条数据放到newList中
            for (int i = rows * (totalPage - 1); i < list.size(); i++) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }
}
