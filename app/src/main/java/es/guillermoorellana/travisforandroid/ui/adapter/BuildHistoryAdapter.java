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
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.api.entity.Build;
import es.guillermoorellana.travisforandroid.api.entity.BuildHistory;

public class BuildHistoryAdapter extends ItemClickableAdapter<BuildHistoryAdapter.BuildViewHolder> {
    @NonNull protected BuildHistory mData = new BuildHistory();

    @Override
    protected BuildViewHolder createViewHolder(View view) {
        return new BuildViewHolder(view);
    }

    @Override
    public BuildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BuildViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    protected int getItemLayout() {
        return R.layout.item_build;
    }

    public Build getItem(int adapterPosition) {
        return mData.getBuilds().get(adapterPosition);
    }

    public void setData(@NonNull BuildHistory data) {
        mData = data; // Prevent possible side-effects.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.getBuilds().size();
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

        public void bind(Build build) {
            buildStatus.setText("Build #" + build.getNumber());
            starter.setText("Started by:");
            branch.setText("Branch:");
            duration.setText("Duration:");
            finishedAgo.setText("Finished:");
        }
    }
}
