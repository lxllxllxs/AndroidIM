package com.yiyekeji.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Base64;

import com.yiyekeji.Config;
import com.yiyekeji.db.DbHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyaozhong on 15/12/2.
 */
public class DbUtils {
    private static SQLiteDatabase db;
    private static List<File> fileList = new ArrayList<File>();

    public static SQLiteDatabase getDb(Context context) {
        if(db == null) {
            DbHelper dbHelper = new DbHelper(context, Config.DB_NAME);
            db = dbHelper.getWritableDatabase();
        }

        return db;
    }


    public static void closeCursor(Cursor cursor) {
        if(cursor!=null && !cursor.isClosed()) {
            cursor.close();
        }
    }
    
    
    public static String encodeContent(String input) {
    	return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }
    
    
    public static String decodeContent(String input) {
    	return new String(Base64.decode(input, Base64.DEFAULT));
    }
    
    
    public static void exportData(String packageName) throws IOException {
    	File dataDir = Environment.getDataDirectory();
    	File dataFile = new File(dataDir + Config.DATA_DIR + packageName);
    	if(!dataFile.exists()) {
    		LogUtil.e(Config.LOG_FILTER, "导出数据失败，指定包名不存在");
    		return;
    	}
    	curFile(dataFile);
    	if(fileList!=null && fileList.size()>0) {
    		LogUtil.i(Config.LOG_FILTER, "开始导出数据");
			FileChannel src = null;
			FileChannel dst = null;
			for(File f : fileList) {
				String tmpFilePath = f.getAbsolutePath().toString().replace(dataFile.toString(), "");
				int index = tmpFilePath.lastIndexOf("/");
				String tmpDir = tmpFilePath.substring(0, index);
				File path = new File(Config.BASE_LOCAL_PATH + "/" + packageName + tmpDir);
				if(!path.exists()) {
					path.mkdirs();
				}
				File file = new File(Config.BASE_LOCAL_PATH  + "/" + packageName + tmpFilePath);
				src = new FileInputStream(f.getAbsoluteFile()).getChannel();
				dst = new FileOutputStream(file.getAbsoluteFile()).getChannel();
				dst.transferFrom(src, 0, src.size());
			}
			if(src != null) {
				src.close();
			}
			if(dst != null) {
				dst.close();
			}
			LogUtil.i(Config.LOG_FILTER, "导出数据成功");
    	}
    }
    
    private static void curFile(File file) {
		File[] tmpList = file.listFiles();
		for(File f : tmpList) {
			if(f.isDirectory()) {
				curFile(f);
			}else {
				fileList.add(f);
			}
		}
    }

}
