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

package es.guillermoorellana.travisforandroid;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import es.guillermoorellana.travisforandroid.api.ApiModule;
import es.guillermoorellana.travisforandroid.api.ChangeableBaseUrl;
import es.guillermoorellana.travisforandroid.devtools.DevToolsModule;
import es.guillermoorellana.travisforandroid.devtools.LeakCanaryProxy;
import es.guillermoorellana.travisforandroid.io.network.NetworkModule;
import es.guillermoorellana.travisforandroid.services.network.TravisApi;
import es.guillermoorellana.travisforandroid.ui.activity.MainActivity;
import es.guillermoorellana.travisforandroid.ui.fragment.BuildsFragment;
import es.guillermoorellana.travisforandroid.ui.fragment.PRFragment;
import es.guillermoorellana.travisforandroid.ui.fragment.ReposFragment;
import es.guillermoorellana.travisforandroid.ui.fragment.SingleRepoFragment;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    ApiModule.class,
    DevToolsModule.class,
    NetworkModule.class
})
public interface ApplicationComponent {
    @NonNull
    TravisApi travisRestApi();

    @NonNull
    ChangeableBaseUrl changeableBaseUrl();

    @NonNull
    Gson gson();

    @NonNull
    LeakCanaryProxy leakCanaryProxy();

    ReposFragment.ReposFragmentComponent plus(@NonNull ReposFragment.ReposFragmentModule reposFragmentModule);

    BuildsFragment.BuildsFragmentComponent plus(@NonNull BuildsFragment.BuildsFragmentModule buildsFragmentModule);

    SingleRepoFragment.SingleRepoFragmentComponent plus(@NonNull SingleRepoFragment.SingleRepoFragmentModule singleRepoFragmentComponent);

    PRFragment.PRFragmentComponent plus(@NonNull PRFragment.PRFragmentModule prFragmentModule);

    void inject(@NonNull TravisApp travisApp);

    void inject(@NonNull MainActivity mainActivity);
}
