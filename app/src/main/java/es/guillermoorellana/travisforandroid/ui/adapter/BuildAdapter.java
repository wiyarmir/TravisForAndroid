package es.guillermoorellana.travisforandroid.ui.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.api.entity.Build;

public class BuildAdapter extends BaseAdapter<Build, BuildAdapter.BuildViewHolder> {
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
