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

package es.guillermoorellana.travisforandroid.ui.presenter;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.guillermoorellana.travisforandroid.TravisDroidRobolectricTestRunner;
import es.guillermoorellana.travisforandroid.api.entity.ApiBuildHistory;
import es.guillermoorellana.travisforandroid.api.entity.ApiRepo;
import es.guillermoorellana.travisforandroid.model.BuildHistoryModel;
import es.guillermoorellana.travisforandroid.mvp.BaseRxLcePresenterTest;
import es.guillermoorellana.travisforandroid.ui.view.BuildHistoryView;
import rx.Single;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(TravisDroidRobolectricTestRunner.class)
public class BuildHistoryPresenterTest extends BaseRxLcePresenterTest<BuildHistoryView, ApiBuildHistory> {
    private BuildHistoryPresenter presenter;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private ApiRepo repo;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    private BuildHistoryModel buildHistoryModel;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        testImpl = new BuildHistoryPresenter(mock(BuildHistoryModel.class), mock(ApiRepo.class));
    }

    @Before
    public void setUp() {
        repo = mock(ApiRepo.class);
        buildHistoryModel = mock(BuildHistoryModel.class);
        presenter = new BuildHistoryPresenter(buildHistoryModel, repo);
    }

    @Test
    public void reloadData_shouldShowLoadingView() {
        BuildHistoryView view = mock(BuildHistoryView.class);
        when(buildHistoryModel.getBuildHistory(anyLong())).thenReturn(Single.just(null));
        presenter.attachView(view);

        presenter.reloadData(false);

        verify(view).showLoading(false);
    }
    
}