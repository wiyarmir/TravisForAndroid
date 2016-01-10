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

import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.api.entity.ApiBuildHistory;
import rx.Single;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BuildHistoryModelTest {
    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private TravisRestApi travisRestApi;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private BuildHistoryModel buildHistoryModel;

    @Before
    public void beforeEachTest() {
        travisRestApi = mock(TravisRestApi.class);
        buildHistoryModel = new BuildHistoryModel(travisRestApi);
    }

    @Test
    public void getBuildHistory_shouldReturnBuildHistoryFromTravisApi() {
        ApiBuildHistory buildHistory = mock(ApiBuildHistory.class);
        when(travisRestApi.buildHistory(anyInt())).thenReturn(Single.just(buildHistory));

        assertThat(buildHistoryModel.getBuildHistory(42).toBlocking().value()).isEqualTo(buildHistory);
    }

    @Test
    public void getRepos_shouldReturnErrorFromTravisApi() {
        Exception error = new RuntimeException();
        when(travisRestApi.buildHistory(anyInt())).thenReturn(Single.error(error));

        try {
            buildHistoryModel.getBuildHistory(42).toBlocking().value();
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (Exception expected) {
            assertThat(expected).isSameAs(error);
        }
    }
}
