package com.platform.generator;

import com.platform.generator.starter.GeneratorStarter;
import com.platform.generator.starter.impl.OracleDefaultGeneratorStarterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mysql自动化生成代码启动入口
 *
 * @author: wangyu
 * @date: 2019/10/26 16:32
 */
public class OracleMybatisGeneratorStarter {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleMybatisGeneratorStarter.class);

    public static void main(String[] args) {
        GeneratorStarter starter = new OracleDefaultGeneratorStarterImpl();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}