package ratingapp.ddey.com.dam_project.activities.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.User;
import ratingapp.ddey.com.dam_project.models.UserType;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText tieEmail, tiePassword, tieConfirmPassword, tieName;
    private RadioButton rbTeacher, rbStudent;
    private ProgressBar progressBar;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeComponents();
        TextView textViewReturn = findViewById(R.id.textViewLogin);
        textViewReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        Button buttonRegister = findViewById(R.id.buttonSignUp);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });
    }


    public void initializeComponents() {
        db = new DbHelper(this);
        tieName = findViewById(R.id.register_tie_name);
        tieEmail = findViewById(R.id.register_tie_email);
        tiePassword = findViewById((R.id.register_tie_password));
        tieConfirmPassword = findViewById((R.id.register_tie_confirm_password));
        rbTeacher = findViewById(R.id.radioButtonT);
        rbStudent = findViewById(R.id.radioButtonS);

        progressBar = findViewById(R.id.progressbar);
    }
    private void registerUser()
    {
        if (isValid()) {
            String email = tieEmail.getText().toString();
            String password = tiePassword.getText().toString();
            String name = tieName.getText().toString();
            UserType userType = null;
            if (rbStudent.isChecked()) {
                userType = UserType.STUDENT;
            } else if (rbTeacher.isChecked()) {
                userType = UserType.TEACHER;
            }

            progressBar.setVisibility(View.VISIBLE);

            User newUser = new User(email,password,name, userType);
            db.insertUser(newUser);
            Toast.makeText(getApplicationContext(), R.string.register_succes, Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.GONE);
            finish();
        }
    }

    public boolean isValid() {
        if (tieName.getText() == null || tieName.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.signup_error_name, Toast.LENGTH_SHORT).show();
            tieName.setError(getString(R.string.signup_error_name));
            tieName.requestFocus();
            return false;
        }
        if (tieEmail.getText() == null || tieEmail.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(tieEmail.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), R.string.signup_error_email, Toast.LENGTH_SHORT).show();
            tieEmail.setError(getString(R.string.signup_error_email));
            tieEmail.requestFocus();
            return false;
        } else if (tiePassword.getText() == null || tiePassword.getText().toString().trim().isEmpty() || tiePassword.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), R.string.signup_error_password, Toast.LENGTH_SHORT).show();
            tiePassword.setError(getString(R.string.signup_error_password));
            tiePassword.requestFocus();
            return false;
        } else if (!tiePassword.getText().toString().equals(tieConfirmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), R.string.signup_error_password_match, Toast.LENGTH_SHORT).show();
            tieConfirmPassword.setError(getString(R.string.signup_error_password_match));
            tieConfirmPassword.requestFocus();
            return false;
        } else if (tieConfirmPassword.getText() == null || tieConfirmPassword.getText().toString().trim().isEmpty() || tieConfirmPassword.getText().toString().length() < 6) {
            Toast.makeText(getApplicationContext(), R.string.signup_error_password, Toast.LENGTH_SHORT).show();
            tieConfirmPassword.setError(getString(R.string.signup_error_password));
            tieConfirmPassword.requestFocus();
            return false;
        }
        return true;
    }
}
