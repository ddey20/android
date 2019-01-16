package ratingapp.ddey.com.dam_project.activities.general;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.activities.student.JoinPrivateQuizActivity;
import ratingapp.ddey.com.dam_project.activities.student.JoinPublicQuizActivity;
import ratingapp.ddey.com.dam_project.activities.student.QuizHistoryActivity;
import ratingapp.ddey.com.dam_project.activities.teacher.NewQuizActivity;
import ratingapp.ddey.com.dam_project.activities.teacher.ActiveQuizzesActivity;
import ratingapp.ddey.com.dam_project.activities.teacher.InactiveQuizzesActivity;
import ratingapp.ddey.com.dam_project.activities.teacher.StudentsResultsActivity;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.others.Session;

public class BaseDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvUserType;

    private CircleImageView ivProfile;
    private Uri imgURL;

    private Session mSession;
    private DbHelper mDb;

    private int semafor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abstract);
    }

    protected void onCreateDrawer() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mSession = new Session(getApplicationContext());
        mDb = new DbHelper(getApplicationContext());

        String name = mDb.getName(mSession);
        String email = mSession.getPrefs().getString("email", null);
        String userType = mDb.getUserType(mSession);

        if (userType.equals("TEACHER"))
            semafor = 0;
        else if (userType.equals("STUDENT"))
            semafor = 1;


        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        tvName = header.findViewById(R.id.nav_header_name);
        tvEmail = header.findViewById(R.id.nav_header_email);
        tvUserType = header.findViewById(R.id.nav_header_typeofaccount);

        tvName.setText(name);
        tvEmail.setText(email);
        tvUserType.setText(userType);

        ivProfile = header.findViewById(R.id.nav_header_profile_picture);
        Uri existingImgUri = mDb.getUserImageUri(mDb.getId(mSession));
        if (existingImgUri != null) {
            ContentResolver resolver = this.getContentResolver();
            imgURL = existingImgUri;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, imgURL);
                bitmap = rotateBitmap(bitmap, -90);
                ivProfile.setImageBitmap(bitmap);
                mDb.updateUserImgUri(mDb.getId(mSession), imgURL.toString());
                //ivProfile.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                // verific versiunea de sdk si pun persistable uri permission ca sa nu dea eroare de permisiune dupa aceea
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                } else {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                }
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select image"), Constants.REQUEST_CODE_GALLERY_UPLOAD_PHOTO);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;

        if (semafor == 1 || semafor == 0) {
            switch (menuItem.getItemId()) {
                case R.id.nav_quiz_charts:
                    intent = new Intent(getApplicationContext(), ChartsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_about:
                    intent = new Intent(getApplicationContext(), AboutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_edit_profile:
                    intent = new Intent(this, EditProfileActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_settings:
                    Toast.makeText(this, R.string.abstract_settings, Toast.LENGTH_SHORT).show();
                    intent = new Intent(this, SettingsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_logout:
                    mSession.logoutUser();
                    Toast.makeText(this, R.string.abstract_logout, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        if (semafor == 0) {

            switch (menuItem.getItemId()) {
                case R.id.nav_new_quizz:
                    intent = new Intent(getApplicationContext(), NewQuizActivity.class);
                    startActivityForResult(intent, Constants.REQUEST_CODE_CREATE_QUIZZ);
                    break;
                case R.id.nav_active_quizzes:
                    intent = new Intent(this, ActiveQuizzesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_inactive_quizzes:
                    intent = new Intent(this, InactiveQuizzesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_student_results:
                    intent = new Intent(this, StudentsResultsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_join_privatequizz:
                    Toast.makeText(getApplicationContext(), R.string.abstract_optionforstudentsonly, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_join_publicquizz:
                    Toast.makeText(getApplicationContext(), R.string.abstract_optionforstudentsonly, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_quizz_history:
                    Toast.makeText(getApplicationContext(), R.string.abstract_optionforstudentsonly, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        if (semafor == 1) {
            switch (menuItem.getItemId()) {
                case R.id.nav_new_quizz:
                    Toast.makeText(getApplicationContext(), R.string.R_string_abstract_optionforteachersonly, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_active_quizzes:
                    Toast.makeText(getApplicationContext(), R.string.R_string_abstract_optionforteachersonly, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_inactive_quizzes:
                    Toast.makeText(getApplicationContext(), R.string.R_string_abstract_optionforteachersonly, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_student_results:
                    Toast.makeText(getApplicationContext(), R.string.R_string_abstract_optionforteachersonly, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_join_privatequizz:
                    intent = new Intent(this, JoinPrivateQuizActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_join_publicquizz:
                    intent = new Intent(this, JoinPublicQuizActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_quizz_history:
                    intent = new Intent(this, QuizHistoryActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        onCreateDrawer();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE_CREATE_QUIZZ && resultCode == RESULT_OK && data != null) {
            Quiz receivedQuiz = data.getParcelableExtra(Constants.ADD_QUIZZ_KEY);

            if (receivedQuiz != null) {
                Intent newIntent = new Intent(getApplicationContext(), InactiveQuizzesActivity.class);
                newIntent.putExtra(Constants.ADD_QUIZZ_KEY, receivedQuiz);
                startActivity(newIntent);
            }
        } else if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == Constants.REQUEST_CODE_GALLERY_UPLOAD_PHOTO) {
                ContentResolver resolver = this.getContentResolver();
                imgURL = data.getData();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    int takeFlags = data.getFlags() & Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    resolver.takePersistableUriPermission(imgURL, takeFlags);
                }
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, imgURL);
                    bitmap = rotateBitmap(bitmap, -90);
                    ivProfile.setImageBitmap(bitmap);
                    mDb.updateUserImgUri(mDb.getId(mSession), imgURL.toString());
                    //ivProfile.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // problema de alocare memorie Bitmap des intalnita pe android -> manifesT: android:hardwareAccelerated="false" , android:largeHeap="true"
    public static Bitmap rotateBitmap(Bitmap sourceBitmap, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight(), matrix, true);
    }


}