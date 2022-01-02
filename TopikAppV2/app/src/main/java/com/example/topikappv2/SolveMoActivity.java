package com.example.topikappv2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SolveMoActivity extends AppCompatActivity{
    String myJson;
    ArrayList<ProblemSet> prob_data = new ArrayList<>();

    public static final String USER_LIST = "user_list";
    private static final String PROB_SCORE = "total_point";
    private static final String PROB_ROUND = "prob_round";
    private List<UserSet> userList;
    private UserAdapter uAdapter = null;
    //점수
    private int mPoint = 0;

    //따로 class 만들면 좋은데, 여기서는 Main에서 다 만들어 놓음.
    public static final String TAG_RESULTS = "result";
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
    public static final String SOLUTION = "explanation";
    public static final String SECTION = "section";
    public static final String IMAGE = "image";
    public static ProblemAdapter pAdapter = null;
    public static final String MP3 = "mp3";

    TextView exampleText;
    TextView problemTextView;

    JSONArray problemList = null;
    ArrayList<HashMap<String,String>> probList; //여기에 DB의 data를 다 넣을 거임.

    ListView listview;

    //선택된 정보 가져오기
    public static final String CHOICE_ROUND = "choice_round";
    public static final String CHOICE_PROB = "choice_prob";
    private String selected_round;
    private String selected_problem;
    private String response_result;

    //번호이동
    ArrayList<String> gridItem;
    GridAdapter gridAdapter;

    private ArrayList<Integer> prob_num_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        setContentView(R.layout.activity_solve_mo);
        listview = findViewById(R.id.listView);
        probList = new ArrayList<HashMap<String, String>>(); //우리껄로 만들거면 우리가 이미 만들어놓은 ProblemSet class나 UserSet class Type으로
        //리스트 만들면 될 듯.
        getData("http://192.168.0.6:5000/topik1_exam_mo/");

        //돌리려면 VS code를 실행해놓고 해야 나옴. 실행 안 하면 빈화면만 출력.

        exampleText = findViewById(R.id.exampleText);
        problemTextView = findViewById(R.id.problemTextView);

        Intent intent = getIntent();
        selected_round = intent.getStringExtra(CHOICE_ROUND);
        selected_problem = intent.getStringExtra(CHOICE_PROB);

        //요청
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formbody = new FormBody.Builder().add("selected_problem",selected_problem).add("selected_round",selected_round).build();

        Request request = new Request.Builder().url("http://192.168.0.6:5000/topik1_exam_mo/").post(formbody).build();

        okHttpClient.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(SolveMoActivity.this, "network error...!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                response_result = response.body().string();
                Log.d("이것인가??",response_result);
            }
        });

        //번호 이동
        prob_num_list = new ArrayList<Integer>();

        CheckBox move_check = findViewById(R.id.move_check);
        GridView gridView = findViewById(R.id.check_list);
        gridItem = new ArrayList<>();
        gridAdapter = new GridAdapter(gridItem);

        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listview.setSelection(position);
            }
        });

        move_check.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(move_check.isChecked()){
                    gridView.setVisibility(VISIBLE);

                } else{
                    gridView.setVisibility(GONE);

                }
            }
        });
    }

    protected void showList(){ //가공은 String으로 다 하고 List로 만들때 int로 바꿔서 만들 수 있을까?
        try{
            if (response_result!= null) {
                JSONObject jsonObject = new JSONObject(response_result); //가공된 json 파일.
                problemList = jsonObject.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < problemList.length(); i++) {
                    JSONObject c = problemList.getJSONObject(i);
                    String prob_set = c.getString(PROB_SET);
                    String prob_num = c.getString(PROB_NUM);
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
                    String image = c.getString(IMAGE);
                    String mp3 = c.getString(MP3);

                    boolean checked1 = false;
                    boolean checked2 = false;
                    boolean checked3 = false;
                    boolean checked4 = false;
                    prob_num_list.add(i + 1);

                    if (question.equals("NA")) {
                        question = "";
                    }

                    if (plural_question.equals("NA")) {
                        plural_question = "";
                    }

                    if (question_example.equals("NA")) {
                        question_example = "";
                    }
                    if (text.equals("NA")) {
                        text = "";
                    }
//                prob_data.add(new ProblemSet(prob_num, question,plural_question ,question_example, text, choice1,
//                        choice2, choice3, choice4,answer, score, null,solution));

                    prob_data.add(new ProblemSet(String.valueOf(i + 1), prob_num, question, plural_question, question_example, text, choice1,
                            choice2, choice3, choice4, answer, score, null, solution, checked1, checked2, checked3, checked4, prob_set, image,mp3));
                }

                if (!prob_num_list.isEmpty()) {
                    for (int j = 0; j < Integer.parseInt(selected_problem); j++) {
                        gridItem.add(prob_num_list.get(j).toString());
                    }
                } else {
                    Log.d("문제리스트", "비어있음");
                }

                ProblemAdapter adapter = new ProblemAdapter(prob_data);

                listview.setAdapter(adapter);
                pAdapter = adapter;
            }
        } catch(JSONException e){
            e.printStackTrace();
        }

    }

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String>{
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0]; //위에서 내가 써놨던 http://192.168.0.4/simpletopik1.php 이거 가져옴.

                //getDate(String url)을 params로 받아서 링크를 가져옴.

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
    public void scoring_mo(View view) {
        if (!pAdapter.isEmpty()) {
            uAdapter = pAdapter.return_uAdapter();
            userList = (ArrayList<UserSet>) uAdapter.returnList();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                userList.sort(Comparator.naturalOrder());
            }
            Intent intent = new Intent(this, ScoringActivity.class);
            intent.putExtra(USER_LIST, (Serializable) userList);
            //intent.putExtra(PROB_ROUND, selected_round);
            startActivity(intent);
        }
    }
}