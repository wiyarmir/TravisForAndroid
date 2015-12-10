/*
 *   Copyright 2015 Guillermo Orellana Ruiz
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

package es.guillermoorellana.travisforandroid.api.entity;

import java.io.Serializable;

public class Commit implements Serializable {
    private static final long serialVersionUID = 4390847522215353298L;
    public static final int SHORT_SHA_LENGTH = 7;

    private long id;
    private String sha;
    private String branch;
    private String message;
    private String commitedAt;
    private String authorName;
    private String authorEmail;
    private String committerName;
    private String committerEmail;
    private String compareUrl;

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

    public String getCommitedAt() {
        return commitedAt;
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
}
