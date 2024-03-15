package com.forum.user.generate;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;

public class MybatisPlusGenerate {

    final static String URL = "jdbc:mysql://localhost:3306/ahut_forum?characterEncoding=UTF8&autoReconnect=true&serverTimezone=Asia/Shanghai";
    final static String USERNAME = "root";
    final static String PASSWORD = "root";

    public static void main(String[] args) {
        // 代码生成器全局配置
        FastAutoGenerator fastAutoGenerator = FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> {
                    builder.author("LiTuiZi")
                            .enableSwagger()
                            .outputDir("/home/lituizi/IdeaProjects/ahut_forum/user_mod/src/main/java")
                            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
                            .dateType(DateType.TIME_PACK);
                });
        fastAutoGenerator.packageConfig(builder -> {
                    //包配置
                    builder.parent("com.forum.user")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "/home/lituizi/IdeaProjects/ahut_forum/user_mod/src/main/resources/mapper"));
                }).strategyConfig(builder -> {
                    builder.addInclude("user").enableCapitalMode();
                    builder.entityBuilder()
                            .enableLombok()
                            .enableFileOverride()
                            .idType(IdType.AUTO)
                            .enableTableFieldAnnotation()
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("modify_time", FieldFill.INSERT_UPDATE)
                            );   //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                    builder.controllerBuilder()
                            .enableRestStyle()
                            .enableHyphenStyle();
                    builder.serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImp");
                    builder.mapperBuilder()
                            .enableFileOverride()
                            .superClass(BaseMapper.class)   //设置父类
                            .enableBaseResultMap()  //启用 BaseResultMap 生成
                            .enableBaseColumnList() //启用 BaseColumnList
                            .mapperAnnotation(Mapper.class)
                            .formatMapperFileName("%sMapper")
                            .formatXmlFileName("%sXml");
                }).templateEngine(new FreemarkerTemplateEngine())
                .execute();


    }
}
