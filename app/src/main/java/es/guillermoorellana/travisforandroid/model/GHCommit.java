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
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import es.guillermoorellana.travisforandroid.data.TravisDatabase;

@ModelContainer
@Table(database = TravisDatabase.class,
        insertConflict = ConflictAction.REPLACE,
        updateConflict = ConflictAction.REPLACE)
public class GHCommit extends BaseModel {

    public static final int SHORT_SHA_LENGTH = 7;

    @SuppressWarnings("checkstyle:membername")
    @PrimaryKey(autoincrement = true)
    long _id;

    @Unique(onUniqueConflict = ConflictAction.REPLACE)
    @Column
    long commitId;

    @Column
    String sha;

    @Column
    String branch;

    @Column
    String message;

    @Column
    long committedAt;

    @Column
    String authorName;

    @Column
    String authorEmail;

    @Column
    String committerName;

    @Column
    String committerEmail;

    @Column
    String compareUrl;

    @Column
    long pullRequestNumber;

    public GHCommit() {
        // empty ctor for dbflow
    }

    GHCommit(long commitId, String sha, String branch, String message, long committedAt,
             String authorName, String authorEmail, String committerName, String committerEmail,
             String compareUrl, long pullRequestNumber) {
        this.commitId = commitId;
        this.sha = sha;
        this.branch = branch;
        this.message = message;
        this.committedAt = committedAt;
        this.authorName = authorName;
        this.authorEmail = authorEmail;
        this.committerName = committerName;
        this.committerEmail = committerEmail;
        this.compareUrl = compareUrl;
        this.pullRequestNumber = pullRequestNumber;
    }

    public long getCommitId() {
        return commitId;
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

    public long getCommittedAt() {
        return committedAt;
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
