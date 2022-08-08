package com.gavin.mbg.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.gavin.mbg.entity.ColumnEntity;
import com.gavin.mbg.entity.TableEntity;
import com.gavin.mbg.entity.vo.MBGEntityVo;
import com.gavin.mbg.exception.MBGException;
import lombok.experimental.UtilityClass;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author Gavin
 */
@UtilityClass
public class GenUtils {

  /**
   * 生成代码
   */
  public void generatorCode(TableEntity tableEntity, List<ColumnEntity> tableColumns, ZipOutputStream zip, MBGEntityVo mbg) {
    //配置信息
    Configuration config = getConfig();
    final boolean[] hasBigDecimal = {false};
    final boolean[] hasList = {false};
    //表名转换成Java类名
    String className = tableToJava(tableEntity.getTableName(), config.getStringArray("tablePrefix"));
    tableEntity.setClassName(className);
    tableEntity.setClassname(StringUtils.uncapitalize(className));

    //列信息
    tableColumns.forEach(i -> {
      //列名转换成Java属性名
      String attrName = columnToJava(i.getColumnName());
      i.setPascalAttrName(attrName);
      i.setCamelAttrName(StringUtils.uncapitalize(attrName));

      //列的数据类型，转换成Java类型
      String attrType = config.getString(i.getDataType(), columnToJava(i.getDataType()));
      i.setAttrType(attrType);

      // 是否允许空值
      i.setNullable("YES".equals(i.getIsNullable()));

      String bigDecimal = "BigDecimal";
      if (!hasBigDecimal[0] && bigDecimal.equals(attrType)) {
        hasBigDecimal[0] = true;
      }
      String array = "array";
      if (!hasList[0] && array.equals(i.getExtra())) {
        hasList[0] = true;
      }

      //是否主键
      String pri = "PRI";
      if (pri.equalsIgnoreCase(i.getColumnKey()) && tableEntity.getPk() == null) {
        tableEntity.setPk(i);
      }
    });

    tableEntity.setColumns(tableColumns);

    //没主键，则第一个字段为主键
    if (tableEntity.getPk() == null) {
      tableEntity.setPk(tableEntity.getColumns().get(0));
    }

    //设置velocity资源加载器
    Properties prop = new Properties();
    prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    Velocity.init(prop);
    String mainPath = config.getString("mainPath");
    mainPath = StringUtils.isBlank(mainPath) ? "com.gavin" : mainPath;

    // 封装模板数据
    Map<String, Object> map = new HashMap<>();
    map.put("tableName", tableEntity.getTableName());
    map.put("comments", tableEntity.getTableComment());
    map.put("pk", tableEntity.getPk());
    map.put("className", tableEntity.getClassName());
    map.put("classname", tableEntity.getClassname());
    map.put("pathName", tableEntity.getClassname().toLowerCase());
    map.put("columns", tableEntity.getColumns());
    map.put("hasBigDecimal", hasBigDecimal[0]);
    map.put("hasList", hasList[0]);
    map.put("mainPath", mainPath);
    map.put("package", config.getString("package"));
    map.put("moduleName", config.getString("moduleName"));
    map.put("author", config.getString("author"));

//    map.put("generateType", generateType);

    // 生成后台管理菜单主键ID
    Snowflake snowflake = IdUtil.getSnowflake(30L, 30L);
    long snowflakeId = snowflake.nextId();
    map.put("parentMenuId", snowflakeId);
    map.put("childMenuId1", snowflakeId + 1);
    map.put("childMenuId2", snowflakeId + 2);
    map.put("childMenuId3", snowflakeId + 3);
    map.put("childMenuId4", snowflakeId + 4);

    // 模板数据放入 Velocity 模板引擎
    VelocityContext context = new VelocityContext(map);

    //获取模板列表
    List<String> templates = getTemplates(mbg);
    for (String template : templates) {
      //渲染模板
      StringWriter sw = new StringWriter();
      Template tpl = Velocity.getTemplate(template, StandardCharsets.UTF_8.name());
      tpl.merge(context, sw);

      try {
        //添加到zip
        zip.putNextEntry(new ZipEntry(Objects.requireNonNull(getFileName(template, tableEntity.getClassName(), config.getString("package"), config.getString("moduleName")))));
        IOUtils.write(sw.toString(), zip, StandardCharsets.UTF_8);
        IOUtils.closeQuietly(sw);
        zip.closeEntry();
      } catch (IOException e) {
        throw new MBGException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
      }
    }
  }

