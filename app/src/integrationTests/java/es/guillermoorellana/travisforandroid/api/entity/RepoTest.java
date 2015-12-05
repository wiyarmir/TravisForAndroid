package es.guillermoorellana.travisforandroid.api.entity;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.guillermoorellana.travisforandroid.TravisDroidRobolectricTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(TravisDroidRobolectricTestRunner.class)
public class RepoTest {
    private static final String JSON_STRING = "{\n" +
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
            "  }";

    @Test
    public void fromJson() {
        Gson gson = TravisDroidRobolectricTestRunner.travisApp().applicationComponent().gson();
        Repo item = gson.fromJson(JSON_STRING, Repo.class);
        assertThat(item.getId()).isEqualTo(82);
        assertThat(item.getSlug()).isEqualTo("sinatra/sinatra");
        assertThat(item.isActive()).isEqualTo(true);
        assertThat(item.getDescription())
                .isEqualTo("Classy web-development dressed in a DSL (official / canonical repo)");
        assertThat(item.getLastBuildId()).isEqualTo(94825892L);
        assertThat(item.getLastBuildNumber()).isEqualTo("1059");
        assertThat(item.getLastBuildState()).isEqualTo("passed");
        assertThat(item.getLastBuildDuration()).isEqualTo(1361);
        assertThat(item.getLastBuildStartedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-12-04T08:54:43Z"));
        assertThat(item.getLastBuildFinishedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-12-04T09:00:25Z"));
        assertThat(item.getGithubLanguage()).isEqualTo("Ruby");
    }
}
