/*
 * Copyright 2016 Guillermo Orellana Ruiz
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

public class Fixtures {
    public static final String FULL_BUILD_HISTORY_JSON = "{\n" +
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
    public static final String BUILD_JSON = " {\n" +
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

    public static final String BUILD_HISTORY_JSON = "{\n" +
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
    public static final String COMMIT_JSON = "{\n" +
            "      \"id\": 25061640,\n" +
            "      \"sha\": \"70cdbd4c93ed80a04e16256c780d96a8cdbed84d\",\n" +
            "      \"branch\": \"2.2.0-alpha\",\n" +
            "      \"message\": \"Add POST verb to Rack::File's ALLOWED_VERBS array\\n\\nThis is a commit to make the tests pass.\",\n" +
            "      \"committed_at\": \"2015-10-29T14:39:43Z\",\n" +
            "      \"author_name\": \"Kashyap\",\n" +
            "      \"author_email\": \"kashyap.kmbc@gmail.com\",\n" +
            "      \"committer_name\": \"Kashyap\",\n" +
            "      \"committer_email\": \"kashyap.kmbc@gmail.com\",\n" +
            "      \"compare_url\": \"https://github.com/sinatra/sinatra/pull/1044\",\n" +
            "      \"pull_request_number\": 1044\n" +
            "    }";
    public static final String JOB_JSON = "{\n" +
            "      \"id\": 94825893,\n" +
            "      \"repository_id\": 82,\n" +
            "      \"build_id\": 94825892,\n" +
            "      \"commit_id\": 26947606,\n" +
            "      \"log_id\": 67657671,\n" +
            "      \"state\": \"passed\",\n" +
            "      \"number\": \"1059.1\",\n" +
            "      \"config\": {\n" +
            "        \"language\": \"ruby\",\n" +
            "        \"rvm\": \"1.8.7\",\n" +
            "        \"sudo\": false,\n" +
            "        \".result\": \"configured\",\n" +
            "        \"group\": \"stable\",\n" +
            "        \"dist\": \"precise\",\n" +
            "        \"os\": \"linux\"\n" +
            "      },\n" +
            "      \"started_at\": \"2015-12-04T08:54:43Z\",\n" +
            "      \"finished_at\": \"2015-12-04T08:55:00Z\",\n" +
            "      \"queue\": \"builds.docker\",\n" +
            "      \"allow_failure\": false,\n" +
            "      \"tags\": null,\n" +
            "      \"annotation_ids\": []\n" +
            "    }";
    public static final String REPO_JSON = "{\n" +
            "    \"id\": 82,\n" +
            "    \"slug\": \"sinatra/sinatra\",\n" +
            "    \"active\": true,\n" +
            "    \"description\": \"Classy web-development dressed in a DSL (official / canonical repo)\",\n" +
            "    \"last_build_id\": 94825892,\n" +
            "    \"last_build_number\": \"1059\",\n" +
            "    \"last_build_state\": \"passed\",\n" +
            "    \"last_build_duration\": 1361,\n" +
            "    \"last_build_language\": \"Ruby\",\n" +
            "    \"last_build_started_at\": \"2015-12-04T08:54:43Z\",\n" +
            "    \"last_build_finished_at\": \"2015-12-04T09:00:25Z\",\n" +
            "    \"github_language\": \"Ruby\"\n" +
            "  }";
    public static final String REPOS_JSON = "{\n" +
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
            "}";

    private Fixtures() {
        // noop
    }
}
