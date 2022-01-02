package com.example.topikappv2;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Boolean.compare;
import static java.util.Comparator.*;

//해결해야 하는 과제들.
//mp.realse()를 어떻게 할 것인가??
public class ProblemAdapter extends BaseAdapter{
    private final List<ProblemSet> mData;
    private UserAdapter uAdapter= new UserAdapter();
    private ListeningOptionsAdapter lAdapter = new ListeningOptionsAdapter() {};
    private ArrayList<ListeningOptions> listeningList;

    int played_num =0;
    //듣기 파일
    //public static String url;

//    int pos; //재생 멈춘 시점
//    boolean isPlaying = false; // 재생중인지 확인할 변수
//    boolean first_touch =true;
//    boolean flag_restart = false;
//    boolean flag_first = true;
//    boolean flag_playing = false;
    MediaPlayer mp; // 음악 재생을 위한 객체
    //int duration;


    public ProblemAdapter(List<ProblemSet> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    } //아이템 갯수

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public boolean isChecked1(int position){
        return mData.get(position).checked1;
    }

    public boolean isChecked2(int position){
        return mData.get(position).checked2;
    }

    public boolean isChecked3(int position){
        return mData.get(position).checked3;
    }

    public boolean isChecked4(int position){
        return mData.get(position).checked4;
    }


    //최적화 작업을 위해서 뷰를 한번 로드하면 재사용하고 표시할 내용만 교체하기 위한 클래스
    static class ViewHolder{
        LinearLayout number_linear;
        LinearLayout radio_linear;
        LinearLayout image_linear;

        TextView arranged_num;
        TextView number;
        TextView common_question;
        TextView plural_question;
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

        TextView exampleText;
        TextView solutionText;

        TextView problemUserAnswer;
        TextView problemRealAnswer;
        TextView score;
        TextView probSet;

        FrameLayout frameDraw;

        CardView imageCard1;
        CardView imageCard2;
        CardView imageCard3;
        CardView imageCard4;

        ImageView choiceImage1;
        ImageView choiceImage2;
        ImageView choiceImage3;
        ImageView choiceImage4;

        ImageView textImage;

        //듣기 파일
        ImageButton bPlay;
        ImageButton bPause;
        ImageButton bRestart;
        ImageButton bStop;

        SeekBar sb; // 음악 재생위치를 나타내는 시크바
        MediaPlayer mp; // 음악 재생을 위한 객체
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //아이템 한 칸에 들어갈 레이아웃
        //MainActivity와 같은 activity에서는 setContentView해서 바로 가져오면 되지만
        //activity가 아닌 일반 class에서는 LayoutInflater로 로드한 View에서 명시적으로 findViewVyId를 사용해야함.
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_problem,parent,false); //item_weather에서 가져오기!

            TextView arranged_num = convertView.findViewById(R.id.arrangedNumber);
            TextView number = convertView.findViewById(R.id.number);
            TextView common_question = convertView.findViewById(R.id.common_question);
            TextView plural_question = convertView.findViewById(R.id.plural_question);
            TextView problemTextView = convertView.findViewById(R.id.problemTextView);
            TextView textTextView = convertView.findViewById(R.id.textTextView);
            TextView choiceNumber1 = convertView.findViewById(R.id.choiceNumber1);
            TextView choiceNumber2 = convertView.findViewById(R.id.choiceNumber2);
            TextView choiceNumber3 = convertView.findViewById(R.id.choiceNumber3);
            TextView choiceNumber4 = convertView.findViewById(R.id.choiceNumber4);
            RadioButton choice1Radio = convertView.findViewById(R.id.choice1Radio);
            RadioButton choice2Radio = convertView.findViewById(R.id.choice2Radio);
            RadioButton choice3Radio = convertView.findViewById(R.id.choice3Radio);
            RadioButton choice4Radio = convertView.findViewById(R.id.choice4Radio);
            TextView exampleText = convertView.findViewById(R.id.exampleText);
            TextView solutionText = convertView.findViewById(R.id.solutionText);
            TextView problemUserAnswer = convertView.findViewById(R.id.problemUserAnswer);
            TextView problemRealAnswer = convertView.findViewById(R.id.problemRealAnswer);
            TextView score = convertView.findViewById(R.id.prob_point);

