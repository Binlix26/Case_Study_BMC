package com.assignment.binlix26.case_study_bmc;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.assignment.binlix26.case_study_bmc.data.BMCContract;
import com.assignment.binlix26.case_study_bmc.home.AdminSignInFragment;
import com.assignment.binlix26.case_study_bmc.home.VisitorSigningFragment;
import com.assignment.binlix26.case_study_bmc.utility.Utility;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // only for testing photo avatar Bitmap
        Utility.bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.staff);

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // get the loader
        getLoaderManager().restartLoader(1, null, this);
    }

    private void setupViewPager(ViewPager viewPager){
        HomePageAdapter adapter = new HomePageAdapter(getSupportFragmentManager());
        adapter.addFragment(new VisitorSigningFragment(), "Visitor");
        adapter.addFragment(new AdminSignInFragment(), "Admin");
        viewPager.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Construct the loader
        Loader<Cursor> cursorLoader = new CursorLoader(
                this,
                BMCContract.PasswordEntry.CONTENT_URI,
                null, null, null, null
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.getCount() > 0) {
            data.moveToFirst();
            String password = data.getString(data.getColumnIndexOrThrow(BMCContract.PasswordEntry.COLUMN_PASSWORD));
            Utility.password = password;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
