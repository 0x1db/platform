package com.platform.generator.core.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.platform.common.utils.CharPool;
import com.platform.generator.config.CodeConfigType;
import com.platform.generator.core.Generator;
import com.platform.generator.core.context.CodeContext;
import com.platform.generator.core.context.GeneratorType;
import com.platform.generator.core.utils.GeneratorFileUtils;
import com.platform.generator.core.utils.GeneratorStringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读取配置文件，生成代码基本实现类
 *
 * @author: wangyu
 * @date: 2016/10/26 16:30
 */
public abstract class AbstractGeneratorImpl implements Generator {

    /**
     * velocity上下文
     */
    protected VelocityContext velocityContext;

    /**
     * 模板存放文件夹
     */
    protected static final String VM_TARGET_PATH = "template";

    /**
     * 自动化创建业务代码
     *
     * @param context
     * @param configType
     */
    @Override
    public void defaultGenerator(CodeContext context, GeneratorType configType) {
        velocityContext = new VelocityContext();
        VelocityEngine velocityEngine = new VelocityEngine(initDefaultProperties());
        velocityEngine.init();
        write(context, velocityEngine);
    }

    /**
     * 插件读取模板文件要从jar包中读取
     *
     * @param context
     * @param configType
     */
    @Override
    public void pluginGenerator(CodeContext context, GeneratorType configType) {
        velocityContext = new VelocityContext();
        VelocityEngine velocityEngine = new VelocityEngine(initPluginProperties());
        velocityEngine.init();
        write(context, velocityEngine);
    }

    /**
     * 读取配置渲染模板，生成文件
     *
     * @param generatorContext
     * @param velocityEngine
     */
    protected void write(CodeContext generatorContext, VelocityEngine velocityEngine) {
        // 读取模板渲染内容，同时创建文件
        Map<String, String> params = initGeneratorParams(generatorContext);
        for (String templateName : params.keySet()) {
            Template template = velocityEngine.getTemplate(VM_TARGET_PATH + CharPool.FORWARD_SLASH + templateName, "UTF-8");
            initVelocityContext(velocityContext, generatorContext);
            try (StringWriter writer = new StringWriter()) {
                template.merge(velocityContext, writer);
                String content = writer.toString();
                GeneratorFileUtils.write(content, params.get(templateName));
            } catch (IOException e) {
                //LOG ERROR
            }
        }
    }

