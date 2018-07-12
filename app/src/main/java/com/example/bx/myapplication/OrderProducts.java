package com.example.bx.myapplication;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import static com.example.bx.myapplication.Main.products;

/**
 * Created by BX on 04.06.2018.
 */

public class OrderProducts extends HashMap<String,String> {

    static final String key = "key";
    static final String val = "val";

    public OrderProducts(String key, String val) {
        super();
        super.put(key, val);
    }

//    public Map<String, String> getProductAll(){
//        for (Map.Entry entry : products.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + " Value: "
//                    + entry.getValue());
//        }
//    }
//    public void setProduct(String code, String col){
//        String[] product_item = new String[2];
//        product_item[0] = code;
//        product_item[1] = col;
//        products.add(product_item);
//    }

//    public String[] getProduct(Integer id){
//        String[] product_item = new String[2];
//        for (int i = 0; i < products.size() ; i++) {
//            if (products.get(i) != "") {
//               product_item = (String[]) products.get(i);
//               return product_item;
//            }
//        }
//        return null;
//    }




}
