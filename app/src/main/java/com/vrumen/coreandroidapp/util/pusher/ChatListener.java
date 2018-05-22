package com.vrumen.coreandroidapp.util.pusher;

/**
 * Created by deny on bandung.
 */

public interface ChatListener {
    void onReceive(String channel, String event, String data);
}
