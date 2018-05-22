package com.vrumen.coreandroidapp.util.pusher;

import android.app.Activity;
import android.util.Log;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.ChannelEventListener;

/**
 * Created by Chandra on 11/16/17.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */

public class PusherHelper {

    private static PusherHelper helper;
    private Activity activity;
    private ChatListener listener;
    Pusher pusher;

    public static PusherHelper instance(Activity activity, ChatListener listener) {
        if (helper == null) helper = new PusherHelper(activity, listener);
        return helper;
    }

    public PusherHelper(Activity activity, ChatListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void config(String appCluster, String appKey, String channelName, String eventName) {
        PusherOptions options = new PusherOptions();
        options.setCluster(appCluster);

        pusher = new Pusher(appKey, options);
        pusher.connect();

        Channel channel = pusher.subscribe(channelName);

        channel.bind(eventName, new ChannelEventListener() {
            @Override
            public void onSubscriptionSucceeded(String s) {

            }

            @Override
            public void onEvent(final String channel_name, final String event_name, final String data) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onReceive(channel_name, event_name, data);
                    }
                });
            }
        });

    }

    public void connect(){
        pusher.connect();
    }

    public void disconnect(String channelName){
        pusher.unsubscribe(channelName);
        pusher.disconnect();
        Log.e("pusher disconnect", channelName);
    }
}
