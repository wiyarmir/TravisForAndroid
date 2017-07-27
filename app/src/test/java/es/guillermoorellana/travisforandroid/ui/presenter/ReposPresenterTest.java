
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