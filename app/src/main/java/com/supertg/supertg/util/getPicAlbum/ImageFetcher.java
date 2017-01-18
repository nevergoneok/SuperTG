package com.supertg.supertg.util.getPicAlbum;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


/**
 * 图片工具类
 * 
 */

public class ImageFetcher {
	private static ImageFetcher instance;
	private Context mContext;
	private HashMap<String, ImageBucket> mBucketList = new HashMap<String, ImageBucket>();
	private HashMap<String, String> mThumbnailList = new HashMap<String, String>();
	private List<ImageItem> dateImageList = new ArrayList<ImageItem>();

	private ImageFetcher(Context context) {
		this.mContext = context;
	}

	public static ImageFetcher getInstance(Context context) {

		if (instance == null) {
			synchronized (ImageFetcher.class) {
				instance = new ImageFetcher(context);
			}
		}
		return instance;
	}

	/**
	 * 是否已加载过了相册集合
	 */
	boolean hasBuildImagesBucketList = false;

	/**
	 * 得到图片集
	 * 每个相册分开装到不同的集合imageList中
	 * @param refresh
	 * @return
	 */
	public List<ImageBucket> getImagesBucketList(boolean refresh) {
		if (refresh || (!refresh && !hasBuildImagesBucketList)) {
			buildImagesBucketList();
		}
		List<ImageBucket> tmpList = new ArrayList<ImageBucket>();
		Iterator<Entry<String, ImageBucket>> itr = mBucketList.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, ImageBucket> entry = itr.next();
			tmpList.add(entry.getValue());
		}
		return tmpList;
	}
	
	/**
	 * 获取所有的照片的地址，按照时间从最近的排序
	 * @param refresh
	 * @return
	 */
	public List<ImageItem> getDateImageList(boolean refresh){
		//第一次进入的时候去相册数据库查询数据
		if (refresh || (!refresh && !hasBuildImagesBucketList)) {
			buildImagesBucketList();
		}
		List<ImageItem> tmpList = new ArrayList<ImageItem>();
		for(int i=dateImageList.size()-1;i>=0;i--){
			ImageItem item = dateImageList.get(i);
			//不能直接tmpList.add(item);这样的话tmpList集合就引用了dateImageList.get(i)的地址，
			//当tmpList中的数据发生改变时，dateImageList中的数据也会被改变
			ImageItem image = new ImageItem();
			image.isSelected = item.isSelected;
			image.sourcePath = item.sourcePath;
			image.thumbnailPath = item.thumbnailPath;
			image.imageId = item.imageId;
			
			tmpList.add(image);
		}
		return tmpList;
	}

	/**
	 * 得到图片集
	 */
	private void buildImagesBucketList() {
		Cursor cur = null;
		try {
			// 构造缩略图索引
			getThumbnail();
			// 构造相册索引
			String columns[] = new String[] { Media._ID, Media.BUCKET_ID, Media.DATA, Media.BUCKET_DISPLAY_NAME , Media.DATE_ADDED};
			// 得到一个游标，按照Media.DATE_ADDED时间顺序获取数据
			cur = mContext.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, columns, null, null, Media.DATE_ADDED);
			if (cur.getCount() != 0 && cur.moveToFirst()) {
				// 获取指定列的索引
				int photoIDIndex = cur.getColumnIndexOrThrow(Media._ID);
				int photoPathIndex = cur.getColumnIndexOrThrow(Media.DATA);
				int bucketDisplayNameIndex = cur.getColumnIndexOrThrow(Media.BUCKET_DISPLAY_NAME);
				int bucketIdIndex = cur.getColumnIndexOrThrow(Media.BUCKET_ID);
				int dateIndex = cur.getColumnIndexOrThrow(Media.DATE_ADDED);
				do {
					String _id = cur.getString(photoIDIndex);
					String path = cur.getString(photoPathIndex);
					String bucketName = cur.getString(bucketDisplayNameIndex);
					String bucketId = cur.getString(bucketIdIndex);
					long date = cur.getLong(dateIndex);
					ImageBucket bucket = mBucketList.get(bucketId);
					if (bucket == null) {
						bucket = new ImageBucket();
						mBucketList.put(bucketId, bucket);
						bucket.imageList = new ArrayList<ImageItem>();
						bucket.bucketName = bucketName;
					}
					bucket.count++;
					ImageItem imageItem = new ImageItem();
					imageItem.imageId = _id;
					imageItem.sourcePath = path;
					imageItem.thumbnailPath = mThumbnailList.get(_id);
					imageItem.date = date;
					bucket.imageList.add(imageItem);
					dateImageList.add(imageItem);
				} while (cur.moveToNext());
			}

			hasBuildImagesBucketList = true;
			System.currentTimeMillis();
		} finally {
			if(cur != null){
				cur.close();
			}
		}
	}

	/**
	 * 得到缩略图
	 */
	private void getThumbnail() {
		Cursor cursor = null;
		try {
			String[] projection = { Thumbnails.IMAGE_ID, Thumbnails.DATA };
			cursor = mContext.getContentResolver().query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, null);
			getThumbnailColumnData(cursor);
		} finally {
			if(cursor != null){
				cursor.close();
			}
		}
	}

	/**
	 * 从数据库中得到缩略图
	 * 
	 * @param cur
	 */
	private void getThumbnailColumnData(Cursor cur) {
		if (cur.moveToFirst()) {
			int image_id;
			String image_path;
			int image_idColumn = cur.getColumnIndex(Thumbnails.IMAGE_ID);
			int dataColumn = cur.getColumnIndex(Thumbnails.DATA);

			do {
				image_id = cur.getInt(image_idColumn);
				image_path = cur.getString(dataColumn);

				mThumbnailList.put("" + image_id, image_path);
			} while (cur.moveToNext());
		}
	}

}
