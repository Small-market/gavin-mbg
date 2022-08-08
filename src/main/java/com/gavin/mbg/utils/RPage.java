package com.gavin.mbg.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 分页类
 *
 * @author gavin
 * @date 2021/7/10 9:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class RPage<T> implements Serializable {
  /**
   * 当的前页码 从 0 开始
   */
  private Long pageIndex;

  /**
   * 每页显示多少条 最大限制 100 条
   */
  private Long pageSize;

  /**
   * 总页数
   */
  private Long totalPage;

  /**
   * 总数
   */
  private Long total;

  /**
   * 分页数据
   */
  private List<T> list;

}
