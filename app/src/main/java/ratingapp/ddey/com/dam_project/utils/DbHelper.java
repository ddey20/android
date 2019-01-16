package ratingapp.ddey.com.dam_project.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

//VERSION = NR FAZA
public class DbHelper extends SQLiteOpenHelper {
        public static final String TAG = DbHelper.class.getSimpleName();
        public static final String DB_NAME = "DAM_DATABASE.db";
        public static final int DB_VERSION = 2;

        //USERS
        public static final String USER_TABLE = "users";
        public static final String COLUMN_ID_USER = "id_user";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASS = "password";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_USER_TYPE = "user_type";

        //RESULTS
        public static final String RESULT_TABLE = "results";
        public static final String COLUMN_ID_RESULT = "id_result";
        public static final String COLUMN_STUDENT_NAME = "name";
        public static final String COLUMN_QUIZZ_NAME = "quizz_name";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_DATETIME = "date";

        //QUIZZES
        public static final String QUIZ_TABLE = "quizzes";
        public static final String COLUMN_ID_QUIZ = "id_quiz";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_VISIBILITY = "visibility";
        public static final String COLUMN_ACCESS_CODE = "access_code";
        public static final String COLUMN_CATEGORY = "category";
        public static final String COLUMN_IS_ACTIVE = "isActive";

        //QUESTIONS
        public static final String QUESTION_TABLE = "questions";
        public static final String COLUMN_ID_QUESTION = "id_question";
        public static final String COLUMN_TEXT = "text_question";
        public static final String COLUMN_ANSWER_TIME = "answer_time";

        //ANSWERS
        public static final String ANSWER_TABLE = "answers";
        public static final String COLUMN_ID_ANSWER = "id_answer";
        public static final String COLUMN_IS_CORRECT = "correct";
        public static final String COLUMN_ANSWER = "answer";

        public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
                    + COLUMN_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EMAIL + " TEXT NOT NULL,"
                    + COLUMN_NAME + " TEXT NOT NULL,"
                    + COLUMN_PASS + " TEXT NOT NULL,"
                    + COLUMN_USER_TYPE + " TEXT NOT NULL,"
                    + COLUMN_DATE + " TEXT,"
                    + COLUMN_GENDER + " TEXT);";

        public static final String CREATE_TABLE_RESULTS = "CREATE TABLE " + RESULT_TABLE + "("
                + COLUMN_ID_RESULT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_STUDENT_NAME + " TEXT NOT NULL,"
                + COLUMN_QUIZZ_NAME + " TEXT NOT NULL,"
                + COLUMN_SCORE + " TEXT NOT NULL,"
                + COLUMN_DATETIME + " TEXT NOT NULL);";

        public static final String CREATE_TABLE_QUIZZ = "CREATE TABLE " + QUIZ_TABLE + "("
                + COLUMN_ID_QUIZ + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT NOT NULL,"
                + COLUMN_DESCRIPTION + " TEXT NOT NULL,"
                + COLUMN_VISIBILITY + " TEXT NOT NULL,"
                + COLUMN_ACCESS_CODE + " TEXT,"
                + COLUMN_IS_ACTIVE + "  INTEGER,"
                + COLUMN_CATEGORY + " TEXT);";

        public static final String CREATE_TABLE_QUESTION = "CREATE TABLE " + QUESTION_TABLE + "("
                + COLUMN_ID_QUESTION + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TEXT + " TEXT NOT NULL,"
                + COLUMN_ANSWER_TIME + " INTEGER NOT NULL,"
                + COLUMN_ID_QUIZ + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COLUMN_ID_QUIZ + ") REFERENCES " + QUIZ_TABLE + "(" + COLUMN_ID_QUIZ +"));";

