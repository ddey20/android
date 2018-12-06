package ratingapp.ddey.com.dam_project.activities.general;


import android.os.Bundle;

import ratingapp.ddey.com.dam_project.R;

public class AboutActivity extends BaseDrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        super.onCreateDrawer();
    }
}
