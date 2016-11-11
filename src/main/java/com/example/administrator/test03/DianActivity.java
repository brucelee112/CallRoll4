package com.example.administrator.test03;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Administrator on 16-11-3.
 */
public class DianActivity extends AppCompatActivity implements View.OnClickListener  {
    private Button btn1;
    private Button btn2;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private SQLiteDatabase db;
    private TextView txt;
    private ImageView iv;
    private int count;
    String text="";
    RadioGroup rg;
    int id=1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dian);
        rb1= (RadioButton) findViewById(R.id.radioButton);
        rb2= (RadioButton) findViewById(R.id.radioButton2);
        rb3= (RadioButton) findViewById(R.id.radioButton3);
        rb4= (RadioButton) findViewById(R.id.radioButton4);
        btn1= (Button) findViewById(R.id.button5);
        btn2= (Button) findViewById(R.id.button6);
        rg= (RadioGroup) findViewById(R.id.group);
        txt= (TextView) findViewById(R.id.textView7);
        iv= (ImageView) findViewById(R.id.imageView3);
        db=openOrCreateDatabase("student.db",MODE_PRIVATE,null);
        btn2.setOnClickListener(this);
        btn1.setOnClickListener(this);
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = "已到";
                onChecked2();
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text="迟到";
                onChecked2();
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text="请假";
                onChecked2();
            }
        });
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text="旷课";
                onChecked2();
            }
        });
    }

    public void onClick(View v) {
        String sno="";
        try{
        Cursor c1=db.rawQuery("select * from stu", null);
        if(c1!=null)   {
           count= c1.getCount();
        }
            c1.close();
        }catch (Exception e){
            Toast.makeText(DianActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        Button bt= (Button) v;
        if(bt.getId()==btn1.getId()){
            if(id<=2){
                Toast.makeText(DianActivity.this, "第一名学生", Toast.LENGTH_SHORT).show();
                return;
            }
            rg.clearFocus();
            id-=2;
        }else{
            if(id>count){
                Toast.makeText(DianActivity.this, "最后名学生", Toast.LENGTH_SHORT).show();
                return;
            }
        }
            String[] ID=new String[]{id+""};
            try{
                Cursor c=db.query("stu",null,"_id=?",ID,null,null,null);
                if(c!=null)   {;
                    if(c.moveToNext()){
                        txt.setText(c.getString(c.getColumnIndex("sname")));
                        sno=c.getString(c.getColumnIndex("sno"));
                    }
                }
                String filename="sdcard/androiddata/stuimage/"+sno+".jpg";
                File file=new File(filename);
                if(file.exists()){
                    Bitmap bitmap= BitmapFactory.decodeFile(filename);
                    iv.setImageBitmap(bitmap);
                }else{
                    iv.setImageResource(R.drawable.portarit);
                }
                id++;
                c.close();
            }catch(Exception e){
                Toast.makeText(DianActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
            }
        }
      void onChecked2() {
        String name=txt.getText().toString();
        ContentValues values=new ContentValues();
        values.put("check1", text);
        try{
            db.update("stu", values,"sname like ?",new String[]{name});
            Toast.makeText(DianActivity.this, "成功", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(DianActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }

    }
}

