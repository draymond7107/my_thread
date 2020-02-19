package com.draymond.thread._04pool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义线程池
 */
public class ThreadPool extends Thread {

    private final int size; //线程池大小
    private final static int DEFAULT_SIZE = 20; //线程数量默认大小
    private final static int MAX_SIZE = 50;     //线程数量最大值
    private final static LinkedList<Runnable> TASK_QUEUE = new LinkedList<>();  //存放要执行的线程
    private final static List<WorkerTask> WORKER_TASKS = new ArrayList<>();     //保存线程池中的线程
    private volatile boolean destory = false;   //线程池的状态
    //线程池中的名字问题
    private volatile int deq = 0;
    private final String THREAD_PREFIX = "SIMPLE_POOL-";
    //线程组统一命名
    private final ThreadGroup group = new ThreadGroup("pool-group");
    private int min;
    private int max;
    private int active;

    @Override
    public void run() {
        init();
        while (true) {
            int taskQueueSize = TASK_QUEUE.size();
            if (taskQueueSize > max && size < max) {
                int addSize = max - size;
                for (int i = 0; i < addSize; i++) {
                    createWorkTask();
                }
            } else if (taskQueueSize > active && taskQueueSize < max) {

            }
        }
    }

    public boolean isDestory() {
        return destory;
    }

    //-----  拒绝策略  -------
    private static int DEFAULT_TASK_QUEUE = 20; //默认接受执行体的最大值
    private static int queueSize;               //接受执行体的最大值
    public final DiscardPolicy discardPolicy;

    public static final DiscardPolicy DEFAULT_DISCARD_POLICY = () -> {
        throw new DiscardException("执行体超过限制");
    };

    public static class DiscardException extends RuntimeException {
        public DiscardException() {
        }

        public DiscardException(String message) {
            super(message);
        }
    }

    // 拒绝策略:抛出异常
    public interface DiscardPolicy {
        public void descard() throws DiscardException;
    }


    public ThreadPool() {
        this(4, 8, 12, DEFAULT_TASK_QUEUE, () -> {
            throw new DiscardException("runnable数量超过限制");
        });
    }

    public ThreadPool(int min, int active, int max, int queueSize, DiscardPolicy discardPolicy) {
        this.size = min;
        this.min = min;
        this.active = active;
        this.max = max;
        this.queueSize = queueSize;
        this.discardPolicy = discardPolicy;
    }

    /**
     * 构建线程池
     */
    private void init() {
        for (int i = 0; i < min; i++) {
            createWorkTask();
        }

    }

    private void createWorkTask() {
        WorkerTask workerTask = new WorkerTask(group, THREAD_PREFIX + deq++);
        workerTask.start();
        WORKER_TASKS.add(workerTask);
    }

    public void submit(Runnable runnable) {
        if (destory) throw new RuntimeException("线程池已经关闭");
        synchronized (TASK_QUEUE) {
            if (TASK_QUEUE.size() > queueSize) {
                try {
                    discardPolicy.descard();    //执行拒绝策略    //不加try会影响主线程的submit,造成主线程终止
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            TASK_QUEUE.addLast(runnable);
            TASK_QUEUE.notifyAll();
        }
    }


    public void shutDown() throws InterruptedException {
        while (!TASK_QUEUE.isEmpty()) {
            Thread.sleep(100);
        }
        int size = WORKER_TASKS.size();
        while (size > 0) {
            for (WorkerTask workerTask : WORKER_TASKS) {
                if (workerTask.taskState == TaskState.BLOCK) {
                    workerTask.close();
                    workerTask.interrupt();
                    size--;
                } else {
                    Thread.sleep(100);
                }
            }
        }
        destory = true;
        System.out.println("-------shunDown----");


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
                            System.out.println("主动中断线程" + Thread.currentThread().getName());
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


    public static void main(String[] args) {
        //  ThreadPool threadPool = new ThreadPool(10, 20, ThreadPool.DEFAULT_DISCARD_POLICY);
        ThreadPool threadPool = new ThreadPool();
        threadPool.start();

        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                threadPool.shutDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("-----------开始执行--------------");
        for (int i = 1; i < 500; i++) {
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
