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

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.jakewharton.rxbinding.view.RxView;

import java.util.List;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        VH viewHolder = createViewHolder(view);
        RxView.clicks(view)
                .map(notUseful -> view)
                .takeUntil(RxView.detaches(parent))
                .subscribe(onClickSubject);
        return viewHolder;
    }

    @LayoutRes
    protected abstract int getItemLayout();

    @Override
    public int getItemCount() {
        return mRepos.size();
    }
}