            FrameLayout frameDraw = convertView.findViewById(R.id.frameDraw);

            //이미지
            CardView imageCard1 = convertView.findViewById(R.id.image_card1);
            CardView imageCard2 = convertView.findViewById(R.id.image_card2);
            CardView imageCard3 = convertView.findViewById(R.id.image_card3);
            CardView imageCard4 = convertView.findViewById(R.id.image_card4);

            ImageView choiceImage1 = convertView.findViewById(R.id.choiceImage1);
            ImageView choiceImage2 = convertView.findViewById(R.id.choiceImage2);
            ImageView choiceImage3 = convertView.findViewById(R.id.choiceImage3);
            ImageView choiceImage4 = convertView.findViewById(R.id.choiceImage4);
            ImageView textImage = convertView.findViewById(R.id.textImage);

            TextView probSet = convertView.findViewById(R.id.probSet);

            ImageButton bPlay = convertView.findViewById(R.id.play);
            ImageButton bPause = convertView.findViewById(R.id.pause);
            ImageButton bRestart= convertView.findViewById(R.id.restart);
            ImageButton bStop = convertView.findViewById(R.id.stop);
            SeekBar sb = convertView.findViewById(R.id.seekBar);

            holder.arranged_num = arranged_num;
            holder.number = number;
            holder.common_question = common_question;
            holder.plural_question = plural_question;
            holder.problemTextView = problemTextView;
            holder.textTextView = textTextView;

            holder.choiceNumber1 = choiceNumber1;
            holder.choiceNumber2 = choiceNumber2;
            holder.choiceNumber3 = choiceNumber3;
            holder.choiceNumber4 = choiceNumber4;

            holder.choice1Radio = choice1Radio;
            holder.choice2Radio = choice2Radio;
            holder.choice3Radio = choice3Radio;
            holder.choice4Radio = choice4Radio;
            holder.exampleText = exampleText;
            holder.solutionText = solutionText;
            holder.frameDraw = frameDraw;

            holder.problemUserAnswer = problemUserAnswer;
            holder.problemRealAnswer = problemRealAnswer;
            holder.score = score;

            holder.probSet = probSet;

            //이미지
            holder.imageCard1 = imageCard1;
            holder.imageCard2 = imageCard2;
            holder.imageCard3 = imageCard3;
            holder.imageCard4 = imageCard4;

            holder.choiceImage1 = choiceImage1;
            holder.choiceImage2 = choiceImage2;
            holder.choiceImage3 = choiceImage3;
            holder.choiceImage4 = choiceImage4;

            holder.textImage = textImage;

            //듣기 파일
            holder.bPlay = bPlay;
            holder.bPause = bPause;
            holder.bRestart = bRestart;
            holder.bStop = bStop;
            holder.sb = sb;

            convertView.setTag(holder);
        } else{ //재사용 할 때
            holder = (ViewHolder) convertView.getTag();
        }
        ProblemSet problemSet = mData.get(position);
