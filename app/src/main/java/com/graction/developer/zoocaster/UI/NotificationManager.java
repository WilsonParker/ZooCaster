package com.graction.developer.zoocaster.UI;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import java.io.Serializable;

/**
 * Created by JeongTaehyun on 2018-02-08.
 */

/*
 * Notification 설정
 */

public class NotificationManager {
    private static final NotificationManager instance = new NotificationManager();
    private android.app.NotificationManager notificationManager;

    public static NotificationManager getInstance() {
        return instance;
    }

    public void noti(Context context, NotificationItem item) {
        notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification =
                new NotificationCompat.Builder(context, item.getChannelId())
                        .setSmallIcon(item.getIcon())
                        .setContentTitle(item.getTitle())
                        .setContentText(item.getText())
                        .build();

        notificationManager.notify(1, notification);
    }

    public static class NotificationItem implements Serializable{
        public String channelId, title, text;
        private int icon;

        public NotificationItem(String channelId, String title, String text, int icon) {
            this.channelId = channelId;
            this.title = title;
            this.text = text;
            this.icon = icon;
        }

        public NotificationItem(String title, String text, int icon) {
            this.title = title;
            this.text = text;
            this.icon = icon;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }
    }
}
