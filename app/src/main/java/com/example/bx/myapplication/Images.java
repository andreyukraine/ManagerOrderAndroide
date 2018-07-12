package com.example.bx.myapplication;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by BX on 05.06.2018.
 */

public class Images extends Product {

    public File getImages(String file_name) {
        final String FILENAME_SD = file_name;
        final String DIR_SD = "detta_img";

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Log.d("Log_tag", "SD-карта не доступна: " + Environment.getExternalStorageState());
            return null;
        }
        // получаем путь к SD
        File sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD);
        // формируем объект File, который содержит путь к файлу
        File sdFile = new File(sdPath, FILENAME_SD);

        return sdFile;
    }
}