//        Log.d("지문",problemSet.getText());
        //듣기
        holder.bPlay.setVisibility(GONE);
        holder.bPause.setVisibility(GONE);
        holder.bRestart.setVisibility(GONE);
        holder.bStop.setVisibility(GONE);
        holder.sb.setVisibility(GONE);


        //이미지 처리
        holder.textImage.setVisibility(GONE);
        holder.textTextView.setVisibility(VISIBLE);
        holder.textTextView.setText(problemSet.getText());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("topik").child("topik1").child("이미지");
        StorageReference pathReference2 = storageReference.child("topik").child("topik1").child("듣기");

        //1. 지문
        if(problemSet.getText().equals("image")){
            holder.textTextView.setVisibility(GONE);
            holder.textImage.setVisibility(VISIBLE);
            String str_image = problemSet.getImage();

            Log.d("이미지 이름", str_image);
            StorageReference submitProfile_image = storageReference.child("topik/topik1/이미지/" + str_image + ".PNG");
            submitProfile_image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(parent.getContext()).load(uri).into(holder.textImage);
                }
            }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

        if(problemSet.getText().equals("")){
            holder.textTextView.setVisibility(GONE);
        }

        //2. 선지
        holder.imageCard1.setVisibility(GONE);
        holder.imageCard2.setVisibility(GONE);
        holder.imageCard3.setVisibility(GONE);
        holder.imageCard4.setVisibility(GONE);

        holder.choiceImage1.setVisibility(GONE);
        holder.choiceImage2.setVisibility(GONE);
        holder.choiceImage3.setVisibility(GONE);
        holder.choiceImage4.setVisibility(GONE);

        if(!problemSet.getChoice1().equals("image")){
            holder.choice1Radio.setText(problemSet.getChoice1());
            holder.choice2Radio.setText(problemSet.getChoice2());
            holder.choice3Radio.setText(problemSet.getChoice3());
            holder.choice4Radio.setText(problemSet.getChoice4());
        } else{
            holder.imageCard1.setVisibility(VISIBLE);
            holder.imageCard2.setVisibility(VISIBLE);
            holder.imageCard3.setVisibility(VISIBLE);
            holder.imageCard4.setVisibility(VISIBLE);

            holder.choiceImage1.setVisibility(VISIBLE);
            holder.choiceImage2.setVisibility(VISIBLE);
            holder.choiceImage3.setVisibility(VISIBLE);
            holder.choiceImage4.setVisibility(VISIBLE);

            holder.choice1Radio.setText("");
            holder.choice2Radio.setText("");
            holder.choice3Radio.setText("");
            holder.choice4Radio.setText("");

            String str = problemSet.getImage();
            String [] array = str.split(",");

            StorageReference submitProfile = storageReference.child("topik/topik1/이미지/" + array[0] + ".PNG");
            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(parent.getContext()).load(uri).into(holder.choiceImage1);
                }
            }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            Log.d("사진1", array[0]);

            StorageReference submitProfile2 = storageReference.child("topik/topik1/이미지/" + array[1] + ".PNG");
            submitProfile2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(parent.getContext()).load(uri).into(holder.choiceImage2);
                }
            }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            StorageReference submitProfile3 = storageReference.child("topik/topik1/이미지/" + array[2] + ".PNG");
            submitProfile3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(parent.getContext()).load(uri).into(holder.choiceImage3);
                }
            }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

            StorageReference submitProfile4 = storageReference.child("topik/topik1/이미지/" + array[3] + ".PNG");
            submitProfile4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(parent.getContext()).load(uri).into(holder.choiceImage4);
                }
            }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
        //////////////////////////////////////////////////////////////////////////////
        ////////////////////여기수정.... compare_num-1

        //듣기 구현
        class MyThread extends Thread {
            @Override
            public void run() { // 쓰레드가 시작되면 콜백되는 메서드
                // 씨크바 막대기 조금씩 움직이기 (노래 끝날 때까지 반복)
                int num =Integer.parseInt((String)holder.arranged_num.getText());
                Log.d("num",String.valueOf(num-1));
                while(lAdapter.getIsPlaying(num-1)) {
                    holder.sb.setProgress(mp.getCurrentPosition());
                }
            }
        }

        if(!problemSet.getMp3().equals("NA")){
            holder.bPlay.setVisibility(VISIBLE);
            holder.bPause.setVisibility(VISIBLE);
            holder.bRestart.setVisibility(VISIBLE);
            holder.bStop.setVisibility(GONE);
            holder.sb.setVisibility(VISIBLE);
            holder.sb = convertView.findViewById(R.id.seekBar);

            holder.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // 사용자가 움직여놓은 위치
                    int compare_num =Integer.parseInt((String)holder.arranged_num.getText());

                    lAdapter.setIsPlaying(compare_num-1,true);
                    Log.d("isPLaying",String.valueOf(lAdapter.getIsPlaying(compare_num-1)));
                    mp.setLooping(false); // true:무한반복
//                    lAdapter.setPosition(compare_num-1,seekBar.getProgress());
                    Log.d("postion",String.valueOf(lAdapter.getPosition(compare_num-1)));
                    mp.seekTo(seekBar.getProgress());// 사용자가 움직여놓은 위치
                    mp.start();
                    new MyThread().start();
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                    int compare_num =Integer.parseInt((String)holder.arranged_num.getText());
                    Log.d("compare_num",String.valueOf(compare_num-1));
                    lAdapter.setIsPlaying(compare_num-1,false);
                    Log.d("isPLaying",String.valueOf(lAdapter.getIsPlaying(compare_num-1)));
                    mp.pause();
                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (seekBar.getMax() == progress) {
//                            holder.bPlay.setVisibility(View.VISIBLE);
//                            holder.bPause.setVisibility(View.GONE);
//                            holder.bRestart.setVisibility(VISIBLE);
                        mp.setLooping(false); // true:무한반복
                    }
                }
            });

            Log.d("듣기 파일", problemSet.getMp3());
            StorageReference submitProfile5 = storageReference.child("topik/topik1/듣기/" + problemSet.getMp3() + ".mp3");

            submitProfile5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String url = uri.toString();
                    String arranged_num = (String) holder.arranged_num.getText();
                    int data_size = lAdapter.getCount();
                    Boolean same_flag = false;
                    Log.d("번호: ", (String) holder.arranged_num.getText());
                    //Log.d("듣기 파일", problemSet.getMp3());
