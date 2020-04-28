package com.tpsoft.babycount;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.tpsoft.babycount.data.HistoryDao;
import com.tpsoft.babycount.data.HistoryModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyWidgetProvider extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            int count = countListAll(context);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);

            remoteViews.setTextViewText(R.id.update, String.valueOf(count));
            Intent intent = new Intent(context, MyWidgetProvider.class);

//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.setAction("com.tpsoft.babycount.MyWidgetProvider");
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals("com.tpsoft.babycount.MyWidgetProvider")) {
            add(context);
            int countNum = countListAll(context);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.update, String.valueOf(countNum));
            ComponentName componentName = new ComponentName(context, MyWidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
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
        String type;
        Calendar cal = Calendar.getInstance();
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);
        if(currentHour > 17){
            type = "A";
        }else if(currentHour > 12){
            type = "N";
        }else{
            type = "M";
        }
        return type;
    }
}
