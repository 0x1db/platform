package com.platform.generator.core.context;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 包名，目标文件夹，生成文件后缀，模板配置枚举类
 *
 * @author: wangyu
 * @date: 2019/10/26 22:56
 */
public enum GeneratorType {

    /**
     * mapper
     */
    MAPPER("mapper",
            "/dao|/dao",
            "Mapper.xml|Mapper.java",
            "mapper.vm|dao.vm"),
    /**
     * oracle_mapper
     */
    ORACLE_MAPPER("oracle_mapper",
            "/dao|/dao",
            "Mapper.xml|Mapper.java",
            "oracle_mapper.vm|dao.vm"),

    /**
     * service
     */
    SERVICE("service",
            "/service|/service/impl",
            "Service.java|ServiceImpl.java",
            "service.vm|service_impl.vm"),

    /**
     * manager
     */
    MANAGER("manage",
            "/manage|/manage/impl|/manage|/manage/impl",
            "QueryManager.java|QueryManagerImpl.java|OperateManager.java|OperateManagerImpl.java",
            "manager_query.vm|manager_query_impl.vm|manager_operate.vm|manager_operate_impl.vm"),

    /**
     * controller
     */
    CONTROLLER("controller",
            "/web/controller",
            "Controller.java",
            "controller.vm"),

    /**
     * domain
     */
    DOMAIN("domain",
            "/domain",
            "{domain}.java",
            "domain.vm"),
    /**
     * vo
     */
    VO("vo",
            "/vo",
            "VO.java",
            "query_vo.vm"),

    /**
     * result
     */
    result("result",
            "/result|/result",
            "Result.java|Exception.java",
            "result.vm|exception.vm"),
    /**
     * jsp
     */
    JSP("jsp",
            "/jsp|/jsp|/jsp",
            "List.jsp|List.js|Detail.jsp",
            "jsp_list.vm|js_list.vm|jsp_detail.vm"),
    ;

    /**
     * 生成文件类型
     */
    private String type;

    /**
     * 生成目标文件夹
     */
    private String targetDir;

    /**
     * 生成文件后缀
     */
    private String fileName;

    /**
     * 生成文件模板
     */
    private String template;

    GeneratorType(String type, String targetDir, String fileName, String template) {
        this.type = type;
        this.targetDir = targetDir;
        this.fileName = fileName;
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTemplate() {
        return template;
    }

    /**
     * 通过类型获取对应的配置枚举
     *
     * @param type
     * @return
     */
    public static GeneratorType getByType(String type) {
        if (StringUtils.isBlank(type)){
            return null;
        }

        for (GeneratorType packageDirType : GeneratorType.values()) {
            if (StringUtils.equals(type, packageDirType.getType())) {
                return packageDirType;
            }
        }

        return null;
    }

    /**
     * 通过配置获取文件配置
     *
     * @return
     */
    public static String getDefaultConfigLayer() {
        List<String> configs = Lists.newArrayList();
        for (GeneratorType packageConfigDirType : GeneratorType.values()) {
            configs.add(packageConfigDirType.getType());
        }

        return StringUtils.join(configs,",");
    }
}
