package com.example.community;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/3/3 16:47
 */
public class BlockingQueueTests {
    public static void main(String[] args) {
        BlockingQueue queue=new ArrayBlockingQueue(20);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();

    }
}

class  Producer implements  Runnable{

    private BlockingQueue<Integer> queue;
    public  Producer(BlockingQueue<Integer> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        try{
            for(int i=0;i<100;i++){
                Thread.sleep(20);
                queue.put(i);
                System.out.println(Thread.currentThread().getName()+"生产数目"+queue.size());
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

class  Consumer implements  Runnable{

    private BlockingQueue<Integer> queue;
    public  Consumer(BlockingQueue<Integer> queue){
        this.queue=queue;
    }

    @Override
    public void run() {
        try{
            while (true){
                Thread.sleep(new Random().nextInt(1000));
                queue.take();
                System.out.println(Thread.currentThread().getName()+" 消费数目"+queue.size());

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

