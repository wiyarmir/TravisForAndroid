package es.guillermoorellana.travisforandroid.ui.adapter;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.model.Build;
import es.guillermoorellana.travisforandroid.model.GHCommit;
import es.guillermoorellana.travisforandroid.ui.view.BuildView;

public class BuildsAdapter extends CursorRecyclerAdapter<BuildsAdapter.BuildViewHolder> {

    public BuildsAdapter(Cursor cursor) {
        super(cursor);
    }

    @Override
    protected BuildViewHolder createViewHolder(View view) {
        return new BuildViewHolder(view);
    }

    @Override
    public void onBindViewHolderCursor(BuildViewHolder holder, Cursor cursor) {
        Build build = FlowManager.getModelAdapter(Build.class).loadFromCursor(cursor);
        GHCommit relatedCommit = build.getCommit();
        holder.bind(build, relatedCommit);
    }

    protected int getItemLayout() {
        return R.layout.item_build;
    }

    public static class BuildViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.build)
        BuildView buildView;

        public BuildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Build build, GHCommit relatedCommit) {
            buildView.setBuildData(build, relatedCommit);
        }
    }
}
