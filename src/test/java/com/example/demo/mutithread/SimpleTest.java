package com.example.demo.mutithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by issac.hu on 2017/12/27.
 */
public class SimpleTest {

    //core为维持的线程，maxpool为最大开启的线程，下面的queue默认是Integer.MAX_VALUE，也即当线程数不够是会被放入队列阻塞，也可以自己设置一个长度来控制队列长度
    static ExecutorService pool= new ThreadPoolExecutor(1, 1,
                    0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>());



    public static void main(String[] args) {

       for (int i=0;i<10;i++){
           int finalI = i;
           Runnable r1 = new Runnable() {
               @Override
               public void run() {
                   try {
                       System.out.println(finalI +"xxx"+Thread.currentThread());
                       Thread.sleep(1000 * 3);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           };
           System.out.println(i);
           try {

               pool.execute(r1);
           }catch (Exception e){
               e.printStackTrace();
               //pool.shutdown();
           }


       }


        try {
            //Thread.sleep(1000 * 3);
            //用于关闭启动线程，如果不调用该语句，jvm不会关闭,也即程序不会关闭，因为线程池一直被维持
            pool.shutdown();
           //用于等待子线程结束，再继续执行下面的代码。设置等待线程池中的线程的最大时间，也即调用这个方法会阻塞程序，直到线程结束或者超时时间到达
            pool.awaitTermination(20,TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("end...");

    }

}
