package com.law.kotlindemo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jungle on 2018/3/25.
 */

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.Holder> {
    private Context mContext;
    private int size;

    private List<Day> days;
    private Day sStart;
    private Day sEnd;

    private int cYear;
    private int cMonth;
    private int cDay;
    private long cMillis;


    public DayAdapter(Context context) {
        this.mContext = context;
        init();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.item_calendar, null, false);
        return new Holder(convertView);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        final Day day = days.get(position);
        ViewGroup.LayoutParams params = holder.mTvDay.getLayoutParams();
        params.height = size;
        holder.mTvDay.setLayoutParams(params);

//        if (day.getYear() <= cYear) {
//            if (day.getMonth() == cMonth) {
//                if (day.getDay() >= 0) {
//                    if (day.getMillis() > cMillis) {
//                        holder.mTvDay.setText(String.valueOf(day.getDay() + 1));
//                        holder.mTvDay.setTextColor(Color.GRAY);
//                    } else if (day.getMillis() <= cMillis) {
//                        holder.mTvDay.setText(String.valueOf(day.getDay() + 1));
//                        holder.mTvDay.setTextColor(Color.BLACK);
//                    }
//                } else {
//                    holder.mTvDay.setText("");
//                }
//            } else if (day.getMonth() < cMonth) {
//                if (day.getDay() >= 0) {
//                    holder.mTvDay.setText(String.valueOf(day.getDay() + 1));
//                } else {
//                }
//            }
//        }
        if (day.getDay() < 0) {
            holder.mTvDay.setText("");
        } else if (day.getMillis() <= cMillis) {
            holder.mTvDay.setText(String.valueOf(day.getDay() + 1));
            holder.mTvDay.setTextColor(Color.BLACK);
        } else if (day.getMillis() > cMillis) {
            holder.mTvDay.setText(String.valueOf(day.getDay() + 1));
            holder.mTvDay.setTextColor(Color.GRAY);
        }

        if (day.getMillis() <= cMillis && day.getDay() >= 0) {
            holder.mTvDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sStart == null && sEnd == null) {
                        sStart = sEnd = day;
                    } else if (sStart == sEnd) {
                        if (day.getMillis() < sStart.getMillis()) {
                            sStart = day;
                        } else if (day.getMillis() > sEnd.getMillis()) {
                            sEnd = day;
                        } else {
                            sStart = sEnd = null;
                        }
                    } else {
                        sStart = sEnd = day;
                    }
                    notifyDataSetChanged();
                }
            });
            if (sStart != null && sEnd != null) {
                if (day.getMillis() == sStart.getMillis() || day.getMillis() == sEnd.getMillis()) {
                    setDayStartOrEnd(holder.mTvDay);
                } else if (day.getMillis() > sStart.getMillis() && day.getMillis() < sEnd.getMillis()) {
                    setDayInRange(holder.mTvDay);
                } else {
                    setDayNor(holder.mTvDay);
                }
            }
        }
        if(day.getDay() < 0){
            holder.mTvDay.setBackgroundColor(Color.WHITE);
            holder.mTvDay.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return days == null ? 0 : days.size();
    }

    private void init() {
        size = mContext.getResources().getDisplayMetrics().widthPixels / 7;
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        cYear = calendar.get(Calendar.YEAR);
        cMonth = calendar.get(Calendar.MONTH);
        cDay = calendar.get(Calendar.DAY_OF_MONTH);
        cMillis = calendar.getTimeInMillis();
        days = new ArrayList<>();
        Log.d("TAG", "current date = " + cYear + "/" + (cMonth + 1) + "/" + cDay);
        Log.d("TAG", "cYear = " + cYear + ",cMonth = " + cMonth + ",cDay = " + cDay);
        calendar.set(Calendar.YEAR, cYear);
        calendar.set(Calendar.MONTH, cMonth);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DATE));
        int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d("TAG", "index = " + index);
        int mMaximum = calendar.getActualMaximum(Calendar.DATE);
        Log.d("TAG", "ActualMaximum = " + mMaximum);
        for (int i = 0; i < index; i++) {
            Day day = new Day();
            day.setDay(-1);
            day.setYear(cYear);
            day.setMonth(cMonth);
            days.add(day);
        }
        for (int i = 0; i < mMaximum; i++) {
            Day day = new Day();
            day.setDay(i);
            day.setYear(cYear);
            day.setMonth(cMonth);
            days.add(day);
        }
    }

    public void setCalendar(int year, int month) {
        days.clear();
        calculate(year, month);
        notifyDataSetChanged();
    }

    private void calculate(int year, int month) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DATE));
        int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d("TAG", "index = " + index);
        int mMaximum = calendar.getActualMaximum(Calendar.DATE);
        Log.d("TAG", "ActualMaximum = " + mMaximum);
        for (int i = 0; i < index; i++) {
            Day day = new Day();
            day.setDay(-1);
            day.setYear(year);
            day.setMonth(month);
            days.add(day);
        }
        for (int i = 0; i < mMaximum; i++) {
            Day day = new Day();
            day.setDay(i);
            day.setYear(year);
            day.setMonth(month);
            days.add(day);
        }
    }

    private void setDayStartOrEnd(TextView tv) {
        tv.setBackgroundColor(Color.BLUE);
        tv.setTextColor(Color.WHITE);
    }

    private void setDayInRange(TextView tv) {
        tv.setBackgroundColor(Color.RED);
        tv.setTextColor(Color.WHITE);
    }

    private void setDayNor(TextView tv) {
        tv.setBackgroundColor(Color.WHITE);
        tv.setTextColor(Color.BLACK);
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView mTvDay;

        public Holder(View itemView) {
            super(itemView);
            mTvDay = itemView.findViewById(R.id.tv_day);
        }
    }
}
