package io.adsoup.sales.mobile;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
class Message {

    private String uid = "";
    private String title = "";
    private String text = "";
    private String appPath = "";

    Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    Message(String appPath, String uid, String title, String text) {
        this.uid = uid;
        this.title = title;
        this.text = text;
        this.appPath = appPath;
    }

    @Exclude
    HashMap<String, Object> buildApp(String appPath){
        String[] arr = appPath.split("-");
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (arr.length > 0)
            map.put("_id", arr[0]);
        else
            map.put("_id", appPath);
        return map;
    }

    @Exclude
    HashMap<String, Object> buildAppUser(String appPath, String title){
        HashMap<String, Object> map = new HashMap<String, Object>();
        String[] arr = appPath.split("-");
        String strId = "internal-"+ (arr.length > 0 ? arr[1] : appPath);

        map.put("_id", strId);
        map.put("givenName", title);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = Calendar.getInstance().getTime();
        String strTime = formatter.format(date);

        map.put("signedUpAt", strTime);

        HashMap<String, Object> client = new HashMap<String, Object>();
        client.put("displayName", title);
        client.put("id", strId);
        client.put("lastSeen", strTime);
        client.put("linkedAt", strTime);
        client.put("platform", "AdsoupMobile-android");

        ArrayList clientList = new ArrayList();
        clientList.add(client);
        map.put("clients", clientList);

        return map;
    }

    @Exclude
    ArrayList buildMsgObj(String appPath, String msg, String pkgName) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        String[] arr = appPath.split("-");
        String strId = "internal-"+ (arr.length > 0 ? arr[1] : appPath);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = Calendar.getInstance().getTime();
        String strTime = formatter.format(date);


        HashMap<String, Object> message = new HashMap<String, Object>();
        String randomHex = String.format("%x",(int)(Math.random()*256*256*256));
        message.put("id", strId+"-"+randomHex);
        message.put("received", date.getTime()/1000);
        message.put("role", "appUser");

        HashMap<String, Object> source = new HashMap<String, Object>();
        source.put("type", pkgName);
        message.put("source", source);

        message.put("text", msg);
        message.put("type", "appUser");

        ArrayList msgList = new ArrayList();
        msgList.add(message);
        // map.put("messages", msgList);

        return msgList;
    }

    @Exclude
    Map<String, Object> toMap()  {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("app", buildApp(this.appPath));
        result.put("appUser", buildAppUser(this.appPath, this.title));
        result.put("messages", buildMsgObj(this.appPath, this.text, this.uid));
        result.put("trigger", "message:appUser");
        return result;
    }
}