    /**
     * 初始化Velocity配置
     *
     * @return
     */
    protected Properties initDefaultProperties() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        return properties;
    }

    /**
     * 初始化Velocity配置
     *
     * @return
     */
    protected Properties initPluginProperties() {
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "jar");
        properties.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
        properties.setProperty("jar.resource.loader.path", "jar:" + getVmFilePath());
        properties.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        properties.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        properties.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        return properties;
    }

    /**
     * 初始化上下文
     *
     * @param velocityContext
     * @param cxt
     * @return
     */
    public void initVelocityContext(VelocityContext velocityContext, CodeContext cxt) {
        velocityContext.put(CodeConfigType.TABLE_NAME.getType(), cxt.getTableName());
        velocityContext.put(CodeConfigType.TABLE_REMARE.getType(), cxt.getAttribute(CodeConfigType.TABLE_REMARE));
        velocityContext.put(CodeConfigType.UP_CLASS_NAME.getType(), cxt.getUpClassName());
        velocityContext.put(CodeConfigType.LOW_CLASS_NAME.getType(), cxt.getLowClassName());
        velocityContext.put(CodeConfigType.PACKAGE_NAME.getType(), cxt.getPackageName());
        velocityContext.put(CodeConfigType.PRIMARY_KEY_TYPE.getDesc(), cxt.getPrimaryKeyType());
        velocityContext.put(CodeConfigType.PRIMARY_KEY.getDesc(), cxt.getPrimaryKey());
        velocityContext.put(CodeConfigType.PRIMARY_KEY_FIRST_SYMBOL_UPPERCASE.getType(), GeneratorStringUtils.firstUpper(cxt.getPrimaryKey()));
        velocityContext.put(CodeConfigType.NORMAL_PRIMARY_KEY.getDesc(), cxt.getAttribute(CodeConfigType.NORMAL_PRIMARY_KEY));
        velocityContext.put(CodeConfigType.CLASS_TITLE.getDesc(), assemblyAutoCreateClassTitle(cxt));
        velocityContext.put(CodeConfigType.DOMAIN.getDesc(), cxt.getAttribute(CodeConfigType.DOMAIN));
        velocityContext.put(CodeConfigType.COL_ALL_UPPERCASE_PRIMARY_KEY.getDesc(), cxt.getAttribute(CodeConfigType.COL_ALL_UPPERCASE_PRIMARY_KEY));
        velocityContext.put(CodeConfigType.COL_NORMAL_PRIMARY_KEY.getDesc(), cxt.getAttribute(CodeConfigType.COL_NORMAL_PRIMARY_KEY));
    }

    /**
     * 初始化生成文件的模板及其文件名称
     *
     * @param context
     * @return
     */
    protected Map<String, String> initGeneratorParams(CodeContext context) {
        GeneratorType configType = getPackageConfigType();
        // 获取模板
        String[] templates = StringUtils.split(configType.getTemplate(), CharPool.PIPE);
        // 基本的文件后缀及其名称
        String[] baseFileNames = StringUtils.split(configType.getFileName(), CharPool.PIPE);
        // 目标文件目录
        String[] targetDirs = StringUtils.split(configType.getTargetDir(), CharPool.PIPE);

        Properties properties = context.getProperties();
        String tableName = context.getTableName();

        Map<String, String> generatorParams = Maps.newHashMap();
        for (int i = 0; i < templates.length; i++) {
            String tempFileName = baseFileNames[i].replace("{domain}", (CharSequence) properties.get(CodeConfigType.DOMAIN.getType()));
            String fileName = GeneratorFileUtils.getPackageDirectory(targetDirs[i], properties)
                    + GeneratorStringUtils.firstUpperAndNoPrefix(tableName, properties)
                    + tempFileName;
            generatorParams.put(templates[i], fileName);
        }
        return generatorParams;
    }

    /**
     * 获取生成目录类型
     *
     * @return
     */
    protected abstract GeneratorType getPackageConfigType();


    /**
     * 获取目录
     *
     * @return
     */
    protected String getVmFilePath() {
        ClassLoader clToUse = ClassUtils.getDefaultClassLoader();
        try {
            Enumeration<URL> urls = clToUse.getResources(VM_TARGET_PATH);
            URL url = urls.nextElement();
            String filePath = url.getFile();
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("read velocity templates error.", e);
        }
    }

    /**
     * 生成field
     *
     * @param map
     * @param columnRemarkMap
     * @return
     */
    protected List<String> generateFields(Map<String, String> map, Map<String, String> columnRemarkMap) {
        Set<String> keySet = map.keySet();
        List<String> fields = Lists.newArrayList();
        for (String key : keySet) {
            StringBuilder sb = new StringBuilder();
            String value = map.get(key);
            sb.append("/** ").append(CharPool.NEW_LINE)
                    .append("\t * ").append(columnRemarkMap.get(key)).append(CharPool.NEW_LINE)
                    .append("\t */").append(CharPool.NEW_LINE)
                    .append("\tprivate ").append(value + " ").append(GeneratorStringUtils.format(key) + ";" + CharPool.NEW_LINE);
            fields.add(sb.toString());
        }
        return fields;
    }

    /**
     * 生成get/set
     *
     * @param map
     * @param cxt
     * @return
     */
    protected List<String> generateGetAndSetMethods(Map<String, String> map, CodeContext cxt) {
        Set<String> keySet = map.keySet();
        List<String> methods = Lists.newArrayList();
        for (String key : keySet) {

            StringBuilder getSb = new StringBuilder();
            StringBuilder setSb = new StringBuilder();
            String field = GeneratorStringUtils.format(key);
            String fieldType = map.get(key);
            //generate get method
            getSb.append("public ").append(fieldType + " ").append("get" + GeneratorStringUtils.firstUpperNoFormat(field) + "() {\n\t\t")
                    .append("return " + field + ";\n\t}\n");
            //generate set method
            setSb.append("public void set" + GeneratorStringUtils.firstUpperNoFormat(field) + "(" + fieldType + " " + field + ") {\n\t\t")
                    .append("this." + field + " = " + field + ";\n\t}\n");
            methods.add(getSb.toString());
            methods.add(setSb.toString());
        }
        return methods;
    }

    /**
     * 组装自动生成类title信息
     *
     * @param cxt
     * @return
     */
    protected String assemblyAutoCreateClassTitle(final CodeContext cxt) {
        Function<CodeContext, String> function = new Function<CodeContext, String>() {
            @Override
            public String apply(CodeContext input) {
                StringBuilder builder = new StringBuilder();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                String author = (String) cxt.getAttribute(CodeConfigType.CLASS_TITLE_AUTHOR);
                String remark = (String) cxt.getAttribute(CodeConfigType.TABLE_REMARE);

                builder.append("/** ").append(CharPool.NEW_LINE);
                builder.append(" * ").append("{classDescription}").append(CharPool.NEW_LINE);
                builder.append(" * ").append(CharPool.NEW_LINE);
                builder.append(" * @author ").append(author).append(CharPool.NEW_LINE);
                builder.append(" * @date ").append(dateFormat.format(new Date())).append(CharPool.NEW_LINE);
                builder.append(" */");

                String desc = input.getUpClassName();
                if (remark != null && remark.length() > 0) {
                    desc = StringUtils.replace(remark, "表", "");
                }
                return replaceClassTitleDescription(builder.toString(), desc);
            }
        };

        return function.apply(cxt);
    }

    /**
     * 生成类的说明
     *
     * @param defaultClassTitle
     * @param upClassName
     * @return
     */
    protected String replaceClassTitleDescription(String defaultClassTitle, String upClassName) {
        return StringUtils.replace(defaultClassTitle, "{classDescription}", upClassName + getDescription());
    }

    /**
     * 获取描述
     *
     * @return
     */
    protected abstract String getDescription();
}
