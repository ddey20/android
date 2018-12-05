package ratingapp.ddey.com.dam_project.utils;

import java.text.SimpleDateFormat;

public interface Constants {

    // DB CONSTANTS SUNT IN DBHELPER
    // SHAREDPREFERENCES CONSTANTS SUNT IN SESSION
    // RESTUL DE CONSTANTS SE AFLA AICI

    String DATE_FORMAT = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    String PREF_EMAIL_KEY = "email";
    String PREF_BIRTHDATE_KEY = "birthDate";

    String PREF_SWITCH1_KEY = "switch1";
    String PREF_SWITCH2_KEY = "switch2";
    String ADD_QUESTION_KEY = "newQuestion";
    String ADD_QUIZZ_KEY = "newQuizz";
    String ACTIVATE_QUIZZ_KEY = "activateQuiz";
    String MODIFY_QUIZZ_KEY = "modifyQuiz";
    String MODIFY_QUESTION_KEY = "modifyQuestion";

    String LV_INDEX_KEY = "index";
    String LV_INDEX_MODIFY_QUIZ_KEY = "modifyQuizIndex";
    String LV_INDEX_MODIFY_QUESTION_KEY = "modifyQuestionIndex";

    String CHANGE_ACCESSTYPE_QUIZZ_KEY = "toConfig";
    String SHOW_QUIZZ_INFO_KEY = "preStartQuizz";
    String START_QUIZZ_KEY = "startQuizz";
    String RESULT_SCORE_KEY = "resultScore";
    String RESULT_ANSWERS_KEY = "resultAnswers";
    String RESULT_QUIZZ_KEY = "resultQuizz";
    String PUBLIC_QUIZZ_KEY = "publicQuizzCategory";
    String PRIVATE_QUIZ_KEY = "privateQuizAccess";

    //REQUEST CODES
    int REQUEST_CODE_ADD_QUESTION = 2;
    int REQUEST_CODE_CREATE_QUIZZ = 3;
    int REQUEST_CODE_ACTIVATE_QUIZZ = 4;
    int REQUEST_CODE_MODIFY_QUIZZ = 5;
    int REQUEST_CODE_ACCESSTYPE_QUIZZ = 7;
    int REQUEST_CODE_MODIFY_QUESTION = 8;

    //CSV CONSTANTS
    String CSV_FILE_NAME = "studentResults.csv";
    String COMMA_DELIMITER = ",";
    String NEW_LINE_SEPARATOR = "\n";
    String FILE_HEADER = "ID, Student Name, Quizz Title, Date, Time, Score";
}
