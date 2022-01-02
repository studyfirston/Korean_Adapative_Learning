package com.example.newtopikapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class SolveMoActivity extends AppCompatActivity{
    private static final String PROB_SCORE = "total_point";
    //문제변수
    private RecyclerView mRecyclerView;
    public static final String PROBLEM_SET = "probset";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder> mFirebaseAdapter;
    private int count = 1;

    private ArrayList<String> roundList;
    private int mPoint = 0;

    //번호이동
    ArrayList<String> gridItem;
    GridAdapter gridAdapter;

    //결과 변수

    //private UserSetAdapter userSetAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    public static final String USER_LIST = "user_list";
    private List<UserSet> userList;

    //private ArrayList<UserSet> userSet = null;
    private UserAdapter cAdapter = null;
    private ListView listView = null;
    private static final String PROB_ROUND = "prob_round";

    //선택된 정보 가져오기
    public static final String CHOICE_ROUND = "choice_round";
    public static final String CHOICE_PROB = "choice_prob";
    private String mRound;
    private int selected_prob_int;


    public class ProblemViewHolder extends RecyclerView.ViewHolder {
        private int lastSelectedPosition = -1;

        TextView commonQuestion;
        TextView commonContent;
        CardView commonCardView;
        TextView exampleText;
        TextView problemNumber;
        TextView problemTextView;
        TextView textTextView;
        TextView choiceNumber1;
        TextView choiceNumber2;
        TextView choiceNumber3;
        TextView choiceNumber4;
        RadioButton choice1Radio;
        RadioButton choice2Radio;
        RadioButton choice3Radio;
        RadioButton choice4Radio;

        //세트문제
        TextView bracket1;
        TextView bracket2;
        TextView commonNumber1;
        TextView commonNumber2;
        TextView mark;

        RadioGroup radioGroup;

        TextView problemUserAnswer;
        TextView problemRealAnswer;
        TextView point;

        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            commonQuestion = itemView.findViewById(R.id.common_question); //공통발문
            commonContent = itemView.findViewById(R.id.common_content); //공통보기
            commonCardView = itemView.findViewById(R.id.cardView); //공통 보기 cardView
            exampleText = itemView.findViewById(R.id.exampleText); //보기라는 단순한 글자

            problemNumber = itemView.findViewById(R.id.number); //문제 번호
            problemTextView = itemView.findViewById(R.id.problemTextView); //발문
            textTextView = itemView.findViewById(R.id.textTextView); //보기


            choiceNumber1 = itemView.findViewById(R.id.choiceNumber1); //선지번호
            choiceNumber2 = itemView.findViewById(R.id.choiceNumber2);
            choiceNumber3 = itemView.findViewById(R.id.choiceNumber3);
            choiceNumber4 = itemView.findViewById(R.id.choiceNumber4);

            choice1Radio = itemView.findViewById(R.id.choice1Radio); //선지
            choice2Radio = itemView.findViewById(R.id.choice2Radio);
            choice3Radio = itemView.findViewById(R.id.choice3Radio);
            choice4Radio = itemView.findViewById(R.id.choice4Radio);

            //세트문제
            bracket1 = itemView.findViewById(R.id.bracket1);
            bracket2 = itemView.findViewById(R.id.bracket2);
            commonNumber1 = itemView.findViewById(R.id.common_number1);
            commonNumber2 = itemView.findViewById(R.id.common_number2);
            mark = itemView.findViewById(R.id.mark);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            //radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

            problemUserAnswer = itemView.findViewById(R.id.problemUserAnswer);
            problemRealAnswer = itemView.findViewById(R.id.problemRealAnswer);
            point = itemView.findViewById(R.id.prob_point);
            //문제1. 새로운 아이템이 불러와지면 버튼이 체크되어 있음.

            //라디어 버튼값 저장
//            choice1Radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean one_isChecked) {
//                    SaveIntoSharedPrefs("Button_One", one_isChecked);
//
//                }
//            });
//
//            choice2Radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean two_isChecked) {
//                    SaveIntoSharedPrefs("Button_Two", two_isChecked);
//                }
//            });
//
//            choice3Radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean three_isChecked) {
//                    SaveIntoSharedPrefs("Button_Three", three_isChecked);
//                }
//            });
//
//            choice4Radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean four_isChecked) {
//                    SaveIntoSharedPrefs("Button_Four", four_isChecked);
//                }
//            });


//            gridItem.add(problemNumber.getText().toString());

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.choice1Radio:
                            ChangeAnswer("1");
                            break;
                        case R.id.choice2Radio:
                            ChangeAnswer("2");
                            break;
                        case R.id.choice3Radio:
                            ChangeAnswer("3");
                            break;
                        case R.id.choice4Radio:
                            ChangeAnswer("4");
                            break;
                        default:
                            break;
                    }
                }

                void DeleteSetItem(Integer userNumber, int problemNumber, int point) {
                    cAdapter.deleteItem(problemNumber);
                    cAdapter.addItem(new UserSet(String.valueOf(problemNumber), String.valueOf(userNumber), problemRealAnswer.getText().toString(),
                            String.valueOf(point)));
                }

                private void ChangeAnswer(String answer) {
                    problemUserAnswer.setText(answer);
                    int numInt = Integer.parseInt(String.valueOf(problemUserAnswer.getText()));
                    if (String.valueOf(numInt) == String.valueOf(1)) {
                        DeleteSetItem(numInt, Integer.parseInt(problemNumber.getText().toString()), Integer.parseInt(point.getText().toString()));
                    } else if (String.valueOf(numInt) == String.valueOf(2)) {
                        DeleteSetItem(numInt, Integer.parseInt(problemNumber.getText().toString()), Integer.parseInt(point.getText().toString()));
                    } else if (String.valueOf(numInt) == String.valueOf(3)) {
                        DeleteSetItem(numInt, Integer.parseInt(problemNumber.getText().toString()), Integer.parseInt(point.getText().toString()));
                    } else {
                        DeleteSetItem(numInt, Integer.parseInt(problemNumber.getText().toString()), Integer.parseInt(point.getText().toString()));
                    }
                }

            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve_mo);

        cAdapter = new UserAdapter(this);

        //문제 표시
        mRecyclerView = findViewById(R.id.problem_recycler_view);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

