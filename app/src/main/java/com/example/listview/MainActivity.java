package com.example.listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final static String PRODUCT = "product";
    private final static String PRICE = "price";
    private final static String CONFIGURATION = "configuration";
    WordsDBHelper mDbHelper;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new WordsDBHelper(this);
        mDbHelper.getWritableDatabase();

    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }


    public void onclickinsert(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_instert, null, false);
        builder.setTitle("长度转换")
                .setView(viewDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         WordsDB wordsDB=new WordsDB(MainActivity.this);
                         EditText e1=viewDialog.findViewById(R.id.insert_name_edit);
                         EditText e2=viewDialog.findViewById(R.id.insert_meaning_edit);
                        EditText e3=viewDialog.findViewById(R.id.insert_sample_edit);
                         wordsDB.Insert(e1.getText().toString(),e2.getText().toString(),e3.getText().toString());

                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }

}