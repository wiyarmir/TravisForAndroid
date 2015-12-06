package es.guillermoorellana.travisforandroid.devtools;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DevToolsModule {
    @Provides
    @NonNull
    @Singleton
    public LeakCanaryProxy provideLeakCanaryProxy() {
        return new NoOpLeakCanaryProxy();
    }
}
