package es.guillermoorellana.travisforandroid.ui.activity;

import android.os.Bundle;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.ui.fragment.ReposFragment;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFrame, new ReposFragment())
                    .commit();
        }
    }
}
