/*
 *   Copyright 2015 Guillermo Orellana Ruiz
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
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
public class ApiGHCommitTest {

    @Test
    public void fromJson() {
        Gson gson = TravisDroidRobolectricTestRunner.travisApp().applicationComponent().gson();
        ApiCommit commit = gson.fromJson(Fixtures.COMMIT_JSON, ApiCommit.class);
        assertThat(commit.getId()).isEqualTo(25061640);
        assertThat(commit.getSha()).isEqualTo("70cdbd4c93ed80a04e16256c780d96a8cdbed84d");
        assertThat(commit.getShortSha()).isEqualTo(commit.getSha().substring(0, ApiCommit.SHORT_SHA_LENGTH - 1));
        assertThat(commit.getBranch()).isEqualTo("2.2.0-alpha");
        assertThat(commit.getMessage()).isEqualTo("Add POST verb to Rack::File's ALLOWED_VERBS array\n\nThis is a commit to make the tests pass.");
        assertThat(commit.getCommittedAt())
                .usingComparator(DateTimeComparator.getInstance())
                .isEqualTo(DateTime.parse("2015-10-29T14:39:43Z"));
        assertThat(commit.getAuthorName()).isEqualTo("Kashyap");
        assertThat(commit.getAuthorEmail()).isEqualTo("kashyap.kmbc@gmail.com");
        assertThat(commit.getCommitterName()).isEqualTo("Kashyap");
        assertThat(commit.getCommitterEmail()).isEqualTo("kashyap.kmbc@gmail.com");
        assertThat(commit.getCompareUrl()).isEqualTo("https://github.com/sinatra/sinatra/pull/1044");
        assertThat(commit.getPullRequestNumber()).isEqualTo(1044L);
    }
}