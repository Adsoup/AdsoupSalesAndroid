package io.adsoup.sales.mobile;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.robj.notificationhelperlibrary.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Action;

import static io.adsoup.sales.mobile.MainActivity.KEY_APP_PATH;

public class BouncerService extends NotificationListenerService {
    private static String CHANNEL_ID = "ADSOUP_CHANNEL_ID"; // This will be ignore if SDK_VERSION < 26
    private static String TAG = BouncerService.class.getSimpleName();
    private static String REPLY_KEYWORD = "reply";

    private ArrayList<NotificationWear> notificationWears = new ArrayList<NotificationWear>();

    @Override
    public void onCreate() {
        Log.i(TAG, "NotificationListener onCreate!!");
        super.onCreate();
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i(TAG, "NotificationListener connected!!");
    }

    private Set<String> loadAppList() {
        //Save selected list
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getStringSet("choosenAppList", new HashSet<String>());
    }

    private String getAppPath(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getString(KEY_APP_PATH, "");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        String packageName = sbn.getPackageName();

        String ticker = "Not Available";
        if (sbn.getNotification().tickerText != null)
            ticker = sbn.getNotification().tickerText.toString();

        Set<String> filterPkg = loadAppList();
        String appPath = getAppPath();

        if (filterPkg.contains(packageName) && !appPath.equals("")){
            try {

            Bundle extras = sbn.getNotification().extras;
            String title = extras.getString("android.title");
            String text = extras.getCharSequence("android.text").toString();

            // postToFirebase(appPath, packageName, text, title);

            Action a = NotificationUtils.getQuickReplyAction(sbn.getNotification(), packageName);
            a.sendReply(getApplicationContext(), "this is a reply from adsoup");

            Log.i(TAG, "title:"+title+" of "+packageName);
            Log.i(TAG, "text:"+text+" ticker:"+ticker);
            } catch (NullPointerException npe){
                Log.e(TAG, "Cannot retrieve data(NullException occurs");
            } catch (PendingIntent.CanceledException e) {
                Log.e(TAG, "Cannot reply to intent");
            }
        } else {
            Log.d(TAG, "Ignore notice from "+packageName);
        }
    }

    void postToFirebase(String appPath, String pkgName, String text, String who){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference myRef = db.getReference();

        Message msg = new Message(appPath, pkgName, who, text);

        String contactKey = pkgName.replace('.','_')+":"+who;
        DatabaseReference node = myRef.child(appPath).child("leads").child(contactKey).push();
        node.updateChildren(msg.toMap());

        Log.i(TAG, "Posted: "+who+"\t"+text+" from "+pkgName);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}
