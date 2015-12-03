package es.guillermoorellana.travisforandroid.ui.activity;

import android.os.Bundle;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TravisApp.get(this).applicationComponent().inject(this);
    }
}
