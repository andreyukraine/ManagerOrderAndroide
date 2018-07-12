package com.example.bx.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.example.bx.myapplication.OrderProducts.key;


public class Main extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    static ArrayList<OrderProducts> products = new ArrayList<>();
    static String client_v = "";
    Connection connection = null;
    static DBHelper dbHelper = null;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText client_edit = findViewById(R.id.client);
        client_edit.setText(client_v);
        //client_edit.setFocusableInTouchMode(false);
        dbHelper = new DBHelper(this);

        getGroduct();

        client_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client_edit.setFocusableInTouchMode(true);
            }
        });

        final EditText editText = (EditText) findViewById(R.id.search);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setFocusableInTouchMode(true);
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    Intent intent_Z = new Intent(Main.this, Product.class);
                    intent_Z.putExtra("code", v.getText().toString());
                    intent_Z.putExtra("col", "0");
                    startActivity(intent_Z);
                    finish();
                    handled = true;
                }
                return handled;
            }
        });

    }

    // Запускаемм сканер штрих кода:
    public void scanBar(View v) {
        try {
            // Запускаем переход на com.google.zxing.client.android.SCAN с помощью intent:
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            EditText client_edit = findViewById(R.id.client);
            client_v = String.valueOf(client_edit.getText());
            startActivityForResult(intent, 0);

        } catch (ActivityNotFoundException anfe) {
            // Предлагаем загрузить с Play Market:
            showDialog(Main.this, "Сканнер не найден", "Установить сканер с Play Market?", "Да", "Нет").show();
        }
    }

    public void updateSQLite(View view) throws SQLException {

        //подключаем sql и обновляем файл sqlLite c остатками
        Database SQL = new Database();
        connection = SQL.connectionSQL();


        SQLiteDatabase db = dbHelper.getWritableDatabase();


        System.out.println("Database created successfully...");
        String query2 = "SELECT * FROM `gps_entries`";
        java.sql.Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query2);
        while (rs.next()) {
            // Создайте новую строку со значениями для вставки.
            ContentValues newValues = new ContentValues();
            // Задайте значения для каждой строки.
            newValues.put(dbHelper.KEY_NAME, rs.getString("gps_entry_id"));
            // Вставьте строку в вашу базу данных.
            db.insert(dbHelper.TABLE_SKU, null, newValues);
            System.out.println("ID : " + rs.getString("gps_entry_id"));
        }
        connection.close();




    }

    @SuppressLint("NewApi")
    public void getGroduct(){



        TableLayout product_table = findViewById(R.id.table_product);

        View.OnClickListener click = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(v.getId());
                if (products.size() > 0) {
                    products.remove(v.getId());
                    Main.this.recreate();
                }
            }
        };


        if (products.size() > 0) {
            for (int i = 0; i < products.size(); i++) {

                TableRow row = new TableRow(this);
                row.setId(i);
                row.setPadding(5,5,5,5);
                TextView text_code = new TextView(this);
                TextView text_col = new TextView(this);

                row.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent_my = new Intent(Main.this, Product.class);
                        intent_my.putExtra("type", "1");


                        //OrderProducts products = new OrderProducts();
//                        String[] result = products.getProduct(v.getId());
//                        for (int j = 0; j < result.length; j++) {
//                            if (j == 0) {
//                                intent_my.putExtra("code", result[j]);
//                            }else{
//                                intent_my.putExtra("col", result[j]);
//                            }
//                        }

                        startActivity(intent_my);
                        finish();
                    }
                });


                if (products.size() != 0) {

                    row.setGravity(Gravity.LEFT);
                    text_code.setGravity(Gravity.LEFT);
                    text_col.setGravity(Gravity.LEFT);

                    for (int j = 0; j < products.size(); j++) {

                        for (int k = 0; k < products.get(j).size(); k++) {

                            for (String key:products.get(k).keySet()){

                                System.out.println("Key:" + key +" Value:" + products.get(k).get(key));

                                text_code.setText(key);
                                text_col.setText(products.get(k).get(key));

                                row.addView(text_code);
                                row.addView(text_col);

                            }
                        }

                    }

                    ImageButton btn = new ImageButton(this);
                    //btn.setText("x");


                    btn.setBackground(ContextCompat.getDrawable(this, android.R.drawable.ic_delete ));

                    btn.setId(i);
                    btn.setOnClickListener(click);
                    row.setGravity(Gravity.RIGHT);
                    row.addView(btn);

                    product_table.addView(row);
                }

            }

        }
    }


    // alert dialog для перехода к загрузке приложения сканера:
    private static AlertDialog showDialog(final Activity act, CharSequence title,
                                          CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {

                // Ссылка поискового запроса для загрузки приложения:
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    // Обрабатываем результат, полученный от приложения сканера:
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                Intent intent_my = new Intent(Main.this, Product.class);
                String col = "0";
                intent_my.putExtra("code", contents);
                intent_my.putExtra("col", col);
                startActivity(intent_my);
                finish();

                //Toast toast = Toast.makeText(this, "Содержание: " + contents + " Формат: " + format, Toast.LENGTH_LONG);
                //toast.show();

            }
        }
    }
}
