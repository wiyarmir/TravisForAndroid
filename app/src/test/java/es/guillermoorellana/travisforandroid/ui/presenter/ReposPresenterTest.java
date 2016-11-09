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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import es.guillermoorellana.travisforandroid.data.Repository;
import es.guillermoorellana.travisforandroid.ui.view.ReposView;
import rx.Single;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ReposPresenterTest {

    private ReposPresenter presenter;
    private Repository repository;

    @Before
    public void setUp() {
        repository = mock(Repository.class);
        presenter = new ReposPresenter(repository, Schedulers.immediate());
    }

    @Test
    public void reloadData_shouldShowLoadingUi() {
        ReposView view = mock(ReposView.class);
        when(repository.getRepos()).thenReturn(Single.just(null));
        presenter.attachView(view);

        presenter.reloadData();

        verify(repository).getRepos();
    }
}