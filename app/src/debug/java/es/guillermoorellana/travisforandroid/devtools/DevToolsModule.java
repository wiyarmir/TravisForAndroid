package es.guillermoorellana.travisforandroid.devtools;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.guillermoorellana.travisforandroid.TravisApp;

@Module
public class DevToolsModule {
    @Provides
    @NonNull
    @Singleton
    public LeakCanaryProxy provideLeakCanaryProxy(@NonNull TravisApp travisApp) {
        return new LeakCanaryProxyImpl(travisApp);
    }
}
