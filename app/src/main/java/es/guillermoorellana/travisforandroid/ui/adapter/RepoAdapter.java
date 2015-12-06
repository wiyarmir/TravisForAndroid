package es.guillermoorellana.travisforandroid.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.joda.time.Period;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.api.entity.Repo;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    @NonNull
    List<Repo> mRepos = emptyList();

    public RepoAdapter() {
        // noop
    }

    public void setData(@NonNull List<Repo> repos) {
        mRepos = unmodifiableList(repos); // Prevent possible side-effects.
        notifyDataSetChanged();
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_repo, parent, false);
        return RepoViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        holder.bind(mRepos.get(position));
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }

    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.buildNumber) TextView buildNumber;
        @Bind(R.id.duration) TextView duration;
        @Bind(R.id.finishedAgo) TextView finishedAgo;
        @Bind(R.id.repo) TextView repoName;
        @Bind(R.id.status) View status;

        public RepoViewHolder(View repoView) {
            super(repoView);
            ButterKnife.bind(this, repoView);
        }

        public static RepoViewHolder newInstance(@NonNull View view) {
            return new RepoViewHolder(view);
        }

        public void bind(@NonNull Repo repo) {
            buildNumber.setText(repo.getLastBuildNumber());
            String durationText = "" + repo.getLastBuildDuration();
            duration.setText(durationText);
            finishedAgo.setText(new Period(repo.getLastBuildFinishedAt(), repo.getLastBuildStartedAt()).toString());
            repoName.setText(repo.getSlug());
            status.setBackgroundColor(repo.getLastBuildState().equals("success") ? Color.GREEN : Color.RED);
        }
    }
}
