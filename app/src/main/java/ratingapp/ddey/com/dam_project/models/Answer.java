package ratingapp.ddey.com.dam_project.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    private int idAnswer;
    private boolean isCorrect;
    private String answer;

    public Answer(int idAnswer, boolean isCorrect, String answer) {
        this.idAnswer = idAnswer;
        this.isCorrect = isCorrect;
        this.answer = answer;
    }

    public Answer(boolean isCorrect, String answer) {
        this.isCorrect = isCorrect;
        this.answer = answer;
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    protected Answer(Parcel in) {
        idAnswer = in.readInt();
        isCorrect = in.readByte() != 0;
        answer = in.readString();
    }

    public static final Creator<Answer> CREATOR = new Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel in) {
            return new Answer(in);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idAnswer);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
        dest.writeString(answer);
    }
}
