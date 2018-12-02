package ratingapp.ddey.com.dam_project.models;

public class Result {
    private int idResult;
    private String studentName;
    private String quizzName;
    private int score;
    private String dateTime;

    public Result(String studentName, String quizzName, int score, String dateTime) {
        this.studentName = studentName;
        this.quizzName = quizzName;
        this.score = score;
        this.dateTime = dateTime;
    }

    public String getQuizzName() {
        return quizzName;
    }

    public void setQuizzName(String quizzName) {
        this.quizzName = quizzName;
    }

    public int getIdResult() {
        return idResult;
    }

    public void setIdResult(int idResult) {
        this.idResult = idResult;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
