package es.guillermoorellana.travisforandroid.api.entity;

import org.joda.time.DateTime;

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

    public DateTime getFinishedAt() {
        return new DateTime(finishedAt);
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

    public DateTime getStartedAt() {
        return new DateTime(startedAt);
    }

    public String getState() {
        return state;
    }
}
