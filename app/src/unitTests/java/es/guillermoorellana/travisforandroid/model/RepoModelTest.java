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
import es.guillermoorellana.travisforandroid.api.entity.ApiRepo;
import rx.Single;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RepoModelTest {

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private TravisRestApi travisRestApi;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private RepoModel repoModel;

    @Before
    public void beforeEachTest() {
        travisRestApi = mock(TravisRestApi.class);
        repoModel = new RepoModel(travisRestApi);
    }

    @Test
    public void getRepos_shouldReturnReposFromTravisApi() {
        List<ApiRepo> items = asList(mock(ApiRepo.class), mock(ApiRepo.class));
        when(travisRestApi.repos()).thenReturn(Single.just(items));

        assertThat(repoModel.getRepos().toBlocking().value()).containsExactlyElementsOf(items);
    }

    @Test
    public void getRepos_shouldReturnErrorFromTravisApi() {
        Exception error = new RuntimeException();
        when(travisRestApi.repos()).thenReturn(Single.error(error));

        try {
            repoModel.getRepos().toBlocking().value();
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (Exception expected) {
            assertThat(expected).isSameAs(error);
        }
    }
}