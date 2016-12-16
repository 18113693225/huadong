package com.demo.app.view;


import com.demo.app.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomeImageTextButton extends LinearLayout {

	private TextView mTv;
	private ImageView mIv;
	private Context mContext;
	LinearLayout ll;
	public CustomeImageTextButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}


	// 导入布局
	protected void inflaterLayout(Context context) {
		ll = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.custome_image_text_layout, this, true);
//		ll.setOrientation(LinearLayout.HORIZONTAL);
	}
	public CustomeImageTextButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		inflaterLayout(context);
		mIv = (ImageView)findViewById(R.id.cImage);
		mTv = (TextView)findViewById(R.id.cText);
		
		//不能够使用ImageView的click方法， 在android里点击事件消息是从内向外传递的，设置了false之后才能传递出来给LinearLayout
		mIv.setClickable(false);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,R.styleable.CustomAttrsImageButton);
		if (typeArray == null) {
			return;
		}
		for(int i=0;i<typeArray.length();i++){
			int attr = typeArray.getIndex(i);
			switch (attr) {
			case R.styleable.CustomAttrsImageButton_text:
				String text = typeArray.getText(attr).toString();
				mTv.setText(text);
				break;
			case R.styleable.CustomAttrsImageButton_textSize:
				break;
			case R.styleable.CustomAttrsImageButton_src:
				int src = typeArray.getResourceId(attr, R.drawable.icon_avatar);
//				mIv.setBackgroundResource(src);
				mIv.setImageResource(src);
				break;
			case R.styleable.CustomAttrsImageButton_orientation:
				String or = typeArray.getText(attr).toString();
				System.out.println(or);
				break;
			default:
				break;
			}
		}
		 typeArray.recycle();
	}
	/**
	 * 设置图片资源
	 * @param resId
	 */
	public void setImageResource(int resId){
		mIv.setImageResource(resId);
	}
	/**
	 * 设置显示的文字
	 * @param text
	 */
	public void setTextViewText(String text){
		mTv.setText(text);
	}
}
