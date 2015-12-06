package es.guillermoorellana.travisforandroid;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import es.guillermoorellana.travisforandroid.devtools.DevToolsModule;
import es.guillermoorellana.travisforandroid.devtools.LeakCanaryProxy;
import es.guillermoorellana.travisforandroid.io.network.NetworkModule;
import es.guillermoorellana.travisforandroid.api.ApiModule;
import es.guillermoorellana.travisforandroid.api.ChangeableBaseUrl;
import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.ui.activity.MainActivity;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class,
        DevToolsModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {
    @NonNull
    TravisRestApi travisRestApi();

    @NonNull
    ChangeableBaseUrl changeableBaseUrl();

    @NonNull
    Gson gson();

    @NonNull
    LeakCanaryProxy leakCanaryProxy();

    void inject(@NonNull TravisApp travisApp);

    void inject(@NonNull MainActivity mainActivity);
}
