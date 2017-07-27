package es.guillermoorellana.travisforandroid.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import es.guillermoorellana.travisforandroid.data.TravisDatabase;

@Table(database = TravisDatabase.class, insertConflict = ConflictAction.REPLACE, updateConflict = ConflictAction.REPLACE)
public class Build extends BaseModel {
    @SuppressWarnings("checkstyle:membername")
    @PrimaryKey(autoincrement = true)
    int _id;

    GHCommit commit;

    @Column
    long commitId;

    @Column
    long duration;

    @Column
    long finishedAt;

    @Unique(onUniqueConflict = ConflictAction.REPLACE)
    @Column
    long id;

    @Column
    String number;

    @Column
    boolean pullRequest;

    @Column
    String pullRequestNumber;

    @Column
    String pullRequestTitle;

    @Column
    int repositoryId;

    @Column
    long startedAt;

    @Column
    String state;

    @ForeignKey(saveForeignKeyModel = false, references = {})
    ForeignKeyContainer<Repo> repoForeignKeyContainer;

    Build() {
        // ctor for DBFlow
    }

    public Build(long commitId, long duration, long finishedAt, long id, String number,
                 boolean pullRequest, String pullRequestNumber, String pullRequestTitle,
                 int repositoryId, long startedAt, String state) {
        this.commitId = commitId;
        this.duration = duration;
        this.finishedAt = finishedAt;
        this.id = id;
        this.number = number;
        this.pullRequest = pullRequest;
        this.pullRequestNumber = pullRequestNumber;
        this.pullRequestTitle = pullRequestTitle;
        this.repositoryId = repositoryId;
        this.startedAt = startedAt;
        this.state = state;
    }

    public void associateRepo(Repo repo) {
        repoForeignKeyContainer = FlowManager.getContainerAdapter(Repo.class).toForeignKeyContainer(repo);
    }

    public long getDuration() {
        return duration;
    }

    public long getFinishedAt() {
        return finishedAt;
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

    public long getStartedAt() {
        return startedAt;
    }

    public String getState() {
        return state;
    }

    public GHCommit getCommit() {
        if (commit == null) {
            commit = SQLite.select()
                    .from(GHCommit.class)
                    .where(GHCommit_Table.commitId.eq(commitId))
                    .querySingle();
        }
        return commit;
    }
}
