/*
 *   Copyright 2016 Guillermo Orellana Ruiz
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package es.guillermoorellana.travisforandroid.ui.adapter;

import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.jakewharton.rxbinding.view.RxView;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Provide a {@link android.support.v7.widget.RecyclerView.Adapter} implementation with cursor
 * support.
 * <p>
 * Child classes only need to implement {@link #onCreateViewHolder(android.view.ViewGroup, int)} and
 * {@link #onBindViewHolderCursor(android.support.v7.widget.RecyclerView.ViewHolder, Cursor)}.
 * <p>
 * This class does not implement deprecated fields and methods from CursorAdapter! Incidentally,
 * only {@link android.widget.CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER} is available, so the
 * flag is implied, and only the Adapter behavior using this flag has been ported.
 *
 * @param <VH> {@inheritDoc}
 * @see android.support.v7.widget.RecyclerView.Adapter
 * @see android.widget.CursorAdapter
 */

public abstract class CursorRecyclerAdapter<VH extends android.support.v7.widget.RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    @NonNull
    private final PublishSubject<View> onClickSubject = PublishSubject.create();
    private boolean mDataValid;
    private int mRowIDColumn;
    private Cursor mCursor;

    public CursorRecyclerAdapter(Cursor cursor) {
        init(cursor);
    }

    void init(Cursor c) {
        boolean cursorPresent = c != null;
        mCursor = c;
        mDataValid = cursorPresent;
        mRowIDColumn = cursorPresent ? c.getColumnIndexOrThrow("_id") : -1;
        setHasStableIds(true);
    }

    @LayoutRes
    protected abstract int getItemLayout();

    @RxLogObservable
    public Observable<View> getOnClickSubject() {
        return onClickSubject;
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

    /**
     * This method will move the Cursor to the correct position and call
     * {@link #onBindViewHolderCursor(android.support.v7.widget.RecyclerView.ViewHolder,
     * Cursor)}.
     *
     * @param holder {@inheritDoc}
     * @param i      {@inheritDoc}
     */
    @Override
    public void onBindViewHolder(VH holder, int i) {
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(i)) {
            throw new IllegalStateException("couldn't move cursor to position " + i);
        }
        onBindViewHolderCursor(holder, mCursor);
    }

    /**
     * See {@link android.widget.CursorAdapter#bindView(android.view.View, android.content.Context,
     * Cursor)},
     * {@link #onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder, int)}
     *
     * @param holder View holder.
     * @param cursor The cursor from which to get the data. The cursor is already
     *               moved to the correct position.
     */
    public abstract void onBindViewHolderCursor(VH holder, Cursor cursor);

    @Override
    public int getItemCount() {
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        } else {
            return 0;
        }
    }

    /**
     * @see android.widget.ListAdapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null) {
            if (mCursor.moveToPosition(position)) {
                return mCursor.getLong(mRowIDColumn);
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public Cursor getCursor() {
        return mCursor;
    }

    /**
     * Change the underlying cursor to a new cursor. If there is an existing cursor it will be
     * closed.
     *
     * @param cursor The new cursor to be used
     */
    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
        notifyDataSetChanged();
    }

    /**
     * Swap in a new Cursor, returning the old Cursor.  Unlike
     * {@link #changeCursor(Cursor)}, the returned old Cursor is <em>not</em>
     * closed.
     *
     * @param newCursor The new cursor to be used.
     * @return Returns the previously set Cursor, or null if there wasa not one.
     * If the given new Cursor is the same instance is the previously set
     * Cursor, null is also returned.
     */
    public Cursor swapCursor(@Nullable Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }
        Cursor oldCursor = mCursor;
        mCursor = newCursor;
        if (newCursor == null) {
            mRowIDColumn = -1;
            mDataValid = false;
            // notify the observers about the lack of a data set
            // notifyDataSetChanged();
            notifyItemRangeRemoved(0, getItemCount());
        } else {
            mRowIDColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            // notify the observers about the new cursor
            notifyDataSetChanged();
        }
        return oldCursor;
    }
}
