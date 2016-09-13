package com.androidkun.imageselectorlibrary.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidkun.imageselectorlibrary.R;

import java.util.List;

public class SelectImageAdapter extends CommonAdapter<String>
{

	/**
	 * 用户选择的图片，存储为图片的完整路径
	 */
//	public static List<String> mSelectedImage = new LinkedList<String>();
	private List<String> selectedImage;
	/**
	 * 可选择的图片总数
	 */
	private int selectNum;
	private TextView text_selectNum;

	private Context context;
	/**
	 * 文件夹路径
	 */
	private String mDirPath;

	public SelectImageAdapter(Context context, List<String> mDatas, int itemLayoutId,
							  String dirPath, List<String> selectedImage, int selectNum, TextView text_selectNum)
	{
		super(context, mDatas, itemLayoutId);
		this.context = context;
		this.mDirPath = dirPath;
		this.selectedImage = selectedImage;
		this.selectNum = selectNum;
		this.text_selectNum = text_selectNum;
	}

	@Override
	public void convert(final ViewHolder helper, final String item)
	{
		//设置no_pic
		helper.setImageResource(R.id.id_item_image, R.drawable.pictures_no);
		//设置no_selected
				helper.setImageResource(R.id.id_item_select,
						R.drawable.picture_unselected);
		//设置图片
		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

//		if()
		
		final ImageView mImageView = helper.getView(R.id.id_item_image);
		final ImageView mSelect = helper.getView(R.id.id_item_select);
		final String imagePath = mDirPath + "/" + item;
		mImageView.setColorFilter(null);
		//设置ImageView的点击事件
		mImageView.setOnClickListener(new OnClickListener()
		{
			//选择，则将图片变暗，反之则反之
			@Override
			public void onClick(View v)
			{
				// 已经选择过该图片
				if (selectedImage.contains(imagePath))
				{
					selectedImage.remove(imagePath);
					text_selectNum.setText(selectedImage.size()+"/"+selectNum);
					mSelect.setImageResource(R.drawable.picture_unselected);
					mImageView.setColorFilter(null);
				} else
				// 未选择该图片
				{
					if(selectedImage.size()>=selectNum){
						Toast.makeText(context,"已达到可选择数量",Toast.LENGTH_SHORT).show();
						return;
					}
					selectedImage.add(imagePath);
					text_selectNum.setText(selectedImage.size()+"/"+selectNum);
					mSelect.setImageResource(R.drawable.pictures_selected);
					mImageView.setColorFilter(Color.parseColor("#77000000"));
				}

			}
		});
		/**
		 * 已经选择过的图片，显示出选择过的效果
		 */
		if (selectedImage.contains(imagePath))
		{
			mSelect.setImageResource(R.drawable.pictures_selected);
			mImageView.setColorFilter(Color.parseColor("#77000000"));
		}

	}
}
