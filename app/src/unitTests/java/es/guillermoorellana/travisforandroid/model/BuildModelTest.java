package es.guillermoorellana.travisforandroid.model;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.TravisRestApi;
import es.guillermoorellana.travisforandroid.api.entity.Build;
import es.guillermoorellana.travisforandroid.api.entity.BuildHistory;
import es.guillermoorellana.travisforandroid.api.entity.Repo;
import rx.Single;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.mockito.Matchers.any;
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
        when(travisRestApi.buildsHistory(anyString())).thenReturn(Single.just(item));

        assertThat(buildModel.getBuilds("slug").toBlocking().value()).containsExactlyElementsOf(list);
    }

    @Test
    public void getRepos_shouldReturnErrorFromTravisApi() {
        Exception error = new RuntimeException();
        when(travisRestApi.buildsHistory(anyString())).thenReturn(Single.error(error));

        try {
            buildModel.getBuilds("slug").toBlocking().value();
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (Exception expected) {
            assertThat(expected).isSameAs(error);
        }
    }
}