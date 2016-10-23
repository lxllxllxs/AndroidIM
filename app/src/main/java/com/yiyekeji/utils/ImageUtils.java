package com.yiyekeji.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Base64;
import android.util.LruCache;

import com.yiyekeji.im.R;

import java.io.File;
import java.io.InputStream;

public class ImageUtils {
	private static LruCache<String, Bitmap> bitmapCache;
	
	public static void initBitmapCache() {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;		
		bitmapCache = new LruCache<String, Bitmap>(cacheSize) {
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
			}			
		};
	}
	
	
	public static LruCache<String, Bitmap> getBitmapLruCache() {
		if(bitmapCache == null) {
			initBitmapCache();
		}
		
		return bitmapCache;
	}
	


	public static Bitmap compressBitmap(String path, int reqWidth, int reqHeight) {
		int inSampleSize = 1;
		Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		int width = options.outWidth;
		int height = options.outHeight;
		if(width>reqWidth || height>reqHeight) {
			int heightRatio = Math.round((float) height / reqHeight);
			int widthRatio = Math.round((float) width / reqWidth); 
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio; 
		}
		options.inJustDecodeBounds = false;
		options.inSampleSize = inSampleSize;
		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		
		return bitmap;
	}
	
	
	public static String toBase64(Context context, Uri uri) throws Exception {
		String path = "";
		if(ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
			Cursor cursor = context.getContentResolver().query(uri, new String[] {ImageColumns.DATA}, null, null, null);
			if(cursor!=null && cursor.getCount()>0) {
				cursor.moveToFirst();
				path = cursor.getString(cursor.getColumnIndex(ImageColumns.DATA));
				cursor.close();
			}
		}else {
			path = uri.getPath();
		}
		File file = new File(path);
		InputStream inputFile =context.getResources().openRawResource(R.raw.pic);
    	byte[] buffer = new byte[inputFile.available()];
    	inputFile.read(buffer);
    	String result = Base64.encodeToString(buffer,Base64.DEFAULT);

		inputFile.close();
    	
    	return result;
	}
	
	

	public static int getBankLogoResId(Context c, String bankCode){
		String rcName = "ic_bank_" + bankCode;
		int rcId = c.getResources()
				.getIdentifier(rcName, "drawable",
						c.getPackageName());
		return rcId ;
	}
	public static int getBankLogo2ResId(Context c, String bankCode){
		String rcName = "ic_bank_" + bankCode+"2";
		int rcId = c.getResources()
				.getIdentifier(rcName, "drawable",
						c.getPackageName());
		return rcId ;
	}
	public static int getBankLogobigResId(Context c, String bankCode){
		String rcName = "ic_big_bank_" + bankCode;
		int rcId = c.getResources()
				.getIdentifier(rcName, "drawable",
						c.getPackageName());
		return rcId ;
	}
	public static int getBankcircularLogoResId(Context c, String bankCode){
		String rcName = "ic_r_bank_" + bankCode;
		int rcId = c.getResources()
				.getIdentifier(rcName, "drawable",
						c.getPackageName());
		return rcId ;
	}

}
