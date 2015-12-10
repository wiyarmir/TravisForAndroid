package es.guillermoorellana.travisforandroid.mvp;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {
}
