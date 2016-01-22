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
import es.guillermoorellana.travisforandroid.api.entity.ApiBuild;
import es.guillermoorellana.travisforandroid.api.entity.ApiBuildHistory;
import es.guillermoorellana.travisforandroid.api.entity.ApiRepo;

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
        mockWebServer.enqueue(new MockResponse().setBody(Fixtures.REPOS_JSON));

        // Get repos from the API
        List<ApiRepo> repos = travisRestApi.repos().toBlocking().value();

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
        mockWebServer.enqueue(new MockResponse().setBody(Fixtures.REPO_JSON));

        ApiRepo repo = travisRestApi.repo(82).toBlocking().value();

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

    @Test
    public void build_byId_shouldHandleCorrectResponse() {
        mockWebServer.enqueue(new MockResponse().setBody(Fixtures.BUILD_HISTORY_JSON));

        ApiBuild build = travisRestApi.build(22555277).toBlocking().value().getBuild();

        assertThat(build.getCommitId()).isEqualTo(6534711L);
        assertThat(build.getDuration()).isEqualTo(2648L);
        assertThat(build.getFinishedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2014-04-08T19:52:56Z"));
        assertThat(build.getId()).isEqualTo(22555277L);
        assertThat(build.getNumber()).isEqualTo("784");
        assertThat(build.isPullRequest()).isEqualTo(true);
        assertThat(build.getPullRequestNumber()).isEqualTo("1912");
        assertThat(build.getPullRequestTitle()).isEqualTo("Example PR");
        assertThat(build.getRepositoryId()).isEqualTo(82);
        assertThat(build.getStartedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2014-04-08T19:37:44Z"));
        assertThat(build.getState()).isEqualTo("failed");
    }

    @Test
    public void buildHistory_byId_shouldHandleCorrectResponse() {
        mockWebServer.enqueue(new MockResponse().setBody(Fixtures.FULL_BUILD_HISTORY_JSON));

        ApiBuildHistory buildHistory = travisRestApi.buildHistory(42).toBlocking().value();
        assertThat(buildHistory).isNotNull();
        assertThat(buildHistory.getBuilds())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);

        ApiBuild build = buildHistory.getBuilds().get(0);

        assertThat(build.getCommitId()).isEqualTo(26947606);
        assertThat(build.getDuration()).isEqualTo(1361L);
        assertThat(build.getFinishedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-12-04T09:00:25Z"));
        assertThat(build.getId()).isEqualTo(94825892L);
        assertThat(build.getNumber()).isEqualTo("1059");
        assertThat(build.isPullRequest()).isEqualTo(true);
        assertThat(build.getPullRequestNumber()).isEqualTo("1053");
        assertThat(build.getPullRequestTitle()).isEqualTo("extended Helper with Forwardable for delegation");
        assertThat(build.getRepositoryId()).isEqualTo(82);
        assertThat(build.getStartedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-12-04T08:54:43Z"));
        assertThat(build.getState()).isEqualTo("passed");
    }
}