package com.example.bx.myapplication;

import android.widget.EditText;

/**
 * Created by BX on 06.06.2018.
 */

public class Client extends Main {

    public String getClient(EditText client){
        return String.valueOf(client.getText());
    }



    public void setClient(String value){
        client_v = value;
    }

}
