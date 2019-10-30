package com.platform.generator.plugin;

import com.platform.generator.starter.GeneratorStarter;
import com.platform.generator.starter.impl.OraclePluginGeneratorStarterImpl;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OracleGeneratorPlugin
 *
 * @author wangyu
 * @date 2019/10/26 23:23
 */
public class MybatisOracleGeneratorPlugin extends AbstractMojo {

    /**
     * sl4j
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisOracleGeneratorPlugin.class);

    /**
     * 执行生成Oracle代码
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        GeneratorStarter starter = new OraclePluginGeneratorStarterImpl();
        starter.start();
        LOGGER.info("auto plugin Generator code finish...");
    }
}
