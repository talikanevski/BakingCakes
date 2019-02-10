package com.example.bakingcakes.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.bakingcakes.Activities.MainActivity;
import com.example.bakingcakes.R;

/**
 * Implementation of App Widget functionality.
 */
public class CakeWidgetProvider extends AppWidgetProvider {


    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cake_widget_provider);
        views.setTextViewText(R.id.appwidget_app_name, context.getString(R.string.app_name));

        // Get recent viewed Cake from SharedPreferences:
        SharedPreferences sharedPreferencesForWidget = context.getSharedPreferences(context.getString(R.string.pref_file_name), Context.MODE_PRIVATE | Context.MODE_MULTI_PROCESS);

        String cakeName = sharedPreferencesForWidget.getString(context.getString(R.string.cake_name_key), "");
        views.setTextViewText(R.id.appwidget_cake_name, cakeName);

        String ingredients = sharedPreferencesForWidget.getString(context.getString(R.string.widget_ingredients), "");
        views.setTextViewText(R.id.widget_ingredients, ingredients);

        // Create an Intent to launch MainActivity when clicked
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Widgets allow click handlers to only launch pending intents
        views.setOnClickPendingIntent(R.id.appwidget_linear_layout, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // called when the user selects a recipe in the app, to update the widget accordingly
        ComponentName bakingAppWidget = new ComponentName(context.getPackageName(), CakeWidgetProvider.class.getName());
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(bakingAppWidget);
        onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
        super.onReceive(context, intent);
    }
}

