/*
 * Copyright 2016 Guillermo Orellana Ruiz
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

package es.guillermoorellana.travisforandroid.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.mvp.BaseFragment;
import es.guillermoorellana.travisforandroid.ui.presenter.BranchesPresenter;
import es.guillermoorellana.travisforandroid.ui.view.BranchesView;

@FragmentWithArgs
public class BranchesFragment extends BaseFragment<BranchesView, BranchesPresenter> {
    @Arg long repoId;

//    private static final int LOADER_ID = 1005;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_branches, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Tracker tracker = TravisApp.get(getContext()).getDefaultTracker();
        tracker.setScreenName("Branches~" + repoId);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public BranchesPresenter createPresenter() {
        return new BranchesPresenter();
    }
}
