package com.googlecode.easyec.modules.bpmn2.utils;

import static com.googlecode.easyec.spirit.web.utils.SpringContextUtils.autowireBeanProperties;

/**
 * 抽象类。
 * <p>
 * 该类负责为子类注入Spring的Bean对象实例
 * </p>
 *
 * @author JunJie
 */
public abstract class ProcessBeanAutowire {

    protected ProcessBeanAutowire() {
        this(false);
    }

    protected ProcessBeanAutowire(boolean dependencyCheck) {
        autowireBeanProperties(this, dependencyCheck);
    }
}
