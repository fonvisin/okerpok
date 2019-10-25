package com.oker.game;

import android.content.Context;
import android.content.SharedPreferences;

public class DatabaseUtils {

    private static String b = "b";
    private static String n = "n";

    private SharedPreferences pReferences;

    public DatabaseUtils(Context context){
        String NAME = "b";
        pReferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void setData(String data){
        pReferences.edit().putString(DatabaseUtils.b, data).apply();
    }

    public String getData(){
        return pReferences.getString(b, "");
    }

    public void setData2(String data2){
        pReferences.edit().putString(DatabaseUtils.n, data2).apply();
    }

    public String getData2(){
        return pReferences.getString(n, "");
    }
}
