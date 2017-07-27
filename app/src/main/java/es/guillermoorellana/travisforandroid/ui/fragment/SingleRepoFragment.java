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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.model.Repo;
import es.guillermoorellana.travisforandroid.model.Repo_Table;
import es.guillermoorellana.travisforandroid.mvp.BaseFragment;
import es.guillermoorellana.travisforandroid.ui.presenter.SingleRepoPresenter;
import es.guillermoorellana.travisforandroid.ui.view.SingleRepoView;

@FragmentWithArgs
public class SingleRepoFragment
        extends BaseFragment<SingleRepoView, SingleRepoPresenter>
        implements SingleRepoView {

    @Arg long repoId;

    @NonNull
    @Inject
    SingleRepoPresenter singlreRepoPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
        TravisApp.get(getContext()).applicationComponent()
                .plus(new SingleRepoFragmentModule())
                .inject(this);
    }

    static class PagerAdapter extends FragmentPagerAdapter {
        private static final int FRAGMENT_BUILDS = 0;
        private static final int FRAGMENT_BRANCHES = 1;
        private static final int FRAGMENT_PR = 2;
        private static final int[] PAGES = {FRAGMENT_BUILDS, FRAGMENT_BRANCHES, FRAGMENT_PR};
        private final long repoId;

        public PagerAdapter(FragmentManager fragmentManager, long repoId) {
            super(fragmentManager);
            this.repoId = repoId;
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_BUILDS:
                    return BuildsFragmentBuilder.newBuildsFragment(repoId);
                case FRAGMENT_BRANCHES:
                    return BranchesFragmentBuilder.newBranchesFragment(repoId);
                case FRAGMENT_PR:
                    return PRFragmentBuilder.newPRFragment(repoId);
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case FRAGMENT_BUILDS:
                    return "Builds";
                case FRAGMENT_BRANCHES:
                    return "Branches";
                case FRAGMENT_PR:
                    return "Pull Requests";
                default:
                    return null;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_singlerepo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPagerAndTabs(view);
        getMainView().setTitle(
                SQLite.select(Repo_Table.slug)
                        .from(Repo.class)
                        .where(Repo_Table.repoId.eq((int) repoId))
                        .querySingle()
                        .getSlug()
        );
    }

    @Override
    public SingleRepoPresenter createPresenter() {
        return singlreRepoPresenter;
    }

    private void initViewPagerAndTabs(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), repoId);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Subcomponent(modules = SingleRepoFragmentModule.class)
    public interface SingleRepoFragmentComponent {
        void inject(@NonNull SingleRepoFragment singleRepoFragment);
    }

    @Module
    public class SingleRepoFragmentModule extends ReposFragment.ReposFragmentModule {
        @Provides
        @NonNull
        public SingleRepoPresenter provideSingleRepoPresenter() {
            return new SingleRepoPresenter();
        }
    }
}
