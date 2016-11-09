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

public class ApiJob {
    private long id;

    private long repositoryId;

    private long buildId;

    private long commitId;

    private long logId;

    private String state;

    private String number;

    private Date startedAt;

    private Date finishedAt;

    private long duration;

    private String queue;

    private boolean allowFailure;

    public long getId() {
        return id;
    }

    public long getRepositoryId() {
        return repositoryId;
    }

    public long getBuildId() {
        return buildId;
    }

    public long getCommitId() {
        return commitId;
    }

    public long getLogId() {
        return logId;
    }

    public String getState() {
        return state;
    }

    public String getNumber() {
        return number;
    }

    public Date getStartedAt() {
        return new Date(startedAt.getTime());
    }

    public Date getFinishedAt() {
        return new Date(finishedAt.getTime());
    }

    public long getDuration() {
        return duration;
    }

    public String getQueue() {
        return queue;
    }

    public boolean isAllowFailure() {
        return allowFailure;
    }
}
