package com.demo.app.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.app.R;
import com.demo.app.activity.index.WorkTicketNameListActivity;
/**
 * 自定义文本输入框2
 * @author S
 *
 */
public class CustomeTextView extends LinearLayout {

	private LinearLayout ll;
	private Context mContext;
	private TextView mTextViewName;
	private TextView mTextViewValue;

	public CustomeTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// 导入布局
	protected void inflaterLayout(Context context) {
		ll = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.custome_textview_layout, this, true);
	}

	public CustomeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		inflaterLayout(context);
		mTextViewName = (TextView) ll.findViewById(R.id.custome_name_textView3);
		mTextViewValue = (TextView) ll.findViewById(R.id.custome_value_textView3);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.CustomAttrsText3Attrs);
		if (typeArray == null) {
			return;
		}
		for (int i = 0; i < typeArray.length(); i++) {
			int attr = typeArray.getIndex(i);
			switch (attr) {
			case R.styleable.CustomAttrsText3Attrs_name_text:
				mTextViewName.setText(typeArray.getText(attr));
				break;
			case R.styleable.CustomAttrsText3Attrs_drawable_l_text:
				int resourceId  = typeArray.getResourceId(attr, -1);
				if(resourceId!=-1){
//				mTextViewName.setCompoundDrawablesWithIntrinsicBounds(resourceId, -1, -1, -1);
					Drawable drawable= getResources().getDrawable(resourceId);  
					/// 这一步必须要做,否则不会显示.  
					drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());  
					mTextViewName.setCompoundDrawables(drawable,null,null,null); 
				}
				break;
			default:
				break;
			}
		}
		typeArray.recycle();
	}
	
	public void setValueText(CharSequence value){
		mTextViewValue.setText(value);
	}
	public CharSequence getValueText(){
		return mTextViewValue.getText()==null?"":mTextViewValue.getText();
	}
}
