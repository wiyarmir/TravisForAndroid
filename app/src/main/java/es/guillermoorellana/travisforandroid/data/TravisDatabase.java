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

package es.guillermoorellana.travisforandroid.data;

import android.net.Uri;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.provider.ContentProvider;
import com.raizlabs.android.dbflow.annotation.provider.ContentUri;
import com.raizlabs.android.dbflow.annotation.provider.TableEndpoint;

import es.guillermoorellana.travisforandroid.model.Build_Table;
import es.guillermoorellana.travisforandroid.model.Repo_Table;

@ContentProvider(authority = TravisDatabase.CONTENT_AUTHORITY,
        database = TravisDatabase.class,
        baseContentUri = "content://" + TravisDatabase.CONTENT_AUTHORITY)
@Database(version = TravisDatabase.VERSION)
public class TravisDatabase {
    public static final int VERSION = 1;
    public static final String CONTENT_AUTHORITY = "es.guillermoorellana.travisforandroid";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private TravisDatabase() {
        // util class
    }

    @TableEndpoint(name = "Repo")
    public static final class REPO_MODEL {
        public static final String PATH_REPO = "repo";
        @ContentUri(path = PATH_REPO, type = ContentUri.ContentType.VND_MULTIPLE + PATH_REPO)
        public static final Uri CONTENT_REPO_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REPO).build();
        public static final String[] PROJECTION = new String[]{
                Repo_Table._id.toString(),
                Repo_Table.repoId.toString(),
                Repo_Table.slug.toString(),
                Repo_Table.active.toString(),
                Repo_Table.description.toString(),
                Repo_Table.lastBuildId.toString(),
                Repo_Table.lastBuildNumber.toString(),
                Repo_Table.lastBuildState.toString(),
                Repo_Table.lastBuildDuration.toString(),
                Repo_Table.lastBuildLanguage.toString(),
                Repo_Table.lastBuildStartedAt.toString(),
                Repo_Table.lastBuildFinishedAt.toString(),
                Repo_Table.githubLanguage.toString(),
        };
    }

    @TableEndpoint(name = "GHCommit")
    public static final class COMMIT_MODEL {
        public static final String PATH_COMMIT = "commit";
        @ContentUri(path = PATH_COMMIT, type = ContentUri.ContentType.VND_MULTIPLE + PATH_COMMIT)
        public static final Uri CONTENT_COMMIT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMMIT).build();
    }

    @SuppressWarnings("PMD")
    @TableEndpoint(name = "Build")
    public static final class BUILD_MODEL {
        public static final String PATH_BUILD = "build";
        public static final String[] FULL_PROJECTION = {
                Build_Table._id.toString(),
                Build_Table.id.toString(),
                Build_Table.duration.toString(),
                Build_Table.startedAt.toString(),
                Build_Table.number.toString(),
                Build_Table.repositoryId.toString(),
                Build_Table.state.toString(),
                Build_Table.finishedAt.toString(),
                Build_Table.pullRequest.toString(),
                Build_Table.pullRequestNumber.toString(),
                Build_Table.pullRequestTitle.toString(),
                Build_Table.commitId.toString()
        };

        @ContentUri(
                path = PATH_BUILD,
                type = ContentUri.ContentType.VND_MULTIPLE + PATH_BUILD
        )
        public static final Uri URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BUILD).build();

        @ContentUri(
                path = PATH_BUILD + "/#",
                segments = @ContentUri.PathSegment(segment = 1, column = "repositoryId"),
                type = ContentUri.ContentType.VND_MULTIPLE + PATH_BUILD
        )
        public static Uri URI_WITH_REPO(long id) {
            return BASE_CONTENT_URI.buildUpon().appendPath(PATH_BUILD).appendPath(String.valueOf(id)).build();
        }
    }
}
