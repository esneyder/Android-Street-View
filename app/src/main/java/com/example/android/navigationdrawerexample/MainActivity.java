/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class MainActivity extends Activity {
    public static final String LOG = "SILVER_LOG";
    private CharSequence mTitle;
    static public ImageLoader il;
    public ArrayList<Long> newLocationIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        il = ImageLoader.getInstance();
        il.init(config);

        //new favorites that were just added to the db & haven't been rendered
        newLocationIds = new ArrayList<>();

        setContentView(R.layout.activity_main);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {
            switchFragment(3, null);
        }
    }

    public void switchToExploreWithRecord(StreetViewLocationRecord r) {
        Bundle bundle = new Bundle();
        bundle.putLong("RECORD_ID", r.getId());
        switchFragment(0, bundle);
    }

    public void switchToExploreWithSaved(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("POSITION", position);
        switchFragment(0, bundle);
    }

    public void switchToExploreWithPoint(LatLng point) {
        Bundle bundle = new Bundle();
        bundle.putDouble("MANUAL_LAT", point.latitude);
        bundle.putDouble("MANUAL_LONG", point.longitude);
        switchFragment(0, bundle);
    }

    public void switchFragment(int position, Bundle bundle) {
        //convert position into fragment
        Fragment fragment;
        if (position == 0) {
            fragment = new ExploreFragment();
            //displayMenuItem(R.id.action_favoriteLocation, true);
        } else if (position == 1) {
            fragment = new FavoritedLocsFragment();
        } else {//if (position == 3) {
            fragment = new ClusterFragment();
        }
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
}