package ratingapp.ddey.com.dam_project.activities.general;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.User;
import ratingapp.ddey.com.dam_project.utils.DbHelper;
import ratingapp.ddey.com.dam_project.utils.Session;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextInputEditText tieEmail, tiePassword;
    private TextView textViewSignUp;
    private Button buttonLogin;
    private DbHelper db;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(this);
        db = new DbHelper(this);

        tieEmail =  findViewById(R.id.login_tie_email);
        tiePassword =  findViewById(R.id.login_tie_password);
        progressBar = findViewById(R.id.progressbar);
        textViewSignUp = findViewById(R.id.textViewSignup);


        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        if (session.isUserLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void loginUser() {
        if (isValid()) {
            String email = tieEmail.getText().toString();
            String password = tiePassword.getText().toString();
            progressBar.setVisibility(View.VISIBLE);

            User loggedUser = new User(email, password);
            if (db.getUser(loggedUser))
            {
                session.createUserLoginSession(loggedUser);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
            else
                Toast.makeText(this, R.string.login_wrongemailorpass, Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }



    }

    public boolean isValid() {
        if (tieEmail.getText() == null || tieEmail.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(tieEmail.getText().toString()).matches()) {
            Toast.makeText(getApplicationContext(), R.string.login_emailerror, Toast.LENGTH_SHORT).show();
            tieEmail.setError(getString(R.string.login_emailerror));
            tieEmail.requestFocus();
            return false;
        } else if (tiePassword.getText() == null || tiePassword.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.login_pass_error, Toast.LENGTH_SHORT).show();
            tiePassword.setError(getString(R.string.login_pass_error));
            tiePassword.requestFocus();
            return false;
        }
        return true;
    }
}
