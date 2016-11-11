package com.example.administrator.test03;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
/**
 * Created by Administrator on 16-11-9.
 */
public class QueryListviewActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ListView listView;
    private ArrayAdapter<String> arr_adapter;
    private ScrollView sv;
    private Button btn;
    private LinearLayout ll1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_listview);
        listView= (ListView) findViewById(R.id.listView);
        ll1= (LinearLayout) findViewById(R.id.linearlayout3);
        db=openOrCreateDatabase("student.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists stu(_id integer primary key autoincrement,sno  text  not null,sname text not null," +
                "class text not null,check1 text,check2 text,check3 text)");
        sv= (ScrollView) findViewById(R.id.scrollView1);
        btn= (Button) findViewById(R.id.button7);
        Log.i("loga",findViewById(R.id.linearlayout3)+"");
       // ll= (LinearLayout) findViewById(linearlayout13);

        int i=0;
        String[] arr_data=new String[98];
        String string="";
        try {
            Cursor c = db.rawQuery("select * from stu", null);
            if (c != null) {
                while (c.moveToNext()) {
                    string= c.getString(c.getColumnIndex("sno"))+"    "+c.getString(c.getColumnIndex("sname"))+
                           "    "+ c.getString(c.getColumnIndex("class"));
                    arr_data[i++]=string;
                }
                arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr_data);
                //3、视图(ListView)加载适配器
                listView.setAdapter(arr_adapter);
            }
        }catch (Exception e){
            Log.i("logcat",e.toString());
        }
        //listView.setOnItemSelectedListener();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Cursor c=db.query("stu", null, "_id=?", ID, null, null, null);
//                if(c!=null)   {;
//                    if(c.moveToNext()){
//                        txt.setText(c.getString(c.getColumnIndex("sname")));
//                        sno=c.getString(c.getColumnIndex("sno"));
//                    }
//                }

                AlertDialog.Builder builder=new AlertDialog.Builder(QueryListviewActivity.this);
                builder.setTitle("自定义对话框");
               // builder.setIcon(R.drawable.ic_launcher);
                LayoutInflater inflater=LayoutInflater.from(QueryListviewActivity.this);
                View dialog_layout=inflater.inflate(R.layout.dialog, null);
                builder.setView(dialog_layout);
                try{ TextView text=new TextView(ll1.getContext());
//                text.setText("jjajjaja");
//                ll.addView(text);
                }catch (Exception e){
                    Log.i("logca",e.toString()+ll1);
                }
                AlertDialog dialog=builder.create();
                dialog.show();
            }
        });
    }


}