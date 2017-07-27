package es.guillermoorellana.travisforandroid.model;

import org.joda.time.DateTime;

import java.util.Date;

public class Job {
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

    public DateTime getStartedAt() {
        return new DateTime(startedAt);
    }

    public DateTime getFinishedAt() {
        return new DateTime(finishedAt);
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
