package es.guillermoorellana.travisforandroid.model;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.api.entity.Repo;
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
        List<Repo> items = asList(mock(Repo.class), mock(Repo.class));
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