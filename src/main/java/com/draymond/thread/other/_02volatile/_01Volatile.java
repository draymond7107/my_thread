package com.draymond.thread.other._02volatile;

/**
 * 缓存不一致的问题
 * <p>
 * jvm的重排序，happen_before的规则
 * volitate关键字
 * 1:保证内存可见性
 * 2:代码执行顺序
 * 程序运行的时候，会将数据从"主存"中复制一份，放到"高速缓存"中
 */
public class _01Volatile {

    private volatile static int INIT_VALUE = 0; //是否有volatile，执行的结果不同
    private volatile static int MAX_VALUE = 5;

    public static void main(String[] args) {
        /*
            此线程只有read的操作，java做的优化，只从cache中拿数据，不更新主内存
         */
        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (localValue < MAX_VALUE) {
                if (localValue != INIT_VALUE) {
                    System.out.printf(" the value update to [%d] \n", INIT_VALUE);
                    localValue = INIT_VALUE;
                }
            }
        }, "reader").start();


        new Thread(() -> {
            int localValue = INIT_VALUE;
            while (INIT_VALUE < MAX_VALUE) {
                System.out.printf(" the value update to [%d] \n", ++localValue);
                INIT_VALUE = localValue;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "update").start();
    }

    /*
        i=i+1;
           1:从主内存把i获取
           2:将i缓存到cache中
           3:执行cpu指令，将i+1
           4:将结果刷到缓存中
           5:将结果刷到主存中(i有修改)
           i:main memory->cache->(+1)->cache->main memory
     */
}
