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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

            postToFirebase(appPath, packageName, text, title);

            NotificationWear nw = extractWearNotification(sbn);
            replyNotification(nw);

            Log.i(TAG, "title:"+title+" of "+packageName);
            Log.i(TAG, "text:"+text+" ticker:"+ticker);
            } catch (NullPointerException npe){
                Log.e(TAG, "Cannot retrieve data(NullException occurs");
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

    /**
     * To extract WearNotification with RemoteInputs that we can use to respond later on
     * @param statusBarNotification
     * @return
     */
    private NotificationWear extractWearNotification(StatusBarNotification statusBarNotification) {
        //Should work for communicators such:"com.whatsapp", "com.facebook.orca", "com.google.android.talk", "jp.naver.line.android", "org.telegram.messenger"
        NotificationWear notificationWear = new NotificationWear();
        notificationWear.packageName = statusBarNotification.getPackageName();

        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender(statusBarNotification.getNotification());
        List<NotificationCompat.Action> actions = wearableExtender.getActions();
        for(NotificationCompat.Action act : actions) {
            if(act != null && act.getRemoteInputs() != null) {
                notificationWear.remoteInputs.addAll(Arrays.asList(act.getRemoteInputs()));
            }
        }
        List<Notification> pages = wearableExtender.getPages();
        notificationWear.pages.addAll(pages);

        notificationWear.bundle = statusBarNotification.getNotification().extras;

        //TODO find how to pass Tag with sending PendingIntent, might fix Hangout problem
        notificationWear.tag = statusBarNotification.getTag();

        notificationWear.pendingIntent = statusBarNotification.getNotification().contentIntent;
        if (notificationWear.pendingIntent == null){
            Log.e(TAG, "NO PENDING INTENT!!!!?!?!");
        }
        return notificationWear;
    }

    private static NotificationCompat.Action getQuickReplyAction(Notification n) {
        for(int i = 0; i < NotificationCompat.getActionCount(n); i++) {
            NotificationCompat.Action action = NotificationCompat.getAction(n, i);
            for(int x = 0; x < action.getRemoteInputs().length; x++) {
                RemoteInput remoteInput = action.getRemoteInputs()[x];
                if(remoteInput.getResultKey().toLowerCase().contains(REPLY_KEYWORD))
                    return action;
            }
        }
        return null;
    }

    private static NotificationCompat.Action getWearReplyAction(Notification n) {
        NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender(n);
        for (NotificationCompat.Action action : wearableExtender.getActions()) {
            for (int x = 0; x < action.getRemoteInputs().length; x++) {
                RemoteInput remoteInput = action.getRemoteInputs()[x];
                if (remoteInput.getResultKey().toLowerCase().contains(REPLY_KEYWORD))
                    return action;
            }
        }
        return null;
    }

    private void replyNotification(NotificationWear nw){
        if (nw.pendingIntent == null)
            return; //No pending intent

        RemoteInput[] remoteInputs = new RemoteInput[nw.remoteInputs.size()];

        Intent localIntent = new Intent();
        //localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle localBundle = nw.bundle;
        int i = 0;
        for(RemoteInput remoteIn : nw.remoteInputs){
            remoteInputs[i] = remoteIn;
            //This work, apart from Hangouts as probably they need additional parameter (notification_tag?)
            localBundle.putCharSequence(remoteInputs[i].getResultKey(), "Our answer: cool!?"+i);
            i++;
        }


        RemoteInput.addResultsToIntent(remoteInputs, localIntent, localBundle);
        try {
            Log.i(TAG, "Trying to send back to intent");
            nw.pendingIntent.send(this.getApplicationContext(), 0, localIntent);
            Log.i(TAG, "Sent!!!!!");
        } catch (PendingIntent.CanceledException e) {
            Log.e(TAG, "replyToLastNotification error: " + e.getLocalizedMessage());
        } catch (NullPointerException npe){
            Log.e(TAG, npe.getMessage(), npe.getCause());
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }
}
