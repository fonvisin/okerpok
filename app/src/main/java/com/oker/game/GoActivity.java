package com.oker.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class GoActivity extends AppCompatActivity {

    WebView view;
    DatabaseUtils databaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (databaseUtils.getData().isEmpty()) {
            if (databaseUtils.getData2().isEmpty() || databaseUtils.getData2().contains("game")) {
                setContentView(R.layout.activity_go);
                view = findViewById(R.id.thin);
                if (!databaseUtils.getData2().contains("game"))
                    new ProContact(this).init(view);
            }else {
               new BuzzUtils().getPolicyData(this, databaseUtils.getData2());
               finish();
            }
        }else {
            new BuzzUtils().getPolicyData(this, databaseUtils.getData());
            finish();
        }
    }

}
