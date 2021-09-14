package com.example.spider.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.spider.MainActivity;
import com.example.spider.R;
import com.example.spider.model.Noti_Data;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Noti_Data noti_data=null;
    int Unique_Id;


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {


            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            JSONObject jsonObject=new JSONObject(remoteMessage.getData());

            noti_data=new Noti_Data(jsonObject);
            sendNotification(noti_data);

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        /*if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().toString());


            sendNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }
*/
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]
    /**
     * There are two scenarios when onNewToken is called:
     * 1) When a new token is generated on initial app startup
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/reinstalls the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
    private void scheduleJob() {
        // [START dispatch_job]
       /* OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
                .build();
        WorkManager.getInstance(this).beginWith(work).enqueue();*/
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any
     * server-side account maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
//     * @param messageBody FCM message body received.
     */
    private void sendNotification(Noti_Data noti_data) {

        Date date=new Date();

        Unique_Id=  (int)date.getTime();
        Intent intent = new Intent(this, MainActivity.class);
        if(noti_data!=null) {

            intent.putExtra("notification","notificaion");
            intent.putExtra("noti_data", noti_data);

        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Unique_Id /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.channel_id);
        Uri defaultSoundUri;
        if(noti_data.getType().equals("withdraw")){
//            defaultSoundUri = Uri.parse("android.resource://"+getPackageName()+"/raw/deposite");
            defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }else  if(noti_data.getType().equals("deposite")){
//            defaultSoundUri = Uri.parse("android.resource://"+getPackageName()+"/raw/withdraw");
            defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }else
        defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Uri defaultSoundUri = Uri.parse("android.resource://"+getPackageName()+"/raw/deposite");

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId+""+Unique_Id)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle(noti_data.getTitle())
                        .setContentText(noti_data.getSubTitle())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri, AudioManager.STREAM_NOTIFICATION)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = getSystemService(NotificationManager.class);

            NotificationChannel existingChannel = notificationManager.getNotificationChannel(channelId);

//it will delete existing channel if it exists
            if (existingChannel != null) {
               mNotificationManager.deleteNotificationChannel(channelId);
            }
            NotificationChannel channel = new NotificationChannel(channelId+""+Unique_Id,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);

            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(defaultSoundUri, att);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(channel);
            }
        }

        notificationManager.notify(Unique_Id /* ID of notification */, notificationBuilder.build());
    }
}