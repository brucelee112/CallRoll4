package com.example.administrator.test03;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button addBtn;
    private Button dianBtn;
    private Button qrBtn;
    private Button leBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn= (Button) findViewById(R.id.button);
        dianBtn= (Button) findViewById(R.id.button3);
        qrBtn= (Button) findViewById(R.id.button2);
        leBtn= (Button) findViewById(R.id.button4);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,AddStudent.class);
                MainActivity.this.startActivity(intent);
            }
        });

        dianBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,DianActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,QueryActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        leBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent();
                intent.setClass(MainActivity.this,LeadActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
