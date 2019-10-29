package com.oker.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class GoActivity extends AppCompatActivity {

    WebView view;
    DatabaseUtils databaseUtils;
    TextView start_textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseUtils = new DatabaseUtils(this);
        if (databaseUtils.getData().isEmpty()) {
            if (databaseUtils.getData2().isEmpty() || databaseUtils.getData2().contains("game")) {
                setContentView(R.layout.activity_go);
                view = findViewById(R.id.thin);
                start_textView = findViewById(R.id.start_textview);
                if (!databaseUtils.getData2().contains("game"))
                    new ProContact(this).init(view, start_textView);
                else {
                    start_textView.setText(R.string.start);
                    start_textView.setOnClickListener(view -> {
                        Intent intent = new Intent(this, StartActivity.class);
                        startActivity(intent);
                        finish();
                    });
                }
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
