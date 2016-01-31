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

package es.guillermoorellana.travisforandroid.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.raizlabs.android.dbflow.config.FlowManager;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.data.TravisDatabase;
import es.guillermoorellana.travisforandroid.model.Build;
import es.guillermoorellana.travisforandroid.model.Build_Table;
import es.guillermoorellana.travisforandroid.model.GHCommit;
import timber.log.Timber;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RepoViewsFactory(getApplicationContext(), intent);
    }

    public class RepoViewsFactory implements RemoteViewsFactory {
        private final Context context;
        private final int appWidgetId;
        private Cursor cursor;
        private ContentObserver observer;

        public RepoViewsFactory(Context applicationContext, Intent intent) {
            context = applicationContext;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            Timber.d("Create");
            cursor = context.getContentResolver().query(
                    TravisDatabase.BUILD_MODEL.URI,
                    TravisDatabase.BUILD_MODEL.FULL_PROJECTION,
                    null,
                    null,
                    Build_Table.startedAt + "DESC"
            );
            observer = new ContentObserver(new Handler(Looper.getMainLooper())) {
                public void onChange(boolean selfChange, Uri uri) {
                    onChange(selfChange);
                }

                public void onChange(boolean selfChange) {
                    AppWidgetManager.getInstance(WidgetService.this)
                            .notifyAppWidgetViewDataChanged(appWidgetId, R.id.build_list);
                }
            };
            cursor.registerContentObserver(observer);
        }

        @Override
        public void onDataSetChanged() {
            onDestroy();
            onCreate();
        }

        @Override
        public void onDestroy() {
            cursor.unregisterContentObserver(observer);
            cursor.close();
        }

        @Override
        public int getCount() {
            Timber.d("Count:" + cursor.getCount());
            return cursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget_list_item);
            cursor.moveToPosition(position);
            Build build = FlowManager.getModelAdapter(Build.class).loadFromCursor(cursor);
            row.setTextViewText(R.id.build_number, "Build #" + build.getNumber());
            row.setTextViewText(R.id.build_state, ": " + build.getState());
            row.setTextViewText(R.id.build_duration, String.valueOf(build.getDuration()));
            row.setTextViewText(R.id.build_finished, String.valueOf(build.getFinishedAt()));
            if (build.isPullRequest()) {
                row.setTextViewText(R.id.build_pull_request_title, build.getPullRequestTitle());
            } else {
                row.setViewVisibility(R.id.build_pull_request_title, View.GONE);
            }
            GHCommit commit = build.getCommit();
            if (commit != null) {
                row.setTextViewText(R.id.build_commit_message, commit.getMessage());
                row.setTextViewText(R.id.build_commit_person, commit.getAuthorName());
                row.setTextViewText(R.id.build_branch, commit.getBranch());
            }
            return row;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
