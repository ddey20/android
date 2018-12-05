package ratingapp.ddey.com.dam_project.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Answer implements Parcelable {
    private long idAnswer;
    private boolean isCorrect;
    private String answer;

    public Answer(long idAnswer, boolean isCorrect, String answer) {
        this.idAnswer = idAnswer;
        this.isCorrect = isCorrect;
        this.answer = answer;
    }

    public Answer(long idAnswer, String answer) {
        this.idAnswer = idAnswer;
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
        idAnswer = in.readLong();
        isCorrect = in.readByte() != 0;
        answer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idAnswer);
        dest.writeByte((byte) (isCorrect ? 1 : 0));
        dest.writeString(answer);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public long getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(long idAnswer) {
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

}
