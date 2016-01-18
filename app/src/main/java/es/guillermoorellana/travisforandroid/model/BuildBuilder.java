/*
 *   Copyright 2016 Guillermo Orellana Ruiz
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

package es.guillermoorellana.travisforandroid.model;

public class BuildBuilder {
    private long commitId;
    private long duration;
    private long finishedAt;
    private long id;
    private String number;
    private boolean pullRequest;
    private String pullRequestNumber;
    private String pullRequestTitle;
    private int repositoryId;
    private long startedAt;
    private String state;

    public BuildBuilder setCommitId(long commitId) {
        this.commitId = commitId;
        return this;
    }

    public BuildBuilder setDuration(long duration) {
        this.duration = duration;
        return this;
    }

    public BuildBuilder setFinishedAt(long finishedAt) {
        this.finishedAt = finishedAt;
        return this;
    }

    public BuildBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public BuildBuilder setNumber(String number) {
        this.number = number;
        return this;
    }

    public BuildBuilder setPullRequest(boolean pullRequest) {
        this.pullRequest = pullRequest;
        return this;
    }

    public BuildBuilder setPullRequestNumber(String pullRequestNumber) {
        this.pullRequestNumber = pullRequestNumber;
        return this;
    }

    public BuildBuilder setPullRequestTitle(String pullRequestTitle) {
        this.pullRequestTitle = pullRequestTitle;
        return this;
    }

    public BuildBuilder setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
        return this;
    }

    public BuildBuilder setStartedAt(long startedAt) {
        this.startedAt = startedAt;
        return this;
    }

    public BuildBuilder setState(String state) {
        this.state = state;
        return this;
    }

    public Build createBuild() {
        return new Build(commitId,
                duration,
                finishedAt,
                id,
                number,
                pullRequest,
                pullRequestNumber,
                pullRequestTitle,
                repositoryId,
                startedAt,
                state);
    }
}
