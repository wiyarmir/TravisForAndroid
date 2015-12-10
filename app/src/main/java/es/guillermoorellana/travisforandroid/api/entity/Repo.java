/*
 * Copyright 2015 Guillermo Orellana Ruiz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.guillermoorellana.travisforandroid.api.entity;


import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

public class Repo implements Serializable {
    private static final long serialVersionUID = 8763033273883847886L;

    private int id;
    private String slug;
    private boolean active;
    private String description;
    private int lastBuildId;
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

    public DateTime getLastBuildStartedAt() {
        return new DateTime(lastBuildStartedAt);
    }

    public DateTime getLastBuildFinishedAt() {
        return new DateTime(lastBuildFinishedAt);
    }

    public String getGithubLanguage() {
        return githubLanguage;
    }
}
