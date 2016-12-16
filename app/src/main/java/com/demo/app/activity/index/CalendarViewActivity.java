package com.demo.app.activity.index;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.util.TitleCommon;
import com.demo.app.view.CustomeTextView;

//日程表视图
public class CalendarViewActivity extends BaseActivity {
    private TimePicker timerPicker;
    private DatePicker datePicker;
    private CustomeTextView selectTime;
    private Button saveTimeBtn;
    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;
    private String hour;
    private String minute;
    private String second;
    private int editTextId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canlendar_view_layout);
        TitleCommon.setTitle(this, null, "选择时间", null, true);

        editTextId = getIntent().getIntExtra("editTextId", -1);
        selectTime = (CustomeTextView) this.findViewById(R.id.selectTime);
        timerPicker = (TimePicker) this.findViewById(R.id.timePicker);
        timerPicker.setIs24HourView(true);
        datePicker = (DatePicker) this.findViewById(R.id.datePicker);
        // datePicker.seton
        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + c.get(Calendar.HOUR_OF_DAY) : c.get(Calendar.HOUR_OF_DAY) + "";
        minute = c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE) + "";
        second = c.get(Calendar.SECOND) < 10 ? "0" + c.get(Calendar.SECOND) : c.get(Calendar.SECOND) + "";
        showDate(year, month, day, hour, minute, second);
        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year, int month,
                                      int day) {
                CalendarViewActivity.this.year = year;
                CalendarViewActivity.this.month = month;
                CalendarViewActivity.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day, hour, minute, second);
            }
        });
        // 为TimePicker指定监听器
        timerPicker.setOnTimeChangedListener(new OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker arg0, int hour, int minute) {
                String hour2 = hour < 10 ? "0" + hour : hour + "";
                String minute2 = minute < 10 ? "0" + minute : minute + "";
                CalendarViewActivity.this.hour = hour2;
                CalendarViewActivity.this.minute = minute2;
                // 显示当前日期、时间
                showDate(year, month, day, hour2, minute2, second);
            }
        });
        saveTimeBtn = (Button) this.findViewById(R.id.saveTimeButton);
        saveTimeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent();
                i.putExtra("time", selectTime.getValueText());
                i.putExtra("editTextId", editTextId);
                CalendarViewActivity.this.setResult(RESULT_OK, i);
                CalendarViewActivity.this.finish();
            }
        });
    }

    // 定义显示当前日期、时间的方法
    private void showDate(int year, int month, int day, String hour, String minute, String second) {
        selectTime.setValueText(year + "-" + ((month + 1) < 10 ? ("0" + (month + 1)) : (month + 1)) + "-" + (day < 10 ? ("0" + day) : day) + " "
                + hour + ":" + minute + ":" + second);
    }
}
