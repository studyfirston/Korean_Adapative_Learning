package com.example.newtopikapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SolutionActivity extends AppCompatActivity{
    private String prob_num;
    private String mRound;
    private String user_answer;
    private String real_answer;


    //문제변수
    private RecyclerView mRecyclerView;
    public static final String PROBLEM_SET = "probset";
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<ProblemSet, ProblemViewHolder> mFirebaseAdapter;

    public class ProblemViewHolder extends RecyclerView.ViewHolder {
        TextView commonQuestion;
        TextView commonContent;
        CardView commonCardView;
        TextView exampleText;

        FrameLayout frameDraw;
        TextView problemNumber;
        TextView problemTextView;
        TextView textTextView;
        TextView solutionText;
        CheckBox solution_check;

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

        public ProblemViewHolder(@NonNull View itemView) {
            super(itemView);
            commonQuestion = itemView.findViewById(R.id.common_question); //공통발문
            commonContent = itemView.findViewById(R.id.common_content); //공통보기
            commonCardView = itemView.findViewById(R.id.cardView); //공통 보기 cardView
            exampleText = itemView.findViewById(R.id.exampleText); //보기라는 단순한 글자
            solutionText = itemView.findViewById(R.id.solutionText); //해설
            solution_check = itemView.findViewById(R.id.solution_check);

            frameDraw = itemView.findViewById(R.id.frameDraw);
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

            radioGroup = itemView.findViewById(R.id.radioGroup);
            //radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

            problemUserAnswer = itemView.findViewById(R.id.problemUserAnswer);
            problemRealAnswer = itemView.findViewById(R.id.problemRealAnswer);
            if(user_answer.equals("1")){
                radioGroup.check(R.id.choice1Radio);
            }
            else if(user_answer.equals("2")){
                radioGroup.check(R.id.choice2Radio);
            }
            else if(user_answer.equals("3")){
                radioGroup.check(R.id.choice3Radio);
            }
            else{
                radioGroup.check(R.id.choice4Radio);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);
        Intent intent = getIntent();
        prob_num = intent.getStringExtra("problemNum");
        int prob_num2 = Integer.parseInt(prob_num);
        mRound = intent.getStringExtra("prob_round");
        user_answer = intent.getStringExtra("user_answer");
        real_answer = intent.getStringExtra("real_answer");


        //문제 표시
        mRecyclerView = findViewById(R.id.solution_recycler_view);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Query query = mFirebaseDatabaseReference.child(PROBLEM_SET).child(mRound).orderByChild("prob_num").equalTo(prob_num2);
        FirebaseRecyclerOptions<ProblemSet> options = new FirebaseRecyclerOptions.Builder<ProblemSet>()
                .setQuery(query, ProblemSet.class)
                .build();

        mFirebaseAdapter = new FirebaseRecyclerAdapter<ProblemSet, SolutionActivity.ProblemViewHolder>(options){

            @Override
            protected void onBindViewHolder(SolutionActivity.ProblemViewHolder problemViewHolder, int i, ProblemSet problemSet) {
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
                //O, X그리기
                O_drawing O_drawing = new O_drawing(getBaseContext());
                X_drawing X_drawing = new X_drawing(getBaseContext());

                Log.d("user_answer", user_answer);
                Log.d("real_answer",real_answer);
                if(Integer.parseInt(user_answer) ==Integer.parseInt(real_answer) ){
                    //Log.d("O","O");
                    problemViewHolder.frameDraw.addView(O_drawing);
                } else {
                    //Log.d("X","X");
                    problemViewHolder.frameDraw.addView(X_drawing);
                }

                problemViewHolder.solution_check.setVisibility(VISIBLE);
                problemViewHolder.solution_check.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(problemViewHolder.solution_check.isChecked()){
                            problemViewHolder.solutionText.setVisibility(VISIBLE);
                        } else{
                            problemViewHolder.solutionText.setVisibility(GONE);
                        }
                    }
                });
                problemViewHolder.choice1Radio.setText(problemSet.getChoice1());
                problemViewHolder.choice2Radio.setText(problemSet.getChoice2());
                problemViewHolder.choice3Radio.setText(problemSet.getChoice3());
                problemViewHolder.choice4Radio.setText(problemSet.getChoice4());
                problemViewHolder.problemRealAnswer.setText(String.valueOf(problemSet.getAnswer()));
                problemViewHolder.problemNumber.setText(String.valueOf(problemSet.getProb_num()));
                problemViewHolder.solutionText.setText(String.valueOf(problemSet.getExplanation()));
            }

            @NonNull
            @Override
            public ProblemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_problem, parent, false);

                return new ProblemViewHolder(view);
            }
        };

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
        //ox_index = ox_index+1;
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
}