//                        Log.d("url제대로?",url);
                    Integer int_arranged_num = Integer.parseInt(arranged_num) - 1;
                    //Log.d("데이터 개수: ", String.valueOf((data_size)));
                    if (data_size != 0) {
                        //Log.d("안 비었다!", "");
                        // 안 비어 있을 경우
                        for (int i = 0; i < lAdapter.getCount(); i++) {
                            //Log.d("들어 있는 것 ", lAdapter.getItem(i).getProb_num());
                            //Log.d("들어 갈 것 ", arranged_num);
                            if (lAdapter.getItem(i).getProb_num() == arranged_num) {
                                same_flag = true;
                                //Log.d("들어 있는 것 MP3 ", lAdapter.getItem(i).getStr_mp3());
                                //Log.d("들어 갈 것 MP3", problemSet.getMp3());
                                if (lAdapter.getItem(i).getStr_mp3() != problemSet.getMp3()) {
                                    lAdapter.getItem(i).setStr_mp3(problemSet.getMp3());
                                    //Log.d("달라서 넣었어", "");
                                }
                            }
                        }
                        if (same_flag == false) {
                            //Log.d("비어 있지는 않은데", "같은 것도 없어");
                            lAdapter.addItem(new ListeningOptions(0, arranged_num, url, problemSet.getMp3(), true,
                                    false, true));
                        }
                    } else {
                        lAdapter.addItem(new ListeningOptions(0, arranged_num, url, problemSet.getMp3(), true,
                                false, true));
                        //Log.d("비어 있어서 넣었다:", String.valueOf(lAdapter.getItem(0).getProb_num()));
                    }
                    Log.d("progress적용",String.valueOf(lAdapter.getPosition(int_arranged_num)));
                    holder.sb.setProgress(lAdapter.getPosition(int_arranged_num));
                    //Log.d("데이터 개수", String.valueOf(lAdapter.getCount()));
                    //정렬
                    if (!lAdapter.isEmpty()) {
                        listeningList = lAdapter.returnList();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            listeningList.sort(Comparator.<ListeningOptions>naturalOrder());
                            lAdapter.setList(listeningList);
                        }
                    }
                    //Log.d("##루프 끝", "##");
                }
            }).addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }

        ///////////sb bar 언제 바뀌는지 확인 용
