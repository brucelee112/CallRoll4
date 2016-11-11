package com.example.administrator.test03;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Administrator on 16-11-3.
 */
public class AddStudent extends AppCompatActivity implements View.OnClickListener {

    private Button btn;
    private EditText text1;
    private EditText text2;
    private Spinner sp;
    private SQLiteDatabase db;
    String string1;
    String string2;
    private String string3;
    private  ContentValues values;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db=openOrCreateDatabase("student.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists stu(_id integer primary key autoincrement,sno  text  not null,sname text not null," +
                "class text not null,check1 text,check2 text,check3 text)");
        btn= (Button) findViewById(R.id.button4);
        text1= (EditText) findViewById(R.id.editText);
        text2= (EditText) findViewById(R.id.editText2);
        sp= (Spinner) findViewById(R.id.spinner);
        values=new ContentValues();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] clas = AddStudent.this.getResources().getStringArray(R.array.spingar);
                AddStudent.this.string3=clas[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        string1=text1.getText().toString();
        string2=text2.getText().toString();
        ContentValues values=new ContentValues();
        values.put("sno",string1);
        values.put("sname",string2);
        values.put("class",string3);
        String string5="添加成功";
        try {
            db.insert("stu",null,values);
            Toast.makeText(AddStudent.this,string5, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            Toast.makeText(AddStudent.this,"添加失败", Toast.LENGTH_SHORT).show();
        }
        values.clear();
    }
}
