
package es.guillermoorellana.travisforandroid.ui.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface ReposView extends MvpView {
    void showError(Throwable error);
}
