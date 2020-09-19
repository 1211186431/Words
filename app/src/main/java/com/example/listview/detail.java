package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class detail extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        WordsDB wordsDB=new WordsDB(this);
        Intent intent=getIntent();
        String id=intent.getStringExtra("id");
        Words.WordDescription wordDescription=wordsDB.getSingleWord(id);
        TextView textView1 = findViewById(R.id.right_name2);
        textView1.setText(wordDescription.getWord());
        TextView textView2 = findViewById(R.id.right_meaning2);
        textView2.setText(wordDescription.getMeaning());
        TextView textView3 = findViewById(R.id.right_sample2);
        textView3.setText(wordDescription.getSample());

    }

}