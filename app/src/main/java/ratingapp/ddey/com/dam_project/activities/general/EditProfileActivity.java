package ratingapp.ddey.com.dam_project.activities.general;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.User;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.others.Session;

public class EditProfileActivity extends AppCompatActivity {

    private Session mSession;
    private DbHelper mDb;

    private TextInputEditText tieEmail;
    private TextInputEditText tieBirthdate;
    private RadioButton profile_radioButtonM;
    private RadioButton profile_radioButtonF;
    private Button buttonSave;
    private Button btnDeleteUser;
    private Toolbar toolbar;

    private String selectedGender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BACK BUTTON pt toolbar pus in locul actionbarului
        this.setTitle("Edit profile");
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initializeComponents();
        retrieveFromDb();
        retrieveFromSharedPreferences();
    }




    public void initializeComponents() {
        mSession = new Session(this);
        mDb = new DbHelper(this);


        tieEmail =  findViewById(R.id.profile_tie_email);
        tieBirthdate = findViewById(R.id.profile_tie_date_of_birth);

        profile_radioButtonM = findViewById(R.id.radioButtonM);
        profile_radioButtonF = findViewById(R.id.radioButtonF);

        buttonSave =  findViewById(R.id.profile_buttonSave);
        buttonSave.setOnClickListener(saveEvent());

        btnDeleteUser = findViewById(R.id.edit_profile_btn_delete);
        btnDeleteUser.setOnClickListener(deleteUserAndLogoutEvent());
    }

    @NonNull
    private View.OnClickListener deleteUserAndLogoutEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);

                builder.setTitle(R.string.alert_delete_title)
                        .setMessage(R.string.alert_message)
                        .setPositiveButton(R.string.alert_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = mDb.getId(mSession);
                                if (id != null) {
                                    mDb.deleteUser(Long.valueOf(id));
                                    mSession.logoutUser();
                                    Toast.makeText(EditProfileActivity.this, getString(R.string.user_success_delete_editprofile), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(EditProfileActivity.this, getString(R.string.error_delete_editprofile), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.alert_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(EditProfileActivity.this, getString(R.string.alert_canceled), Toast.LENGTH_SHORT).show();
                            }
                        });

                builder.create().show();
            }
        };
    }

    public View.OnClickListener saveEvent() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid() == true) {
                    saveData();
                }
            }
        };
    }

    public boolean isValid() {
        if (tieEmail.getText() == null || tieEmail.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(tieEmail.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), R.string.profile_activity_email_error, Toast.LENGTH_SHORT).show();
            tieEmail.setError(getString(R.string.profile_activity_email_error));
            return false;
        } else if (tieBirthdate.getText() == null || tieBirthdate.getText().toString().trim().isEmpty() || convertStringToDate(tieBirthdate.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(), R.string.date_format_error, Toast.LENGTH_SHORT).show();
            tieBirthdate.setError(getString(R.string.date_format_error));
            return false;
        }
        return true;
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }


    //save to DB
    public void saveData()
    {
        String id = mDb.getId(mSession);
        String newEmail = tieEmail.getText().toString();
        mDb.updateEmail(id, newEmail);
        mSession.getEditor().putString(Constants.PREF_EMAIL_KEY, newEmail);
        mSession.getEditor().commit();

        String date = tieBirthdate.getText().toString();
        mDb.updateDate(id, date);
        mSession.getEditor().putString(Constants.PREF_BIRTHDATE_KEY, date);
        mSession.getEditor().commit();

        if (profile_radioButtonM.isChecked())
            selectedGender = "Male";
        else if (profile_radioButtonF.isChecked())
            selectedGender = "Female";

        mDb.updateGender(id, selectedGender);

        finish();
    }

    public void retrieveFromDb(){
        User userRetrievedFromDb = mDb.retrieveProfile(mSession);

        if (userRetrievedFromDb.getGender() != null) {
            if (userRetrievedFromDb.getGender().equals("Male")) {
                profile_radioButtonM.setChecked(true);
                profile_radioButtonF.setChecked(false);
            } else {
                profile_radioButtonM.setChecked(false);
                profile_radioButtonF.setChecked(true);
            }
        }
    }

    public void retrieveFromSharedPreferences() {
        String strEmail = mSession.getPrefs().getString(Constants.PREF_EMAIL_KEY, null);
        if (strEmail != null)
            tieEmail.setText(strEmail);

        String strBirthDate = mSession.getPrefs().getString(Constants.PREF_BIRTHDATE_KEY, null);
        if (strBirthDate != null)
            tieBirthdate.setText(strBirthDate);
    }

    public static Date convertStringToDate(String string) {
        Date result = null;
        try {
            result = Constants.simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

}
