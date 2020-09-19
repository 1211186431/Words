package com.example.listview;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity implements LeftFragment.OnFragmentInteractionListener{

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


    public void onclickinsert() {  //增加
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View viewDialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_instert, null, false);
        builder.setTitle("insert")
                .setView(viewDialog)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                         WordsDB wordsDB=new WordsDB(MainActivity.this);
                         EditText e1=viewDialog.findViewById(R.id.insert_name_edit);
                         EditText e2=viewDialog.findViewById(R.id.insert_meaning_edit);
                        EditText e3=viewDialog.findViewById(R.id.insert_sample_edit);
                         wordsDB.Insert(e1.getText().toString(),e2.getText().toString(),e3.getText().toString());
                         refreshWordsList(wordsDB);
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }


    public void onclickfind() {
      //  Intent intent=new Intent(MainActivity.this,Search.class);
    // v     startActivity(intent);
    }


    public void refreshWordsList(WordsDB wordsDB){
        ListView list = (ListView)findViewById(R.id.list);
        ArrayList<Map<String, String>> items = wordsDB.getAllWords();
        SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, items, R.layout.item,
                new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                new int[]{R.id.textId, R.id.textViewWord});
        list.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_find:
                onclickfind();
                break;
            case R.id.action_insert:
                onclickinsert();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onWordItemClick(String id) {
        Intent intent = new Intent(MainActivity.this,detail.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}