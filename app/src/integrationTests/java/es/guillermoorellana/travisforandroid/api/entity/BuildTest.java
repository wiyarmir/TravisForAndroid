package es.guillermoorellana.travisforandroid.api.entity;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.guillermoorellana.travisforandroid.TravisDroidRobolectricTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(TravisDroidRobolectricTestRunner.class)
public class BuildTest {
    private static final String JSON_STRING = " {\n" +
            "      \"commit_id\": 6534711,\n" +
            "      \"config\": { },\n" +
            "      \"duration\": 2648,\n" +
            "      \"finished_at\": \"2014-04-08T19:52:56Z\",\n" +
            "      \"id\": 22555277,\n" +
            "      \"job_ids\": [22555278, 22555279, 22555280, 22555281],\n" +
            "      \"number\": \"784\",\n" +
            "      \"pull_request\": true,\n" +
            "      \"pull_request_number\": \"1912\",\n" +
            "      \"pull_request_title\": \"Example PR\",\n" +
            "      \"repository_id\": 82,\n" +
            "      \"started_at\": \"2014-04-08T19:37:44Z\",\n" +
            "      \"state\": \"failed\"\n" +
            "    }";

    @Test
    public void fromJson() {
        Gson gson = TravisDroidRobolectricTestRunner.travisApp().applicationComponent().gson();
        Build build = gson.fromJson(JSON_STRING, Build.class);
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
}
