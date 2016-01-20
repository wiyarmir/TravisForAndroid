/*
 * Copyright 2015 Guillermo Orellana Ruiz
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

package es.guillermoorellana.travisforandroid.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.ui.fragment.ReposFragment;
import es.guillermoorellana.travisforandroid.ui.view.MainView;
import hugo.weaving.DebugLog;

public class MainActivity extends BaseActivity implements MainView {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new ReposFragment());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        // onCreateOptionsMenu is called after the fragment being added
        setSearchableCallback(getSupportFragmentManager().findFragmentById(R.id.mainFrame));

        return super.onCreateOptionsMenu(menu);
    }

    @DebugLog
    @Override
    public void addFragment(Fragment fragment) {
        setSearchableCallback(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFrame, fragment)
                .commit();
    }

    private void setSearchableCallback(Fragment fragment) {
        if (searchView != null && fragment instanceof SearchView.OnQueryTextListener) {
            searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) fragment);
        }
    }

    @Override
    public void addFragmentBackstack(Fragment fragment) {
        setSearchableCallback(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @DebugLog
    @Override
    public void replaceFragment(Fragment fragment) {
        setSearchableCallback(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, fragment)
                .commit();
    }

    @Override
    public void replaceFragmentBackStack(Fragment fragment) {
        setSearchableCallback(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @DebugLog
    @Override
    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        // pop the callback as well
        setSearchableCallback(getSupportFragmentManager().findFragmentById(R.id.mainFrame));
    }
}
