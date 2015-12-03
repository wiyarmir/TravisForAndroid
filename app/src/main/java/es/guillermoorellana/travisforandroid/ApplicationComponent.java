package es.guillermoorellana.travisforandroid;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Component;
import es.guillermoorellana.travisforandroid.io.network.NetworkModule;
import es.guillermoorellana.travisforandroid.model.api.ApiModule;
import es.guillermoorellana.travisforandroid.model.api.ChangeableBaseUrl;
import es.guillermoorellana.travisforandroid.model.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.ui.activity.MainActivity;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class,
        NetworkModule.class
})
public interface ApplicationComponent {
    @NonNull
    TravisRestApi travisRestApi();

    @NonNull
    ChangeableBaseUrl changeableBaseUrl();

    void inject(@NonNull TravisApp travisApp);

    void inject(@NonNull MainActivity mainActivity);
}
