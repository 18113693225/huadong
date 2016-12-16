/**
 * 
 */
package com.demo.app.view;

import com.demo.app.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * @author S
 *
 */
public class CustomeGridView extends GridView {

	/**
	 * @param context
	 */
	public CustomeGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CustomeGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	public CustomeGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);
		View localView1 = getChildAt(0);
		int column = 3;
		int childCount = 9;

		Paint localPaint;// 画笔
		localPaint = new Paint();
		localPaint.setStyle(Paint.Style.STROKE);
		localPaint.setColor(getContext().getResources().getColor(R.color.line));// 设置画笔的颜色
		// 遍历子view
		for (int i = 0; i < childCount; i++) {
			View cellView = getChildAt(i);// 获取子view
			// 第一行 去上
			if (i < 3) {
				canvas.drawLine(cellView.getLeft(), cellView.getTop(), cellView.getRight(), cellView.getTop(),
						localPaint);
			}
			if (i % column == 0) {// 第一列 去左
				canvas.drawLine(cellView.getLeft(), cellView.getTop(), cellView.getLeft(), cellView.getTop(),
						localPaint);
			}
			if ((i + 1) % column == 0) {// 第三列 去右
				// 画子view底部横线
//				if(i!=8)
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
						localPaint);
				canvas.drawLine(cellView.getLeft(), cellView.getTop(), cellView.getRight(), cellView.getTop(),
						localPaint);
			}else if(i==6||i==7||i==8){
				// 画子view的右边竖线
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(),
						localPaint);
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
						localPaint);
			} else {// 如果view不是最后一行
				// 画子view的右边竖线
				canvas.drawLine(cellView.getRight(), cellView.getTop(), cellView.getRight(), cellView.getBottom(),
						localPaint);
				
				// 画子view的底部横线
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
						localPaint);
			}
		}
	}
}
