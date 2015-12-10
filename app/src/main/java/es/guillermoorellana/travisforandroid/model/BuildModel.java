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

package es.guillermoorellana.travisforandroid.model;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import java.util.List;

import javax.inject.Inject;

import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.api.entity.Build;
import es.guillermoorellana.travisforandroid.api.entity.BuildHistory;
import rx.Single;

public class BuildModel {

    private final TravisRestApi mTravisRestApi;

    @Inject
    public BuildModel(@NonNull TravisRestApi travisRestApi) {
        mTravisRestApi = travisRestApi;
    }

    @RxLogObservable
    public Single<List<Build>> getBuilds(String slug) {
        String[] split = slug.split("/");
        return mTravisRestApi.buildsHistory(split[0], split[1])
                .map(BuildHistory::getBuilds);
    }
}
