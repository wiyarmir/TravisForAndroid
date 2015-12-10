package es.guillermoorellana.travisforandroid.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

import es.guillermoorellana.travisforandroid.R;
import rx.Observable;
import rx.subjects.PublishSubject;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public abstract class BaseAdapter<M, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    @NonNull
    private final PublishSubject<View> onClickSubject = PublishSubject.create();
    @NonNull protected List<M> mRepos = emptyList();

    @RxLogObservable
    public Observable<View> getOnClickSubject() {
        return onClickSubject;
    }

    public M getItem(int adapterPosition) {
        return mRepos.get(adapterPosition);
    }

    public void setData(@NonNull List<M> repos) {
        mRepos = unmodifiableList(repos); // Prevent possible side-effects.
        notifyDataSetChanged();
    }

    protected abstract VH createViewHolder(View view);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);
        VH viewHolder = createViewHolder(view);
        RxView.clicks(view)
                .map(notUseful -> view)
                .takeUntil(RxView.detaches(parent))
                .subscribe(onClickSubject);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }
}
