
package es.guillermoorellana.travisforandroid;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import es.guillermoorellana.travisforandroid.api.ApiModule;
import es.guillermoorellana.travisforandroid.devtools.DevToolsModule;
import es.guillermoorellana.travisforandroid.devtools.LeakCanaryProxy;
import es.guillermoorellana.travisforandroid.io.network.NetworkModule;
import es.guillermoorellana.travisforandroid.services.network.TravisApi;
import es.guillermoorellana.travisforandroid.ui.activity.MainActivity;
import es.guillermoorellana.travisforandroid.ui.fragment.BuildsFragment;
import es.guillermoorellana.travisforandroid.ui.fragment.PRFragment;
import es.guillermoorellana.travisforandroid.ui.fragment.ReposFragment;
import es.guillermoorellana.travisforandroid.ui.fragment.SingleRepoFragment;
import okhttp3.HttpUrl;

@Singleton
@Component(modules = {
    ApplicationModule.class,
    ApiModule.class,
    DevToolsModule.class,
    NetworkModule.class
})
public interface ApplicationComponent {
    @NonNull
    TravisApi travisApi();

    @Named("baseUrl")
    @NonNull
    HttpUrl baseUrl();

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
