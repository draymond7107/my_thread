package com.draymond.thread._01base;

/**
 * id
 *
 * @Auther: ZhangSuchao
 * @Date: 2020/1/7 17:42
 */
public class _07ThreadId {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        thread.start();
        System.out.println("name==" + thread.getName());                // name==t1
        System.out.println("id==" + thread.getId());                    // id    和进程的Pid一样，每个线程也有对应的id即tid  打线程堆栈需要？？
        System.out.println("priority==" + thread.getPriority());        // 优先级，默认为：5  优先级高的并不是一定先执行
    }
}