package demo.randompage.modules.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import demo.randompage.R;

/**
 * Created by smy on 18-3-15.
 */

public class NetTestActivity extends AppCompatActivity {
    public static final String THEME_DATA_NAME = "ThemeData.json";
    public static final String DEFAULT_FOLDER_NAME = "FlashLight";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testnet);

     String name = getGifFolderName("Test",false);
        Log.d("test","name = "+name);

    }

    public static String getGifFolderName(String name,boolean isTemp){
        if(name == null)return "";
        if (isTemp){
            return  name.replace(" ", "-") + ".temp";
        } else {
            return  name.replace(" ", "-") + ".gif";
        }
    }
    public static String getDataFromFile(Context context) {
        String TAG = ((Activity) context).getLocalClassName();
        String Path = getDefaultPath() + THEME_DATA_NAME; //判断文件是否存在
        BufferedReader reader = null;
        String laststr = "";
//        if (PermissionHelper.checkStoragePermissions((Activity) context)) {
//            Log.w(TAG, "we have no Permissions to Write/Read file;");// try catch will protected exception
//        }
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }
    public static String getDefaultPath() {
        String record_save_dir = getSdcardPath();

        if (TextUtils.isEmpty(record_save_dir)) {
            return record_save_dir;
        }

        if (!record_save_dir.endsWith(File.separator)) {
            record_save_dir = record_save_dir + File.separator;
        }
        record_save_dir = record_save_dir + DEFAULT_FOLDER_NAME + File.separator;
        return record_save_dir;
    }

    public static String getSdcardPath() {
        String path = "";
        if (isSdcardExit()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return path;
    }
    public static boolean isSdcardExit() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
