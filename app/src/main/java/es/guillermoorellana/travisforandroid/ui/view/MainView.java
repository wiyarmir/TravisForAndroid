package es.guillermoorellana.travisforandroid.ui.view;

import android.support.v4.app.Fragment;

public interface MainView {
    void addFragment(Fragment fragment);
    void replaceFragment(Fragment fragment);
    void removeFragment(Fragment fragment);
}
