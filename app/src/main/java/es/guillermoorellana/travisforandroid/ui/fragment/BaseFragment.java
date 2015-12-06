package es.guillermoorellana.travisforandroid.ui.fragment;

import android.support.v4.app.Fragment;

import es.guillermoorellana.travisforandroid.TravisApp;

@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BaseFragment extends Fragment {
    @Override
    public void onDestroy() {
        TravisApp.get(getContext()).applicationComponent().leakCanaryProxy().watch(this);
        super.onDestroy();
    }
}
