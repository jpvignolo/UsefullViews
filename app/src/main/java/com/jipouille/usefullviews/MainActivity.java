package com.jipouille.usefullviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;

import com.jipouille.usefullviews.views.EditTextPlusPhoneNumber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditTextPlusPhoneNumber tel = (EditTextPlusPhoneNumber) findViewById(R.id.telinput);
        PhoneNumberFormattingTextWatcher watcher = new PhoneNumberFormattingTextWatcher();
        tel.addTextChangedListener(watcher);
    }
}
