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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import es.guillermoorellana.travisforandroid.TravisDroidRobolectricTestRunner;
import es.guillermoorellana.travisforandroid.api.entity.ApiRepo;
import es.guillermoorellana.travisforandroid.model.RepoModel;
import es.guillermoorellana.travisforandroid.mvp.BaseRxLcePresenterTest;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;
import rx.Single;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(TravisDroidRobolectricTestRunner.class)
public class ReposPresenterTest extends BaseRxLcePresenterTest<ReposView, List<ApiRepo>> {

    private ReposPresenter presenter;
    private RepoModel repoModel;

    @BeforeClass
    public static void setUpBeforeClass()  {
        testImpl = new ReposPresenter(mock(RepoModel.class));
    }

    @Before
    public void setUp() {
        repoModel = mock(RepoModel.class);
        presenter = new ReposPresenter(repoModel);
    }

    @Test
    public void reloadData_shouldShowLoadingUi() {
        ReposView view = mock(ReposView.class);
        when(repoModel.getRepos()).thenReturn(Single.just(null));
        presenter.attachView(view);

        presenter.reloadData(false);

        verify(view).showLoading(false);
    }
}