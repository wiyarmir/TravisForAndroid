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