//        roundList = new ArrayList<>();
//        roundList.add("prob_set35");
//        roundList.add("prob_set36");
//        roundList.add("prob_set52");
//        roundList.add("prob_set60");
//        roundList.add("prob_set64");

        //파이어베이스 특성상 다중 조건문을 사용하는게 매우 제한적이라, 랜덤하게 문제를 불러 오는 것은 나중에 하고
        //방법 1. 일단 범위를 지정해서 해당 범위 안에 있는 문제를 전부 가져오게 함.
        //방법 2.아예 랜덤하게 하려면, 일단 전체 문제를 다 가져오고 문제 번호 리스트 안에 랜덤한 수를 넣은 다음에
        ///만약 문제수가 일치하면 보여주고, 일치하지 않으면 안 보여주고 이런식으로 해야할 듯.
        //근데 이렇게 해도 문제인게, 지문이 잘려서 나올 수가 있어서 그럼 코드가 좀 복잡해 질 듯.
        //공통 보기 같은 경우는 그 보기를 참고해야 하는데 예를 들어, 1번이 공통 보기인데 2번부터 출력되면
        //1번 보기를 참고를 못함. 그래서 일단은 이게 1번 방법으로 함.
        Intent intent = getIntent();
        mRound = intent.getStringExtra(CHOICE_ROUND);
        selected_prob_int = intent.getIntExtra(CHOICE_PROB,-1);
        Random rnd = new Random();
        int num = rnd.nextInt(50);
        if(50 - num < selected_prob_int){ //Topik1이냐, 2이냐에 따라서 50문제, 70문제로 좀 바뀔듯.
            num = 50-selected_prob_int+1;
            selected_prob_int = selected_prob_int+1;
        }
        if(num == 0){
            selected_prob_int = selected_prob_int+1;
        }
        Query query = mFirebaseDatabaseReference.child(PROBLEM_SET).child(mRound).orderByChild("prob_num").startAt(num).endAt(selected_prob_int+num-1);
        //만약 문제수가 45문젠데 랜덤 count가 45부터 시작하면 50문제 밖에 없으니까 6개 밖에 안 나오게 될 것이다.
        //이렇게 범위가 50이상이 되어버리는 경우 해당 범위에서 51-i 부터 start되게 하면 될 듯.
        //70문제면 71-i
        FirebaseRecyclerOptions<ProblemSet> options = new FirebaseRecyclerOptions.Builder<ProblemSet>()
                .setQuery(query, ProblemSet.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder>(options){
//            List positionList = new ArrayList<Integer>();

            @Override
            protected void onBindViewHolder(ProblemViewHolder problemViewHolder, int i, ProblemSet problemSet) {

                //else if(problemSet.getMultiple_question() != null && Integer.parseInt(problemSet.getMultiple_question()) == problemSet.getProb_num()-1){
//                    problemViewHolder.commonNumber1.setText(String.valueOf(problemSet.getProb_num()));
//                    problemViewHolder.commonNumber2.setText(String.valueOf(problemSet.getProb_num()+1));
//                }
                if (problemSet.getExample().equals("")) {
                    problemViewHolder.exampleText.setVisibility(GONE);
                    problemViewHolder.commonCardView.setVisibility(GONE);
                    problemViewHolder.commonContent.setVisibility(GONE);

                } else {
                    problemViewHolder.exampleText.setVisibility(VISIBLE);
                    problemViewHolder.commonCardView.setVisibility(VISIBLE);
                    problemViewHolder.commonContent.setText(problemSet.getExample());
                    problemViewHolder.commonContent.setVisibility(VISIBLE);
                }

                if (problemSet.getQuestion().equals("")) {
                    problemViewHolder.commonQuestion.setVisibility(GONE);
                } else {
                    problemViewHolder.commonQuestion.setText(problemSet.getQuestion());
                    problemViewHolder.commonQuestion.setVisibility(VISIBLE);
                }

                if (problemSet.getText().equals("")) {
                    problemViewHolder.textTextView.setVisibility(GONE);
                } else {
                    problemViewHolder.textTextView.setText(problemSet.getText());
                    problemViewHolder.textTextView.setVisibility(VISIBLE);
                }

//                if(!(positionList.contains(i))){
//                    problemViewHolder.choice1Radio.setChecked(false);
//                    problemViewHolder.choice2Radio.setChecked(false);
//                    problemViewHolder.choice3Radio.setChecked(false);
//                    problemViewHolder.choice4Radio.setChecked(false);
//                    positionList.add(i);
//                }
//                Log.e("tag", String.valueOf(positionList.indexOf(i)));

//                problemViewHolder.choice1Radio.setChecked(false);
//                problemViewHolder.choice2Radio.setChecked(false);
//                problemViewHolder.choice3Radio.setChecked(false);
//                problemViewHolder.choice4Radio.setChecked(false);

//                problemViewHolder.choice1Radio.setChecked(Update("Button_One"));
//                problemViewHolder.choice2Radio.setChecked(Update("Button_Two"));
//                problemViewHolder.choice3Radio.setChecked(Update("Button_Three"));
//                problemViewHolder.choice4Radio.setChecked(Update("Button_Four"));

                problemViewHolder.choice1Radio.setText(problemSet.getChoice1());
                problemViewHolder.choice2Radio.setText(problemSet.getChoice2());
                problemViewHolder.choice3Radio.setText(problemSet.getChoice3());
                problemViewHolder.choice4Radio.setText(problemSet.getChoice4());
                problemViewHolder.problemRealAnswer.setText(String.valueOf(problemSet.getAnswer()));
                problemViewHolder.problemNumber.setText(String.valueOf(problemSet.getProb_num()));
                problemViewHolder.point.setText(String.valueOf(problemSet.getPoint()));
            }

            @NonNull
            @Override
            public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_problem, parent, false);

                return new ProblemViewHolder(view);
            }
        };

        //번호이동
        //화면에 표시된 문제 번호들을 다 리스트에 집어 넣고 하나하나 add
        //첨 시작된 문제 번호랑 끝만 알면 사이즈 파악해서 넣으면 될 듯.
        //문제이동 성공!하지만 선택했던 결과가 저장이 안 된다....
        //이상한 값들이 들어가 있음. 클릭하면 결과를 문제번호랑 같이 저장하고 담에 불러올 때 해당 번호에 대한 결과가 있다면 미리
        // 해야할 듯. setCheck해놓게
        CheckBox move_check = findViewById(R.id.move_check);
        GridView gridView = findViewById(R.id.check_list);
        gridItem = new ArrayList<>();
        gridAdapter = new GridAdapter(gridItem);
        if(num == 0){
            num = 1;
        }
        for(int i = num; i < num + selected_prob_int; i++ ){
            gridItem.add(String.valueOf(i));
        }
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRecyclerView.scrollToPosition(position);
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //번호이동
        //1. Gridview로 아이템을 만들고
        //2. 아이템을 클릭하면 해당 번호의 문제로 이동하게
