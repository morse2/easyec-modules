package com.googlecode.easyec.bpmn2.test;

import ch.qos.logback.classic.BasicConfigurator;
import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * Created by 俊杰 on 2014/6/26.
 */
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class BaseBpmn2Test extends AbstractTransactionalJUnit4SpringContextTests {

    @BeforeClass
    public static void beforeRun() {
        BasicConfigurator.configureDefaultContext();
    }
}
