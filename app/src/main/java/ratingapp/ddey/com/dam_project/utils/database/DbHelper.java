package ratingapp.ddey.com.dam_project.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ratingapp.ddey.com.dam_project.activities.general.EditProfileActivity;
import ratingapp.ddey.com.dam_project.models.Answer;
import ratingapp.ddey.com.dam_project.models.Question;
import ratingapp.ddey.com.dam_project.models.Quiz;
import ratingapp.ddey.com.dam_project.models.Result;
import ratingapp.ddey.com.dam_project.models.User;
import ratingapp.ddey.com.dam_project.models.UserType;
import ratingapp.ddey.com.dam_project.utils.others.Constants;
import ratingapp.ddey.com.dam_project.utils.others.Session;

//VERSION = NR FAZA
public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, DbConstants.DB_NAME, null, DbConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbConstants.CREATE_TABLE_USERS);
        db.execSQL(DbConstants.CREATE_TABLE_RESULTS);
        db.execSQL(DbConstants.CREATE_TABLE_QUIZZ);
        db.execSQL(DbConstants.CREATE_TABLE_QUESTION);
        db.execSQL(DbConstants.CREATE_TABLE_ANSWER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.RESULT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.QUIZ_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.QUESTION_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DbConstants.ANSWER_TABLE);
        onCreate(db);
    }

    // OPERATII CRUD (pentru fiecare tabela exista cel putin o metoda din fiecare)
    // C - CREATE (INSERT)

    public void insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_EMAIL, user.getEmail());
        values.put(DbConstants.COLUMN_NAME, user.getName());
        values.put(DbConstants.COLUMN_PASS, user.getPassword());
        values.put(DbConstants.COLUMN_USER_TYPE, user.getUserType().name());
        Log.d(DbConstants.TAG, "insertUser: " + user.getUserType().name());
        values.put(DbConstants.COLUMN_DATE, "");
        values.put(DbConstants.COLUMN_GENDER, "");
        long id = db.insert(DbConstants.USER_TABLE, null, values);

        user.setIdUser(id);

        db.close();
        Log.d(DbConstants.TAG, "User inserted " + id);
    }

    public void insertResult(Result result) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_STUDENT_NAME, result.getStudentName());
        values.put(DbConstants.COLUMN_QUIZZ_NAME, result.getQuizzName());
        values.put(DbConstants.COLUMN_SCORE, result.getScore());
        values.put(DbConstants.COLUMN_DATE, result.getDateTime());
        Log.d(DbConstants.TAG, "insertResult: " + result.getStudentName());

        long id = db.insert(DbConstants.RESULT_TABLE, null, values);
        result.setIdResult(id);
        db.close();
        Log.d(DbConstants.TAG, "Result inserted " + id);
    }

    public void insertQuizz(Quiz newQuiz) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_TITLE, newQuiz.getTitle());
        values.put(DbConstants.COLUMN_DESCRIPTION, newQuiz.getDescription());
        values.put(DbConstants.COLUMN_VISIBILITY, newQuiz.getVisibility());
        values.put(DbConstants.COLUMN_ACCESS_CODE, newQuiz.getAccessCode());
        values.put(DbConstants.COLUMN_CATEGORY, newQuiz.getCategory());
        values.put(DbConstants.COLUMN_IS_ACTIVE, newQuiz.isActive());

        int id = (int) db.insert(DbConstants.QUIZ_TABLE, null, values);
        newQuiz.setIdQuizz(id);
        db.close();
        for (Question q : newQuiz.getQuestionsList()) {
            insertQuestion(q, newQuiz);
        }
        Log.d(DbConstants.TAG, "Quizz inserted " + id);
    }

    public void insertQuestion(Question q, Quiz currentQuiz) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_TEXT, q.getQuestionText());
        values.put(DbConstants.COLUMN_ANSWER_TIME, q.getAnswerTime());
        values.put(DbConstants.COLUMN_ID_QUIZ, currentQuiz.getIdQuizz());

        int id = (int) db.insert(DbConstants.QUESTION_TABLE, null, values);
        q.setIdQuestion(id);
        db.close();
        for (Answer a : q.getAnswersList()) {
            insertAnswer(a, q);
        }
        Log.d(DbConstants.TAG, "Question inserted " + id);
    }

    public void insertAnswer(Answer answer, Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_ANSWER, answer.getAnswer());
        values.put(DbConstants.COLUMN_IS_CORRECT, answer.isCorrect());
        values.put(DbConstants.COLUMN_ID_QUESTION, question.getIdQuestion());

        int id = (int) db.insert(DbConstants.ANSWER_TABLE, null, values);
        answer.setIdAnswer(id);
        db.close();
        Log.d(DbConstants.TAG, "Answer inserted " + id);
    }

    public void insertQuizzWithoutAnswers(Quiz newQuiz) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_TITLE, newQuiz.getTitle());
        values.put(DbConstants.COLUMN_DESCRIPTION, newQuiz.getDescription());
        values.put(DbConstants.COLUMN_VISIBILITY, newQuiz.getVisibility());
        values.put(DbConstants.COLUMN_ACCESS_CODE, newQuiz.getAccessCode());
        values.put(DbConstants.COLUMN_CATEGORY, newQuiz.getCategory());
        values.put(DbConstants.COLUMN_IS_ACTIVE, newQuiz.isActive());

        int id = (int) db.insert(DbConstants.QUIZ_TABLE, null, values);
        newQuiz.setIdQuizz(id);
        db.close();
        for (Question q : newQuiz.getQuestionsList()) {
            insertQuestionsWithoutAnswers(q, newQuiz);
        }
        Log.d(DbConstants.TAG, "Quizz inserted " + id);
    }

    public void insertQuestionsWithoutAnswers(Question q, Quiz currentQuiz) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_TEXT, q.getQuestionText());
        values.put(DbConstants.COLUMN_ANSWER_TIME, q.getAnswerTime());
        values.put(DbConstants.COLUMN_ID_QUIZ, currentQuiz.getIdQuizz());

        int id = (int) db.insert(DbConstants.QUESTION_TABLE, null, values);
        q.setIdQuestion(id);
        db.close();
        Log.d(DbConstants.TAG, "Question inserted " + id);
    }

    //R - READ (GET)

    public List<Quiz> getQuizzes(boolean state) {
        List<Quiz> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbConstants.QUIZ_TABLE, new String[]{DbConstants.COLUMN_ID_QUIZ, DbConstants.COLUMN_TITLE, DbConstants.COLUMN_DESCRIPTION, DbConstants.COLUMN_VISIBILITY, DbConstants.COLUMN_ACCESS_CODE, DbConstants.COLUMN_CATEGORY, DbConstants.COLUMN_IS_ACTIVE}, null, null, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(DbConstants.COLUMN_ID_QUIZ);
            long id = c.getLong(index);

            index = c.getColumnIndex(DbConstants.COLUMN_TITLE);
            String title = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_DESCRIPTION);
            String desc = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_VISIBILITY);
            String visib = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_ACCESS_CODE);
            long code = Long.parseLong(c.getString(index));

            index = c.getColumnIndex(DbConstants.COLUMN_CATEGORY);
            String category = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_IS_ACTIVE);
            boolean isActive;
            if (c.getInt(index) == 1)
                isActive = true;
            else
                isActive = false;

            if (isActive == state) {
                List<Question> questions = getQuestions(id);

                Quiz q = new Quiz(id, title, desc, visib, code, questions, category, isActive);
                resultList.add(q);
            }
        }
        c.close();
        db.close();
        return resultList;
    }

    public List<Quiz> getAllQuizzes() {
        List<Quiz> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbConstants.QUIZ_TABLE, new String[]{DbConstants.COLUMN_ID_QUIZ, DbConstants.COLUMN_TITLE, DbConstants.COLUMN_DESCRIPTION, DbConstants.COLUMN_VISIBILITY, DbConstants.COLUMN_ACCESS_CODE, DbConstants.COLUMN_CATEGORY, DbConstants.COLUMN_IS_ACTIVE}, null, null, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(DbConstants.COLUMN_ID_QUIZ);
            long id = c.getLong(index);

            index = c.getColumnIndex(DbConstants.COLUMN_TITLE);
            String title = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_DESCRIPTION);
            String desc = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_VISIBILITY);
            String visib = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_ACCESS_CODE);
            long code = Long.parseLong(c.getString(index));

            index = c.getColumnIndex(DbConstants.COLUMN_CATEGORY);
            String category = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_IS_ACTIVE);
            boolean isActive;
            if (c.getInt(index) == 1)
                isActive = true;
            else
                isActive = false;

            List<Question> questions = getQuestions(id);

            Quiz q = new Quiz(id, title, desc, visib, code, questions, category, isActive);
            resultList.add(q);
        }
        c.close();
        db.close();
        return resultList;
    }

    public Quiz getPrivateQuiz(long searchedAccessCode) {
        Quiz result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbConstants.QUIZ_TABLE, new String[]{DbConstants.COLUMN_ID_QUIZ, DbConstants.COLUMN_TITLE, DbConstants.COLUMN_DESCRIPTION, DbConstants.COLUMN_VISIBILITY, DbConstants.COLUMN_ACCESS_CODE, DbConstants.COLUMN_CATEGORY, DbConstants.COLUMN_IS_ACTIVE}, null, null, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(DbConstants.COLUMN_ID_QUIZ);
            long id = c.getLong(index);

            index = c.getColumnIndex(DbConstants.COLUMN_TITLE);
            String title = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_DESCRIPTION);
            String desc = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_VISIBILITY);
            String visib = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_ACCESS_CODE);
            long code = Long.parseLong(c.getString(index));

            index = c.getColumnIndex(DbConstants.COLUMN_CATEGORY);
            String category = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_IS_ACTIVE);
            boolean isActive;
            if (c.getInt(index) == 1)
                isActive = true;
            else
                isActive = false;

            if (code == searchedAccessCode && visib.equals("Private")) {
                List<Question> questions = getQuestions(id);

                result = new Quiz(id, title, desc, visib, code, questions, category, isActive);

            }
        }
        c.close();
        db.close();
        return result;
    }

    public List<Quiz> getQuizzesByCategory(String searchedCategory) {
        List<Quiz> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbConstants.QUIZ_TABLE, new String[]{DbConstants.COLUMN_ID_QUIZ, DbConstants.COLUMN_TITLE, DbConstants.COLUMN_DESCRIPTION, DbConstants.COLUMN_VISIBILITY, DbConstants.COLUMN_ACCESS_CODE, DbConstants.COLUMN_CATEGORY, DbConstants.COLUMN_IS_ACTIVE}, null, null, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(DbConstants.COLUMN_ID_QUIZ);
            long id = c.getLong(index);

            index = c.getColumnIndex(DbConstants.COLUMN_TITLE);
            String title = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_DESCRIPTION);
            String desc = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_VISIBILITY);
            String visib = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_ACCESS_CODE);
            long code = Long.parseLong(c.getString(index));

            index = c.getColumnIndex(DbConstants.COLUMN_CATEGORY);
            String category = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_IS_ACTIVE);
            boolean isActive;
            if (c.getInt(index) == 1)
                isActive = true;
            else
                isActive = false;

            if (category.equals(searchedCategory) && visib.equals("Public")) {
                List<Question> questions = getQuestions(id);

                Quiz q = new Quiz(id, title, desc, visib, code, questions, category, isActive);
                resultList.add(q);
            }
        }
        c.close();
        db.close();
        return resultList;
    }

    public List<Question> getQuestions(long idQuizz) {
        List<Question> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbConstants.QUESTION_TABLE, new String[]{DbConstants.COLUMN_ID_QUESTION, DbConstants.COLUMN_TEXT, DbConstants.COLUMN_ANSWER_TIME}, DbConstants.COLUMN_ID_QUIZ + "= ?", new String[]{String.valueOf(idQuizz)}, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(DbConstants.COLUMN_ID_QUESTION);
            long id = c.getLong(index);

            index = c.getColumnIndex(DbConstants.COLUMN_TEXT);
            String title = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_ANSWER_TIME);
            int time = c.getInt(index);

            List<Answer> answers = getAnswers(id);
            Question q = new Question(id, title, time, answers);
            resultList.add(q);
        }
        c.close();
        db.close();

        return resultList;
    }

    public List<Answer> getAnswers(long idQuestion) {
        List<Answer> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbConstants.ANSWER_TABLE, new String[]{DbConstants.COLUMN_ID_ANSWER, DbConstants.COLUMN_IS_CORRECT, DbConstants.COLUMN_ANSWER}, DbConstants.COLUMN_ID_QUESTION + "= ?", new String[]{String.valueOf(idQuestion)}, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(DbConstants.COLUMN_ID_ANSWER);
            long id = c.getLong(index);

            index = c.getColumnIndex(DbConstants.COLUMN_IS_CORRECT);
            int correct = c.getInt(index);

            boolean isCorrect;
            if (correct == 1)
                isCorrect = true;
            else
                isCorrect = false;

            index = c.getColumnIndex(DbConstants.COLUMN_ANSWER);
            String answer = c.getString(index);

            Answer a = new Answer(id, isCorrect, answer);
            resultList.add(a);
        }
        c.close();
        db.close();

        return resultList;
    }

    public List<Result> getResultsByName(Session session, String name) {
        List<Result> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbConstants.RESULT_TABLE, new String[]{DbConstants.COLUMN_QUIZZ_NAME, DbConstants.COLUMN_SCORE, DbConstants.COLUMN_DATETIME}, DbConstants.COLUMN_STUDENT_NAME + " = ?", new String[]{name}, null, null, null);
        while (c.moveToNext()) {
            int index = c.getColumnIndex(DbConstants.COLUMN_QUIZZ_NAME);
            String quizzName = c.getString(index);
            index = c.getColumnIndex(DbConstants.COLUMN_SCORE);
            int score = c.getInt(index);
            index = c.getColumnIndex(DbConstants.COLUMN_DATETIME);
            String date = c.getString(index);
            Result res = new Result(getName(session), quizzName, score, date);
            resultList.add(res);
        }
        c.close();
        db.close();
        return resultList;
    }

    public List<Result> getAllResults() {
        List<Result> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DbConstants.RESULT_TABLE, new String[]{DbConstants.COLUMN_ID_RESULT, DbConstants.COLUMN_QUIZZ_NAME, DbConstants.COLUMN_STUDENT_NAME, DbConstants.COLUMN_SCORE, DbConstants.COLUMN_DATETIME}, null, null, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(DbConstants.COLUMN_ID_RESULT);
            long idResult = c.getLong(index);

            index = c.getColumnIndex(DbConstants.COLUMN_QUIZZ_NAME);
            String quizzName = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_STUDENT_NAME);
            String studentName = c.getString(index);

            index = c.getColumnIndex(DbConstants.COLUMN_SCORE);
            int score = c.getInt(index);

            index = c.getColumnIndex(DbConstants.COLUMN_DATETIME);
            String date = c.getString(index);

            Result res = new Result(idResult, studentName, quizzName, score, date);
            resultList.add(res);
        }
        c.close();
        db.close();
        return resultList;
    }

    public boolean getUser(User user) {
        String selectQuery = "SELECT * FROM " + DbConstants.USER_TABLE + " WHERE "
                + DbConstants.COLUMN_EMAIL + " = " + "'" + user.getEmail() + "'" + " and "
                + DbConstants.COLUMN_PASS + " = " + "'" + user.getPassword() + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            return true;

        cursor.close();
        db.close();

        return false;
    }

    public String getUserType(Session session) {
        HashMap<String, String> user = session.getUserDetails();
        String email = user.get(Constants.KEY_EMAIL);
        String pass = user.get(Constants.KEY_PASS);

        String selectQuery = "SELECT " + DbConstants.COLUMN_USER_TYPE + " FROM " + DbConstants.USER_TABLE + " WHERE "
                + DbConstants.COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                + DbConstants.COLUMN_PASS + " = " + "'" + pass + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        String rez = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            rez = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_USER_TYPE));
        }

        cursor.close();
        db.close();

        return rez;
    }

    public String getName(Session session) {
        HashMap<String, String> user = session.getUserDetails();
        String email = user.get(Constants.KEY_EMAIL);
        String pass = user.get(Constants.KEY_PASS);

        String selectQuery = "SELECT name FROM " + DbConstants.USER_TABLE + " WHERE "
                + DbConstants.COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                + DbConstants.COLUMN_PASS + " = " + "'" + pass + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        String rez = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            rez = cursor.getString(cursor.getColumnIndex("name"));
        }

        cursor.close();
        db.close();

        return rez;
    }

    public String emailGet(Session session) {
        HashMap<String, String> user = session.getUserDetails();
        String email = user.get(Constants.KEY_EMAIL);
        String pass = user.get(Constants.KEY_PASS);

        String selectQuery = "SELECT email FROM " + DbConstants.USER_TABLE + " WHERE "
                + DbConstants.COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                + DbConstants.COLUMN_PASS + " = " + "'" + pass + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        String rez = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            rez = cursor.getString(cursor.getColumnIndex("email"));
        }

        cursor.close();
        db.close();

        return rez;
    }

    public String getId(Session session) {
        HashMap<String, String> user = session.getUserDetails();
        String email = user.get(Constants.KEY_EMAIL);
        String pass = user.get(Constants.KEY_PASS);

        String selectQuery = "SELECT " + DbConstants.COLUMN_ID_USER + " FROM " + DbConstants.USER_TABLE + " WHERE "
                + DbConstants.COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                + DbConstants.COLUMN_PASS + " = " + "'" + pass + "'";

        SQLiteDatabase db = getReadableDatabase();
        String rez = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        rez = String.valueOf(result);
        cursor.close();

        return rez;
    }

    public User retrieveProfile(Session session) {
        SQLiteDatabase db = this.getReadableDatabase();

        HashMap<String, String> user = session.getUserDetails();
        String email = user.get(Constants.KEY_EMAIL);
        String pass = user.get(Constants.KEY_PASS);
        String selectQuery = "SELECT * FROM " + DbConstants.USER_TABLE + " WHERE "
                + DbConstants.COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                + DbConstants.COLUMN_PASS + " = " + "'" + pass + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);
        User userRetrieved = new User();
        if (cursor.moveToFirst()) {
            do {
                userRetrieved.setEmail(cursor.getString(1));
                userRetrieved.setName(cursor.getString(2));
                userRetrieved.setPassword(cursor.getString(3));
                userRetrieved.setUserType(UserType.valueOf(cursor.getString(4)));
                userRetrieved.setDate(EditProfileActivity.convertStringToDate(cursor.getString(5)));
                userRetrieved.setGender(cursor.getString(6));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return userRetrieved;
    }

    // U - UPDATE

    public void updateEmail(String id, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_EMAIL, email);

        db.update(DbConstants.USER_TABLE, values, DbConstants.COLUMN_ID_USER + " = " + id, null);
        db.close();
    }

    public void updateDate(String id, String birthDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_DATE, birthDate);
        db.update(DbConstants.USER_TABLE, values, DbConstants.COLUMN_ID_USER + " = " + id, null);
        db.close();
    }

    public void updateGender(String id, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_GENDER, gender);
        db.update(DbConstants.USER_TABLE, values, DbConstants.COLUMN_ID_USER + " = " + id, null);
        db.close();
    }

    public void updateQuizActivity(Quiz quiz, boolean state) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_IS_ACTIVE, state);
        db.update(DbConstants.QUIZ_TABLE, values, DbConstants.COLUMN_ID_QUIZ + " = " + quiz.getIdQuizz(), null);
        db.close();
    }

    public void updateCategory(Quiz quiz, String categ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_CATEGORY, categ);
        db.update(DbConstants.QUIZ_TABLE, values, DbConstants.COLUMN_ID_QUIZ + " = " + quiz.getIdQuizz(), null);
        db.close();
    }

    public void updateVisibility(Quiz quiz, String visib) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_VISIBILITY, visib);
        db.update(DbConstants.QUIZ_TABLE, values, DbConstants.COLUMN_ID_QUIZ + " = " + quiz.getIdQuizz(), null);
        db.close();
    }

    public void updateCode(Quiz quiz, long code) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_ACCESS_CODE, code);
        db.update(DbConstants.QUIZ_TABLE, values, DbConstants.COLUMN_ID_QUIZ + " = " + quiz.getIdQuizz(), null);
        db.close();
    }

    public void updateQuiz(Quiz quiz) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DbConstants.COLUMN_TITLE, quiz.getTitle());
        cv.put(DbConstants.COLUMN_DESCRIPTION, quiz.getDescription());
        cv.put(DbConstants.COLUMN_VISIBILITY, quiz.getVisibility());
        cv.put(DbConstants.COLUMN_ACCESS_CODE, quiz.getAccessCode());
        cv.put(DbConstants.COLUMN_CATEGORY, quiz.getCategory());

        db.update(DbConstants.QUIZ_TABLE, cv, DbConstants.COLUMN_ID_QUIZ + " = ? ", new String[]{String.valueOf(quiz.getIdQuizz())});
        db.close();
    }

    public void updateQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DbConstants.COLUMN_ANSWER_TIME, question.getAnswerTime());
        cv.put(DbConstants.COLUMN_TEXT, question.getQuestionText());

        db.update(DbConstants.QUESTION_TABLE, cv, DbConstants.COLUMN_ID_QUESTION + " = ? ", new String[]{String.valueOf(question.getIdQuestion())});
        db.close();
    }

    public void updateAnswer(Answer answer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DbConstants.COLUMN_ANSWER, answer.getAnswer());
        cv.put(DbConstants.COLUMN_IS_CORRECT, answer.isCorrect());

        db.update(DbConstants.ANSWER_TABLE, cv, DbConstants.COLUMN_ID_ANSWER + " = ? ", new String[]{String.valueOf(answer.getIdAnswer())});
        db.close();
    }

    public void updateResultScore(Result r, int newScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DbConstants.COLUMN_SCORE, newScore);
        db.update(DbConstants.RESULT_TABLE, cv, DbConstants.COLUMN_ID_RESULT + " = ?", new String[]{String.valueOf(r.getIdResult())});

        db.close();
    }

    // D - DELETE
    public void deleteUser(long idUser) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(DbConstants.USER_TABLE, DbConstants.COLUMN_ID_USER + " = ?", new String[]{String.valueOf(idUser)});
        db.close();
    }

    public void deleteResult(Result r) {
        if (r != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DbConstants.RESULT_TABLE, DbConstants.COLUMN_ID_RESULT + " = ?", new String[]{String.valueOf(r.getIdResult())});
            db.close();
        }
    }

    public void deleteQuizz(Quiz q) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbConstants.QUIZ_TABLE, DbConstants.COLUMN_ID_QUIZ + " = ?", new String[]{String.valueOf(q.getIdQuizz())});
        db.close();

        for (Question question : q.getQuestionsList()) {
            deleteQuestion(question, q.getIdQuizz());
        }
    }

    public void deleteQuestion(Question question, long idQuizz) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbConstants.QUESTION_TABLE, DbConstants.COLUMN_ID_QUIZ + " = ?", new String[]{String.valueOf(idQuizz)});
        db.close();
        for (Answer a : question.getAnswersList()) {
            deleteAnswer(a, question.getIdQuestion());
        }
    }

    public void deleteQuestionSolely(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbConstants.QUESTION_TABLE, DbConstants.COLUMN_ID_QUESTION + " = ?", new String[]{String.valueOf(question.getIdQuestion())});
        db.close();
        for (Answer a : question.getAnswersList()) {
            deleteAnswer(a, question.getIdQuestion());
        }
    }

    public void deleteAnswer(Answer answer, long idQuestion) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbConstants.ANSWER_TABLE, DbConstants.COLUMN_ID_QUESTION + " = ?", new String[]{String.valueOf(idQuestion)});
        db.close();
    }

    // faza 4 stocare imagine in local database, urmand s-o preiei in cazul in care exista
    public void updateUserImgUri(String id, String imgUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_IMG_URI, imgUri);
        db.update(DbConstants.USER_TABLE, values, DbConstants.COLUMN_ID_USER + " = ?", new String[]{id});
        db.close();
    }

    public Uri getUserImageUri(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Uri resultUri = null;
        String rawSQL = "SELECT " + DbConstants.COLUMN_IMG_URI + " FROM " + DbConstants.USER_TABLE + " WHERE " + DbConstants.COLUMN_ID_USER + " = " + id + ";";
        Cursor cursor = db.rawQuery(rawSQL, null);
        cursor.moveToFirst();
        String result = cursor.getString(cursor.getColumnIndex(DbConstants.COLUMN_IMG_URI));

        if (result != null) {
            resultUri = Uri.parse(result);
        }
        cursor.close();
        db.close();

        return resultUri;
    }
}

