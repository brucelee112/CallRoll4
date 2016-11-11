package com.example.administrator.test03;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.administrator.test03.R.id.linearlayout12;

/**
 * Created by Administrator on 16-11-3.
 */
public class QueryActivity extends AppCompatActivity implements View.OnClickListener{

    private Button allBtn;
    private SQLiteDatabase db;
    private ScrollView sv;
    private String string="";
    private LinearLayout ll;
    private int i=1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        db=openOrCreateDatabase("student.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists stu(_id integer primary key autoincrement,sno  text  not null,sname text not null," +
                "class text not null,check1 text,check2 text,check3 text)");
        allBtn= (Button) findViewById(R.id.button7);
        sv= (ScrollView) findViewById(R.id.scrollView);
        ll= (LinearLayout) findViewById(linearlayout12);
        allBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        ll.removeAllViews();
        try{
            Cursor c=db.rawQuery("select * from stu",null);
            if(c!=null){
                while(c.moveToNext()){
                    string=c.getString(c.getColumnIndex("sno"))+"     "+c.getString(c.getColumnIndex("sname"))+"     "
                            +c.getString(c.getColumnIndex("class")) +"        "+c.getString(c.getColumnIndex("check1"));
                    TextView text=new TextView(ll.getContext());
                    text.setText(string);
                    text.setTextSize(15);
                    ll.addView(text);
                }
            }
        }catch(Exception e){
            Toast.makeText(QueryActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
