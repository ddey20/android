package ratingapp.ddey.com.dam_project.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Question implements Parcelable {
    private int idQuestion;
    private String questionText;
    private int answerTime;
    private List<Answer> answersList;

    public Question(int idQuestion, String questionText, int answerTime, List<Answer> answersList) {
        this.idQuestion = idQuestion;
        this.questionText = questionText;
        this.answerTime = answerTime;
        this.answersList = answersList;
    }

    public Question(String questionText, int answerTime, List<Answer> answersList) {
        this.questionText = questionText;
        this.answerTime = answerTime;
        this.answersList = answersList;
    }


    protected Question(Parcel in) {
        idQuestion = in.readInt();
        questionText = in.readString();
        answerTime = in.readInt();
        answersList = in.createTypedArrayList(Answer.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idQuestion);
        dest.writeString(questionText);
        dest.writeInt(answerTime);
        dest.writeTypedList(answersList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(int answerTime) {
        this.answerTime = answerTime;
    }

    public List<Answer> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<Answer> answersList) {
        this.answersList = answersList;
    }


}