        public static final String CREATE_TABLE_ANSWER = "CREATE TABLE " + ANSWER_TABLE + "("
                + COLUMN_ID_ANSWER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_IS_CORRECT + " INTEGER NOT NULL,"
                + COLUMN_ANSWER + " TEXT NOT NULL,"
                + COLUMN_ID_QUESTION + " INTEGER NOT NULL, "
                + "FOREIGN KEY (" + COLUMN_ID_QUESTION + ") REFERENCES " + QUESTION_TABLE + "(" + COLUMN_ID_QUESTION +"));";


        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_USERS);
            db.execSQL(CREATE_TABLE_RESULTS);
            db.execSQL(CREATE_TABLE_QUIZZ);
            db.execSQL(CREATE_TABLE_QUESTION);
            db.execSQL(CREATE_TABLE_ANSWER);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + RESULT_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + QUIZ_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + ANSWER_TABLE);
            onCreate(db);
        }

        public void insertUser(User user) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, user.getEmail());
            values.put(COLUMN_NAME, user.getName());
            values.put(COLUMN_PASS, user.getPassword());
            values.put(COLUMN_USER_TYPE, user.getUserType().name());
            Log.d(TAG, "insertUser: " + user.getUserType().name());
            values.put(COLUMN_DATE, "");
            values.put(COLUMN_GENDER, "");

            long id = db.insert(USER_TABLE, null, values);
            db.close();
            Log.d(TAG, "User inserted " + id);
        }

        public void insertResult(Result result) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_STUDENT_NAME, result.getStudentName());
            values.put(COLUMN_QUIZZ_NAME, result.getQuizzName());
            values.put(COLUMN_SCORE, result.getScore());
            values.put(COLUMN_DATE, result.getDateTime());
            Log.d(TAG, "insertResult: " + result.getStudentName());

            long id = db.insert(RESULT_TABLE, null, values);
            db.close();
            Log.d(TAG, "Result inserted " + id);
        }

        public void insertQuizz(Quiz newQuiz) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, newQuiz.getTitle());
            values.put(COLUMN_DESCRIPTION, newQuiz.getDescription());
            values.put(COLUMN_VISIBILITY, newQuiz.getVisibility());
            values.put(COLUMN_ACCESS_CODE, newQuiz.getAccessCode());
            values.put(COLUMN_CATEGORY, newQuiz.getCategory());
            values.put(COLUMN_IS_ACTIVE, newQuiz.isActive());

            int id = (int) db.insert(QUIZ_TABLE, null, values);
            newQuiz.setIdQuizz(id);
            db.close();
            for (Question q : newQuiz.getQuestionsList()) {
                insertQuestion(q, newQuiz);
            }
            Log.d(TAG, "Quizz inserted " + id);
        }

        public void insertQuestion(Question q, Quiz currentQuiz) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_TEXT,q.getQuestionText());
            values.put(COLUMN_ANSWER_TIME,q.getAnswerTime());
            values.put(COLUMN_ID_QUIZ,currentQuiz.getIdQuizz());

            int id = (int) db.insert(QUESTION_TABLE, null, values);
            q.setIdQuestion(id);
            db.close();
            for (Answer a : q.getAnswersList()) {
                insertAnswer(a, q);
            }
            Log.d(TAG, "Question inserted " + id);
        }

        public void insertAnswer(Answer answer, Question question) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_ANSWER,answer.getAnswer());
            values.put(COLUMN_IS_CORRECT,answer.isCorrect());
            values.put(COLUMN_ID_QUESTION,question.getIdQuestion());

            int id = (int) db.insert(ANSWER_TABLE, null, values);
            answer.setIdAnswer(id);
            db.close();
            Log.d(TAG, "Answer inserted " + id);
        }


        //LOAD QUIZZES (active/inactive)
        public List<Quiz> loadQuizzes(boolean state) {
            List<Quiz> resultList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.query(QUIZ_TABLE,new String[]{COLUMN_ID_QUIZ, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_VISIBILITY, COLUMN_ACCESS_CODE, COLUMN_CATEGORY, COLUMN_IS_ACTIVE },   null, null, null, null, null);
            while (c.moveToNext()) {

                int index = c.getColumnIndex(COLUMN_ID_QUIZ);
                int id = c.getInt(index);

                index = c.getColumnIndex(COLUMN_TITLE);
                String title = c.getString(index);

                index = c.getColumnIndex(COLUMN_DESCRIPTION);
                String desc = c.getString(index);

                index = c.getColumnIndex(COLUMN_VISIBILITY);
                String visib = c.getString(index);

                index = c.getColumnIndex(COLUMN_ACCESS_CODE);
                long code = Long.parseLong(c.getString(index));

                index = c.getColumnIndex(COLUMN_CATEGORY);
                String category = c.getString(index);

                index = c.getColumnIndex(COLUMN_IS_ACTIVE);
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

    //LOAD QUIZZES (dupa categorie)
    public Quiz loadPrivateQuiz(long searchedAccessCode) {
        Quiz result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(QUIZ_TABLE,new String[]{COLUMN_ID_QUIZ, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_VISIBILITY, COLUMN_ACCESS_CODE, COLUMN_CATEGORY, COLUMN_IS_ACTIVE },   null, null, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(COLUMN_ID_QUIZ);
            int id = c.getInt(index);

            index = c.getColumnIndex(COLUMN_TITLE);
            String title = c.getString(index);

            index = c.getColumnIndex(COLUMN_DESCRIPTION);
            String desc = c.getString(index);

            index = c.getColumnIndex(COLUMN_VISIBILITY);
            String visib = c.getString(index);

            index = c.getColumnIndex(COLUMN_ACCESS_CODE);
            long code = Long.parseLong(c.getString(index));

            index = c.getColumnIndex(COLUMN_CATEGORY);
            String category = c.getString(index);

            index = c.getColumnIndex(COLUMN_IS_ACTIVE);
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

    //LOAD QUIZZES (dupa categorie)
    public List<Quiz> loadQuizzesByCategory(String searchedCategory) {
        List<Quiz> resultList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(QUIZ_TABLE,new String[]{COLUMN_ID_QUIZ, COLUMN_TITLE, COLUMN_DESCRIPTION, COLUMN_VISIBILITY, COLUMN_ACCESS_CODE, COLUMN_CATEGORY, COLUMN_IS_ACTIVE },   null, null, null, null, null);
        while (c.moveToNext()) {

            int index = c.getColumnIndex(COLUMN_ID_QUIZ);
            int id = c.getInt(index);

            index = c.getColumnIndex(COLUMN_TITLE);
            String title = c.getString(index);

            index = c.getColumnIndex(COLUMN_DESCRIPTION);
            String desc = c.getString(index);

            index = c.getColumnIndex(COLUMN_VISIBILITY);
            String visib = c.getString(index);

            index = c.getColumnIndex(COLUMN_ACCESS_CODE);
            long code = Long.parseLong(c.getString(index));

            index = c.getColumnIndex(COLUMN_CATEGORY);
            String category = c.getString(index);

            index = c.getColumnIndex(COLUMN_IS_ACTIVE);
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


        public List<Question> getQuestions(int idQuizz) {
            List<Question> resultList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.query(QUESTION_TABLE,new String[]{COLUMN_ID_QUESTION, COLUMN_TEXT, COLUMN_ANSWER_TIME},   COLUMN_ID_QUIZ +"= ?", new String[] {String.valueOf(idQuizz)}, null, null, null);
            while (c.moveToNext()) {

                int index = c.getColumnIndex(COLUMN_ID_QUESTION);
                int id = c.getInt(index);

                index = c.getColumnIndex(COLUMN_TEXT);
                String title = c.getString(index);

                index = c.getColumnIndex(COLUMN_ANSWER_TIME);
                int time = c.getInt(index);

                List<Answer> answers = getAnswers(id);
                Question q = new Question(id, title, time, answers);
                resultList.add(q);
            }
            c.close();
            db.close();

            return resultList;
        }

        public List<Answer> getAnswers(int idQuestion) {
            List<Answer> resultList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.query(ANSWER_TABLE,new String[]{COLUMN_ID_ANSWER, COLUMN_IS_CORRECT, COLUMN_ANSWER},   COLUMN_ID_QUESTION +"= ?", new String[] {String.valueOf(idQuestion)}, null, null, null);
            while (c.moveToNext()) {

                int index = c.getColumnIndex(COLUMN_ID_ANSWER);
                int id = c.getInt(index);

                index = c.getColumnIndex(COLUMN_IS_CORRECT);
                int correct = c.getInt(index);

                boolean isCorrect;
                if (correct == 1)
                    isCorrect = true;
                else
                    isCorrect = false;

                index = c.getColumnIndex(COLUMN_ANSWER);
                String answer = c.getString(index);

                Answer a = new Answer(id, isCorrect, answer);
                resultList.add(a);
            }
            c.close();
            db.close();

            return resultList;
        }


        //LOAD RESULTS
        public List<Result> getResultsByName(Session session, String name) {
            List<Result> resultList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query(RESULT_TABLE,new String[]{COLUMN_QUIZZ_NAME, COLUMN_SCORE, COLUMN_DATETIME},   COLUMN_STUDENT_NAME + " = ?", new String[]{name}, null, null, null);
                while (c.moveToNext()) {
                    int index = c.getColumnIndex(COLUMN_QUIZZ_NAME);
                    String quizzName = c.getString(index);
                    index = c.getColumnIndex(COLUMN_SCORE);
                    int score = c.getInt(index);
                    index = c.getColumnIndex(COLUMN_DATETIME);
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
            Cursor c = db.query(RESULT_TABLE,new String[]{COLUMN_QUIZZ_NAME, COLUMN_STUDENT_NAME, COLUMN_SCORE, COLUMN_DATETIME},   null, null, null, null, null);
            while (c.moveToNext()) {
                int index = c.getColumnIndex(COLUMN_QUIZZ_NAME);
                String quizzName = c.getString(index);

                index = c.getColumnIndex(COLUMN_STUDENT_NAME);
                String studentName = c.getString(index);

                index = c.getColumnIndex(COLUMN_SCORE);
                int score = c.getInt(index);

                index = c.getColumnIndex(COLUMN_DATETIME);
                String date = c.getString(index);

                Result res = new Result(studentName, quizzName, score, date);
                resultList.add(res);
            }
            c.close();
            db.close();
            return resultList;
        }



        public boolean getUser(User user) {
            String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE "
                    + COLUMN_EMAIL + " = " + "'" + user.getEmail() + "'" + " and "
                    + COLUMN_PASS + " = " + "'" + user.getPassword() + "'";
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
            String email = user.get(Session.KEY_EMAIL);
            String pass = user.get(Session.KEY_PASS);

            String selectQuery = "SELECT " + COLUMN_USER_TYPE + " FROM " + USER_TABLE + " WHERE "
                    + COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                    + COLUMN_PASS + " = " + "'" + pass + "'";

            SQLiteDatabase db = this.getReadableDatabase();
            String rez = "";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0)
            {
                rez = cursor.getString(cursor.getColumnIndex(COLUMN_USER_TYPE));
            }

            cursor.close();
            db.close();

            return rez;
        }

        public String getName(Session session) {
        HashMap<String, String> user = session.getUserDetails();
        String email = user.get(Session.KEY_EMAIL);
        String pass = user.get(Session.KEY_PASS);

        String selectQuery = "SELECT name FROM " + USER_TABLE + " WHERE "
                + COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                + COLUMN_PASS + " = " + "'" + pass + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        String rez = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            rez = cursor.getString(cursor.getColumnIndex("name"));
        }

        cursor.close();
        db.close();

        return rez;
    }

        public String getId(Session session) {
            HashMap<String, String> user = session.getUserDetails();
            String email = user.get(Session.KEY_EMAIL);
            String pass = user.get(Session.KEY_PASS);

            String selectQuery = "SELECT " + COLUMN_ID_USER + " FROM " + USER_TABLE + " WHERE "
                    + COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                    + COLUMN_PASS + " = " + "'" + pass + "'";

            SQLiteDatabase db = getReadableDatabase();
            String rez = "";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToFirst();
            int result = cursor.getInt(0);
            rez = String.valueOf(result);
            cursor.close();

            return rez;
        }

        public void updateEmail(String id, String email) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_EMAIL, email);

            db.update(USER_TABLE, values, COLUMN_ID_USER + " = " + id, null);
            db.close();
        }

        public void updateDate(String id, String birthDate) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_DATE, birthDate);
            db.update(USER_TABLE, values, COLUMN_ID_USER + " = " + id, null);
            db.close();
        }

        public User retrieveProfile(Session session) {
            ArrayList<User> list = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            HashMap<String, String> user = session.getUserDetails();
            String email = user.get(Session.KEY_EMAIL);
            String pass = user.get(Session.KEY_PASS);
            String selectQuery = "SELECT * FROM " + USER_TABLE + " WHERE "
                    + COLUMN_EMAIL + " = " + "'" + email + "'" + " and "
                    + COLUMN_PASS + " = " + "'" + pass + "'";

            Cursor cursor = db.rawQuery(selectQuery, null);
            User userRetrieved = new User();
            if (cursor.moveToFirst())
            {
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

        public void updateGender(String id, String gender) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_GENDER, gender);
            db.update(USER_TABLE, values, COLUMN_ID_USER + " = " + id, null);
            db.close();
        }

        public void updateQuizActivity(Quiz quiz, boolean state) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_IS_ACTIVE, state);
            db.update(QUIZ_TABLE, values, COLUMN_ID_QUIZ + " = " + quiz.getIdQuizz(), null);
            db.close();
        }

        public void updateCategory(Quiz quiz, String categ) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY, categ);
            db.update(QUIZ_TABLE, values, COLUMN_ID_QUIZ + " = " + quiz.getIdQuizz(), null);
            db.close();
        }

        public void updateVisibility(Quiz quiz, String visib) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_VISIBILITY, visib);
            db.update(QUIZ_TABLE, values, COLUMN_ID_QUIZ + " = " + quiz.getIdQuizz(), null);
            db.close();
        }

        public void updateCode(Quiz quiz, long code) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_ACCESS_CODE, code);
            db.update(QUIZ_TABLE, values, COLUMN_ID_QUIZ + " = " + quiz.getIdQuizz(), null);
            db.close();
        }

    public void deleteQuizz(Quiz q) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(QUIZ_TABLE, COLUMN_ID_QUIZ + " = ?", new String[]{String.valueOf(q.getIdQuizz())});
        db.close();

        for (Question question : q.getQuestionsList()) {
            deleteQuestion(question, q.getIdQuizz());
        }
    }

    public void deleteQuestion(Question question, long idQuizz) {
            SQLiteDatabase db = this.getWritableDatabase();

            for (Answer a : question.getAnswersList()) {
                db.delete(ANSWER_TABLE, COLUMN_ID_QUESTION + " = ?", new String[] {String.valueOf(question.getIdQuestion())});
            }

            db.delete(QUESTION_TABLE, COLUMN_ID_QUIZ + " = ?", new String[]{String.valueOf(idQuizz)});
            db.close();
    }

}

