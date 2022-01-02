package com.example.topikappv3app;

//채점하기 버튼을 통해 넘어온 화면이다.

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScoringActivity extends AppCompatActivity{

    private UserAdapter uAdapter;
    private ListView listView;
    private ArrayList<com.example.topikappv2.UserSet> user_list;
    private String arranged_num;
    private String prob_num;
    private String user_answer;
    private String real_answer;
    private String prob_set;
    private String result;
    private String point;
    private String section;
    private int minusPoint =0;
    private int totalScore =0;

    String myJson;
    ArrayList<com.example.topikappv2.ProblemSet> prob_data = new ArrayList<>();

    //따로 class 만들면 좋은데, 여기서는 Main에서 다 만들어 놓음.
    public static final String TAG_RESULTS = "result";
    public static final String PROB_NUM = "prob_num";
    public static final String PROB_SET = "prob_set";
    public static final String QUESTION = "question";
    public static final String PLURAL_QUESTION = "plural_question";
    public static final String QUESTION_EXAMPLE = "question_example";
    public static final String TEXT = "text";
    public static final String CHOICE1 = "choice1";
    public static final String CHOICE2 = "choice2";
    public static final String CHOICE3 = "choice3";
    public static final String CHOICE4 = "choice4";
    public static final String ANSWER = "answer";
    private static final String RESULT = "result";
    private static final String SECTION = "section";

    private static final String ARRANGED_NUM = "arranged_num";
    private static final String USER_ANSWER = "user_answer";
    private static final String REAL_ANSWER = "real_answer";

    TextView exampleText;
    TextView problemTextView;

    JSONArray problemList = null;
    ArrayList<HashMap<String,String>> probList; //여기에 DB의 data를 다 넣을 거임.

    //DB에 문제풀이 기록
    String p1;
    String p2;
    String p3;
    String p4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        listView = (ListView) findViewById(R.id.result_list_view2);
        probList = new ArrayList<HashMap<String, String>>(); //우리껄로 만들거면 우리가 이미 만들어놓은 ProblemSet class나 UserSet class Type으로

        exampleText = findViewById(R.id.exampleText);
        problemTextView = findViewById(R.id.problemTextView);

        uAdapter = new UserAdapter(this);
        Intent intent = getIntent();
        section = intent.getStringExtra("section");
        user_list = (ArrayList<com.example.topikappv2.UserSet>) intent.getSerializableExtra("user_list");
        uAdapter.setList(user_list);

        TextView pointTextView = findViewById(R.id.total_point);
        for(int i = 0; i< user_list.size(); i++){
            totalScore = uAdapter.getTotal(i);
            if (uAdapter.getResult(i).equals("X")) {
                Log.d("uAdapter.getScore(i)",(String) uAdapter.getScore(i));
                minusPoint = minusPoint + Integer.parseInt((String)uAdapter.getScore(i));
                Log.d("minusPoint",String.valueOf(minusPoint));
            }
        }
        String totalText = String.valueOf(totalScore-minusPoint)+"/"+String.valueOf(totalScore);

        pointTextView.setText(totalText);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_user_result, listView, false);

        LinearLayout resultLayout = (LinearLayout) findViewById(R.id.result_layout2);
        resultLayout.setVisibility(VISIBLE);
        listView.setVisibility(VISIBLE);
        listView.setAdapter(uAdapter);

        //DB에 기록
        OkHttpClient okHttpClient = new OkHttpClient();

        //userList 값 전달
        for(com.example.topikappv2.UserSet items : user_list) { //for문을 통한 전체출력
            p1 = items.getProb_num();
            p2 = items.getU_answer();
            p3 = items.getR_answer();
            p4 = items.getFinal_result();

            RequestBody formbody = new FormBody.Builder().add("prob_num", p1).add("user_answer", p2).add("real_answer", p3)
                    .add("result", p4).build();

            Request request = new Request.Builder().url("http://192.168.0.6:5000/insert_prob/").post(formbody).build();

            okHttpClient.newCall(request).enqueue(new Callback(){
                @Override
                public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                    runOnUiThread(new Runnable(){
                        public void run() {

                        }
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                }
            });
        }

        //클릭 했을 때 해당 문제가 출력 되는 것. 해설도 같이
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arranged_num = (String) uAdapter.getItem(position).getArranged_num();
                prob_num = (String) uAdapter.getItem(position).getProb_num();
                prob_set = (String)uAdapter.getItem(position).getProb_set();
                user_answer = (String) uAdapter.getU_answer(position);
                real_answer = (String) uAdapter.getP_answer(position);
                result = (String) uAdapter.getResult(position);
                point = (String) uAdapter.getScore(position);
                Intent intent = new Intent(ScoringActivity.this, SolutionActivity.class);
                intent.putExtra(ARRANGED_NUM,arranged_num);
                intent.putExtra(PROB_NUM, prob_num);
                intent.putExtra(PROB_SET,prob_set);
                intent.putExtra(USER_ANSWER, user_answer);
                intent.putExtra(REAL_ANSWER, real_answer);
                //intent.putExtra(RESULT,result);
                startActivity(intent);
            }
        });
    }
}