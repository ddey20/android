package ratingapp.ddey.com.dam_project.activities.general;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;
import ratingapp.ddey.com.dam_project.utils.others.ChartsView;
import ratingapp.ddey.com.dam_project.utils.others.Session;

public class ChartsActivity extends AppCompatActivity {
    private DbHelper mDb;
    private Session mSession;

    private List<Quiz> quizzesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_charts);
        mDb = new DbHelper(this);
        mSession = new Session(this);

        quizzesList = mDb.getAllQuizzes();
        ChartsView cv = new ChartsView(ChartsActivity.this, formSourceValues(quizzesList));
        setContentView(cv);
    }


    private Map<String, Integer> formSourceValues(List<Quiz> initialList) {
        Map<String,Integer> resultMap = null;
        if (initialList != null && initialList.size() > 0) {
            resultMap = new HashMap<>();
            for (Quiz q : initialList) {
                if (resultMap.containsKey(q.getCategory())) {
                    resultMap.put(q.getCategory(), resultMap.get(q.getCategory()) + 1);
                } else {
                    resultMap.put(q.getCategory(), 1);
                }
            }
        }
        return resultMap;
    }

    //Back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}
