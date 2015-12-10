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

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.api.entity.Build;
import es.guillermoorellana.travisforandroid.api.entity.BuildHistory;
import rx.Single;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuildModelTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private TravisRestApi travisRestApi;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private BuildModel buildModel;

    @Before
    public void beforeEachTest() {
        travisRestApi = mock(TravisRestApi.class);
        buildModel = new BuildModel(travisRestApi);
    }

    @Test
    public void getRepos_shouldReturnReposFromTravisApi() {
        BuildHistory item = mock(BuildHistory.class);
        List<Build> list = asList(mock(Build.class), mock(Build.class));
        when(item.getBuilds()).thenReturn(list);
        when(travisRestApi.buildHistory(anyString(), anyString())).thenReturn(Single.just(item));

        assertThat(buildModel.getBuilds("sl/ug").toBlocking().value()).containsExactlyElementsOf(list);
    }

    @Test
    public void getRepos_shouldReturnErrorFromTravisApi() {
        Exception error = new RuntimeException();
        when(travisRestApi.buildHistory(anyString(), anyString())).thenReturn(Single.error(error));

        try {
            buildModel.getBuilds("sl/ug").toBlocking().value();
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (Exception expected) {
            assertThat(expected).isSameAs(error);
        }
    }
}