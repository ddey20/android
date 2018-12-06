package ratingapp.ddey.com.dam_project.utils.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.activities.teacher.InactiveQuizzesActivity;
import ratingapp.ddey.com.dam_project.activities.teacher.QuizAccessTypeActivity;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;

public class ActiveQuizAdapter extends ArrayAdapter<Quiz> {
    private Context mContext;
    private int mResource;
    private List<Quiz> mList;
    private Activity mActivity;
    private DbHelper mDb;

    public ActiveQuizAdapter(@NonNull Context context, int resource, @NonNull List<Quiz> list, Activity activity) {
        super(context, resource, list);
        this.mContext = context;
        this.mResource = resource;
        this.mList = list;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
        }

        Quiz quiz = getItem(position);

        if (quiz != null) {
            TextView tvTitle = view.findViewById(R.id.lv_activequizz_title);
            TextView tvDescription = view.findViewById(R.id.lv_activequizz_description);
            TextView tvVisibility = view.findViewById(R.id.lv_activequizz_visibility);
            TextView tvCategory = view.findViewById(R.id.lv_activequizz_category);
            TextView tvAccessCode = view.findViewById(R.id.lv_activequizz_accesscode);

            if (quiz.getTitle() != null) {
                tvTitle.setText(quiz.getTitle());
            }
            if (quiz.getDescription() != null) {
                tvDescription.setText(quiz.getDescription());
            }
            if (quiz.getVisibility() != null) {
                String strTemp = "Visibility: " + quiz.getVisibility();
                tvVisibility.setText(strTemp);
            }
            if (quiz.getAccessCode() != 0) {
                String strTemp = "Access code: " + String.valueOf(quiz.getAccessCode());
                tvAccessCode.setText(strTemp);
            }
            if (quiz.getCategory() != null) {
                String strTemp = "Quiz category: " + quiz.getCategory();
                tvCategory.setText(strTemp);
            }
        }

        ImageButton btnDelete = view.findViewById(R.id.lv_activequizz_imgbutton_delete);
        btnDelete.setTag(position);

        btnDelete.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                dialog.setMessage(R.string.adapter_areyousure)
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = (int) v.getTag();
                                Quiz q = mList.get(index);
                                mDb.deleteQuizz(q);
                                mList.remove(index);
                                notifyDataSetChanged();
                            }
                        });

                AlertDialog alert = dialog.create();
                alert.setTitle("Delete quiz");
                alert.show();
            }
        });

        mDb = new DbHelper(mActivity);
        Button btnDeactivate = view.findViewById(R.id.lv_activequizz_btn_deactivate);
        btnDeactivate.setTag(position);
        btnDeactivate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                dialog.setMessage(R.string.adapter_areyousure_deactivate)
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int index = (int) v.getTag();
                                Quiz currentQuiz = mList.get(index);
                                mDb.updateQuizActivity(currentQuiz, false);
                                Intent intent = new Intent(mContext, InactiveQuizzesActivity.class);
                                //intent.putExtra(Constants.DEACTIVATE_QUIZZ_KEY, currentQuiz);
                                mActivity.startActivity(intent);
                                mList.remove(index);
                                notifyDataSetChanged();
                                mActivity.finish();
                            }
                        });

                AlertDialog alert = dialog.create();
                alert.setTitle("Deactivate quiz");
                alert.show();
            }
        });

        Button btnAccessType = view.findViewById(R.id.lv_activequizz_btn_accesstype_config);
        btnAccessType.setTag(position);

        btnAccessType.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(final View v) {

                int index = (int) v.getTag();
                Quiz currentQuiz = mList.get(index);
                Intent intent = new Intent(mContext, QuizAccessTypeActivity.class);
                intent.putExtra(Constants.CHANGE_ACCESSTYPE_QUIZZ_KEY, currentQuiz);
                intent.putExtra(Constants.LV_INDEX_KEY, position);
                mActivity.startActivityForResult(intent, Constants.REQUEST_CODE_ACCESSTYPE_QUIZZ);
            }
        });

        return view;
    }

    public void refreshList(List<Quiz> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }
}
