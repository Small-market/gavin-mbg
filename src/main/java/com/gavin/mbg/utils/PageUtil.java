package com.gavin.mbg.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.UtilityClass;

import java.io.Serializable;

/**
 * 分页数据封装类
 *
 * @author gavin
 */
@UtilityClass
public class PageUtil implements Serializable {

  /**
   * 将PageHelper分页后的list转为分页信息
   */
  public <T> RPage<T> toPage(Page<T> page) {
    RPage<T> basePage = new RPage<>();
    basePage.setPageIndex(page.getCurrent())
            .setPageSize(page.getSize())
            .setTotalPage(page.getPages())
            .setTotal(page.getSize())
            .setList(page.getRecords());
    return basePage;
  }

}
