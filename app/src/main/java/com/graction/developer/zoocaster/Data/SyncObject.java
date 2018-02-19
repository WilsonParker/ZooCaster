package com.graction.developer.zoocaster.Data;

import android.os.Handler;

import java.util.LinkedList;

/**
 * Created by Graction06 on 2018-01-19.
 */

public class SyncObject {
    private static final SyncObject instance = new SyncObject();
    private static final int THREAD_SLEEP = 200;
    private boolean isRunning;
    private LinkedList<SyncItem> list = new LinkedList<>();
    private SyncItem nowSync;
    private Thread nowThread;

    public static SyncObject getInstance() {
        return instance;
    }

    public void addAction(OnSyncAction action, int id) throws InterruptedException {
        list.offer(new SyncItem(action, id));
    }

    public void start() throws InterruptedException {
        if (!isRunning && list.size() > 0) {
            nowSync = list.poll();
            Handler handler = new Handler();
            nowThread = new Thread(() -> {
                handler.getLooper().prepare();
                handler.post(() -> nowSync.getOnSyncAction().onStart());
                try {
                    while (isRunning) {
                        Thread.sleep(THREAD_SLEEP);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            isRunning = true;
            nowThread.start();
        }
    }

    /*public void start() throws InterruptedException {
        nowSync = list.poll();
        if (nowSync != null) {
            Log.i(getClass().getName(), "#################### id : " + nowSync.getId());
            if (nowSync.isThread()) {
                Thread thread = nowSync.getThread();
                thread.start();
                while (thread.isAlive()) {
                    Thread.sleep(200);
                    Log.i(getClass().getName(), "#################### loading id : " + nowSync.getId() + " : " + thread.isAlive());
                    Log.i(getClass().getName(), "#################### loading getState : "  + (thread.getState() == Thread.State.TERMINATED));
                }
                thread.join();
            } else {
                Call call = nowSync.getCall();
                Log.i(getClass().getName(), "#################### loading id : " + nowSync.getId());
                Log.i(getClass().getName(), "#################### loading isExecuted : " + call.isExecuted());
                Log.i(getClass().getName(), "#################### loading isCanceled : " + call.isCanceled());
            }

            start();
        }
    }*/

    public void end(int id) throws InterruptedException {
        if (nowSync.getId() == id) {
            isRunning = false;
            start();
        }
    }

    public interface OnSyncAction {
        void onStart();
    }

    public class SyncItem {
        private OnSyncAction onSyncAction;
        private int id;

        public SyncItem(OnSyncAction onSyncAction, int id) {
            this.onSyncAction = onSyncAction;
            this.id = id;
        }

        public OnSyncAction getOnSyncAction() {
            return onSyncAction;
        }

        public void setOnSyncAction(OnSyncAction onSyncAction) {
            this.onSyncAction = onSyncAction;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

    }
}
