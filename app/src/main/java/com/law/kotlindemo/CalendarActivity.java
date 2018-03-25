package com.law.kotlindemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Jungle on 2018/3/25.
 */

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvPre, mTvNext;
    private TextView mTvYear, mTvMonth;
    private RecyclerView mRvCalendar;
    private DayAdapter mAdapter;

    private int sYear;
    private int sMonth;
    private int cYear;
    private int cMonth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mTvPre = findViewById(R.id.tv_pre);
        mTvNext = findViewById(R.id.tv_next);
        mTvYear = findViewById(R.id.tv_year);
        mTvMonth = findViewById(R.id.tv_month);

        mRvCalendar = findViewById(R.id.rv_calendar);
        mRvCalendar.setLayoutManager(new GridLayoutManager(this, 7));
        mAdapter = new DayAdapter(this);
        mRvCalendar.setAdapter(mAdapter);

        Calendar calendar = Calendar.getInstance();
        sYear = cYear = calendar.get(Calendar.YEAR);
        sMonth = cMonth = calendar.get(Calendar.MONTH);
        setDateDisplay(cYear, cMonth);
        mTvPre.setOnClickListener(this);
        mTvNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pre:
                if (cMonth == 0) {
                    cYear--;
                    cMonth = 11;
                } else {
                    cMonth--;
                }
                Log.d("TAG", cYear + "/" + cMonth);
                mAdapter.setCalendar(cYear, cMonth);
                setDateDisplay(cYear, cMonth);
                break;
            case R.id.tv_next:
                if (sYear == cYear) {
                    if (cMonth == sMonth) {
                        Toast.makeText(this, "超过", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        cMonth++;
                    }
                } else if (sYear > cYear) {
                    if (cMonth == 11) {
                        cYear++;
                        cMonth = 0;
                    } else {
                        cMonth++;
                    }
                }
                Log.d("TAG", cYear + "/" + cMonth);
                mAdapter.setCalendar(cYear, cMonth);
                setDateDisplay(cYear, cMonth);
                break;
        }
    }

    private void setDateDisplay(int year, int month) {
        mTvYear.setText(year + "年");
        mTvMonth.setText((month + 1) + "月");
    }
}
