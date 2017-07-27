
package es.guillermoorellana.travisforandroid.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import es.guillermoorellana.travisforandroid.R;
import es.guillermoorellana.travisforandroid.ui.fragment.ReposFragment;
import es.guillermoorellana.travisforandroid.ui.view.MainView;

public class MainActivity extends BaseActivity implements MainView {

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            replaceFragment(new ReposFragment());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }

        // onCreateOptionsMenu is called after the fragment being added
        setSearchableCallback(getSupportFragmentManager().findFragmentById(R.id.mainFrame));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void addFragment(Fragment fragment) {
        setSearchableCallback(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFrame, fragment)
                .commit();
    }

    private void setSearchableCallback(Fragment fragment) {
        if (searchView != null && fragment instanceof SearchView.OnQueryTextListener) {
            searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) fragment);
        }
    }

    @Override
    public void addFragmentBackstack(Fragment fragment) {
        setSearchableCallback(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        setSearchableCallback(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, fragment)
                .commit();
    }

    @Override
    public void replaceFragmentBackStack(Fragment fragment) {
        setSearchableCallback(fragment);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFrame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void removeFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(fragment)
                .commit();
        // pop the callback as well
        setSearchableCallback(getSupportFragmentManager().findFragmentById(R.id.mainFrame));
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
