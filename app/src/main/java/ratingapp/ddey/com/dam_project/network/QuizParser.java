package ratingapp.ddey.com.dam_project.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratingapp.ddey.com.dam_project.models.Answer;
import ratingapp.ddey.com.dam_project.models.Question;
import ratingapp.ddey.com.dam_project.models.Quiz;

public class QuizParser {

    public static List<Quiz> getListFromJson(String json) throws JSONException {
        List<Quiz> resultList = null;
        if (json != null) {
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("quizzes");
            if (array != null) {

                resultList = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    Quiz quiz = getQuizFromJSONObject(array.getJSONObject(i));

                    if (quiz != null)
                        resultList.add(quiz);
                }
            }
        }

         return resultList;
    }

    public static Quiz getQuizFromJSONObject(JSONObject object) throws JSONException {
        Quiz quizResult = null;
        if (object != null) {
            String title = object.getString("title");
            String description = object.getString("description");
            String visibility = object.getString("visibility");
            long accessCode = object.getLong("accessCode");
            String category = object.getString("category");

            List<Question> questionList = new ArrayList<>();
            JSONArray questionsArray = object.getJSONArray("questions");
            if (questionsArray != null) {
                for (int i = 0; i < questionsArray.length(); i++) {
                    Question q = getQuestionFromJsonObject(questionsArray.getJSONObject(i));
                    questionList.add(q);
                }
            }
            quizResult = new Quiz(title, description, visibility, accessCode, questionList, category,true);
        }
        return quizResult;
    }

    public static Question getQuestionFromJsonObject(JSONObject object) throws JSONException {
        Question questionResult = null;
        if (object != null) {
            String questionText = object.getString("questionText");
            int answerTime = object.getInt("answerTime");
            JSONArray answers = object.getJSONArray("answers");

            List<Answer> answersList = new ArrayList<>();
            if (answers != null) {
                for (int i = 0; i < answers.length(); i++) {
                    Answer a = getAnswerFromJSONObject(answers.getJSONObject(i));

                    if (a != null)
                        answersList.add(a);
                }
            }
            questionResult = new Question(questionText, answerTime, answersList);
        }
        return questionResult;
    }

    public static Answer getAnswerFromJSONObject(JSONObject obj) throws JSONException {
        Answer result = null;
        if (obj != null) {
            boolean isCorrect = obj.getBoolean("isCorrect");
            String answer = obj.getString("answer");

            result = new Answer(isCorrect, answer);
        }
        return result;
    }
}
