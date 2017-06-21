package com.assignment.binlix26.case_study_bmc;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.binlix26.case_study_bmc.admin.EditAppointmentActivity;
import com.assignment.binlix26.case_study_bmc.admin.EditStaffActivity;
import com.assignment.binlix26.case_study_bmc.admin.EditVisitorActivity;
import com.assignment.binlix26.case_study_bmc.data.BMCContract.PasswordEntry;
import com.assignment.binlix26.case_study_bmc.utility.Utility;

public class AdminActivity extends AppCompatActivity {

    public static final String APP_TAB = "Appointment";
    public static final String VISITOR_TAB = "Visitor";
    public static final String STAFF_TAB = "Staff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new AdminPageAdapter(getSupportFragmentManager(),
                AdminActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.new_visitor:
                // go to edit visitor
                Intent addVisitor = new Intent(this, EditVisitorActivity.class);
                startActivity(addVisitor);
                return true;
            case R.id.new_staff:
                // go to edit staff
                Intent addStaff = new Intent(this, EditStaffActivity.class);
                addStaff.putExtra("exist", false);
                startActivity(addStaff);
                return true;
            case R.id.new_app:
                // go to edit appointment
                Intent addApp = new Intent(this, EditAppointmentActivity.class);
                addApp.putExtra("exist", false);
                startActivity(addApp);
                return true;
            case R.id.change_password:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);

                alert.setTitle("Change Password");
                //alert.setMessage("Enter your new password");

                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);

                // Set an EditText view to get user input
                final EditText inputText1 = new EditText(this);
                inputText1.setHint("New Password");
                layout.addView(inputText1);

                final EditText inputText2 = new EditText(this);
                inputText2.setHint("Confirmation");
                layout.addView(inputText2);

                final TextView prompt = new TextView(this);
                layout.addView(prompt);

                alert.setView(layout);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        String value1 = inputText1.getText().toString();
                        String value2 = inputText2.getText().toString();

                        if (value1 != null && value2 !=null &&
                                value1.equals(value2) && value1.length() > 0) {
                            Utility.password = value1;

                            String selection = PasswordEntry._ID + "=?";
                            String[] selectionArgs = new String[]{
                                    String.valueOf(1)
                            };

                            ContentValues values = new ContentValues();
                            values.put(PasswordEntry.COLUMN_PASSWORD, value1);

                            getContentResolver().update(PasswordEntry.CONTENT_URI, values, selection, selectionArgs);

                            Toast.makeText(getApplicationContext(),"Password changed!",Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(),"Password does not match, no changes made!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                alert.show();

                return true;
            case R.id.log_out:
                Intent homePage = new Intent(this, MainActivity.class);
                startActivity(homePage);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
