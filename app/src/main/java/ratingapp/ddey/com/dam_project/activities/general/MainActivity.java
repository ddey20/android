package ratingapp.ddey.com.dam_project.activities.general;

import android.os.Bundle;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.others.Session;

import android.widget.TextView;

public class MainActivity extends BaseDrawerActivity {
    private TextView tvMessage;
    private Session mSession;
    private DbHelper mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();

        mSession = new Session(this);
        mDb = new DbHelper(this);

        tvMessage = findViewById(R.id.main_tv_user);
        String helloUser = "Hello " + mDb.getName(mSession) + "!";
        tvMessage.setText(helloUser);
    }
}
