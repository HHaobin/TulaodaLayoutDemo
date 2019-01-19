package com.javis.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjs.gjsmall.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Adapter_ListView_ware extends BaseAdapter {
	private Context context;
	@SuppressWarnings("unused")
	private int[]   data;
	private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();

	public Adapter_ListView_ware(Context context, ArrayList<HashMap<String, Object>> arrayList) {
		Log.d("TEST_SIZE", "Size :"+arrayList.size());
		this.context = context;
		this.arrayList = arrayList;
	}

	public Adapter_ListView_ware(Context context) {
		this.context = context;

	}

	@Override
	public int getCount() {
		return (arrayList != null && arrayList.size() == 0) ? 30: arrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View currentView, ViewGroup arg2) {
		HolderView holderView = null;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(R.layout.adapter_listview_ware, null);
			holderView.iv_pic = (ImageView) currentView.findViewById(R.id.iv_adapter_list_pic);
			holderView.tv_name = (TextView) currentView.findViewById(R.id.name);
			holderView.tv_price = (TextView) currentView.findViewById(R.id.price);
			holderView.tv_sale_num = (TextView) currentView.findViewById(R.id.sale_num);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		if (arrayList.size() != 0) {
			String   name   = null;
			String  price = null;
			String   pic   = null;
			String  marketprice = null;
			String evaluation_good_star= null;
			String goods_id= null;
			String goods_salenum= null;

			goods_id = arrayList.get(position).get("goods_id").toString().trim();
			name = arrayList.get(position).get("goods_name").toString().trim();
			price = arrayList.get(position).get("goods_price").toString().trim();
			pic = arrayList.get(position).get("goods_image_url").toString().trim();
			marketprice = arrayList.get(position).get("goods_marketprice").toString().trim();
			evaluation_good_star=arrayList.get(position).get("evaluation_good_star").toString().trim();
			goods_salenum=arrayList.get(position).get("goods_salenum").toString().trim();

			holderView.tv_name.setText(name);
			holderView.tv_price.setText("现价：￥" +price);
			holderView.tv_sale_num.setText("市场价：￥" +marketprice);

			//			DownloadImageTask localDownloadImageTask = new DownloadImageTask(holderView.iv_pic);
			//			String[]          arrayOfString          = new String[1];
			//			arrayOfString[0] = pic;
			//			localDownloadImageTask.execute(arrayOfString);
			//控件加载图片
			//显示图片的配置
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.ic_launcher)
					.showImageOnFail(R.drawable.ic_launcher)
					.cacheInMemory(true)
					.cacheOnDisk(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.build();
			ImageLoader.getInstance().displayImage("" + pic, holderView.iv_pic, options); // imageUrl代表图片的URL地址，imageView代表承载图片的IMAGEVIEW控件


			//			ImageLoader.ImageListener listener = ImageLoader.getImageListener(holderView.iv_pic, R.drawable.ic_launcher, R.drawable.ic_launcher);
			//			CU_VolleyTool.getInstance(context).getmImageLoader().get("http://192.168.0.111:3000/taoBao/img/" + arrayList.get(position).get("pic"), listener);

			//			holderView.tv_name.setText(arrayList.get(position).get("name").toString().trim());
			//			holderView.tv_price.setText("￥" + arrayList.get(position).get("price").toString().trim() + "Ԫ");
			//			holderView.tv_sale_num.setText("月销量:" + arrayList.get(position).get("sale_num").toString() + "件     " + arrayList.get(position).get("address").toString().trim());
		}
		return currentView;
	}

	public class HolderView {

		private ImageView iv_pic;
		private TextView  tv_sale_num;
		private TextView  tv_name;
		private TextView  tv_price;

	}

	private class DownloadImageTask extends AsyncTask<String, Void, Drawable>
	{
		ImageView imageView;
		private DownloadImageTask(ImageView imageView)
		{

			this.imageView = imageView;
		}

		protected Drawable doInBackground(String[] paramArrayOfString)
		{
			return loadImageFromNetwork(paramArrayOfString[0]);
		}

		protected void onPostExecute(Drawable paramDrawable)
		{
			imageView.setImageDrawable(paramDrawable);
		}
	}

	private Drawable loadImageFromNetwork(String paramString)
	{
		Drawable localDrawable1;
		try
		{
			Drawable localDrawable2 = Drawable.createFromStream(new URL(paramString).openStream(), "image.jpg");
			localDrawable1 = localDrawable2;
			if (localDrawable1 == null)
			{
				Log.d("test", "null drawable");
				return localDrawable1;
			}
		}
		catch (IOException localIOException)
		{
			while (true)
			{
				Log.d("test", localIOException.getMessage());
				localDrawable1 = null;
			}
		}
		return localDrawable1;
	}



}
