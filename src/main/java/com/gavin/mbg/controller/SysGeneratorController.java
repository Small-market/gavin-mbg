package com.gavin.mbg.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gavin.mbg.entity.TableEntity;
import com.gavin.mbg.entity.vo.MBGEntityVo;
import com.gavin.mbg.service.SysGeneratorService;
import com.gavin.mbg.utils.PageUtil;
import com.gavin.mbg.utils.R;
import com.gavin.mbg.utils.RPage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * 代码生成器
 *
 * @author Gavin
 */
@RestController
@RequiredArgsConstructor
public class SysGeneratorController {

  private final SysGeneratorService sysGeneratorService;
//  private final MBGProperties mbgProperties;

  /**
   * 获取表的列表
   *
   * @param pageIndex 页码
   * @param pageSize  一页的数量
   * @param tableName 表名
   * @return 符合条件的表
   */
  @GetMapping("list")
  public R<IPage<TableEntity>> getList(@RequestParam(defaultValue = "1") Long pageIndex, @RequestParam(defaultValue = "10") Long pageSize, String tableName) {
    IPage<TableEntity> tableList = sysGeneratorService.getTableList(new Page<>(pageIndex, pageSize), tableName);
    return R.ok(tableList);
  }

  /**
   * 生成代码
   */
  @GetMapping("code")
  public void code(String tableList, HttpServletResponse response) throws IOException {
    MBGEntityVo mbg = new MBGEntityVo();
    mbg.setTableList(Arrays.asList(tableList.split(",")));

    byte[] data = sysGeneratorService.generatorCode(mbg);

    response.reset();
//    response.setHeader("Content-Disposition", "attachment; filename=\"" + IdUtil.simpleUUID() + ".zip\"");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode("模板", "UTF-8") + ".zip\"");
    response.addHeader("Content-Length", "" + data.length);
    response.setContentType("application/octet-stream;charset=UTF-8");

    IOUtils.write(data, response.getOutputStream());
  }

  @GetMapping
  public R<RPage<TableEntity>> list(@RequestParam(value = "pageSize", defaultValue = "5") Long pageSize,
                                    @RequestParam(value = "pageIndex", defaultValue = "1") Long pageIndex) {
    return R.ok(PageUtil.toPage(sysGeneratorService.page(new Page<>(pageIndex, pageSize))));
  }

  @GetMapping("{id}")
  public R<TableEntity> getById(@PathVariable Long id) {
    TableEntity byId = sysGeneratorService.getById(id);
    return R.ok(byId);
  }

  @PostMapping
  public R<Boolean> add(TableEntity tableEntity) {
    return oper(sysGeneratorService.save(tableEntity));
  }

  @PutMapping
  public R<Boolean> update(TableEntity tableEntity) {
    return oper(sysGeneratorService.updateById(tableEntity));
  }

  @Delete("{id}")
  public R<Boolean> del(@PathVariable Long id) {
    return oper(sysGeneratorService.removeById(id));
  }

  private R<Boolean> oper(boolean flag) {
    if (flag) {
      return R.ok();
    }
    return R.fail();
  }
}
