package com.example.qizhang.teamone;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MorePageAcitvity extends AppCompatActivity {
    private static final String TAG = MorePageAcitvity.class.getSimpleName();
    private static final String SOURCE_STRING = "source_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_page_acitvity);

        String source = getIntent().getStringExtra(SOURCE_STRING);
        Log.d(TAG, "source: " + source);

        // show different content depends on the source
    }

    public static Intent newIntent(String source, Context packageContext) {
        Intent intent = new Intent(packageContext, MorePageAcitvity.class);
        intent.putExtra(SOURCE_STRING, source);
        return intent;
    }
}
