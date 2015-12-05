package es.guillermoorellana.travisforandroid.api.entities;

import org.joda.time.DateTime;

public class Repo {
    private int id;
    private String slug;
    private boolean active;
    private String description;
    private int lastBuildId;
    private String lastBuildNumber;
    private String lastBuildState;
    private long lastBuildDuration;
    private String lastBuildLanguage;
    private DateTime lastBuildStartedAt;
    private DateTime lastBuildFinishedAt;
    private String githubLanguage;

    public int getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    public boolean isActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }

    public int getLastBuildId() {
        return lastBuildId;
    }

    public String getLastBuildNumber() {
        return lastBuildNumber;
    }

    public String getLastBuildState() {
        return lastBuildState;
    }

    public long getLastBuildDuration() {
        return lastBuildDuration;
    }

    public String getLastBuildLanguage() {
        return lastBuildLanguage;
    }

    public DateTime getLastBuildStartedAt() {
        return lastBuildStartedAt;
    }

    public DateTime getLastBuildFinishedAt() {
        return lastBuildFinishedAt;
    }

    public String getGithubLanguage() {
        return githubLanguage;
    }
}
