package com.example.topikappv2;

//채점하기 버튼을 통해 넘어온 화면이다.

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.View.VISIBLE;

public class ScoringActivity extends AppCompatActivity{

    private UserAdapter uAdapter;
    private ListView listView;
    private ArrayList<UserSet> user_list;
    private String arranged_num;
    private String prob_num;
    private String selected_lev;
    private String user_answer;
    private String real_answer;
    private String prob_set;
    private String result;
    private String point;
    private String section;
    private int minusPoint =0;
    private int totalScore =0;

    //시간, 바꾼 횟수
    private int time_stamp;
    private int change;

    String myJson;
    ArrayList<ProblemSet> prob_data = new ArrayList<>();

    //따로 class 만들면 좋은데, 여기서는 Main에서 다 만들어 놓음.
    public static final String TAG_RESULTS = "result";
    public static final String TOPIK_LEVEL = "topik_level";
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
    public static final String TIME_STAMP = "time_stamp";
    public static final String CHANGE = "change";

    TextView exampleText;
    TextView problemTextView;

    JSONArray problemList = null;
    ArrayList<HashMap<String,String>> probList; //여기에 DB의 data를 다 넣을 거임.

    //DB에 문제풀이 기록
    String p_id; //문제 id
    String p1; //사용자가 선택하 답
    String p2; //정답
    String p3; //맞았는지 틀렸는지 여부
    int p4; //걸린 시간
    int p5; //바꾼 횟수

    //사용자 ID
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private String mUserID;


    @RequiresApi(api = Build.VERSION_CODES.N)
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
        selected_lev = intent.getStringExtra("topik_level");
        user_list = (ArrayList<UserSet>) intent.getSerializableExtra("user_list");
        uAdapter.setList(user_list);

        TextView pointTextView = findViewById(R.id.total_point);
        for(int i = 0; i< user_list.size(); i++){
            totalScore = uAdapter.getTotal(i);
            if (uAdapter.getResult(i).equals("X")) {
                minusPoint = minusPoint + Integer.parseInt((String)uAdapter.getScore(i));
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

        //사용자 ID
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mUserID = mFirebaseUser.getEmail();

        //userList 값 전달
        for(UserSet items : user_list) { //for문을 통한 전체출력
            p_id = items.getProblem_id();
            p1 = items.getU_answer();
            p2 = items.getR_answer();
            p3 = items.getFinal_result();
            p4 = items.getTime_Stamp();
            p5 = items.getChange();

            RequestBody formbody = new FormBody.Builder().add("user_ID", mUserID).add("problem_ID",p_id).add("user_answer", p1).add("real_answer", p2)
                    .add("result", p3).add("time_stamp", String.valueOf(p4)).add("change_counts", String.valueOf(p5)).build();

            Request request = new Request.Builder().url("http://210.114.1.17/insert_prob/").post(formbody).build();

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

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);

        RequestBody formbody2 = new FormBody.Builder().add("user_ID", mUserID).add("submit_time", getTime).build();

        Request request2 = new Request.Builder().url("http://210.114.1.17/insert_users/").post(formbody2).build();

        okHttpClient.newCall(request2).enqueue(new Callback(){
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


        //클릭 했을 때 해당 문제가 출력 되는 것. 해설도 같이
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                arranged_num = (String) uAdapter.getItem(position).getArranged_num();
                prob_num = (String) uAdapter.getItem(position).getProb_num();
                section = uAdapter.getItem(position).getSection();
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
                intent.putExtra(TOPIK_LEVEL,selected_lev);
                intent.putExtra(SECTION,section);
                //intent.putExtra(RESULT,result);
                startActivity(intent);
            }
        });
    }
}