//        if(lAdapter.getCount()!= 0 && lAdapter.getCount() >= Integer.parseInt((String) holder.arranged_num.getText())) {
//            if (lAdapter.getAfter_flag(Integer.parseInt((String) holder.arranged_num.getText()) - 1)) {
//
//                Log.d("어디니?2", String.valueOf(lAdapter.getPosition(Integer.parseInt((String) holder.arranged_num.getText()) - 1)));
//                holder.sb.setProgress(lAdapter.getPosition(Integer.parseInt((String) holder.arranged_num.getText()) - 1));
//                Log.d("어디니?2", String.valueOf(lAdapter.getPosition(Integer.parseInt((String) holder.arranged_num.getText()) - 1)));
//            }
//        }

        holder.choice1Radio.setChecked(mData.get(position).checked1);
        holder.choice2Radio.setChecked(mData.get(position).checked2);
        holder.choice3Radio.setChecked(mData.get(position).checked3);
        holder.choice4Radio.setChecked(mData.get(position).checked4);

        holder.choice1Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState1 = !mData.get(position).isChecked1();
                mData.get(position).checked1 = newState1;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = false;

                holder.choice2Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("1");
            }
            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set()),String.valueOf(problemSet.getScore())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }
        });

        holder.choice2Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState2 = !mData.get(position).isChecked2();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = newState2;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = false;

                holder.choice1Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("2");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set()),String.valueOf(problemSet.getScore())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }
        });

        holder.choice3Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState3 = !mData.get(position).isChecked3();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = newState3;
                mData.get(position).checked4 = false;

                holder.choice1Radio.setChecked(false);
                holder.choice2Radio.setChecked(false);
                holder.choice4Radio.setChecked(false);
                ChangeAnswer("3");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set()),String.valueOf(problemSet.getScore())));
                //Log.d("prob_set,PAdapter",mData.get(position).getProb_set());
            }

        });

        holder.choice4Radio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean newState4 = !mData.get(position).isChecked4();
                mData.get(position).checked1 = false;
                mData.get(position).checked2 = false;
                mData.get(position).checked3 = false;
                mData.get(position).checked4 = newState4;

                holder.choice1Radio.setChecked(false);
                holder.choice2Radio.setChecked(false);
                holder.choice3Radio.setChecked(false);
                ChangeAnswer("4");
            }

            private void ChangeAnswer(String answer) {
                holder.problemUserAnswer.setText(answer);
                int numInt = Integer.parseInt(String.valueOf(answer));
                if (String.valueOf(numInt) == String.valueOf(1)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(2)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else if (String.valueOf(numInt) == String.valueOf(3)) {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                } else {
                    DeleteSetItem(numInt, Integer.parseInt(holder.arranged_num.getText().toString()), Integer.parseInt(holder.number.getText().toString()), Integer.parseInt(holder.score.getText().toString()));
                }
            }

            public void DeleteSetItem(Integer userNumber, int arrangedNumber, int problemNumber, int point) {
                uAdapter.deleteItem(arrangedNumber);
                uAdapter.addItem(new UserSet(String.valueOf(arrangedNumber),String.valueOf(problemNumber), String.valueOf(userNumber), holder.problemRealAnswer.getText().toString(),String.valueOf(problemSet.getProb_set()),String.valueOf(problemSet.getScore())));
                Log.d("prob_set,PAdapter",problemSet.getProb_set());
            }
        });

        //데이터 설정
        holder.number.setText(problemSet.getProb_num());
        holder.arranged_num.setText(problemSet.getArranged_num());
        holder.common_question.setText(problemSet.getQuestion());
        holder.probSet.setText(problemSet.getProb_set());
        if(holder.common_question.getText().equals("")){
            holder.common_question.setVisibility(GONE);
        }
        holder.plural_question.setText(problemSet.getPlural_question());
        if(!holder.plural_question.getText().equals("")){
            holder.plural_question.setVisibility(VISIBLE);
        }
        holder.problemTextView.setText(problemSet.getQuestion_example());
        if(!holder.problemTextView.getText().equals("")){
            holder.exampleText.setVisibility(VISIBLE);
        } else{
            holder.exampleText.setVisibility(GONE);
        }
