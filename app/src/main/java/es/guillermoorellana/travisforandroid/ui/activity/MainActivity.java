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

import android.os.Bundle;
import android.support.v4.app.Fragment;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.ui.fragment.ReposFragment;
import es.guillermoorellana.travisforandroid.ui.view.MainView;
import hugo.weaving.DebugLog;

public class MainActivity extends BaseActivity implements MainView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new ReposFragment());
        }
    }

    @DebugLog
    @Override
    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFrame, fragment)
                .commit();
    }

    @Override
    public void addFragmentBackstack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @DebugLog
    @Override
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, fragment)
                .commit();
    }

    @Override
    public void replaceFragmentBackStack(Fragment fragment) {
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
    }
}
