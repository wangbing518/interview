package com.myself.interview.jvm;

/**
 * @ClassName OSRDemo
 * @Description
 * @Author wb
 * @Date 2021/9/8 0008 下午 2:28
 */
public class OSRDemo {
    //加volatile 也可以 不知道原因
    volatile static long counter;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        startBusinessThread();
        startProblemThread();
        //等待线程执行
        Thread.sleep(500);
        System.gc();//其他线程暂停 线程安全点
        System.out.println("main end");
    }

    public static void startProblemThread(){
       new Thread(new MyRun()).start();
    }

    public static class MyRun implements  Runnable{

        @Override
        public void run() {
            System.out.println("Problem start");
            //其他线程已经暂停了 到达线程安全点了 但这个线程没有暂停
            // 还在计算中 换成long是告诉JVm这不是一个小的有限循环 这个时候回去检测线程安全点 让线程停止
            for (int i=0;i<100000000;i++){
                for (int j=0;j<1000;j++){
                    counter+=i%33;
                    counter+=i%333;
                }
                method();
            }
            System.out.println("Problem end");
        }
    }

    //线程安全点的检测会发生在方法调用前 是空方法 则编译器会优化掉 不去检测
    public static void method(){
//        Thread.currentThread().getId();
    }

    public static void startBusinessThread(){
        new Thread(()->{
            System.out.println("业务线程-1 start");
            for (;;){
                System.out.println("执行业务线程1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            System.out.println("业务线程-2 start");
            for (;;){
                System.out.println("执行业务线程2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
