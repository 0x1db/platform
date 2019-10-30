package com.platform.generator;

import com.platform.generator.starter.GeneratorStarter;
import com.platform.generator.starter.impl.MysqlDefaultGeneratorStarterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mysql自动化生成代码启动入口
 *
 * @author: wangyu
 * @date: 2019/10/26 16:24
 */
public class MysqlMybatisGeneratorStarter {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlMybatisGeneratorStarter.class);

    public static void main(String[] args) {
        GeneratorStarter starter = new MysqlDefaultGeneratorStarterImpl();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}
