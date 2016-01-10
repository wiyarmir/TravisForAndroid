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

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowManager;

import net.danlew.android.joda.JodaTimeAndroid;

import es.guillermoorellana.travisforandroid.api.ApiModule;
import es.guillermoorellana.travisforandroid.devtools.DevTools;
import timber.log.Timber;

public class TravisApp extends Application {

    @SuppressWarnings("NullableProblems")
    // Initialized in onCreate. But be careful if you have ContentProviders in different processes -> their onCreate will be called before app.onCreate().
    @NonNull
    private ApplicationComponent applicationComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static TravisApp get(@NonNull Context context) {
        return (TravisApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Since this is app for developers we can log even in release build.
        Timber.plant(new Timber.DebugTree());

        JodaTimeAndroid.init(this);

        FlowManager.init(this);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule("https://api.travis-ci.org/"))
                .build();

        applicationComponent.inject(this);

        developerTools();
    }

    // To be overriden in robolectric tests
    protected void developerTools() {
        DevTools.init(this);
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
