package com.emerap.ExpandableAdapter;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by karbunkul on 28.01.17.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
