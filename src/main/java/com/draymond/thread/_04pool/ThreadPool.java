package com.draymond.thread._04pool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义线程池
 */
public class ThreadPool {
    /*
     * 定义线程池大小
     * 初始化(提前创建线程)
     *
     */
    private final int size; //线程池大小

    private final static int DEFAULT_SIZE = 20; //默认大小
    private final static int MAX_SIZE = 50;     //最大值
    private volatile int deq = 0;       //自增序号
    private final String THREAD_PREFIX = "SIMPLE_POOL-";
    private final ThreadGroup group = new ThreadGroup("pool-group");
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();  //存放要执行的线程
    private final static List<WorkerTask> WORKER_TASKS = new ArrayList<>();     //保存线程池中的线程


    public ThreadPool() {
        this(DEFAULT_SIZE);
    }

    public ThreadPool(int size) {
        this.size = size;
        init();
    }

    /**
     * 构建线程池
     */
    private void init() {
        for (int i = 0; i < size; i++) {
            createWorkTask();
        }

    }

    private void createWorkTask() {
        WorkerTask workerTask = new WorkerTask(group, THREAD_PREFIX + deq++);
        workerTask.start();
        WORKER_TASKS.add(workerTask);
    }

    public void submit(Runnable runnable) {
        synchronized (TASK_QUEUE) {
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
            List<Runnable> runnableList = ThreadPool.TASK_QUEUE;
            int i = 0;
        }
    }

    //-------------------   Thread  -------------------
    /*
     * 单个线程状态
     */
    private enum TaskState {
        FREE, RUNNING, BLOCK, DEAD
    }

    /*
     * 单个工作线程
     */
    private static class WorkerTask extends Thread {
        private volatile TaskState taskState = TaskState.FREE;

        public WorkerTask() {
        }

        public WorkerTask(ThreadGroup group, String name) { //为什么要传一个ThreadGroup,后期要使用group
            super(group, name);
        }

        public TaskState getTaskState() {
            return this.taskState;
        }

        @Override
        public void run() { //执行任务体：执行完后不能挂掉
            OUTER:
            while (taskState != TaskState.DEAD) {

                Runnable runnable = null;
                synchronized (TASK_QUEUE) {
                    while (TASK_QUEUE.isEmpty()) {
                        try {
                            taskState = TaskState.BLOCK;    //为什么不放到正在执行run方法上面
                            TASK_QUEUE.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            break OUTER;    //避免被打断后再次进入while循环
                        }
                    }
                    runnable = TASK_QUEUE.removeFirst();    //获取runnable在synchronized中，先取出赋值，run()方法可以并行执行；
                                                            // 如果removeFirst不在synchronized中，则会发生多个线程同时执行removeFirst，造成获取空异常(有疑问分析synchronized的作用以及范围)
                }

                if (runnable != null) {
                    runnable.run();     //直接调用run()方法，而不是通过start()(原因：该Thread正在运行，直接替换run()的执行体，就可以替换执行的方法内容)
                    taskState = TaskState.FREE;

                }
            }
        }

        public void close() {
            this.taskState = TaskState.DEAD;
        }

    }


    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(20);

        System.out.println("-----------开始执行--------------");
        for (int i = 1; i < 50; i++) {
            final int k = i;
            threadPool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + "线程执行了" + k + "方法");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }

}
