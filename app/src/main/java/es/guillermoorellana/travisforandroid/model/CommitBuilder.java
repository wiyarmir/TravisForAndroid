package es.guillermoorellana.travisforandroid.model;

public class CommitBuilder {
    private long commitId;
    private String sha;
    private String branch;
    private String message;
    private long committedAt;
    private String authorName;
    private String authorEmail;
    private String committerName;
    private String committerEmail;
    private String compareUrl;
    private long pullRequestNumber;

    public CommitBuilder setCommitId(long commitId) {
        this.commitId = commitId;
        return this;
    }

    public CommitBuilder setSha(String sha) {
        this.sha = sha;
        return this;
    }

    public CommitBuilder setBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public CommitBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public CommitBuilder setCommittedAt(long committedAt) {
        this.committedAt = committedAt;
        return this;
    }

    public CommitBuilder setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public CommitBuilder setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public CommitBuilder setCommitterName(String committerName) {
        this.committerName = committerName;
        return this;
    }

    public CommitBuilder setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
        return this;
    }

    public CommitBuilder setCompareUrl(String compareUrl) {
        this.compareUrl = compareUrl;
        return this;
    }

    public CommitBuilder setPullRequestNumber(long pullRequestNumber) {
        this.pullRequestNumber = pullRequestNumber;
        return this;
    }

    public GHCommit createCommit() {
        return new GHCommit(commitId, sha, branch, message, committedAt, authorName, authorEmail, committerName, committerEmail, compareUrl, pullRequestNumber);
    }
}