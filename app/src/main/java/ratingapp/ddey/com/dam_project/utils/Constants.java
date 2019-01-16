package ratingapp.ddey.com.dam_project.utils;

import java.text.SimpleDateFormat;

public interface Constants {
    String DATE_FORMAT = "dd-MM-yyyy";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

    String PREF_EMAIL_KEY = "email";
    String PREF_BIRTHDATE_KEY = "birthDate";

    String PREF_SWITCH1_KEY = "switch1";
    String PREF_SWITCH2_KEY = "switch2";
    String ADD_QUESTION_KEY = "newQuestion";
    String ADD_QUIZZ_KEY = "newQuizz";
    String ACTIVATE_QUIZZ_KEY = "toActive";
    String MODIFY_QUIZZ_KEY = "toModify";
    String CHANGE_ACCESSTYPE_QUIZZ_KEY = "toConfig";
    String SHOW_QUIZZ_INFO_KEY = "preStartQuizz";
    String START_QUIZZ_KEY = "startQuizz";
    String RESULT_SCORE_KEY = "resultScore";
    String RESULT_ANSWERS_KEY = "resultAnswers";
    String RESULT_QUIZZ_KEY = "resultQuizz";
    String PUBLIC_QUIZZ_KEY = "publicQuizzCategory";
    String PRIVATE_QUIZ_KEY = "privateQuizAccess";

    int REQUEST_CODE_ADD_QUESTION = 2;
    int REQUEST_CODE_CREATE_QUIZZ = 3;
    int REQUEST_CODE_ACTIVATE_QUIZZ = 4;
    int REQUEST_CODE_MODIFY_QUIZZ = 5;
    int REQUEST_CODE_ACCESSTYPE_QUIZZ = 7;
}
