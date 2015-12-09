package es.guillermoorellana.travisforandroid.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.ui.fragment.ReposFragment;
import es.guillermoorellana.travisforandroid.ui.view.MainView;
import hugo.weaving.DebugLog;

public class MainActivity extends BaseActivity implements MainView {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new ReposFragment());
        }
    }

    @DebugLog
    @Override
    public void addFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFrame, fragment)
                .commit();
    }

    @DebugLog
    @Override
    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, fragment)
                .commit();
    }

    @DebugLog
    @Override
    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
    }
}
