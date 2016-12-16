/**
 * 
 */
package com.demo.app.view;

import com.demo.app.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author S 自定义Button组件，相同tag的且是兄弟关系的button选中互斥
 */
public class CustomeButton extends Button {

	private boolean isSelected = false;
	private String buttonValue;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @param context
	 */
	public CustomeButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomeButton(final Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		final Drawable b1 = getResources().getDrawable(
				R.drawable.icon_rounded_rectangle);
		final Drawable b2 = getResources().getDrawable(
				R.drawable.icon_rounded_rectangle_down);
		this.setBackgroundDrawable(b1);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.CustomAttrsButton);
		if (typeArray == null) {
			return;
		}
		for (int i = 0; i < typeArray.length(); i++) {
			int attr = typeArray.getIndex(i);
			switch (attr) {
			case R.styleable.CustomAttrsButton_selected:
				if (typeArray.getBoolean(attr, false)) {
					this.setBackgroundDrawable(b2);// 默认选中颜色
				} else {
					// this.setBackgroundDrawable(b1);
				}
				break;
			case R.styleable.CustomAttrsButton_button_value:
				String value = typeArray.getText(attr).toString();
				System.out.println(this.getText()+"--"+value);
				setButtonValue(value);
				break;
			default:
				break;
			}
		}
		typeArray.recycle();
		this.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Drawable dr = v.getBackground();
				// 选中button父组件
				LinearLayout vp = (LinearLayout) v.getParent();
				// 选中button父组件的父亲
				LinearLayout vp_parent = (LinearLayout) vp.getParent();
				// 同类tag
				Object tag = v.getTag();
				// 父类tag
				Object tag_p = vp.getTag();
				if (tag_p == null) {
					vp.setTag(tag);
					tag_p = tag;
				}

				int num_parent = vp_parent.getChildCount();
				for (int j = 0; j < num_parent; j++) {
					if (vp_parent.getChildAt(j) instanceof LinearLayout) {
						LinearLayout child_parent = (LinearLayout) vp_parent
								.getChildAt(j);
						if (tag_p.equals(child_parent.getTag())) {
							// 兄弟个数
							int num = child_parent.getChildCount();
							for (int i = 0; i < num; i++) {
								// 判断是否是自定义的组件类型
								if (child_parent.getChildAt(i) instanceof CustomeButton) {
									CustomeButton button = (CustomeButton) child_parent
											.getChildAt(i);
									// 判断是否是相同的Tag
									if (tag.equals(button.getTag())
											&& button.getId() != v.getId()) {
										button.setBackgroundDrawable(b1);
										button.setSelected(false);
									}
								}
							}
						}
					}
				}

				// 重新设置选中组件的颜色
				if (dr.equals(b2)) {
					// v.setBackgroundResource(R.drawable.icon_rounded_rectangle);
					// 必须选中一个
					// v.setBackgroundDrawable(b1);
					// setSelected(false);
				} else {
					// v.setBackgroundResource(R.drawable.icon_rounded_rectangle_down);
					v.setBackgroundDrawable(b2);
					setSelected(true);
				}
				//其它手动填写内容
				if(v.getId()==R.id.fr_coper_others||
						v.getId()==R.id.franchise_person_coper_others||
						v.getId()==R.id.coper_others||
						v.getId()==R.id.ct_others){
//						Toast.makeText(context, "其它", Toast.LENGTH_SHORT).show();
					showInputTextDialog(context,v);
				}
			}
		});
	}

	public String getCustomeTag() {
		if (isSelected) {
			return this.getTag().toString();
		}
		return "#";
	}

	public String getButtonValue() {
		return buttonValue;
	}

	public void setButtonValue(String buttonValue) {
		this.buttonValue = buttonValue;
	}

	//其它输入框
	public void showInputTextDialog(Context context,final View view) {
		/*final EditText inputServer = new EditText(context);
		inputServer.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.icon_rounded_rectangle));
		inputServer.setText(((Button)v).getText());*/
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		 int width = wm.getDefaultDisplay().getWidth();
		 int height = wm.getDefaultDisplay().getHeight();
		 View layout = inflate(context,R.layout.others_dialog_input_layout,null);
		 final Dialog dialog = new Dialog(context/*,R.style.CustomDialogStyle*/);
		 dialog.show();
		/*AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final AlertDialog dialog = builder.create();
		dialog.show();*/
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));  
		dialog.setContentView(layout);
		WindowManager.LayoutParams  lp= dialog.getWindow().getAttributes();  
        lp.width=width*3/4;//定义宽度  
        lp.height=height*2/5;//定义高度  
        dialog.getWindow().setAttributes(lp); 
        
		final EditText input = (EditText)dialog.getWindow().findViewById(R.id.other_dialog_editText);
		input.setText(((Button)view).getText());
		Button ok = (Button)dialog.getWindow().findViewById(R.id.other_dialog_ok);
		Button cancel = (Button)dialog.getWindow().findViewById(R.id.other_dialog_cancel);
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String other = input.getText().toString();
				((Button)view).setText("".equals(other)?"其它":other);
				dialog.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
	}
}
