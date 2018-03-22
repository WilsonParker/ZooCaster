package com.graction.developer.zoocaster.Data;

import android.os.Handler;

import java.util.LinkedList;

/**
 * Created by JeongTaehyun
 */

/*
 * 서버 통신할 때 충돌 방지
 */

public class SyncObject {
    private static final SyncObject instance = new SyncObject();
    private static final int THREAD_SLEEP = 200;                        // Thread 대기 시간
    private boolean isRunning;                                          // 현재 Thread 실행 여부
    private LinkedList<SyncItem> list = new LinkedList<>();             // 저장된 Thread List
    private SyncItem nowSync;                                           // 현재 SyncItem
    private Thread nowThread;                                           // 현재 Thread

    public static SyncObject getInstance() {
        return instance;
    }

    public void addAction(OnSyncAction action, int id) throws InterruptedException {
        list.offer(new SyncItem(action, id));
    }

    /*
     * Thread 실행
     */
    public synchronized void start() throws InterruptedException {
        /*
         * 실행 중이 아니고
         * List 에 다른 Item 이 존재 할 경우
         */
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

    /*
     * 종료
     */
    public void end(int id) throws InterruptedException {
        // 현재 실행되는 SyncItem 과 id 가 같을 경우
        if (nowSync.getId() == id) {
            // 다음 SyncItem 실행
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
