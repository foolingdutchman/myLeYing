package com.secondproject.com.secondproject.utils;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

public class BitmapCacheHelper {
	private static final String TAG = "BitmapCacheHelper";

	Map<String, SoftReference<Bitmap>> softCaches = new HashMap<String, SoftReference<Bitmap>>();

	private MyLruCache lruCache = null;

	public BitmapCacheHelper() {

		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		if (lruCache == null) {
			lruCache = new MyLruCache(maxMemory / 8);
		}

	}

	public void getBitmap(String url, ImageView imageView) {
		Bitmap bitmap = null;

		if (lruCache != null) {
			Log.i("lruCache", "???");
			Log.d("lruCache", "============>" + url);
			bitmap = lruCache.get(url);

			if (bitmap != null) {

				imageView.setImageBitmap(bitmap);
			} else {
				Log.i("SoftReference", "???");

				SoftReference<Bitmap> soft = softCaches.get(url);
				if (soft != null) {

					bitmap = soft.get();
				}
				if (bitmap != null) {

					soft.clear();
					lruCache.put(url, bitmap);
					imageView.setImageBitmap(bitmap);
				} else {
					Log.i("SDCardHelper", "???");

					String fileName = SDCardHelper
							.getSDCardPublicDir(Environment.DIRECTORY_DOWNLOADS)
							+ File.separator + MD5Util.getMD5(url);
					File file = new File(fileName);
					if (file.exists()) {

						byte[] data = SDCardHelper.loadFileFromSDCard(fileName);
						bitmap = BitmapFactory.decodeByteArray(data, 0,
								data.length);
						lruCache.put(url, bitmap);
						imageView.setImageBitmap(bitmap);

					} else {
						Log.i("BitmapAsyncTask", "???");

						new BitmapAsyncTask(imageView).execute(url);
					}

				}

			}

		}
	}

	class BitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

		private ImageView imageView = null;

		BitmapAsyncTask(ImageView imageView) {
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Log.i("bitmap", "doInBackground:" + params[0]);
			Bitmap bitmap = null;
			byte[] data = OkHttpUtils.getByteArrayFromUrl(params[0]);
			if (data != null) {
				SDCardHelper.saveFileToSDCardPublicDir(data,
						Environment.DIRECTORY_DOWNLOADS,
						MD5Util.getMD5(params[0]));
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				lruCache.put(params[0], bitmap);
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				imageView.setImageBitmap(result);
			}
		}

	}

	class MyLruCache extends LruCache<String, Bitmap> {

		public MyLruCache(int maxSize) {
			super(maxSize);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected int sizeOf(String key, Bitmap value) {
			// TODO Auto-generated method stub
			return value.getByteCount();
		}


		@Override
		protected void entryRemoved(boolean evicted, String key,
				Bitmap oldValue, Bitmap newValue) {
			// TODO Auto-generated method stub
			if (evicted) {

				SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(
						oldValue);
				softCaches.put(key, softReference);
			}
		}

	}

}
