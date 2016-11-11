package com.example.administrator.test03;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Administrator on 16-11-5.
 */
public class LeadActivity extends AppCompatActivity implements  View.OnClickListener,Runnable{

    private SQLiteDatabase db;
    private FrameLayout fl;
    private ProgressBar pb;
    private boolean stop=true;
    private Button bt1;
    private Button bt2;
    private  int no=0;
    private int pro=1;
    private int all=10000;
    Handler  mHandler;
    Sheet sheet;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        db=openOrCreateDatabase("student.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists stu(_id integer primary key autoincrement,sno  text  not null,sname text not null," +
                "class text not null,check1 text,check2 text,check3 text)");
        fl= (FrameLayout) findViewById(R.id.framelayout12);
        pb=new ProgressBar(fl.getContext(), null, android.R.attr.progressBarStyleHorizontal);
        pb.setMax(100);
        //创建一个Handler
         mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //处理消息
                switch (msg.what) {
                    case 12:
                        //设置滚动条和text的值
                        pb.setProgress(pro);
                        break;
                    default:
                        break;
                }
            }
        };
        bt1= (Button) findViewById(R.id.button8);
        bt2= (Button) findViewById(R.id.button9);
        fl.addView(pb);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(no==0){
                    return;
                }else{
                    Intent intent =new Intent();
                    intent.setClass(LeadActivity.this,MainActivity.class);
                    LeadActivity.this.startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        SQLiteDatabase db=openOrCreateDatabase("student.db", MODE_PRIVATE, null);
        try{
            InputStream is=new FileInputStream("sdcard/androiddata/dianming2.xls");
            Workbook book= Workbook.getWorkbook(new File("sdcard/androiddata/dianming2.xls"));
            book.getNumberOfSheets();
            //获得第一个工作表对象
            sheet=book.getSheet(0);
        }catch(Exception e){

        }
        new Thread(this).start();
      // readExcel();
        no=1;
    }
    public void readExcel(){
        ContentValues values=new ContentValues();
        try{
            SQLiteDatabase db=openOrCreateDatabase("student.db",MODE_PRIVATE,null);
            InputStream is=new FileInputStream("sdcard/androiddata/dianming2.xls");
            Workbook book= Workbook.getWorkbook(new File("sdcard/androiddata/dianming2.xls"));
            book.getNumberOfSheets();
            //获得第一个工作表对象
            Sheet sheet=book.getSheet(0);
            int rows=sheet.getRows()-3;
            all=rows;
            for(int i=6;i<rows;i++){
                //getCell(Col,Row)获得单元格
                values.put("sno", sheet.getCell(1, i).getContents().toString());
                values.put("sname", sheet.getCell(2, i).getContents().toString());
                values.put("class", sheet.getCell(3, i).getContents().toString());
                db.insert("stu", null, values);
                pro=i;
                Thread.sleep(20);
                values.clear();
            }
            stop=false;
         //   Looper.prepare();
            Toast.makeText(LeadActivity.this,"添加成功", Toast.LENGTH_SHORT).show();
         //   Looper.loop();
        }catch (Exception e){
            Toast.makeText(LeadActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void run() {
        ContentValues values=new ContentValues();
        int rows=sheet.getRows()-3;
        all=rows;
        int max = pb.getMax();
        try {
            for(int i=6;i<rows;i++){
                //getCell(Col,Row)获得单元格
                values.put("sno",sheet.getCell(1, i).getContents());
                values.put("sname", sheet.getCell(2, i).getContents().toString());
                values.put("class", sheet.getCell(3, i).getContents().toString());
                db.insert("stu", null, values);
                pro=i;
                Message msg = new Message();
                msg.what =12 ;
                mHandler.sendMessage(msg);
                Thread.sleep(20);
                values.clear();
            }
            Looper.prepare();
            Toast.makeText(LeadActivity.this,"添加成功", Toast.LENGTH_SHORT).show();
            Looper.loop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

