package es.guillermoorellana.travisforandroid.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.TravisApp;
import es.guillermoorellana.travisforandroid.ui.adapter.RepoAdapter;
import es.guillermoorellana.travisforandroid.ui.DividerItemDecoration;

public class MainActivity extends BaseActivity {

    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TravisApp.get(this).applicationComponent().inject(this);
        ButterKnife.bind(this);

        recyclerView.setAdapter(new RepoAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
    }
}
