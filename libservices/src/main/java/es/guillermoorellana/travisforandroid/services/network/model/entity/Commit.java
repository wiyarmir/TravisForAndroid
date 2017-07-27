package es.guillermoorellana.travisforandroid.services.network.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Commit implements Serializable {
    private static final long serialVersionUID = 4390847522215353298L;
    public static final int SHORT_SHA_LENGTH = 7;

    private long id;
    private String sha;
    private String branch;
    private String message;
    private Date committedAt;
    private String authorName;
    private String authorEmail;
    private String committerName;
    private String committerEmail;
    private String compareUrl;
    private long pullRequestNumber;

    public long getId() {
        return id;
    }

    public String getSha() {
        return sha;
    }

    public String getShortSha() {
        return sha.substring(0, SHORT_SHA_LENGTH - 1);
    }

    public String getBranch() {
        return branch;
    }

    public String getMessage() {
        return message;
    }

    public Date getCommittedAt() {
        return new Date(committedAt.getTime());
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public String getCommitterName() {
        return committerName;
    }

    public String getCommitterEmail() {
        return committerEmail;
    }

    public String getCompareUrl() {
        return compareUrl;
    }

    public long getPullRequestNumber() {
        return pullRequestNumber;
    }
}
