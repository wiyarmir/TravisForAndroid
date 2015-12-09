package es.guillermoorellana.travisforandroid.ui.adapter;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.jakewharton.rxbinding.view.RxView;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;
import org.joda.time.format.PeriodFormatter;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.api.entity.Repo;
import rx.Observable;
import rx.subjects.PublishSubject;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class RepoAdapter extends RecyclerView.Adapter<RepoAdapter.RepoViewHolder> {

    @NonNull
    private List<Repo> mRepos = emptyList();
    @NonNull
    private final PublishSubject<View> onClickSubject = PublishSubject.create();

    public RepoAdapter() {
        // noop
    }

    @RxLogObservable
    public Observable<View> getOnClickSubject() {
        return onClickSubject;
    }

    public Repo getItem(int adapterPosition) {
        return mRepos.get(adapterPosition);
    }

    public void setData(@NonNull List<Repo> repos) {
        mRepos = unmodifiableList(repos); // Prevent possible side-effects.
        notifyDataSetChanged();
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        RepoViewHolder repoViewHolder = RepoViewHolder.newInstance(view);
        RxView.clicks(view)
                .map(notUseful -> view)
                .takeUntil(RxView.detaches(parent))
                .subscribe(onClickSubject);
        return repoViewHolder;
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

        public static RepoViewHolder newInstance(@NonNull View view) {
            return new RepoViewHolder(view);
        }

        public void bind(@NonNull Repo repo) {
            buildNumber.setText(repo.getLastBuildNumber());
            duration.setText(durationText(repo.getLastBuildDuration()));
            finishedAgo.setText(finishedText(repo));
            repoName.setText(repo.getSlug());
            status.setBackgroundColor(colorForRepo(repo));
        }

        @NonNull
        private static String finishedText(@NonNull Repo repo) {
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
        private static int colorForRepo(@NonNull Repo repo) {
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
