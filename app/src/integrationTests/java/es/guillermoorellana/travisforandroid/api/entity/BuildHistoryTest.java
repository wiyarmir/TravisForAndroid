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
public class BuildHistoryTest {
    private static final String JSON_STRING = "{\n" +
            "    \"builds\": [\n" +
            "        {\n" +
            "            \"id\": 94825892,\n" +
            "            \"repository_id\": 82,\n" +
            "            \"commit_id\": 26947606,\n" +
            "            \"number\": \"1059\",\n" +
            "            \"event_type\": \"pull_request\",\n" +
            "            \"pull_request\": true,\n" +
            "            \"pull_request_title\": \"extended Helper with Forwardable for delegation\",\n" +
            "            \"pull_request_number\": 1053,\n" +
            "            \"config\": {" +
            "            },\n" +
            "            \"state\": \"passed\",\n" +
            "            \"started_at\": \"2015-12-04T08:54:43Z\",\n" +
            "            \"finished_at\": \"2015-12-04T09:00:25Z\",\n" +
            "            \"duration\": 1361,\n" +
            "            \"job_ids\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 94742163,\n" +
            "            \"repository_id\": 82,\n" +
            "            \"commit_id\": 26925170,\n" +
            "            \"number\": \"1058\",\n" +
            "            \"event_type\": \"pull_request\",\n" +
            "            \"pull_request\": true,\n" +
            "            \"pull_request_title\": \"extended Helper with Forwardable for delegation\",\n" +
            "            \"pull_request_number\": 1053,\n" +
            "            \"config\": {},\n" +
            "            \"state\": \"passed\",\n" +
            "            \"started_at\": \"2015-12-03T21:46:07Z\",\n" +
            "            \"finished_at\": \"2015-12-03T21:51:29Z\",\n" +
            "            \"duration\": 1298,\n" +
            "            \"job_ids\": []\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 94707827,\n" +
            "            \"repository_id\": 82,\n" +
            "            \"commit_id\": 26916021,\n" +
            "            \"number\": \"1057\",\n" +
            "            \"event_type\": \"pull_request\",\n" +
            "            \"pull_request\": true,\n" +
            "            \"pull_request_title\": \"Clarify default setting of :static to reflect the Proc\",\n" +
            "            \"pull_request_number\": 1052,\n" +
            "            \"config\": {},\n" +
            "            \"state\": \"passed\",\n" +
            "            \"started_at\": \"2015-12-03T19:08:54Z\",\n" +
            "            \"finished_at\": \"2015-12-03T19:14:30Z\",\n" +
            "            \"duration\": 1406,\n" +
            "            \"job_ids\": []\n" +
            "        }" +
            "    ],\n" +
            "    \"commits\": [\n" +
            "        {\n" +
            "            \"id\": 26947606,\n" +
            "            \"sha\": \"f4823b6e13ca97a40207a9341ed00d1c5f4b9beb\",\n" +
            "            \"branch\": \"master\",\n" +
            "            \"message\": \"explicitly require Forwardable before usage\",\n" +
            "            \"committed_at\": \"2015-12-04T08:54:18Z\",\n" +
            "            \"author_name\": \"Syed Humza Shah\",\n" +
            "            \"author_email\": \"humzashah@gmail.com\",\n" +
            "            \"committer_name\": \"Syed Humza Shah\",\n" +
            "            \"committer_email\": \"humzashah@gmail.com\",\n" +
            "            \"compare_url\": \"https://github.com/sinatra/sinatra/pull/1053\",\n" +
            "            \"pull_request_number\": 1053\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 26925170,\n" +
            "            \"sha\": \"b43754e821ce625b9edf9eb52269f2eaafe89c17\",\n" +
            "            \"branch\": \"master\",\n" +
            "            \"message\": \"extended Helper with Forwardable for delegation\",\n" +
            "            \"committed_at\": \"2015-12-03T21:42:28Z\",\n" +
            "            \"author_name\": \"Syed Humza Shah\",\n" +
            "            \"author_email\": \"humzashah@gmail.com\",\n" +
            "            \"committer_name\": \"Syed Humza Shah\",\n" +
            "            \"committer_email\": \"humzashah@gmail.com\",\n" +
            "            \"compare_url\": \"https://github.com/sinatra/sinatra/pull/1053\",\n" +
            "            \"pull_request_number\": 1053\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 26916021,\n" +
            "            \"sha\": \"2f780e5fea16838c7c67fb7c93dfe516257766e0\",\n" +
            "            \"branch\": \"master\",\n" +
            "            \"message\": \"Clarify default setting of :static to reflect the Proc\",\n" +
            "            \"committed_at\": \"2015-12-03T19:05:29Z\",\n" +
            "            \"author_name\": \"Mike Pastore\",\n" +
            "            \"author_email\": \"mike@oobak.org\",\n" +
            "            \"committer_name\": \"Mike Pastore\",\n" +
            "            \"committer_email\": \"mike@oobak.org\",\n" +
            "            \"compare_url\": \"https://github.com/sinatra/sinatra/pull/1052\",\n" +
            "            \"pull_request_number\": 1052\n" +
            "        }" +
            "    ]\n" +
            "}";

    @Test
    public void fromJson() {
        Gson gson = TravisDroidRobolectricTestRunner.travisApp().applicationComponent().gson();
        BuildHistory build = gson.fromJson(JSON_STRING, BuildHistory.class);
        assertThat(build.getBuilds())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
        assertThat(build.getCommits())
                .isNotNull()
                .isNotEmpty()
                .hasSize(3);
    }
}
