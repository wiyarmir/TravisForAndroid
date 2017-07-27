package es.guillermoorellana.travisforandroid.ui.view;

import es.guillermoorellana.travisforandroid.mvp.BaseView;

public interface PRView extends BaseView {
    void showError(Throwable throwable);
}
