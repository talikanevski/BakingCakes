package com.example.bakingcakes.Widget;

import android.app.IntentService;
import android.content.Intent;

public class CakeWidgetService extends IntentService {//TODO do I need it??? what action am I suppose to update???

    public static final String CAKE_TO_USE = "com.example.bakingcakes.Widget.cake_to_use";
    public static final String CAKE_TO_USE_ACTION = "com.example.bakingcakes.Widget.cake_to_use_action";


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param "CakeWidgetService" Used to name the worker thread, important only for debugging.
     */
    public CakeWidgetService() {
        super("CakeWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
