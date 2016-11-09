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

import java.util.Date;

public class ApiBuild {
    private long commitId;
    private long duration;
    private Date finishedAt;
    private long id;
    private String number;
    private boolean pullRequest;
    private String pullRequestNumber;
    private String pullRequestTitle;
    private int repositoryId;
    private Date startedAt;
    private String state;

    public long getCommitId() {
        return commitId;
    }

    public long getDuration() {
        return duration;
    }

    public Date getFinishedAt() {
        return new Date(finishedAt.getTime());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public boolean isPullRequest() {
        return pullRequest;
    }

    public String getPullRequestNumber() {
        return pullRequestNumber;
    }

    public String getPullRequestTitle() {
        return pullRequestTitle;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public Date getStartedAt() {
        return new Date(startedAt.getTime());
    }

    public String getState() {
        return state;
    }
}