///////////sb bar 언제 바뀌는지 확인 용
//        if(lAdapter.getCount()!= 0 && lAdapter.getCount() >= Integer.parseInt((String) holder.arranged_num.getText())) {
//            if (lAdapter.getAfter_flag(Integer.parseInt((String) holder.arranged_num.getText()) - 1)) {
//                Log.d("어디니?3", String.valueOf(lAdapter.getPosition(Integer.parseInt((String) holder.arranged_num.getText()) - 1)));
//            }
//        }
        holder.score.setText(problemSet.getScore());
        holder.solutionText.setText(problemSet.getSolution());

        holder.problemRealAnswer.setText(problemSet.getAnswer());
        holder.problemUserAnswer.setText(problemSet.getUser_answer());

        String user_answer = problemSet.getUser_answer();
        String real_answer = problemSet.getAnswer();

        ///////////sb bar 언제 바뀌는지 확인 용
//        if(lAdapter.getCount()!= 0 && lAdapter.getCount() >= Integer.parseInt((String) holder.arranged_num.getText())) {
//            if (lAdapter.getAfter_flag(Integer.parseInt((String) holder.arranged_num.getText()) - 1)) {
//                Log.d("어디니?4", String.valueOf(lAdapter.getPosition(Integer.parseInt((String) holder.arranged_num.getText()) - 1)));
//            }
//        }
        if (user_answer != null){
            //O, X그리기
            O_drawing O_drawing = new O_drawing(convertView.getContext());
            X_drawing X_drawing = new X_drawing(convertView.getContext());
            if(Integer.parseInt(user_answer) ==Integer.parseInt(real_answer) ){
                //Log.d("O","O");
                holder.frameDraw.addView(O_drawing);
            } else {
                //Log.d("X","X");
                holder.frameDraw.addView(X_drawing);
            }

            //solution 용
            if(user_answer.equals("1")){
                holder.choice1Radio.setChecked(TRUE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else if(user_answer.equals("2")){
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(TRUE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else if(user_answer.equals("3")){
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(TRUE);
                holder.choice4Radio.setChecked(FALSE);
            }
            else{
                holder.choice1Radio.setChecked(FALSE);
                holder.choice2Radio.setChecked(FALSE);
                holder.choice3Radio.setChecked(FALSE);
                holder.choice4Radio.setChecked(TRUE);
            }
        }

        ///////////sb bar 언제 바뀌는지 확인 용
//        if(lAdapter.getCount()!= 0 && lAdapter.getCount() >= Integer.parseInt((String) holder.arranged_num.getText())) {
//            if (lAdapter.getAfter_flag(Integer.parseInt((String) holder.arranged_num.getText()) - 1)) {
//                Log.d("어디니?1", String.valueOf(lAdapter.getPosition(Integer.parseInt((String) holder.arranged_num.getText()) - 1)));
//            }
//        }
        holder.bPlay.setVisibility(holder.bPlay.getVisibility());
        holder.bPause.setVisibility(holder.bPause.getVisibility());
        holder.bRestart.setVisibility(holder.bRestart.getVisibility());
        holder.bStop.setVisibility(holder.bStop.getVisibility());

        holder.bPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int compare_num = Integer.parseInt((String) holder.arranged_num.getText());
                Log.d("문제 번호", (String) lAdapter.getProb_num(compare_num-1));
                String url = lAdapter.getUrl(compare_num-1);

                if(played_num == 0) {
                }else{
                   if(played_num !=compare_num){
                       lAdapter.setIsPlaying(played_num-1,false); //멈추고
                       mp.pause();
                       lAdapter.setPosition(played_num-1,mp.getCurrentPosition()); // 위치 기억
                       lAdapter.setFlag_restart(played_num-1,true); // 다시 시작
                       
                       //mp에 url, sb 설정
                       
                   }
                }

                //Log.d("문제 url",  lAdapter.getItem(compare_num-1).getUrl());
                //Log.d("문제 str_mp3",  lAdapter.getItem(compare_num-1).getStr_mp3());
               // Log.d("문제 isPlaying",  String.valueOf(lAdapter.getIsPlaying(compare_num-1)));

                if (!lAdapter.getIsPlaying(compare_num-1) || lAdapter.getFlag_first(compare_num-1)) {
                    lAdapter.setIsPlaying(compare_num-1,true);
                    //Log.d("문제 isPlaying", String.valueOf(lAdapter.getIsPlaying(compare_num-1)));
                    if (lAdapter.getFlag_first(compare_num-1) != true) {
                        //Log.d("flag_first", "false");
                        if (mp.getDuration() == holder.sb.getMax()) {
                            mp.seekTo(0);
                            //holder.sb.setProgress(0);
                        }
                    }

                    if (lAdapter.getFlag_restart(compare_num-1) != true) {
                        //첫 시작일 때
                        lAdapter.setFlag_first(compare_num-1,false);
                       // Log.d("flag_first false 바뀌었나?",  String.valueOf(lAdapter.getFlag_first(compare_num-1)));
                        //첫 실행이면 플레이어 새로 생성
                        mp = new MediaPlayer();

                        //데이터 집어 넣기
                        try {
                            mp.setDataSource(url);
                            mp.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        int a = mp.getDuration(); // 노래의 재생시간(miliSecond)
                        holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정

                    } else {
                        lAdapter.setFlag_restart(compare_num-1,false);
                        //lAdapter.getItem(compare_num-1).setFlag_restart(false);
                        Log.d("restart false바뀌었나?", String.valueOf(lAdapter.getFlag_restart(compare_num-1)));

                    }
                    //포지션 넣기
                    if (lAdapter.getPosition(compare_num-1) != 0) {
                        mp.seekTo(lAdapter.getPosition(compare_num-1));
                        Log.d("이게 실행되면 안 되는데?","그래");
                    }

                    lAdapter.setIsPlaying(compare_num-1,true);

                    mp.setLooping(false); // true:무한반복
                    mp.start();
                    new MyThread().start(); // 씨크바 그려줄 쓰레드 시작

//                    holder.bPlay.setVisibility(GONE);
//                    holder.bPause.setVisibility(VISIBLE);
                }
                played_num= compare_num;
            }
        });

        holder.bPause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int compare_num = Integer.parseInt((String) holder.arranged_num.getText());

                Log.d("문제 번호", (String) lAdapter.getProb_num(compare_num-1));
                Log.d("pause isPlaying",String.valueOf(lAdapter.getItem(compare_num-1).getIsPlaying()));
                if (lAdapter.getIsPlaying(compare_num-1)) {
                    lAdapter.setIsPlaying(compare_num-1,false);
                   // Log.d("pause isPlaying:",  String.valueOf(lAdapter.getItem(compare_num-1).getIsPlaying()));
                    if (mp != null) {
                        lAdapter.setPosition(compare_num-1,mp.getCurrentPosition());
                        mp.pause();
                    }
                    lAdapter.setIsPlaying(compare_num-1,false);// 쓰레드 정지
                    lAdapter.setFlag_restart(compare_num-1, true);
//                    holder.bPause.setVisibility(GONE);
//                    holder.bPlay.setVisibility(VISIBLE);
                }
            }
        });

        holder.bRestart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                int compare_num =Integer.parseInt((String)holder.arranged_num.getText());
                Log.d("compare_num",String.valueOf(holder.arranged_num.getText()));
                Log.d("문제 번호", (String) lAdapter.getProb_num(compare_num-1));

                if (mp == null) {
                    Log.d("mp가 없습니다","없음!");
                    //mp 생성하기
                    Log.d("mp 생성 first_flag:",String.valueOf(lAdapter.getFlag_first(compare_num-1)));
                    mp = new MediaPlayer();
                    lAdapter.setFlag_first(compare_num-1,false);

                    Log.d("first_flag:",String.valueOf(lAdapter.getFlag_first(compare_num-1)));

                    //데이터 넣기
                    try {
                        String url =lAdapter.getUrl(compare_num-1);
                        Log.d("url",url);
                        mp.setDataSource(url);
                        Log.d("데이터 넣기 완료","!");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //준비하기
                    try {
                        mp.prepare();
                        Log.d("mp 준비 완료","!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int a = mp.getDuration(); // 노래의 재생시간(miliSecond)
                    Log.d("mp duration 완료",String.valueOf(a));
                    holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정
                    Log.d("sb max치 완료",String.valueOf(a));
                }
                Log.d("mp가 null 아닐 때 실행되는 것 ㅎㅎ","!");
                holder.sb.setProgress(0); // 씨크바 초기화
                mp.setLooping(false);
                mp.seekTo(0);
                Log.d("sb 초기화 완료","!");
                mp.start();
                Log.d("시작 완료","!");
                lAdapter.setIsPlaying(compare_num-1,true); // 씨크바 쓰레드 반복 하도록
                lAdapter.setFlag_restart(compare_num-1,true);
                new MyThread().start(); // 씨크바 그려줄 쓰레드 시작
                Log.d("끝 isPlaying:",String.valueOf(lAdapter.getIsPlaying(compare_num-1)));

//                holder.bPlay.setVisibility(GONE);
//                holder.bPause.setVisibility(VISIBLE);
            }
        });

