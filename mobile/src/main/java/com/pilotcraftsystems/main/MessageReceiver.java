package com.pilotcraftsystems.main;

import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by Wasim on 08-05-2015.
 */
public class MessageReceiver extends WearableListenerService {

    public static String SERVICE_CALLED_WEAR = "WearListClicked";


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);

        String event = messageEvent.getPath();

        Log.d("Listclicked", event);

        String [] message = event.split("--");

        if (message[0].equals(SERVICE_CALLED_WEAR)) {

            Log.d("Doge", "Doge is Doge");

        }
    }
}