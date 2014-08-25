package com.googlecode.easyec.modules.bpmn2.domain.enums;

/**
 * 流程的优先级的枚举类
 *
 * @author JunJie
 */
public enum ProcessPriority {

    P1(10),
    P2(20),
    P3(30),
    P4(40),
    P5(50);

    private int priority;

    ProcessPriority(int priority) {
        this.priority = priority;
    }

    /**
     * 返回优先级的数字表现形式
     *
     * @return 优先级
     */
    public int getPriority() {
        return priority;
    }
}