//        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this) {
//            @Override
//            protected int getVerticalSnapPreference() {
//                return LinearSmoothScroller.SNAP_TO_START;
//            }
//        };

//        smoothScroller.setTargetPosition(5);
//        layoutManager.startSmoothScroll(smoothScroller);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mFirebaseAdapter);

    }

    //파이어베이스 어댑터 쓸 거면 무조건 이거 해줘야함.
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAdapter.stopListening();
    }


    public void scoring(View view) {
        //넘길 때 회차정보인 child도 같이 넘기기. 굳이 어레이에 묶을 필요 없을 듯.
        userList = new ArrayList<UserSet>();
        userList = (ArrayList<UserSet>) cAdapter.returnList();
//        Collections.sort(userList);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            userList.sort(Comparator.naturalOrder());
        }
        Intent intent = new Intent(this, ScoringActivity.class);
        intent.putExtra(USER_LIST, (Serializable) userList);
        intent.putExtra(PROB_ROUND, mRound);
        intent.putExtra(PROB_SCORE, mPoint);
        startActivity(intent);
    }

    public void move_problem(View view) {
        //이 버튼을 누르면 쉽게 이동하네
        //그렇다면 여기서 해야할게 우선 문제 이동하는 체크박스 느낌으로 gridvies를 만들어야하는데
        //gridview에는 불러와진 번호만 아이템으로 들어감. 그리고 그 아이템의 순서가 곧,
        //이동할 position이 되겠지.
//        new Handler().postDelayed(new Runnable(){
//            @Override
//            public void run() {
//                mRecyclerView.scrollToPosition(5);
//            }
//        },100);
    }

    private void SaveIntoSharedPrefs(String key, boolean value){
        SharedPreferences sp = getSharedPreferences("BUTTON_SAVE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.apply();;
    }

    private boolean Update(String key){
        SharedPreferences sp = getSharedPreferences("BUTTON_SAVE",MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

}
