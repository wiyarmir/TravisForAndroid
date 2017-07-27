
package es.guillermoorellana.travisforandroid.services.network.model.entity;


import java.io.Serializable;
import java.util.Date;

public class Repo implements Serializable {
    private static final long serialVersionUID = 8763033273883847886L;

    private int id;
    private String slug;
    private boolean active;
    private String description;
    private long lastBuildId;
    private String lastBuildNumber;
    private String lastBuildState;
    private long lastBuildDuration;
    private String lastBuildLanguage;
    private Date lastBuildStartedAt;
    private Date lastBuildFinishedAt;
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

    public long getLastBuildId() {
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

    public Date getLastBuildStartedAt() {
        return new Date(lastBuildStartedAt.getTime());
    }

    public Date getLastBuildFinishedAt() {
        return new Date(lastBuildFinishedAt.getTime());
    }

    public String getGithubLanguage() {
        return githubLanguage;
    }
}
