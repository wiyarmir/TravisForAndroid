
package es.guillermoorellana.travisforandroid.services.network.model.entity;

import java.util.Date;

public class Build {
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
