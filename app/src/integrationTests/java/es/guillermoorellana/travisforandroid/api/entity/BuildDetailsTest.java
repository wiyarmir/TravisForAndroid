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

import org.junit.Test;
import org.junit.runner.RunWith;

import es.guillermoorellana.travisforandroid.TravisDroidRobolectricTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(TravisDroidRobolectricTestRunner.class)
public class BuildDetailsTest {
    private static final String JSON_STRING = "{\n" +
            "    \"build\": {\n" +
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
            "    },\n" +
            "    \"commit\": {\n" +
            "        \"id\": 6534711,\n" +
            "        \"sha\": \"a02354f98395166360cb76a545751f234e5045fd\",\n" +
            "        \"branch\": \"master\",\n" +
            "        \"message\": \"Merge pull request #861 from kant/patch-1\\n\\nUpdate README.es.md\",\n" +
            "        \"committed_at\": \"2014-04-08T19:36:07Z\",\n" +
            "        \"author_name\": \"Konstantin Haase\",\n" +
            "        \"author_email\": \"konstantin.mailinglists@googlemail.com\",\n" +
            "        \"committer_name\": \"Konstantin Haase\",\n" +
            "        \"committer_email\": \"konstantin.mailinglists@googlemail.com\",\n" +
            "        \"compare_url\": \"https://github.com/sinatra/sinatra/compare/1ac65a4089a5...a02354f98395\"\n" +
            "    },\n" +
            "    \"jobs\": [],\n" +
            "    \"annotations\": []\n" +
            "}";

    @Test
    public void fromJson() {
        Gson gson = TravisDroidRobolectricTestRunner.travisApp().applicationComponent().gson();
        BuildDetails buildDetails = gson.fromJson(JSON_STRING, BuildDetails.class);
        assertThat(buildDetails.getBuild()).isNotNull();
    }
}