package com.example.chhattlal.chatapp.ui;

import android.os.Bundle;
import com.example.chhattlal.chatapp.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
    }
}
