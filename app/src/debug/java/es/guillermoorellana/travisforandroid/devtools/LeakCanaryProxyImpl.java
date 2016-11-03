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

package es.guillermoorellana.travisforandroid.devtools;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class LeakCanaryProxyImpl implements LeakCanaryProxy {
    @NonNull private final Application application;
    @Nullable private RefWatcher refWatcher;

    public LeakCanaryProxyImpl(@NonNull Application application) {
        this.application = application;
    }

    @Override
    public void init() {
        refWatcher = LeakCanary.install(application);
    }

    @Override
    public void watch(@NonNull Object object) {
        if (refWatcher != null) {
            refWatcher.watch(object);
        }
    }
}
