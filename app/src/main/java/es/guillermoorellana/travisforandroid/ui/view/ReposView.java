package es.guillermoorellana.travisforandroid.ui.view;

import android.support.annotation.NonNull;

import java.util.List;

import es.guillermoorellana.travisforandroid.api.entity.Repo;

public interface ReposView {    // Presenter does not know about Main Thread. It's a detail of View implementation!
    void showLoadingUi();
    void showErrorUi(@NonNull Throwable error);
    void showContentUi(@NonNull List<Repo> items);
}
