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
import android.widget.ImageView;

import com.yiyekeji.im.R;

import java.io.File;
import java.io.InputStream;

public class ImageUtils {

	public static void byteToBitmap(byte[] bytes, ImageView imageView){
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		imageView.setImageBitmap(bitmap);
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
	
	


}
