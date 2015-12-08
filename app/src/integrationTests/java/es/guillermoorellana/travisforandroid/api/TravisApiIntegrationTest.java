package es.guillermoorellana.travisforandroid.api;

import android.support.annotation.NonNull;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import es.guillermoorellana.travisforandroid.TravisDroidRobolectricTestRunner;
import es.guillermoorellana.travisforandroid.api.entity.Repo;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(TravisDroidRobolectricTestRunner.class)
public class TravisApiIntegrationTest {
    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    MockWebServer mockWebServer;

    @SuppressWarnings("NullableProblems") // Initialized in @Before.
    @NonNull
    TravisRestApi travisRestApi;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        // Change base url to the mocked
        TravisDroidRobolectricTestRunner.travisApp().applicationComponent()
                .changeableBaseUrl()
                .setBaseUrl(mockWebServer.url("").toString());

        travisRestApi = TravisDroidRobolectricTestRunner.travisApp().applicationComponent()
                .travisRestApi();
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void repos_shouldHandleCorrectResponse() {
        mockWebServer.enqueue(new MockResponse().setBody("{\n" +
                "   \"repos\":[\n" +
                "      {\n" +
                "         \"id\":6683949,\n" +
                "         \"slug\":\"pugachAG/PointOfSaleTerminal\",\n" +
                "         \"description\":\"Point-of-sale scanning API task\",\n" +
                "         \"last_build_id\":95087455,\n" +
                "         \"last_build_number\":\"7\",\n" +
                "         \"last_build_state\":\"started\",\n" +
                "         \"last_build_duration\":null,\n" +
                "         \"last_build_language\":null,\n" +
                "         \"last_build_started_at\":\"2015-12-05T18:37:52Z\",\n" +
                "         \"last_build_finished_at\":null,\n" +
                "         \"active\":true,\n" +
                "         \"github_language\":\"C#\"\n" +
                "      },\n" +
                "      {\n" +
                "    \"id\": 82,\n" +
                "    \"slug\": \"sinatra/sinatra\",\n" +
                "    \"active\": true,\n" +
                "    \"description\": \"Classy web-development dressed in a DSL (official / canonical repo)\",\n" +
                "    \"last_build_id\": 94825892,\n" +
                "    \"last_build_number\": \"1059\",\n" +
                "    \"last_build_state\": \"passed\",\n" +
                "    \"last_build_duration\": 1361,\n" +
                "    \"last_build_language\": null,\n" +
                "    \"last_build_started_at\": \"2015-12-04T08:54:43Z\",\n" +
                "    \"last_build_finished_at\": \"2015-12-04T09:00:25Z\",\n" +
                "    \"github_language\": \"Ruby\"\n" +
                "  },\n" +
                "      {\n" +
                "         \"id\":4089,\n" +
                "         \"slug\":\"symfony/symfony\",\n" +
                "         \"description\":\"The Symfony PHP framework\",\n" +
                "         \"last_build_id\":95086758,\n" +
                "         \"last_build_number\":\"27161\",\n" +
                "         \"last_build_state\":\"started\",\n" +
                "         \"last_build_duration\":null,\n" +
                "         \"last_build_language\":null,\n" +
                "         \"last_build_started_at\":\"2015-12-05T18:37:50Z\",\n" +
                "         \"last_build_finished_at\":null,\n" +
                "         \"active\":true,\n" +
                "         \"github_language\":\"PHP\"\n" +
                "      }\n" +
                "   ]\n" +
                "}"));

        // Get repos from the API
        List<Repo> repos = travisRestApi.repos().toBlocking().value();

        assertThat(repos).hasSize(3);

        // TODO test item #0

        assertThat(repos.get(1).getId()).isEqualTo(82);
        assertThat(repos.get(1).getSlug()).isEqualTo("sinatra/sinatra");
        assertThat(repos.get(1).isActive()).isEqualTo(true);
        assertThat(repos.get(1).getDescription())
                .isEqualTo("Classy web-development dressed in a DSL (official / canonical repo)");
        assertThat(repos.get(1).getLastBuildId()).isEqualTo(94825892L);
        assertThat(repos.get(1).getLastBuildNumber()).isEqualTo("1059");
        assertThat(repos.get(1).getLastBuildState()).isEqualTo("passed");
        assertThat(repos.get(1).getLastBuildDuration()).isEqualTo(1361);
        assertThat(repos.get(1).getLastBuildStartedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-12-04T08:54:43Z"));
        assertThat(repos.get(1).getLastBuildFinishedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-12-04T09:00:25Z"));
        assertThat(repos.get(1).getGithubLanguage()).isEqualTo("Ruby");

        // TODO test item #2
    }

    @Test
    public void repo_byId_shouldHandleCorrectResponse() {
        mockWebServer.enqueue(new MockResponse().setBody("{\n" +
                "  \"repo\": {\n" +
                "    \"id\": 82,\n" +
                "    \"slug\": \"sinatra/sinatra\",\n" +
                "    \"active\": true,\n" +
                "    \"description\": \"Classy web-development dressed in a DSL (official / canonical repo)\",\n" +
                "    \"last_build_id\": 94825892,\n" +
                "    \"last_build_number\": \"1059\",\n" +
                "    \"last_build_state\": \"passed\",\n" +
                "    \"last_build_duration\": 1361,\n" +
                "    \"last_build_language\": null,\n" +
                "    \"last_build_started_at\": \"2015-12-04T08:54:43Z\",\n" +
                "    \"last_build_finished_at\": \"2015-12-04T09:00:25Z\",\n" +
                "    \"github_language\": \"Ruby\"\n" +
                "  }\n" +
                "}"));

        Repo repo = travisRestApi.repo(82).toBlocking().value();

        assertThat(repo.getId()).isEqualTo(82);
        assertThat(repo.getSlug()).isEqualTo("sinatra/sinatra");
        assertThat(repo.isActive()).isEqualTo(true);
        assertThat(repo.getDescription())
                .isEqualTo("Classy web-development dressed in a DSL (official / canonical repo)");
        assertThat(repo.getLastBuildId()).isEqualTo(94825892L);
        assertThat(repo.getLastBuildNumber()).isEqualTo("1059");
        assertThat(repo.getLastBuildState()).isEqualTo("passed");
        assertThat(repo.getLastBuildDuration()).isEqualTo(1361);
        assertThat(repo.getLastBuildStartedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-12-04T08:54:43Z"));
        assertThat(repo.getLastBuildFinishedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-12-04T09:00:25Z"));
        assertThat(repo.getGithubLanguage()).isEqualTo("Ruby");
    }
}