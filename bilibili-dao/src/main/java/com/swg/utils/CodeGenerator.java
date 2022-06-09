package com.swg.utils;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : jundger
 * @date : 2021-05-27 23:49
 * Description : Mybatis-plus 代码生成器
 */
public class CodeGenerator {

    /**
     * 是否生成控制层代码：生成控制层 - true， 不生成 - false
     */
    private static final boolean IS_CREATE_CONTROLLER = false;

    /**
     * 是否以生成的文件覆盖原文件，容易丢失写好的代码，慎用
     */
    private static final boolean IS_FILE_OVERRIDE = false;

    /**
     * 数据库链接地址
     */
    private static final String URL = "jdbc:mysql://localhost:3306/bilibili?useUnicode=true&serverTimezone=UTC&useSSL=false&characterEncoding=utf8";

    /**
     * 驱动
     */
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    /**
     * 用户名
     */
    private static final String USERNAME = "root";

    /**
     * 密码
     */
    private static final String PASSWORD = "123456";

    /**
     * 作者
     */
    private static final String AUTHOR = "swg";

    /**
     * 父包名
     */
    private static final String PACKAGE_PARENT = "com.swg";

    /**
     * mapper包名
     */
    private static final String PACKAGE_MAPPER = "dao";

    /**
     * controller包名
     */
    private static final String PACKAGE_CONTROLLER = "controller";


    /**
     * 全局配置
     *
     * @return 返回 GlobalConfig
     */
    private static GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir") + "/bilibili-dao/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setOpen(false);
        // XML 二级缓存
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);

        // 执行时是否覆盖原文件，易将写好的代码覆盖，慎用
        if (IS_FILE_OVERRIDE) {
            gc.setFileOverride(true);
        }
        return gc;
    }

    /**
     * 数据源配置
     *
     * @return 返回 getDataSourceConfig
     */
    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        return dsc;
    }

    /**
     * 包名配置
     *
     * @return 返回 PackageConfig
     */
    private static PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        pc.setParent(PACKAGE_PARENT);
        pc.setMapper(PACKAGE_MAPPER);
        pc.setController(PACKAGE_CONTROLLER);
        return pc;
    }

    /**
     * 模板引擎配置
     *
     * @return 返回 TemplateConfig
     */
    private static TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        // 可配置不生成控制层代码
        if (!IS_CREATE_CONTROLLER) {
            templateConfig.setController(null);
        }
        return templateConfig;
    }

    /**
     * 策略配置
     *
     * @param tableNames 表名称
         * strategy.setInclude(tableNames) 传入需要 "生成" 的表名
     * strategy.setExclude(tableNames) 传入需要 "过滤" 的表名
     * strategy.setSuperEntityColumns("id");
     * @return 返回 getStrategyConfig
     */
    private static StrategyConfig getStrategyConfig(String... tableNames) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tableNames);
        // controller映射地址：驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix("t_");
        return strategy;
    }

    /**
     * 自定义配置
     *
     * @return 返回 InjectionConfig
     */
    private static InjectionConfig getInjectionConfig() {

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        final String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return System.getProperty("user.dir") + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);

        return cfg;
    }


    /**
     * 获取代码生成器
     *
     * @return 返回代码生成器
     */
    private static AutoGenerator getAutoGenerator(String... tableNames) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        mpg.setGlobalConfig(getGlobalConfig());

        // 数据源配置
        mpg.setDataSource(getDataSourceConfig());

        // 包配置
        mpg.setPackageInfo(getPackageConfig());

        // 自定义配置
        mpg.setCfg(getInjectionConfig());

        // 配置模板
        mpg.setTemplate(getTemplateConfig());

        // 策略配置
        mpg.setStrategy(getStrategyConfig(tableNames));

        // 设置模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        return mpg;
    }

    /**
     * 生产代码
     *
     * @param args args
     */
    public static void main(String[] args) {
        // 执行
        getAutoGenerator("t_user_info").execute();
    }

}