package com.jipouille.usefullviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;

import com.jipouille.usefullviews.views.EditTextPlusPhoneNumber;
import com.jipouille.usefullviews.views.GroupRadioPlus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List a = new <String>ArrayList();
        a.add("Bar");
        a.add("Salle");
        a.add("Terrasse");
        a.add("Cuisine");
        a.add("Reception");
        a.add("Foo");
        a.add("Bar");
        GroupRadioPlus tel = (GroupRadioPlus) findViewById(R.id.telinput);
        tel.setDataList(a);
    }
}
