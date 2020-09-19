package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class Search extends AppCompatActivity {
    private SearchView searchView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        WordsDB wordsDB=new WordsDB(this);
        listView = (ListView) findViewById(R.id.list);
        ArrayList<Map<String, String>> items = wordsDB.getAllWords();
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
                new String[]{ Words.Word.COLUMN_NAME_WORD},
                new int[]{R.id.textViewWord});

        listView.setAdapter(adapter);
        //为ListView启动过滤
        listView.setTextFilterEnabled(true);
        searchView = (SearchView) findViewById(R.id.sv);
        //设置SearchView自动缩小为图标
        searchView.setIconifiedByDefault(false);//设为true则搜索栏 缩小成俄日一个图标点击展开
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        //设置默认提示文字
        searchView.setQueryHint("输入您想查找的内容");
        //配置监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                //此处添加查询开始后的具体时间和方法
                Toast.makeText(Search.this,"you choose:" + query,Toast.LENGTH_LONG).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //如果newText长度不为0
                if (TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                }else{
                    listView.setFilterText(newText);
                    listView.dispatchDisplayHint(View.INVISIBLE);  //隐藏黑框
                    //  adapter.getFilter().filter(newText.toString());//替换成本句后消失黑框！！！
                }
                return true;

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView txtId=view.findViewById(R.id.textId);
                String id=txtId.getText().toString();
                TextView textViewWord=view.findViewById(R.id.textViewWord);
                String name=textViewWord.getText().toString();
                //Toast.makeText(context,pro,Toast.LENGTH_LONG).show();
                searchView.setQuery(name,true);
            }
        });
    }
}

