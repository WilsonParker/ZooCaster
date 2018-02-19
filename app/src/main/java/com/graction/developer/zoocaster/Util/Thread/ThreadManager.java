package com.graction.developer.zoocaster.Util.Thread;

/**
 * Created by Graction06 on 2018-01-11.
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
//        return threadComplete.complete();
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
