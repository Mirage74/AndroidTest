package com.example.testexpress;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        Intent intent = getIntent();
//        int i = intent.getIntExtra("int1", 1);
//        System.out.println("i : " + i);
//        String s = Integer.toString(i);
//        TextView textView = (TextView) findViewById(R.id.text_other_activity);
//        textView.setText(s);
        Intent intent = getIntent();
        List<countryDescribe> countryList = new ArrayList<>();
        countryList = (List<countryDescribe>)intent.getSerializableExtra("al");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            countryDescribe cD = new countryDescribe();
//            intent.getParcelableArrayListExtra("al", Class countryDescribe );
//        }
        //System.out.println("countryList : " + countryList);

    }
}