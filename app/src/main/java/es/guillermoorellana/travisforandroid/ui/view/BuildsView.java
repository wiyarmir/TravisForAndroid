
package es.guillermoorellana.travisforandroid.ui.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface BuildsView extends MvpView {
    void showError(Throwable error);
}
