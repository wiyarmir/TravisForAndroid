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
    public static final class RepoModel {
        public static final String PATH_REPO = "repo";
        @ContentUri(path = PATH_REPO, type = ContentUri.ContentType.VND_MULTIPLE + PATH_REPO)
        public static final Uri CONTENT_REPO_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REPO).build();
    }
}
