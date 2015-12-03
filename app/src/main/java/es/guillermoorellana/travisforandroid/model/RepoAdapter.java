package es.guillermoorellana.travisforandroid.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.guillermoorellana.travisforandroid.R;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {
    public RepoAdapter() {
        // noop
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_repo, parent, false);
        return RepoViewHolder.newInstance(view);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        // TODO do data bindings
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    @SuppressWarnings("PMD") // didnt figure out how to make PMD like ViewHolders
    public static class RepoViewHolder extends RecyclerView.ViewHolder {
        public RepoViewHolder(View itemView) {
            super(itemView);
        }

        public static RepoViewHolder newInstance(View view) {
            return new RepoViewHolder(view);
        }
    }
}
