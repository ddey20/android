package ratingapp.ddey.com.dam_project.utils.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ratingapp.ddey.com.dam_project.R;
import ratingapp.ddey.com.dam_project.models.Result;
import ratingapp.ddey.com.dam_project.utils.database.DbHelper;

public class StudentResultsAdapter extends ArrayAdapter<Result> {
    private Context mContext;
    private int mResource;
    private List<Result> mList;
    private Activity mActivity;

    private DbHelper mDb;

    public StudentResultsAdapter(@NonNull Context context, int resource, @NonNull List<Result> list, Activity activity) {
        super(context, resource, list);
        mContext = context;
        mResource = resource;
        mList = list;
        mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater  = LayoutInflater.from(mContext);
            view = inflater.inflate(mResource, null);
        }

        TextView tvQuizz = view.findViewById(R.id.lv_student_results_quizzname);
        TextView tvName = view.findViewById(R.id.lv_student_results_student_name);
        TextView tvScore = view.findViewById(R.id.lv_student_results_score);
        TextView tvDate = view.findViewById(R.id.lv_student_results_date);

        final Result r = getItem(position);

        if (r != null) {
            if (r.getQuizzName() != null) {
                tvQuizz.setText(r.getQuizzName());
            }
            if (r.getStudentName() != null) {
                tvName.setText(r.getStudentName());
            }
            if (r.getScore() != -1) {
                String strTemp = "Score: " + String.valueOf(r.getScore());
                tvScore.setText(strTemp);
            }
            if (r.getDateTime() != null) {
                String strTemp = "Date: " + r.getDateTime();
                tvDate.setText(strTemp);
            }
        }


        // DELETE RESULT
        mDb = new DbHelper(mActivity);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                dialog.setMessage(mContext.getString(R.string.student_delete_dialog_message))
                        .setTitle(mContext.getString(R.string.student_delete_dialog_title))
                        .setPositiveButton(mContext.getString(R.string.student_delete_dialog_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mList.remove(position);
                                mDb.deleteResult(r);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(mContext.getString(R.string.student_delete_dialog_no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dialog.create().show();
                return true;
            }
        });

        //MODIFY SCORE
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder insertDialog = new AlertDialog.Builder(mActivity);

                final EditText input = new EditText(mActivity);
                insertDialog.setView(input);

                insertDialog.setTitle(mContext.getString(R.string.change_student_dialog))
                        .setMessage(mContext.getString(R.string.insert_final_dialog_result))
                        .setPositiveButton(mContext.getString(R.string.save_update_result), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!input.getText().toString().trim().isEmpty() && input.getText() != null) {
                                    mList.get(position).setScore(Integer.valueOf(input.getText().toString()));
                                    mDb.updateResultScore(mList.get(position), Integer.valueOf(input.getText().toString()));
                                    notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton(mContext.getString(R.string.cancel_update_result), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //dialog.dismiss();
                                Toast.makeText(mActivity, mContext.getString(R.string.change_student_score_cancel_dialog), Toast.LENGTH_SHORT).show();
                            }
                        });

                insertDialog.create();
                insertDialog.show();
            }
        });
        return view;
    }
}


