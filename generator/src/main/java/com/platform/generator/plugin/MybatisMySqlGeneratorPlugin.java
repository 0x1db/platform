package com.platform.generator.plugin;

import com.platform.generator.starter.GeneratorStarter;
import com.platform.generator.starter.impl.MySqlPluginGeneratorStarterImpl;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MySqlGeneratorPlugin
 *
 * @author wangyu
 * @date 2019/10/26 23:23
 */
public class MybatisMySqlGeneratorPlugin extends AbstractMojo {

    /**
     * 打印MybatisGeneratorPlugin.java日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisMySqlGeneratorPlugin.class);

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        GeneratorStarter starter = new MySqlPluginGeneratorStarterImpl();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}
