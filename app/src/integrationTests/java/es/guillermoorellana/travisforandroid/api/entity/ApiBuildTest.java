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

package es.guillermoorellana.travisforandroid.api.entity;

import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.guillermoorellana.travisforandroid.TravisDroidRobolectricTestRunner;
import es.guillermoorellana.travisforandroid.api.Fixtures;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(TravisDroidRobolectricTestRunner.class)
public class ApiBuildTest {

    @Test
    public void fromJson() {
        Gson gson = TravisDroidRobolectricTestRunner.travisApp().applicationComponent().gson();
        ApiBuild build = gson.fromJson(Fixtures.BUILD_JSON, ApiBuild.class);
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
