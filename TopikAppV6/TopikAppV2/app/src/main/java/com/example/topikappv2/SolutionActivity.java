package com.example.topikappv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SolutionActivity extends AppCompatActivity{


    String myJson;
    ArrayList<ProblemSet> prob_data = new ArrayList<>();

    //따로 class 만들면 좋은데, 여기서는 Main에서 다 만들어 놓음.
    public static final String TAG_RESULTS = "result";
    public static final String PROB_ID = "prob_id";
    public static final String TOPIK_LEVEL = "topik_level";
    public static final String SECTION = "section";
    public static final String PROB_SET = "prob_set";
    public static final String PROB_NUM = "prob_num";
    public static final String QUESTION = "question";
    public static final String PLURAL_QUESTION = "plural_question";
    public static final String QUESTION_EXAMPLE = "question_example";
    public static final String TEXT = "text";
    public static final String CHOICE1 = "choice1";
    public static final String CHOICE2 = "choice2";
    public static final String CHOICE3 = "choice3";
    public static final String CHOICE4 = "choice4";
    public static final String ANSWER = "answer";
    public static final String SCORE = "score";
    public static final String ARRANGED_NUM = "arranged_num";
    public static final String USER_ANSWER = "user_answer";
    public static final String REAL_ANSWER = "real_answer";
    public static final String IMAGE = "image";
    public static final String MP3 = "mp3";

    TextView exampleText;
    TextView problemTextView;
    TextView solutionTextView;

    JSONArray problemList = null;
    ArrayList<HashMap<String,String>> probList; //여기에 DB의 data를 다 넣을 거임.

    ListView listview;

    //선택된 정보 가져오기
    public static final String CHOICE_ROUND = "choice_round";
    public static final String CHOICE_PROB = "choice_prob";
    private String selected_round;
    private String selected_problem;
    private String response_result;

    private String arranged_num;
    private String section;
    private String selected_lev;
    private String prob_num;
    private String prob_round;
    private String user_answer;
    private String real_answer;

    //문제변수
    private RecyclerView mRecyclerView;
    public static final String PROBLEM_LISTENING = "probset_listening";
    private static final String SOLUTION = "explanation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        Intent intent = getIntent();
        prob_num = intent.getStringExtra(PROB_NUM);
        arranged_num = intent.getStringExtra(ARRANGED_NUM);
        section = intent.getStringExtra(SECTION);
        prob_round = intent.getStringExtra(PROB_SET);
        user_answer = intent.getStringExtra(USER_ANSWER);
        real_answer = intent.getStringExtra(REAL_ANSWER);
        selected_lev = intent.getStringExtra(TOPIK_LEVEL);
        listview = findViewById(R.id.listView_solution);
        probList = new ArrayList<HashMap<String, String>>(); //우리껄로 만들거면 우리가 이미 만들어놓은 ProblemSet class나 UserSet class Type으로
        //돌리려면 VS code를 실행해놓고 해야 나옴. 실행 안 하면 빈화면만 출력.
        exampleText = findViewById(R.id.exampleText);
        problemTextView = findViewById(R.id.problemTextView);
        Log.d("section뭐야",section);


        OkHttpClient okHttpClient = new OkHttpClient();

        //RequestBody formbody = new FormBody.Builder().add("selected_problem",prob_num).add("selected_round",selected_round).build();
        RequestBody formbody = new FormBody.Builder().add("section",section).add("selected_level",selected_lev).add("prob_num",prob_num).add("prob_round",prob_round).build();

        Request request = new Request.Builder().url("http://13.209.64.234/topik1_solution/").post(formbody).build();

        okHttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SolutionActivity.this, "network error...!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                response_result = response.body().string();
                //intent 정보로 문제 filtering
                getData("http://13.209.64.234/topik1_solution/"); // visual code 상에서 구현

                Log.d("이것인가??",response_result);
            }
        });
    }

    protected void showList(){ //가공은 String으로 다 하고 List로 만들때 int로 바꿔서 만들 수 있을까?
        try{
            JSONObject jsonObject = new JSONObject(myJson); //가공된 json 파일.
            problemList = jsonObject.getJSONArray(TAG_RESULTS);

            for(int i = 0; i < problemList.length(); i++){
                JSONObject c = problemList.getJSONObject(i);
                String prob_id = c.getString(PROB_ID);
                String prob_set = c.getString(PROB_SET);
                String prob_num = c.getString(PROB_NUM);
                String topik_level = c.getString(TOPIK_LEVEL);
                String question = c.getString(QUESTION); //각 칼럼들 가져오기. 여기서는 다 String이네.
                String plural_question = c.getString(PLURAL_QUESTION);
                String question_example = c.getString(QUESTION_EXAMPLE);
                String text = c.getString(TEXT);
                String choice1 = c.getString(CHOICE1);
                String choice2 = c.getString(CHOICE2);
                String choice3 = c.getString(CHOICE3);
                String choice4 = c.getString(CHOICE4);
                String answer = c.getString(ANSWER);
                String score = c.getString(SCORE);
                String solution = c.getString(SOLUTION);
                String u_answer = user_answer;
                String image = c.getString(IMAGE);
                String mp3 = c.getString(MP3);
                String section = c.getString(SECTION);

                boolean b = false;
                boolean b2 = false;
                boolean b3 = false;
                boolean b4 = false;

                if(question.equals("NA")){
                    question = "";
                }
                if(plural_question.equals("NA")){
                    plural_question = "";
                }
                if(question_example.equals("NA")){
                    question_example = "";
                }
                if(text.equals("NA")){
                    text = "";
                }


                prob_data.add(new ProblemSet(prob_id,arranged_num,prob_num, question,plural_question ,question_example, text, choice1,
                        choice2, choice3, choice4,answer, score, u_answer,solution,b,b2,b3,b4,prob_set,image,mp3,topik_level, section));
            }
            ProblemAdapter adapter = new ProblemAdapter(prob_data);

            listview.setAdapter(adapter);

        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                //getDate(String url)을 params로 받아서 링크를 가져옴.
                Log.d("url확인",uri);
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(uri); //String으로 받아온 uri를 URL 타입으로 변경
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim(); // 받아온 json의 공백 제거.
                } catch (Exception e){
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) { //doInBackground에서 return한 값을 받음.
                if(response_result != null){
                    myJson = response_result;
                    showList();
                } else{
                    Log.d("없다", "없다...");
                }
//                myJson = response_result;
//                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}

