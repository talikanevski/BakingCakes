package com.example.bakingcakes.Activities;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.example.bakingcakes.Adapters.CakeAdapter;
import com.example.bakingcakes.CakeLoader;
import com.example.bakingcakes.Models.Cake;
import com.example.bakingcakes.R;
import com.example.bakingcakes.Utils;
import com.example.bakingcakes.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import static com.example.bakingcakes.Utils.GIVEN_JSON_DATA;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a { DetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Cake>> {
    private static final String LOG_TAG = MainActivity.class.getName();
    /**
     * Constant value for the loader ID. We can choose any integer.
     */
    private static final int LOADER_ID = 1;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private View recyclerView;
    private boolean mTwoPane;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (findViewById(R.id.tablet_main_layout) != null) {
            mTwoPane = true;

        } else {
            mTwoPane = false;
        }

        setSupportActionBar(mBinding.toolbar);

        mBinding.toolbar.setTitle(getTitle());

        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        /* In case that there is no internet connection:
          I don't want to show that "No cakes found" -
          I want to show that "No internet connection"
          Get a reference to the ConnectivityManager to check state of network connectivity**/

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        /*Get details on the currently active default data network**/
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        /* If there is a network connection, fetch data**/
        if (networkInfo != null && networkInfo.isConnected()) {

            /* to retrieve a movie info, we need to get the loader manager
               and tell the loader manager to initialize the loader with the specified ID,
               the second argument allows us to pass a bundle of additional information, which we'll skip.
               The third argument is what object should receive the LoaderCallbacks
               (and therefore, the data when the load is complete!) - which will be this activity.
               This code goes inside the onCreate() method of the MainActivity,
               so that the loader can be initialized as soon as the app opens.**/

            /*Get a reference to the LoaderManager, in order to interact with loaders.**/
            final LoaderManager loaderManager = getLoaderManager();

            /*Initialize the loader. Pass in the int ID constant defined above and pass in null for
             // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
             // because this activity implements the LoaderCallbacks interface).**/

            Log.i(LOG_TAG, "Test: calling initLoader");

            loaderManager.initLoader(LOADER_ID, null, this);

            /* if internet connection got lost and than back, we'll see mEmptyView
              with text "no internet connection"
              we need to reload the app and while it's reloading it's better to see a spinner**/
            mBinding.emptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload();
                    View loading = findViewById(R.id.loading_spinner);
                    loading.setVisibility(View.VISIBLE);
                }
            });
        } else {/* Otherwise, display error
         // First, hide loading indicator so error message will be visible**/
            mBinding.loadingSpinner.setVisibility(View.GONE);

            /* Update empty state with no connection error message**/
            mBinding.emptyView.setText(R.string.no_internet);
        }

        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                }
                i.putExtra(Intent.EXTRA_SUBJECT, "Honey, I am going to bake a cake, which one would you prefer: ");
                i.putExtra(Intent.EXTRA_TEXT, "Honey, I am going to bake a cake, which one would you prefer: " +
                       Utils.cakesNamesToShare + "?");
                startActivity(Intent.createChooser(i, getString(R.string.share_text_for_chooser)));
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "Test:  onCreateLoader called");

        // Create a new loader for the given URL
        return new CakeLoader(this, GIVEN_JSON_DATA);
    }

    @Override
    public void onLoadFinished(Loader<List<Cake>> loader, List<Cake> cakes) {
        Log.i(LOG_TAG, "Test:  onLoadFinished called");

        ProgressBar loading = findViewById(R.id.loading_spinner);
        loading.setVisibility(View.GONE);

        /* in case the internet connection got lost in the middle**/
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Set empty state text to display "No movies found."
            mBinding.emptyView.setText(R.string.no_cakes);
        } else// Update empty state with no connection error message
        {
            mBinding.emptyView.setText(R.string.no_internet);
        }

        // If there is a valid list of cakes, then add them to the adapter's data set.
        // This will trigger the RecyclerView to update.
        if (cakes != null && !cakes.isEmpty()) {
            updateRecyclerView((RecyclerView) recyclerView, cakes);
            mBinding.emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Cake>> loader) {
        Log.i(LOG_TAG, "Test:  onLoaderReset called");
    }

    private void reload() {
        getLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new CakeAdapter(this, new ArrayList<Cake>(), mTwoPane));
        if(mTwoPane) {// 2- how many rows in the grid view
            recyclerView.setLayoutManager
                    (new GridLayoutManager(recyclerView.getContext(), 2));
        }
    }

    private void updateRecyclerView(@NonNull RecyclerView recyclerView, List<Cake> cakes) {
        recyclerView.setAdapter(new CakeAdapter(this,  cakes, mTwoPane));
        if(mTwoPane) {// 2- how many rows in the grid view
            recyclerView.setLayoutManager
                    (new GridLayoutManager(recyclerView.getContext(), 2));
        }
    }
}
