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


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;
import java.util.List;

import es.guillermoorellana.travisforandroid.data.TravisDatabase;

import static com.raizlabs.android.dbflow.annotation.OneToMany.Method.ALL;

@ModelContainer
@Table(database = TravisDatabase.class)
public class Repo extends BaseModel implements Serializable {
    private static final long serialVersionUID = 8763033273883847886L;

    @SuppressWarnings("checkstyle:membername")
    @PrimaryKey(autoincrement = true)
    int _id;

    @Column
    int repoId;

    @Column
    String slug;

    @Column
    boolean active;

    @Column
    String description;

    @Column
    long lastBuildId;

    @Column
    String lastBuildNumber;

    @Column
    String lastBuildState;

    @Column
    long lastBuildDuration;

    @Column
    String lastBuildLanguage;

    @Column
    long lastBuildStartedAt;

    @Column
    long lastBuildFinishedAt;

    @Column
    String githubLanguage;

    List<Build> builds;

    Repo() {
        // for DBFlow to use
    }

    Repo(int repoId, String slug, boolean active, String description, long lastBuildId,
         String lastBuildNumber, String lastBuildState, long lastBuildDuration,
         String lastBuildLanguage, long lastBuildStartedAt, long lastBuildFinishedAt,
         String githubLanguage) {
        this.repoId = repoId;
        this.slug = slug;
        this.active = active;
        this.description = description;
        this.lastBuildId = lastBuildId;
        this.lastBuildNumber = lastBuildNumber;
        this.lastBuildState = lastBuildState;
        this.lastBuildDuration = lastBuildDuration;
        this.lastBuildLanguage = lastBuildLanguage;
        this.lastBuildStartedAt = lastBuildStartedAt;
        this.lastBuildFinishedAt = lastBuildFinishedAt;
        this.githubLanguage = githubLanguage;
    }

    @OneToMany(methods = ALL)
    public List<Build> getBuilds() {
        if (builds == null || builds.isEmpty()) {
            builds = SQLite.select()
                    .from(Build.class)
                    .where(Build_Table.repoForeignKeyContainer__id.eq(_id))
                    .queryList();
        }
        return builds;
    }

    public int getId() {
        return _id;
    }

    public int getRepoId() {
        return repoId;
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

    public long getLastBuildStartedAt() {
        return lastBuildStartedAt;
    }

    public long getLastBuildFinishedAt() {
        return lastBuildFinishedAt;
    }

    public String getGithubLanguage() {
        return githubLanguage;
    }

}
