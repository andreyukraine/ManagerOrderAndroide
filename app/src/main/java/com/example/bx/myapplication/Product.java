package com.example.bx.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.bx.myapplication.Main.dbHelper;
import static com.example.bx.myapplication.Main.products;


/**
 * Created by BX on 01.06.2018.
 */

public class Product extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        




        setTitle("Наличие");
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final TextView col = findViewById(R.id.col);
        final TextView code = findViewById(R.id.code);


        TextView title = findViewById(R.id.store);
        Button button_plus = (Button) findViewById(R.id.button1);
        Button button_minus = (Button) findViewById(R.id.button2);
        Button button_push = (Button) findViewById(R.id.push);

        String type = getIntent().getStringExtra("type");
        String code_sku = getIntent().getStringExtra("code");
        String col_sku = getIntent().getStringExtra("col");

        title.setText("Тут выводим остатки товара");

        ImageView imageV = findViewById(R.id.image);
        Images image = new Images();

        File b = image.getImages(code_sku + ".jpg");
        imageV.setImageURI(Uri.fromFile(new File(b.toString())));

        code.setText(code_sku);
        col.setText(col_sku);

        View.OnClickListener oMyButton_plus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stoka = String.valueOf(col.getText());
                int result = Integer.parseInt(stoka)+ 1;
                col.setText(Integer.toString(result));
            }

        };

        View.OnClickListener oMyButton_minus = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stoka = String.valueOf(col.getText());
                int result = Integer.parseInt(stoka)- 1;
                col.setText(Integer.toString(result));
            }
        };


        View.OnClickListener oMyButton_push = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent_main = new Intent(Product.this, Main.class);
                //OrderProducts products = new OrderProducts();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor result = db.rawQuery("select * from sku", null);
                //String title = result.getString(result.getColumnIndex("name"));
                //System.out.println("sqlite reult " + title);
                db.close();
                String key = code.getText().toString();
                String val = col.getText().toString();
                products.add(new OrderProducts(key,val));

                startActivity(intent_main);
                finish();
            }
        };

        button_plus.setOnClickListener(oMyButton_plus);
        button_minus.setOnClickListener(oMyButton_minus);
        button_push.setOnClickListener(oMyButton_push);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
        //code = ;
        //code.set(intent.getStringExtra("You Code"+ code));



    }

}
