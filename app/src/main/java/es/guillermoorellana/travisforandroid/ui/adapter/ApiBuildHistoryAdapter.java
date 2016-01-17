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

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.api.entity.ApiBuild;
import es.guillermoorellana.travisforandroid.api.entity.ApiBuildHistory;
import es.guillermoorellana.travisforandroid.api.entity.ApiCommit;

public class ApiBuildHistoryAdapter extends ItemClickableAdapter<ApiBuildHistoryAdapter.BuildViewHolder> {
    @NonNull protected ApiBuildHistory mData = new ApiBuildHistory();

    @Override
    protected BuildViewHolder createViewHolder(View view) {
        return new BuildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BuildViewHolder holder, int position) {
        ApiBuild build = getItem(position);
        ApiCommit relatedCommit = null;
        for (ApiCommit commit : mData.getCommits()) {
            if (build.getCommitId() == commit.getId()) {
                relatedCommit = commit;
                break;
            }
        }
        holder.bind(build, relatedCommit);
    }

    protected int getItemLayout() {
        return R.layout.item_build;
    }

    public ApiBuild getItem(int adapterPosition) {
        return mData.getBuilds().get(adapterPosition);
    }

    public void setData(@NonNull ApiBuildHistory data) {
        mData = data; // Prevent possible side-effects.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.getBuilds().size();
    }

    public ApiBuildHistory getData() {
        return mData;
    }

    @SuppressLint("SetTextI18n")
    public static class BuildViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.buildStatus) TextView buildStatus;
        @Bind(R.id.starter) TextView starter;
        @Bind(R.id.branch) TextView branch;
        @Bind(R.id.duration) TextView duration;
        @Bind(R.id.finishedAgo) TextView finishedAgo;

        public BuildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(ApiBuild build, ApiCommit relatedCommit) {
            buildStatus.setText("Build #" + build.getNumber());
            duration.setText("Duration:" + build.getDuration());
            finishedAgo.setText("Finished:" + build.getFinishedAt());
            if (relatedCommit != null) {
                starter.setText("Started by:" + relatedCommit.getCommitterName());
                branch.setText("Branch:" + relatedCommit.getBranch());
            }
        }
    }
}