//        holder.bStop.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                int compare_num =Integer.parseInt((String)holder.arranged_num.getText());
//                ListeningOptions LOptions = lAdapter.getItem(compare_num-1);
//
//                LOptions.setFlag_restart(false);
//                LOptions.setPosition(0);
//                stopAudio(LOptions.getIsPlaying());
//                holder.sb.setProgress(0); // 씨크바 초기화
//                holder.bPause.setVisibility(GONE);
//                holder.bPlay.setVisibility(VISIBLE);
//            }
//        });

        ///////////sb bar 언제 바뀌는지 확인 용
//        if(lAdapter.getCount()!= 0 && lAdapter.getCount() >= Integer.parseInt((String) holder.arranged_num.getText())) {
//            if (lAdapter.getAfter_flag(Integer.parseInt((String) holder.arranged_num.getText()) - 1)) {
//                Log.d("어디니?5", String.valueOf(lAdapter.getPosition(Integer.parseInt((String) holder.arranged_num.getText()) - 1)));
//            }
//        }
        return convertView;
    }


    public UserAdapter return_uAdapter(){
        final UserAdapter uAdapter = this.uAdapter;
        return uAdapter;
    }

//    private void playAudio(String url_mp3, boolean flag_first) throws IOException {
//        if(flag_first) {
//            mp = new MediaPlayer();
//        }
//        mp.setDataSource(String.valueOf(url_mp3));
//    }
//
//    // 현재 일시정지가 되었는지 중지가 되었는지 헷갈릴 수 있기 때문에 스위치 변수를 선언해 구분할 필요가 있다. (구현은 안했다.)
//    private void pauseAudio(int pos) {
//        if (mp != null) {
//            lAdapter.getItem() = mp.getCurrentPosition();
//            mp.pause();
//        }
//    }
//
//    private void resumeAudio(boolean flag_restart, boolean isPlaying) {
//        flag_restart =true;
//        if (mp != null) {
//            isPlaying = true; // 씨크바 쓰레드 반복 하도록
//            mp.setLooping(false); // true:무한반복
//            mp.seekTo(0);
//            mp.start();
//        }
//    }
//
//    private void stopAudio(boolean isPlaying) {
//        if(mp != null){
//            isPlaying = false; // 쓰레드 종료
//            mp.stop();
//            mp.release(); // 자원 해제
//        }
//    }

    /* 녹음 시 마이크 리소스 제한. 누군가가 lock 걸어놓으면 다른 앱에서 사용할 수 없음.
     * 따라서 꼭 리소스를 해제해주어야함. */
//    public void closePlayer() {
//        if (mp != null) {
//            mp.release();
//            mp = null;
//        }
//    }

}
