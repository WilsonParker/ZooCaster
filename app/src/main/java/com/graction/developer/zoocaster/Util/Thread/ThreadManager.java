package com.graction.developer.zoocaster.Util.Thread;

/**
 * Created by JeongTaehyun on 2018-01-11.
 */

/*
 * Thread 설정
 * Thread 를 이용하여 return 하고자 할 때 사용
 */

public class ThreadManager<T> {
    private ThreadStart threadStart;
    private ThreadComplete threadComplete;
    private boolean isComplete;
    private long sleepTime = 200;
    private T data;
    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            threadStart.start();
        }
    });

    public ThreadManager(ThreadStart threadStart, ThreadComplete threadComplete) {
        this.threadStart = threadStart;
        this.threadComplete = threadComplete;
    }

    public T run() throws InterruptedException {
        isComplete = false;
        thread.start();
        while(!isComplete){
            Thread.sleep(sleepTime);
        }
        return data;
    }

    // save data after run()
    public void saveData(T date){
        this.data = date;
    }

    public void threadComplete(){
        isComplete = true;
        threadComplete.complete();
    }

    public interface ThreadStart {
        void start();
    }

    public interface ThreadComplete {
        void complete();
    }
}
