package com.draymond.thread._05design.single;

/**
 * 枚举方式
 */
public class SingleModel5 {
    private SingleModel5() {
    }

    public enum InstanceEnum {
        INSTANCE;
        private final SingleModel5 instance;

        InstanceEnum() {
            instance = new SingleModel5();
        }
    }

    public SingleModel5 getInstance() {
        return InstanceEnum.INSTANCE.instance;
    }
}