  /**
   * 加载模板
   *
   * @param mbg 判断哪些模板不必要添加
   * @return 模板集合
   */
  public List<String> getTemplates(MBGEntityVo mbg) {
    List<String> templates = new ArrayList<>();
    // 后端
    templates.add("template/backend/Entity.java.vm");
    templates.add("template/backend/Mapper.java.vm");
    templates.add("template/backend/Service.java.vm");
    templates.add("template/backend/AdminController.java.vm");
    templates.add("template/backend/AdminListDTO.java.vm");
    templates.add("template/backend/AdminInsertOrUpdateDTO.java.vm");
    templates.add("template/backend/BO.java.vm");
    templates.add("template/backend/sys_menu.sql.vm");

//    if (Constant.GENERATE_TYPE_CLOUD.equals(generateType)) {
//      templates.add("template/backend/Facade.java.vm");
//      templates.add("template/backend/FacadeImpl.java.vm");
//    }

    // 前端代码 Vben Admin
    templates.add("template/frontend/api/Api.ts.vm");
    templates.add("template/frontend/api/Model.ts.vm");
    templates.add("template/frontend/views/data.ts.vm");
    templates.add("template/frontend/views/detail-drawer.vue.vm");
    templates.add("template/frontend/views/update-drawer.vue.vm");
    templates.add("template/frontend/views/index.vue.vm");

    return templates;
  }

  /**
   * 列名转换成Java属性名
   */
  public String columnToJava(String columnName) {
    return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
  }

  /**
   * 表名转换成Java类名
   */
  public String tableToJava(String tableName, String[] tablePrefixArray) {
    if (null != tablePrefixArray && tablePrefixArray.length > 0) {
      for (String tablePrefix : tablePrefixArray) {
        if (tableName.startsWith(tablePrefix)) {
          tableName = tableName.replaceFirst(tablePrefix, "");
        }
      }
    }
    return columnToJava(tableName);
  }

  /**
   * 获取配置信息
   */
  public Configuration getConfig() {
    try {
      return new Configurations().properties("generator.properties");
    } catch (Exception e) {
      throw new MBGException("获取配置文件失败，", e);
    }
  }

  /**
   * 获取文件名
   */
  public String getFileName(String template, String className, String packageName, String moduleName) {
    /*
    后端代码
     */
    String backendPathPrefix = "后端代码" + File.separator;
    if (org.apache.commons.lang.StringUtils.isNotBlank(packageName)) {
      backendPathPrefix += packageName.replace(".", File.separator) + File.separator + moduleName + File.separator;
    }
    if (template.contains("Entity.java.vm")) {
      return backendPathPrefix + "entity" + File.separator + className + "Entity.java";
    }

    if (template.contains("Mapper.java.vm")) {
      return backendPathPrefix + "mapper" + File.separator + className + "Mapper.java";
    }

    if (template.contains("Service.java.vm")) {
      return backendPathPrefix + "service" + File.separator + className + "Service.java";
    }

    if (template.contains("Facade.java.vm")) {
      return backendPathPrefix + "facade" + File.separator + className + "Facade.java";
    }

    if (template.contains("FacadeImpl.java.vm")) {
      return backendPathPrefix + "biz" + File.separator + className + "FacadeImpl.java";
    }

    if (template.contains("AdminController.java.vm")) {
      return backendPathPrefix + "Admin" + className + "Controller.java";
    }

    if (template.contains("AdminListDTO.java.vm")) {
      return backendPathPrefix + "model" + File.separator + "request" + File.separator + "AdminList" + className + "DTO.java";
    }

    if (template.contains("AdminInsertOrUpdateDTO.java.vm")) {
      return backendPathPrefix + "model" + File.separator + "request" + File.separator + "AdminInsertOrUpdate" + className + "DTO.java";
    }

    if (template.contains("BO.java.vm")) {
      return backendPathPrefix + "model" + File.separator + "response" + File.separator + className + "BO.java";
    }

    if (template.contains("sys_menu.sql.vm")) {
      return backendPathPrefix + "后台管理菜单-" + className + ".sql";
    }

    /*
    前端代码(Vben Admin)
     */
    String frontendPathPrefix = "前端代码" + File.separator + "src" + File.separator;
    if (template.contains("Api.ts.vm")) {
      return frontendPathPrefix + "api" + File.separator + moduleName + File.separator + className + "Api.ts";
    }

    if (template.contains("Model.ts.vm")) {
      return frontendPathPrefix + "api" + File.separator + moduleName + File.separator + "model" + File.separator + className + "Model.ts";
    }

    if (template.contains("data.ts.vm")) {
      return frontendPathPrefix + "views" + File.separator + moduleName + File.separator + className + File.separator + "data.ts";
    }

    if (template.contains("detail-drawer.vue.vm")) {
      return frontendPathPrefix + "views" + File.separator + moduleName + File.separator + className + File.separator + "detail-drawer.vue";
    }

    if (template.contains("update-drawer.vue.vm")) {
      return frontendPathPrefix + "views" + File.separator + moduleName + File.separator + className + File.separator + "update-drawer.vue";
    }

    if (template.contains("index.vue.vm")) {
      return frontendPathPrefix + "views" + File.separator + moduleName + File.separator + className + File.separator + "index.vue";
    }
    return null;
  }
}
