package com.tpsoft.babycount;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.tpsoft.babycount.data.HistoryDao;
import com.tpsoft.babycount.data.HistoryModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyWidgetProvider extends AppWidgetProvider{


    private static final String ACTION_CLICK = "ACTION_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // Get all ids
        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            // create some random data
            add(context);
            int x = countListAll(context);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            Log.w("WidgetExample", String.valueOf(x));
            // Set the text
            remoteViews.setTextViewText(R.id.update, String.valueOf(x));
            // Register an onClickListener
            Intent intent = new Intent(context, MyWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private int countListAll(Context context){
        HistoryDao dao = new HistoryDao(context);
        List<HistoryModel>  models = dao.getTodayList();
        return models.size();
    }

    private void add(Context context){
        HistoryDao dao = new HistoryDao(context);
        HistoryModel model = new HistoryModel();
        String type = getType();
        String typeText;
        if(type.equals("M")){
            typeText = "เพิ่มตอนเช้าแล้ว";
        }else if(type.equals("N")){
            typeText = "เพิ่มตอนเที่ยงแล้ว";
        }else{
            typeText = "เพิ่มตอนเย็นแล้ว";
        }
        model.setType(type);
        model.setComment("Update By Widget");
        model.setCreatedDate(getCurrentDate());
        dao.insert(model);

        Toast.makeText(context,typeText,Toast.LENGTH_SHORT).show();
    }

    private String getCurrentDate(){
        DateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getType() {
        String type = "";
        try {
            String noon = "12:00:00";
            Date noonTime = new SimpleDateFormat("HH:mm:ss").parse(noon);
            Calendar noonCalendar = Calendar.getInstance();
            noonCalendar.setTime(noonTime);
            noonCalendar.add(Calendar.DATE, 1);

            String afternoon = "17:00:00";
            Date afternoonTime = new SimpleDateFormat("HH:mm:ss").parse(afternoon);
            Calendar afternoonCalendar = Calendar.getInstance();
            afternoonCalendar.setTime(afternoonTime);
            afternoonCalendar.add(Calendar.DATE, 1);

            Date current = new Date();
            if (current.after(afternoonCalendar.getTime())) {
                type = "A";
            }else if(current.after(noonCalendar.getTime())){
                type = "N";
            }else{
                type = "M";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return type;
    }
}
