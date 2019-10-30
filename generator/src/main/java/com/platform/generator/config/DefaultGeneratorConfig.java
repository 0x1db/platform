package com.platform.generator.config;


import com.google.common.collect.Lists;
import com.platform.generator.core.context.GeneratorType;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * 基本的配置类实现
 *
 *  @author: wangyu
 *  @date: 2019/10/26 22:56
 */
public class DefaultGeneratorConfig implements GeneratorConfig {

    /**
     * 打印DefaultGeneratorConfig.java日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGeneratorConfig.class);

    private static Properties properties;

    /**
     * 获取配置
     *
     * @return
     */
    @Override
    public Properties getProperties() {
        synchronized (DefaultGeneratorConfig.class) {
            if (null == properties) {
                loadProperties();
            }
            return properties;
        }
    }

    /**
     * 初始化默认配置参数
     */
    @Override
    public void initConfigParams() {
        initPackage();
        initProjectName();
        initTablePrefix();      //表前缀
        initPrecision();        //浮点数进度
        initDomain();           //domain
        initLayers();           //生成代码层
        initLocation();         //生成代码位置
        initJavaSrc();
        initSchema();
    }

    protected void initPackage() {
        String value = (String) properties.get("generator.package");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.package", GENERATOR_PACKAGE);
    }

    protected void initProjectName() {
        String value = (String) properties.get("generator.project.name");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.project.name", GENERATOR_PROJECT_NAME);
    }

    protected void initTablePrefix() {
        String value = (String) properties.get("generator.tablePrefix");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.tablePrefix", GENERATOR_TABLEPREFIX);
    }

    protected void initPrecision() {
        String value = (String) properties.get("generator.precision");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.precision", GENERATOR_PRECISION);
    }

    protected void initDomain() {
        String value = (String) properties.get("generator.domain");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.domain", GENERATOR_DOMAIN);
    }


    protected void initLayers() {
        String value = (String) properties.get("generator.layers");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.layers", GeneratorType.getDefaultConfigLayer());
    }

    protected void initLocation() {
        String value = (String) properties.get("generator.location");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("generator.location", GENERATOR_LOCATION);
    }

    protected void initJavaSrc() {
        String value = (String) properties.get("java.src");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("java.src", JAVA_SRC);
    }

    protected void initSchema() {
        String value = (String) properties.get("oracle.schema");
        if (StringUtils.isNotBlank(value)) {
            return;
        }
        properties.setProperty("oracle.schema", ORACLE_SCHEMA);
    }

    /**
     * 加载配置文件
     */
    protected void loadProperties() {
        //获取用户当前目录
        List<String> dirAllFiles = listProjectDirAllFiles(System.getProperties().getProperty("user.dir"));
        if (CollectionUtils.isEmpty(dirAllFiles)) {
            throw new RuntimeException("读取工程目录下的文件为空.");
        }

        String configFilePath = null;
        for (String dirAllFile : dirAllFiles) {
            if (dirAllFile.endsWith(CONFIG_GENERATOR_PATH)) {
                configFilePath = dirAllFile;
                break;
            }
        }

        DefaultGeneratorConfig.properties = new Properties();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        try {
            LOGGER.info("加载配置文件" + configFilePath);
            inputStream = new FileInputStream(configFilePath);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
            DefaultGeneratorConfig.properties.load(bufferedReader);
        } catch (Exception e) {
            LOGGER.warn("加载配置文件出现异常，读取默认配置" + LOCAL_GENERATOR_PATH);
            try {
                DefaultGeneratorConfig.properties = PropertiesLoaderUtils.loadAllProperties(LOCAL_GENERATOR_PATH);
            } catch (IOException ex) {
                throw new RuntimeException("读取配置文件失败.", e);
            }
        } finally {
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 获取项目目录下的所有properties文件
     * @param projectPath
     * @return
     */
    private static List<String> listProjectDirAllFiles(String projectPath) {
        List<String> fileNames = Lists.newArrayList();
        Vector<String> vector = new Vector<String>();
        vector.add(projectPath);
        while (vector.size() > 0) {
            File[] files = new File(vector.get(0).toString()).listFiles();
            vector.remove(0);

            int len = files.length;
            for (int i = 0; i < len; i++) {
                String tmpDir = files[i].getAbsolutePath();
                if (files[i].isDirectory()) {
                    vector.add(tmpDir);
                } else if (tmpDir.endsWith(".properties") && !tmpDir.contains("class")) {
                    fileNames.add(tmpDir);
                }
            }
        }
        return fileNames;
    }
}
