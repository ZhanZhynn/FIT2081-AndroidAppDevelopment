package com.example.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (int i = 0; i < messages.length; i++) {
            SmsMessage message = messages[i];
            String messageBody = message.getMessageBody();
            String messageSender = message.getOriginatingAddress();
            // Do something with the message

            Intent myIntent = new Intent();
            myIntent.setAction("SMS_RECEIVED_ACTION");
            myIntent.putExtra("message", messageBody);
            context.sendBroadcast(myIntent);
        }
    }
}
