package ratingapp.ddey.com.dam_project.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Quiz implements Parcelable {
    private long idQuizz;
    private String title;
    private String description;
    private String visibility;
    private long accessCode;
    private List<Question> questionsList;
    private String category;
    private boolean isActive = false;


    protected Quiz(Parcel in) {
        idQuizz = in.readLong();
        title = in.readString();
        description = in.readString();
        visibility = in.readString();
        accessCode = in.readLong();
        questionsList = in.createTypedArrayList(Question.CREATOR);
        category = in.readString();
        isActive = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idQuizz);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(visibility);
        dest.writeLong(accessCode);
        dest.writeTypedList(questionsList);
        dest.writeString(category);
        dest.writeByte((byte) (isActive ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Quiz> CREATOR = new Creator<Quiz>() {
        @Override
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        @Override
        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Quiz(long idQuizz, String title, String description, String visibility, long accessCode, List<Question> questionsList, String category, boolean isActive) {
        this.idQuizz = idQuizz;
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.accessCode = accessCode;
        this.questionsList = questionsList;
        this.category = category;
        this.isActive = isActive;
    }

    public Quiz(String title, String description, String visibility, long accessCode, List<Question> questionsList, String category, boolean isActive) {
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.accessCode = accessCode;
        this.questionsList = questionsList;
        this.category = category;
        this.isActive = isActive;
    }



    public Quiz(String title, String description, String visibility, String category) {
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.category = category;
    }

    public Quiz(String title, String description, String visibility, List<Question> questionsList, String category) {
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.questionsList = questionsList;
        this.category = category;
    }

    public Quiz(String title, String description, String visibility, long accessCode, List<Question> questionsList, String category) {
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.accessCode = accessCode;
        this.questionsList = questionsList;
        this.category = category;
    }

    public Quiz(int idQuizz, String title, String description, String visibility, long accessCode, List<Question> questionsList, String category) {
        this.idQuizz = idQuizz;
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.accessCode = accessCode;
        this.questionsList = questionsList;
        this.category = category;
    }

    public Quiz(String title, String description, String visibility) {
        this.title = title;
        this.description = description;
        this.visibility = visibility;
    }

    public long getIdQuizz() {
        return idQuizz;
    }

    public void setIdQuizz(long idQuizz) {
        this.idQuizz = idQuizz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public long getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(long accessCode) {
        this.accessCode = accessCode;
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
