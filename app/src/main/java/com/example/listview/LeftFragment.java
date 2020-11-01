package com.example.listview;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeftFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeftFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SearchView searchView;
    private OnFragmentInteractionListener mListener;
    private ListView listView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LeftFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeftFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeftFragment newInstance(String param1, String param2) {
        LeftFragment fragment = new LeftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.v("tag","onCreat");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_left, container, false);
        final Context context = contentView.getContext();
        ListView list = (ListView) contentView.findViewById(R.id.list);
        this.registerForContextMenu(list);
        WordsDB wordsDB=new WordsDB(context);
            ArrayList<Map<String, String>> items = wordsDB.getAllWords();
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item,
                    new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                    new int[]{R.id.textId, R.id.textViewWord});
            list.setAdapter(adapter);
            find(contentView,context);
        return contentView;
    }
    @Override

    public void onResume() {

        // TODO Auto-generated method stub

        super.onResume();

    }
    @Override

    public void onCreateContextMenu(ContextMenu menu, View v,

                                    ContextMenu.ContextMenuInfo menuInfo) {

       // Log.v(TAG, "WordItemFragment::onCreateContextMenu()");
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.contextmenu_wordslistview, menu);
    }

    @Override

    public boolean onContextItemSelected(MenuItem item) {   //上下文菜单
        TextView textId = null;
        AdapterView.AdapterContextMenuInfo info = null;
        View itemView = null;

        switch (item.getItemId()) {
            case R.id.action_delete:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                itemView = info.targetView;
                //删除单词
                textId = (TextView) itemView.findViewById(R.id.textId);
                if (textId != null) {
                    onDeleteDialog(textId.getText().toString());
                }
                break;
            case R.id.action_update:
                //修改单词
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                itemView = info.targetView;
                textId = (TextView) itemView.findViewById(R.id.textId);

                if (textId != null) {
                      onUpdateDialog(textId.getText().toString());
                }
                break;
        }
        return true;
    }

    public void onDeleteDialog(final String strId) {  //删除
        new android.app.AlertDialog.Builder(getContext()).setTitle("删除单词").setMessage("是否真的删除单词?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //既可以使用Sql语句删除，也可以使用使用delete方法删除
                WordsDB wordsDB=new WordsDB(getContext());
                wordsDB.DeleteUseSql(strId);
                //单词已经删除，更新显示列表
                refreshWordsList(wordsDB);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }


    public void onUpdateDialog(String strId) {
        WordsDB wordsDB=new WordsDB(getContext());
        if (wordsDB != null && strId != null) {

            Words.WordDescription item = wordsDB.getSingleWord(strId);
            if (item != null) {
                UpdateDialog(strId, item.word, item.meaning, item.sample);
            }

        }

    }
    //修改对话框
    private void UpdateDialog(final String strId, final String strWord, final String strMeaning, final String strSample) {
       // final View tableLayout = getLayoutInflater().inflate(R.layout.activity_instert, null);
        final View tableLayout = LayoutInflater.from(getContext()).inflate(R.layout.activity_instert, null, false);
        ((EditText) tableLayout.findViewById(R.id.insert_name_edit)).setText(strWord);
        ((EditText) tableLayout.findViewById(R.id.insert_meaning_edit)).setText(strMeaning);
        ((EditText) tableLayout.findViewById(R.id.insert_sample_edit)).setText(strSample);
        new AlertDialog.Builder(getContext())
                .setTitle("修改单词")//标题
                .setView(tableLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String strWord = ((EditText) tableLayout.findViewById(R.id.insert_name_edit)).getText().toString();
                        String strNewMeaning = ((EditText) tableLayout.findViewById(R.id.insert_meaning_edit)).getText().toString();
                        String strNewSample = ((EditText) tableLayout.findViewById(R.id.insert_sample_edit)).getText().toString();
                        if(strWord.equals("")||strNewMeaning.equals("")||strNewSample.equals("")){
                            Toast.makeText(getContext(),"修改失败",Toast.LENGTH_LONG).show();
                        }
                        else{
                            //既可以使用Sql语句更新，也可以使用使用update方法更新
                            WordsDB wordsDB=new WordsDB(getContext());
                            wordsDB.UpdateUseSql(strId, strWord, strNewMeaning, strNewSample);
                            //单词已经更新，更新显示列表
                            refreshWordsList(wordsDB);
                            Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
                        }

                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()//创建对话框
                .show();//显示对话框


    }
    public void refreshWordsList(WordsDB wordsDB){  //刷新界面
        ListView list = (ListView)getActivity().findViewById(R.id.list);
        ArrayList<Map<String, String>> items = wordsDB.getAllWords();
        SimpleAdapter adapter = new SimpleAdapter(getContext(), items, R.layout.item,
                new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                new int[]{R.id.textId, R.id.textViewWord});
        list.setAdapter(adapter);
    }
    public void find(View contentView, final Context context){

        final WordsDB wordsDB=new WordsDB(getContext());
        listView = (ListView) contentView.findViewById(R.id.list);
        ArrayList<Map<String, String>> items = wordsDB.getAllWords();
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item,
                new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                new int[]{R.id.textId, R.id.textViewWord});

        listView.setAdapter(adapter);
        //为ListView启动过滤
        listView.setTextFilterEnabled(true);
        searchView = (SearchView) contentView.findViewById(R.id.sv);//设置SearchView自动缩小为图标
        searchView.setIconifiedByDefault(false);//设为true则搜索栏 缩小成俄日一个图标点击展开
        searchView.setSubmitButtonEnabled(true);//设置该SearchView显示搜索按钮
        searchView.setQueryHint("输入您想查找的内容");//设置默认提示文字
        //配置监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                WordsDB wordsDB2=new WordsDB(getContext());         //自己写的过滤
                ArrayList<Map<String, String>> items = wordsDB2.SearchUseSql(query);
                SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item,
                        new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                        new int[]{R.id.textId, R.id.textViewWord});
                listView.setAdapter(adapter);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //如果newText长度不为0
                if (TextUtils.isEmpty(newText)){
                    refreshWordsList(wordsDB);
                }else{
                    WordsDB wordsDB2=new WordsDB(getContext());         //自己写的过滤
                    ArrayList<Map<String, String>> items = wordsDB2.SearchUseSql(newText);
                    SimpleAdapter adapter = new SimpleAdapter(getActivity(), items, R.layout.item,
                            new String[]{Words.Word._ID, Words.Word.COLUMN_NAME_WORD},
                            new int[]{R.id.textId, R.id.textViewWord});
                    listView.setAdapter(adapter);

                 //   listView.setFilterText(newText);
                 //   listView.dispatchDisplayHint(View.INVISIBLE);  //隐藏黑框
                    //  adapter.getFilter().filter(newText.toString());//替换成本句后消失黑框！！！
                }
                return true;

            }
        });




        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView txtId=view.findViewById(R.id.textId);
                String id=txtId.getText().toString();             //右边fragment切换
                //Toast.makeText(context,pro,Toast.LENGTH_LONG).show();
                if(island()){    //直接横竖屏有问题 都没结果 可能是id问题
                    final RightFragment f1=new RightFragment();
                    fragmentManager = getActivity().getSupportFragmentManager();
//        通过begin开启事务
                    fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("id", id);
                    f1.setArguments(args);
                    fragmentTransaction.replace(R.id.right,f1);
//        将事务添加到返回栈中   是当用户按下Back后就直接退出活动了，而如上设置为null则返回到上一个碎片。
     //               fragmentTransaction.addToBackStack(null);

                    fragmentTransaction.commit();
                }
                else{
                    mListener.onWordItemClick(id);

                }

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取实现接口的activity
        //mListener = (mListener) getActivity();//或者myListener=(MainActivity) context;
        mListener=(MainActivity) context;
    }


    public boolean island(){
        boolean island=true;
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation; //获取屏幕方向
        if (ori == mConfiguration.ORIENTATION_LANDSCAPE) {
              island=true;
        } else if (ori == mConfiguration.ORIENTATION_PORTRAIT) {
            //竖屏
            island=false;
        }

        return island;
    }
    /**
     * Fragment所在的Activity必须实现该接口，通过该接口Fragment和Activity可以进行通信
     */
    public interface OnFragmentInteractionListener {
        public void onWordItemClick(String id);
    }

}