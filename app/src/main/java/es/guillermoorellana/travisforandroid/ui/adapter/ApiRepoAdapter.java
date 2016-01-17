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

package es.guillermoorellana.travisforandroid.ui.adapter;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.api.entity.ApiRepo;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class ApiRepoAdapter extends ItemClickableAdapter<ApiRepoAdapter.RepoViewHolder> {

    @NonNull protected List<ApiRepo> mData = emptyList();

    public ApiRepoAdapter() {
        // noop
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    protected RepoViewHolder createViewHolder(View view) {
        return new RepoViewHolder(view);
    }

    protected int getItemLayout() {
        return R.layout.item_repo;
    }

    public ApiRepo getItem(int adapterPosition) {
        return mData.get(adapterPosition);
    }

    public void setData(@NonNull List<ApiRepo> data) {
        mData = unmodifiableList(data); // Prevent possible side-effects.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<ApiRepo> getData() {
        return mData;
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        private static final PeriodFormatter FORMATTER = PeriodFormat.wordBased(Locale.getDefault());

        @Bind(R.id.buildNumber) TextView buildNumber;
        @Bind(R.id.duration) TextView duration;
        @Bind(R.id.finishedAgo) TextView finishedAgo;
        @Bind(R.id.repo) TextView repoName;
        @Bind(R.id.status) View status;

        public RepoViewHolder(View repoView) {
            super(repoView);
            ButterKnife.bind(this, repoView);
        }

        public void bind(@NonNull ApiRepo repo) {
            buildNumber.setText(repo.getLastBuildNumber());
            duration.setText(durationText(repo.getLastBuildDuration()));
            finishedAgo.setText(finishedText(repo));
            repoName.setText(repo.getSlug());
            status.setBackgroundColor(colorForRepo(repo));
        }

        @NonNull
        private static String finishedText(@NonNull ApiRepo repo) {
            String verb;
            if (repo.isActive()) {
                verb = "Started: %s ago";
            } else {
                verb = "Finished: %s ago";
            }
            Period period = new Period(repo.getLastBuildStartedAt(), repo.getLastBuildFinishedAt()).withMillis(0);
            if (period.getMinutes() == 0) {
                return String.format(verb, "less than a minute");
            }
            return String.format(verb, FORMATTER.print(period));
        }

        @ColorInt
        private static int colorForRepo(@NonNull ApiRepo repo) {
            if (repo.isActive()) {
                return Color.YELLOW;
            }
            if (repo.getLastBuildState().equals("success")) {
                return Color.GREEN;
            }
            return Color.RED;
        }

        @NonNull
        private static String durationText(long lastBuildDuration) {
            if (lastBuildDuration == 0) {
                return "Currently running";
            }
            return String.valueOf(lastBuildDuration);
        }
    }
}
