package com.example.bakingcakes;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


import com.example.bakingcakes.Models.Cake;

import java.util.List;

/**
 * Loads a list of cakes by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class CakeLoader extends AsyncTaskLoader<List<Cake>> {

    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = CakeLoader.class.getName();

    /**
     * Query URL
     */
    private final String mUrl;

    public CakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "Test:  onStartLoading called");

        forceLoad();
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    public List<Cake> loadInBackground() {
        Log.i(LOG_TAG, "Test:  loadInBackground called");

        if (mUrl == null) {
            return null;
        }

        /*Perform the network request, parse the response, and extract a list of cakes.**/
        return Utils.fetchData(mUrl);
    }
}