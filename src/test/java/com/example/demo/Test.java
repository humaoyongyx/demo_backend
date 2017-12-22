package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by issac.hu on 2017/12/21.
 */
public class Test {

    private static  ExecutorService pool= Executors.newFixedThreadPool(15);

    public static void main(String[] args) {

        for (int i=0;i<30;i++){
            System.out.println("i=="+i);
            Runnable r=  new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3000);
                        System.out.println(Thread.currentThread().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            };

            pool.execute(r);

        }

        System.out.println("end...");

    }
}
