package ratingapp.ddey.com.dam_project.utils.database;

public interface DbConstants {
    String TAG = DbHelper.class.getSimpleName();
    String DB_NAME = "DAM_DATABASE.db";
    int DB_VERSION = 3;

    //USERS
    String USER_TABLE = "users";
    String COLUMN_ID_USER = "id_user";
    String COLUMN_EMAIL = "email";
    String COLUMN_PASS = "password";
    String COLUMN_NAME = "name";
    String COLUMN_DATE = "date";
    String COLUMN_GENDER = "gender";
    String COLUMN_USER_TYPE = "user_type";
    String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
            + COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_EMAIL + " TEXT NOT NULL,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_PASS + " TEXT NOT NULL,"
            + COLUMN_USER_TYPE + " TEXT NOT NULL,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_GENDER + " TEXT);";
    //RESULTS
    String RESULT_TABLE = "results";
    String COLUMN_ID_RESULT = "id_result";
    String COLUMN_STUDENT_NAME = "name";
    String COLUMN_QUIZZ_NAME = "quizz_name";
    String COLUMN_SCORE = "score";
    String COLUMN_DATETIME = "date";
    String CREATE_TABLE_RESULTS = "CREATE TABLE " + RESULT_TABLE + "("
            + COLUMN_ID_RESULT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_STUDENT_NAME + " TEXT NOT NULL,"
            + COLUMN_QUIZZ_NAME + " TEXT NOT NULL,"
            + COLUMN_SCORE + " TEXT NOT NULL,"
            + COLUMN_DATETIME + " TEXT NOT NULL);";
    //QUIZZES
    String QUIZ_TABLE = "quizzes";
    String COLUMN_ID_QUIZ = "id_quiz";
    String COLUMN_TITLE = "title";
    String COLUMN_DESCRIPTION = "description";
    String COLUMN_VISIBILITY = "visibility";
    String COLUMN_ACCESS_CODE = "access_code";
    String COLUMN_CATEGORY = "category";
    String COLUMN_IS_ACTIVE = "isActive";
    String CREATE_TABLE_QUIZZ = "CREATE TABLE " + QUIZ_TABLE + "("
            + COLUMN_ID_QUIZ + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TITLE + " TEXT NOT NULL,"
            + COLUMN_DESCRIPTION + " TEXT NOT NULL,"
            + COLUMN_VISIBILITY + " TEXT NOT NULL,"
            + COLUMN_ACCESS_CODE + " TEXT,"
            + COLUMN_IS_ACTIVE + "  INTEGER,"
            + COLUMN_CATEGORY + " TEXT);";
    //QUESTIONS
    String QUESTION_TABLE = "questions";
    String COLUMN_ID_QUESTION = "id_question";
    String COLUMN_TEXT = "text_question";
    String COLUMN_ANSWER_TIME = "answer_time";
    String CREATE_TABLE_QUESTION = "CREATE TABLE " + QUESTION_TABLE + "("
            + COLUMN_ID_QUESTION + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TEXT + " TEXT NOT NULL,"
            + COLUMN_ANSWER_TIME + " INTEGER NOT NULL,"
            + COLUMN_ID_QUIZ + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_ID_QUIZ + ") REFERENCES " + QUIZ_TABLE + "(" + COLUMN_ID_QUIZ + "));";
    //ANSWERS
    String ANSWER_TABLE = "answers";
    String COLUMN_ID_ANSWER = "id_answer";
    String COLUMN_IS_CORRECT = "correct";
    String COLUMN_ANSWER = "answer";
    String CREATE_TABLE_ANSWER = "CREATE TABLE " + ANSWER_TABLE + "("
            + COLUMN_ID_ANSWER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_IS_CORRECT + " INTEGER NOT NULL,"
            + COLUMN_ANSWER + " TEXT NOT NULL,"
            + COLUMN_ID_QUESTION + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_ID_QUESTION + ") REFERENCES " + QUESTION_TABLE + "(" + COLUMN_ID_QUESTION + "));";
}
