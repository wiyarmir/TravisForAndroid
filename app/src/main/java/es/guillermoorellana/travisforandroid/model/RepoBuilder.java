package es.guillermoorellana.travisforandroid.model;

public class RepoBuilder {
    private int id;
    private String slug;
    private boolean active;
    private String description;
    private long lastBuildId;
    private String lastBuildNumber;
    private String lastBuildState;
    private long lastBuildDuration;
    private String lastBuildLanguage;
    private long lastBuildStartedAt;
    private long lastBuildFinishedAt;
    private String githubLanguage;

    public RepoBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public RepoBuilder setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public RepoBuilder setActive(boolean active) {
        this.active = active;
        return this;
    }

    public RepoBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public RepoBuilder setLastBuildId(long lastBuildId) {
        this.lastBuildId = lastBuildId;
        return this;
    }

    public RepoBuilder setLastBuildNumber(String lastBuildNumber) {
        this.lastBuildNumber = lastBuildNumber;
        return this;
    }

    public RepoBuilder setLastBuildState(String lastBuildState) {
        this.lastBuildState = lastBuildState;
        return this;
    }

    public RepoBuilder setLastBuildDuration(long lastBuildDuration) {
        this.lastBuildDuration = lastBuildDuration;
        return this;
    }

    public RepoBuilder setLastBuildLanguage(String lastBuildLanguage) {
        this.lastBuildLanguage = lastBuildLanguage;
        return this;
    }

    public RepoBuilder setLastBuildStartedAt(long lastBuildStartedAt) {
        this.lastBuildStartedAt = lastBuildStartedAt;
        return this;
    }

    public RepoBuilder setLastBuildFinishedAt(long lastBuildFinishedAt) {
        this.lastBuildFinishedAt = lastBuildFinishedAt;
        return this;
    }

    public RepoBuilder setGithubLanguage(String githubLanguage) {
        this.githubLanguage = githubLanguage;
        return this;
    }

    public Repo createRepo() {
        return new Repo(id,
                slug,
                active,
                description,
                lastBuildId,
                lastBuildNumber,
                lastBuildState,
                lastBuildDuration,
                lastBuildLanguage,
                lastBuildStartedAt,
                lastBuildFinishedAt,
                githubLanguage
        );
    }
}
