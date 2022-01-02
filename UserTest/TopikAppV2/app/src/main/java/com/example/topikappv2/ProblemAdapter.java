package com.example.topikappv2;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import org.w3c.dom.Text;

public class ProblemAdapter extends BaseAdapter{

    //시간 측정
    private List<Time> timeList;
    private TimeAdapter tAdapter = new TimeAdapter();
    long start,end;
    long count_time = 0;
    //long count_load =0;
    int original_time = 0;
    int change = 1;
    private final List<ProblemSet> mData;
    private UserAdapter uAdapter= new UserAdapter();
    private List<UserSet> user_list = new ArrayList<>();
    private ListeningOptionsAdapter lAdapter = new ListeningOptionsAdapter() {};
    private ArrayList<ListeningOptions> listeningList;
    private ProgressBar common_sb;

    //바로 직전에 시작된 듣기 파일의 이름
    int played_num =0;


    //듣기,읽기 구분
    String section;

    //문제 ID
    private String problem_id;

    //듣기 파일
    public static String url;
    //MediaPlayer player;
    MediaPlayer mp;

    int position = 0; // 다시 시작 기능을 위한 현재 재생 위치 확인 변수

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

    public TimeAdapter return_tAdapter(){
        final TimeAdapter tAdapter = this.tAdapter;
        return tAdapter;
    }


    //최적화 작업을 위해서 뷰를 한번 로드하면 재사용하고 표시할 내용만 교체하기 위한 클래스
    static class ViewHolder{

        TextView arranged_num;
        TextView number;
        TextView common_question;
        TextView plural_question;
        TextView problemTextView;
        TextView textTextView;
        TextView exampleTextView;

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

        TextView realText;

        FrameLayout frameDraw;

        CardView exampleCard;
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

        CheckBox solution_check;
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
            TextView exampleTextView = convertView.findViewById(R.id.exampleTextView);
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
            TextView realText = convertView.findViewById(R.id.realText);
            CheckBox solution_check = convertView.findViewById(R.id.solution_check);

            FrameLayout frameDraw = convertView.findViewById(R.id.frameDraw);

            //59번 보기 카드뷰
            CardView exampleCard = convertView.findViewById(R.id.example_card);

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
            holder.realText = realText;

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
            holder.exampleTextView = exampleTextView;

            holder.solution_check = solution_check;
            holder.exampleCard = exampleCard;

            //듣기 파일
            holder.bPlay = bPlay;
            holder.bPause = bPause;
            holder.bRestart = bRestart;
            holder.bStop = bStop;
            holder.sb = sb;
            start = System.currentTimeMillis();
            convertView.setTag(holder);
        } else{ //재사용 할 때
            holder = (ViewHolder) convertView.getTag();
        }

        ////Log.d("position 확인합니다 solution",String.valueOf(position));
        ////Log.d("mData 크기 -1", String.valueOf(mData.size()-1));
        if (position >= mData.size() - 1 && mData.size() != 1) {
            //잘 되는데 왜??
            ////Log.d("이것 때문에 안보이는가? pos: ",String.valueOf(position));
            ////Log.d("이것 때문에? mData_size:",String.valueOf(mData.size()-1));
            holder.arranged_num.setVisibility(GONE);
            holder.choice1Radio.setVisibility(GONE);
            holder.choice2Radio.setVisibility(GONE);
            holder.choice3Radio.setVisibility(GONE);
            holder.choice4Radio.setVisibility(GONE);
            holder.choiceNumber1.setVisibility(GONE);
            holder.choiceNumber2.setVisibility(GONE);
            holder.choiceNumber3.setVisibility(GONE);
            holder.choiceNumber4.setVisibility(GONE);
            holder.common_question.setVisibility(GONE);
            holder.exampleText.setVisibility(GONE);
            holder.imageCard1.setVisibility(GONE);
            holder.imageCard2.setVisibility(GONE);
            holder.imageCard3.setVisibility(GONE);
            holder.imageCard4.setVisibility(GONE);
            holder.choiceImage1.setVisibility(GONE);
            holder.choiceImage2.setVisibility(GONE);
            holder.choiceImage3.setVisibility(GONE);
            holder.choiceImage4.setVisibility(GONE);
            holder.number.setVisibility(GONE);
            holder.plural_question.setVisibility(GONE);
            holder.problemRealAnswer.setVisibility(GONE);
            holder.problemTextView.setVisibility(GONE);
            holder.problemUserAnswer.setVisibility(GONE);
            holder.probSet.setVisibility(GONE);
            holder.textImage.setVisibility(GONE);
            holder.textTextView.setVisibility(GONE);
            holder.sb.setVisibility(GONE);
            holder.bStop.setVisibility(GONE);
            holder.bPlay.setVisibility(GONE);
            holder.bRestart.setVisibility(GONE);
            holder.bPause.setVisibility(GONE);
            holder.realText.setVisibility(GONE);
        } else {
            //checked list
            holder.arranged_num.setVisibility(VISIBLE);
            holder.choice1Radio.setVisibility(VISIBLE);
            holder.choice2Radio.setVisibility(VISIBLE);
            holder.choice3Radio.setVisibility(VISIBLE);
            holder.choice4Radio.setVisibility(VISIBLE);
            holder.choiceNumber1.setVisibility(VISIBLE);
            holder.choiceNumber2.setVisibility(VISIBLE);
            holder.choiceNumber3.setVisibility(VISIBLE);
            holder.choiceNumber4.setVisibility(VISIBLE);

            holder.plural_question.setVisibility(GONE);
            holder.common_question.setVisibility(VISIBLE);
            holder.exampleText.setVisibility(GONE);

            holder.imageCard1.setVisibility(VISIBLE);
            holder.imageCard2.setVisibility(VISIBLE);
            holder.imageCard3.setVisibility(VISIBLE);
            holder.imageCard4.setVisibility(VISIBLE);
            holder.choiceImage1.setVisibility(GONE);
            holder.choiceImage2.setVisibility(GONE);
            holder.choiceImage3.setVisibility(GONE);
            holder.choiceImage4.setVisibility(GONE);

            holder.number.setVisibility(GONE);
            holder.problemRealAnswer.setVisibility(GONE);
            holder.problemTextView.setVisibility(GONE);
            holder.problemUserAnswer.setVisibility(GONE);
            holder.probSet.setVisibility(GONE);
            holder.textImage.setVisibility(GONE);
            holder.textTextView.setVisibility(GONE);


            ProblemSet problemSet = mData.get(position);
            ArrangedNum arrangedNumList = mData.get(mData.size()- 1).return_arrangedList();

            //듣기
            holder.sb.setVisibility(GONE);
            holder.bStop.setVisibility(GONE);
            holder.bPlay.setVisibility(GONE);
            holder.bRestart.setVisibility(GONE);
            holder.bPause.setVisibility(GONE);

            //59번을 위한 보기 추가
            holder.exampleTextView.setVisibility(GONE);
            holder.exampleCard.setVisibility(GONE);

            if (problemSet.getExample().equals("NA")){
                holder.exampleTextView.setText(problemSet.getExample());
            }else{
                holder.exampleTextView.setText(Html.fromHtml(problemSet.getExample()));
            }
            if(!holder.exampleTextView.getText().equals("NA")){
                holder.exampleCard.setVisibility(VISIBLE);
                holder.exampleTextView.setVisibility(VISIBLE);
            }

            if (arrangedNumList == null) {
                holder.arranged_num.setText("1");
                holder.textTextView.setVisibility(VISIBLE); //듣기 문제 스크립트 보이기
            } else {
                ////Log.d("arranged", arrangedNumList.get_num(position));
                holder.arranged_num.setText(arrangedNumList.get_num(position));
            }
//        //Log.d("지문",problemSet.getText());

            //이미지 처리
            holder.textImage.setVisibility(GONE);

            holder.textTextView.setText(Html.fromHtml(problemSet.getText()));

            if (!holder.textTextView.getText().equals("NA") &&!holder.textTextView.getText().equals("image")) {
                holder.textTextView.setVisibility(VISIBLE);
                ////Log.d("지문 뭐지", holder.textTextView.getText().toString());
            }


            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            String topik_level = problemSet.getTopik_level();
            StorageReference pathReference = storageReference.child("topik").child("topik" + topik_level).child("이미지");
            StorageReference pathReference2 = storageReference.child("topik").child("topik" + topik_level).child("듣기");

            //1. 지문
            if (problemSet.getText().equals("image")) {
                holder.textTextView.setVisibility(GONE);
                holder.textImage.setVisibility(VISIBLE);
                String str_image = problemSet.getImage();

                if (pathReference == null) {
                    ////Log.d("사진없음", "사진이 없습니다.");
                } else {
                    ////Log.d("이미지 이름", str_image);
                    StorageReference submitProfile_image = storageReference.child("topik/topik" + topik_level + "/이미지/" + str_image + ".PNG");
                    submitProfile_image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(parent.getContext()).load(uri).into(holder.textImage);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }

            if (problemSet.getText().equals("")) {
                holder.textTextView.setVisibility(GONE);
            }

            //2. 선지

            if (!problemSet.getChoice1().equals("image")) {
                holder.choice1Radio.setText(problemSet.getChoice1());
                holder.choice2Radio.setText(problemSet.getChoice2());
                holder.choice3Radio.setText(problemSet.getChoice3());
                holder.choice4Radio.setText(problemSet.getChoice4());
            } else {
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
                String[] array = str.split(",");

                if (pathReference == null) {
                   // //Log.d("사진없음", "사진이 없습니다.");
                } else {
                    StorageReference submitProfile = storageReference.child("topik/topik" + topik_level + "/이미지/" + array[0] + ".PNG");
                    submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(parent.getContext()).load(uri).into(holder.choiceImage1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                    ////Log.d("사진1", array[0]);

                    StorageReference submitProfile2 = storageReference.child("topik/topik" + topik_level + "/이미지/" + array[1] + ".PNG");
                    submitProfile2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(parent.getContext()).load(uri).into(holder.choiceImage2);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                    StorageReference submitProfile3 = storageReference.child("topik/topik" + topik_level + "/이미지/" + array[2] + ".PNG");
                    submitProfile3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(parent.getContext()).load(uri).into(holder.choiceImage3);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                    StorageReference submitProfile4 = storageReference.child("topik/topik" + topik_level + "/이미지/" + array[3] + ".PNG");
                    submitProfile4.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(parent.getContext()).load(uri).into(holder.choiceImage4);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

            }

            //듣기 구현
            class MyThread extends Thread {
                @Override
                public void run() { // 쓰레드가 시작되면 콜백되는 메서드
                    // 씨크바 막대기 조금씩 움직이기 (노래 끝날 때까지 반복)
                    int present_num = 0;
                    if (!holder.solutionText.getText().toString().equals("NA")) {
                        present_num = 1;
                    }else{
                        present_num = Integer.parseInt(holder.arranged_num.getText().toString());
                    }

                    if(played_num != 0) {
                        ////Log.d("num", String.valueOf(num - 1));
                        ////Log.d("null///////", String.valueOf(mp.getCurrentPosition()));
                        ////Log.d("isPlaying", String.valueOf(lAdapter.getIsPlaying(num - 1)));
                        while (lAdapter.getIsPlaying(played_num-1)) {
                            if(played_num == present_num) {
                                holder.sb.setProgress(mp.getCurrentPosition());
                            }
                        }
                    }
                }
            }

            if (!problemSet.getMp3().equals("NA")) {

                holder.bPlay.setVisibility(VISIBLE);
                holder.bPause.setVisibility(VISIBLE);
                holder.bRestart.setVisibility(VISIBLE);
                holder.sb.setVisibility(VISIBLE);
                holder.sb = convertView.findViewById(R.id.seekBar);

                holder.textTextView.setVisibility(GONE);//듣기 문제 스크립트

                holder.sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        // 사용자가 움직여놓은 위치
                        int compare_num = 0;
                        if (!holder.solutionText.getText().toString().equals("NA")) {
                            compare_num = 1;
                        }else{
                            compare_num = Integer.parseInt(holder.arranged_num.getText().toString());
                        }

                        int compare_index = compare_num - 1;
                        if (lAdapter.getFlag_first(compare_index) != true) {
                            lAdapter.setIsPlaying(compare_index, true);
//                            //Log.d("isPLaying", String.valueOf(lAdapter.getIsPlaying(compare_index)));
                            mp.setLooping(false); // true:무한반복
                            //lAdapter.setPosition(compare_num-1,seekBar.getProgress());
                            //seekBar.setProgress(lAdapter.getPosition(compare_num-1));
//                            //Log.d("postion", String.valueOf(lAdapter.getPosition(compare_index)));

                            mp.seekTo(seekBar.getProgress());// 사용자가 움직여놓은 위치
                            ////Log.d("됩니까?","됩니까/");

                            mp.start();
                            new MyThread().start();
                        } else {

                            lAdapter.setPosition(compare_index, seekBar.getProgress());
                        }
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) {
                        int compare_num = 0;
                        if (!holder.solutionText.getText().toString().equals("NA")) {
                            compare_num = 1;
                        }else{
                            compare_num = Integer.parseInt(holder.arranged_num.getText().toString());
                        }

                        int compare_index = compare_num - 1;
                        if(played_num != compare_num && played_num != 0){
                            lAdapter.setIsPlaying(played_num - 1, false); //멈추고
                            mp.pause();
                            lAdapter.setPosition(played_num - 1, mp.getCurrentPosition()); // 위치 기억
                            lAdapter.setFlag_restart(played_num - 1, true); // 다시 시작
                            played_num = 0;
                        }
                        if (lAdapter.getFlag_first(compare_index) != true) {
//                            //Log.d("compare_num", String.valueOf(compare_num));
                            String url = lAdapter.getUrl(compare_index);

                            lAdapter.setIsPlaying(compare_index, false);
//                            //Log.d("isPLaying", String.valueOf(lAdapter.getIsPlaying(compare_index)));

                            mp.pause();
                        } else {
                            String url = lAdapter.getUrl(compare_index);
                            mp = new MediaPlayer();

                            //데이터 집어 넣기
                            try {
                                mp.setDataSource(url);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                mp.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            int a = mp.getDuration(); // 노래의 재생시간(miliSecond)
                            holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정
                        }
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

                if (pathReference2 == null) {
                    ////Log.d("듣기 파일없음", "듣기 파일이 없습니다.");
                } else {
                    ////Log.d("듣기 파일", problemSet.getMp3());
                    StorageReference submitProfile5 = storageReference.child("topik/topik" + topik_level + "/듣기/" + problemSet.getMp3() + ".mp3");
                    submitProfile5.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            String arranged_num = "0";
                            if (!holder.solutionText.getText().toString().equals("NA")) {
                                arranged_num = "1";
                            }else{
                                arranged_num = holder.arranged_num.getText().toString();
                            }

                            int data_size = lAdapter.getCount();
                            Boolean same_flag = false;
                            ////Log.d("번호: ", (String) holder.arranged_num.getText());
                            Integer int_arranged_num = Integer.parseInt(arranged_num) - 1;
                            if (data_size != 0) {
                                // 안 비어 있을 경우
                                for (int i = 0; i < lAdapter.getCount(); i++) {
                                    if (lAdapter.getItem(i).getProb_num() == arranged_num) {
                                        same_flag = true;
                                        if (lAdapter.getItem(i).getStr_mp3() != problemSet.getMp3()) {
                                            lAdapter.getItem(i).setStr_mp3(problemSet.getMp3());
                                            lAdapter.getItem(i).setUrl(url);
                                        }
                                    }
                                }
                                if (same_flag == false) {
                                    lAdapter.addItem(new ListeningOptions(0, arranged_num, url, problemSet.getMp3(), true,
                                            false, true));
                                }
                            } else {
                                lAdapter.addItem(new ListeningOptions(0, arranged_num, url, problemSet.getMp3(), true,
                                        false, true));
                            }
                            if (lAdapter.getCount() > int_arranged_num) {
                                holder.sb.setProgress(lAdapter.getPosition(int_arranged_num));
                            }
                            //정렬
                            if (!lAdapter.isEmpty()) {
                                listeningList = lAdapter.returnList();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    listeningList.sort(Comparator.<ListeningOptions>naturalOrder());
                                    lAdapter.setList(listeningList);
                                }
                            }
//                            if(count_load<10){
//                                count_load++;
//                            }
                            ////Log.d("##루프 끝", "##");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        }
                    });
                }
            }else {
                boolean after_loop = false;
                boolean same = false;
                if (!lAdapter.isEmpty()) {
                    for (int i = 0; i < lAdapter.getCount(); i++) {
                        ////Log.d("lAdpater item", lAdapter.getItem(i).getProb_num());
                        ////Log.d("arrangedNumList", arrangedNumList.toString());
                        ////Log.d("arrangedNumList item", arrangedNumList.get_num(Integer.parseInt((String) holder.arranged_num.getText()) - 1));
                        if (lAdapter.getItem(i).getProb_num() == arrangedNumList.get_num(Integer.parseInt((String) holder.arranged_num.getText()) - 1)) {
                            same = true;
                        }
                        ////Log.d("same", String.valueOf(same));
                        after_loop = true;
                    }
                } else {
                    after_loop = true;
                }
                if (after_loop) {
                    ////Log.d("same2", String.valueOf(same));
                    if (!same) {
                        if (arrangedNumList != null) {
                            String num = arrangedNumList.get_num(position);
                            lAdapter.addItem(new ListeningOptions(num));
                            ////Log.d("읽기 문제", "빈 값 넣음");
                           ////Log.d("LA count", String.valueOf(lAdapter.getCount()));
                            holder.textTextView.setVisibility(VISIBLE);
                        }
                    } else {
//                        //Log.d("읽기 문제", "있어서 안 넣음");
                    }
                } else {
//                    //Log.d("이상해 이상해", "참 이상해");
                }
                after_loop = false;
                same = false;
            }

            holder.choice1Radio.setChecked(mData.get(position).checked1);
            holder.choice2Radio.setChecked(mData.get(position).checked2);
            holder.choice3Radio.setChecked(mData.get(position).checked3);
            holder.choice4Radio.setChecked(mData.get(position).checked4);

            holder.choice1Radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean newState1 = !mData.get(position).isChecked1();
                    problem_id = mData.get(position).getProblem_id();
                    section = mData.get(position).getSection();
                    mData.get(position).checked1 = newState1;
                    mData.get(position).checked2 = false;
                    mData.get(position).checked3 = false;
                    mData.get(position).checked4 = false;

                    holder.choice2Radio.setChecked(false);
                    holder.choice3Radio.setChecked(false);
                    holder.choice4Radio.setChecked(false);

                    ChangeAnswer("1");
                    end = System.currentTimeMillis();
                    count_time = (end - start) / 1000;
                    timeList = (ArrayList<Time>) tAdapter.returnList();
                    timeList.add(new Time(Integer.parseInt(mData.get(position).getArranged_num()), (int) count_time, (int) change));
                    ////Log.d("시간2", "count time은 " + String.valueOf(count_time));
                    start = System.currentTimeMillis();


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
                    uAdapter.addItem(new UserSet(problem_id, String.valueOf(arrangedNumber), String.valueOf(problemNumber), String.valueOf(userNumber),
                            holder.problemRealAnswer.getText().toString(), String.valueOf(problemSet.getProb_set()), String.valueOf(problemSet.getScore()),
                            0, change, section));
                }
            });

            holder.choice2Radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean newState2 = !mData.get(position).isChecked2();
                    problem_id = mData.get(position).getProblem_id();
                    section = mData.get(position).getSection();

                    mData.get(position).checked1 = false;
                    mData.get(position).checked2 = newState2;
                    mData.get(position).checked3 = false;
                    mData.get(position).checked4 = false;

                    holder.choice1Radio.setChecked(false);
                    holder.choice3Radio.setChecked(false);
                    holder.choice4Radio.setChecked(false);
                    ChangeAnswer("2");
                    end = System.currentTimeMillis();
                    count_time = (end - start) / 1000;
                    timeList = (ArrayList<Time>) tAdapter.returnList();
                    timeList.add(new Time(Integer.parseInt(mData.get(position).getArranged_num()), (int) count_time, (int) change));
                    ////Log.d("시간2", "count time은 " + String.valueOf(count_time));
                    start = System.currentTimeMillis();

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
                    uAdapter.addItem(new UserSet(problem_id, String.valueOf(arrangedNumber), String.valueOf(problemNumber), String.valueOf(userNumber),
                            holder.problemRealAnswer.getText().toString(), String.valueOf(problemSet.getProb_set()), String.valueOf(problemSet.getScore()),
                            0, change, section));
                }
            });

            holder.choice3Radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean newState3 = !mData.get(position).isChecked3();
                    problem_id = mData.get(position).getProblem_id();
                    section = mData.get(position).getSection();

                    mData.get(position).checked1 = false;
                    mData.get(position).checked2 = false;
                    mData.get(position).checked3 = newState3;
                    mData.get(position).checked4 = false;

                    holder.choice1Radio.setChecked(false);
                    holder.choice2Radio.setChecked(false);
                    holder.choice4Radio.setChecked(false);
                    ChangeAnswer("3");
                    end = System.currentTimeMillis();
                    count_time = (end - start) / 1000;
                    timeList = (ArrayList<Time>) tAdapter.returnList();
                    timeList.add(new Time(Integer.parseInt(mData.get(position).getArranged_num()), (int) count_time, (int) change));
                    //Log.d("시간2", "count time은 " + String.valueOf(count_time));
                    start = System.currentTimeMillis();

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
                    uAdapter.addItem(new UserSet(problem_id, String.valueOf(arrangedNumber), String.valueOf(problemNumber), String.valueOf(userNumber),
                            holder.problemRealAnswer.getText().toString(), String.valueOf(problemSet.getProb_set()), String.valueOf(problemSet.getScore()),
                            0, change, section));
                }

            });

            holder.choice4Radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean newState4 = !mData.get(position).isChecked4();
                    problem_id = mData.get(position).getProblem_id();
                    section = mData.get(position).getSection();

                    mData.get(position).checked1 = false;
                    mData.get(position).checked2 = false;
                    mData.get(position).checked3 = false;
                    mData.get(position).checked4 = newState4;

                    holder.choice1Radio.setChecked(false);
                    holder.choice2Radio.setChecked(false);
                    holder.choice3Radio.setChecked(false);
                    ChangeAnswer("4");
                    end = System.currentTimeMillis();
                    count_time = (end - start) / 1000;
                    timeList = (ArrayList<Time>) tAdapter.returnList();
                    timeList.add(new Time(Integer.parseInt(mData.get(position).getArranged_num()), (int) count_time, change));
                    //Log.d("시간2", "count time은 " + String.valueOf(count_time));
                    start = System.currentTimeMillis();

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
                    uAdapter.addItem(new UserSet(problem_id, String.valueOf(arrangedNumber), String.valueOf(problemNumber), String.valueOf(userNumber),
                            holder.problemRealAnswer.getText().toString(), String.valueOf(problemSet.getProb_set()), String.valueOf(problemSet.getScore()),
                            0, change, section));

                }
            });

            //데이터 설정
            holder.number.setText(problemSet.getProb_num());
            holder.arranged_num.setText(problemSet.getArranged_num());

            holder.common_question.setText(Html.fromHtml(problemSet.getQuestion()));
            holder.probSet.setText(problemSet.getProb_set());


            holder.plural_question.setText(Html.fromHtml(problemSet.getPlural_question()));

            holder.problemTextView.setText(Html.fromHtml(problemSet.getQuestion_example()));

            if (!holder.common_question.getText().equals("NA")) {
                holder.common_question.setVisibility(VISIBLE);
            } else {
                holder.common_question.setVisibility(GONE);
            }

            if (!holder.plural_question.getText().equals("NA")) {
                holder.plural_question.setVisibility(VISIBLE);
            } else {
                holder.plural_question.setVisibility(GONE);
            }
            //Log.d("문제 번호:",holder.arranged_num.getText().toString());

            //Log.d("보기가 왜 안 나오지?",holder.problemTextView.getText().toString());
            if (holder.problemTextView.getText().toString().length() != 0 && !holder.problemTextView.getText().equals("NA")) {
                holder.problemTextView.setVisibility(VISIBLE);
                //Log.d("왜 또 실행하니?","Eh?");
                holder.exampleText.setVisibility(VISIBLE);
            } else {
                //Log.d("실행 안 됨?","Eh?");
                holder.exampleText.setVisibility(GONE);
                holder.problemTextView.setVisibility(GONE);
            }

            holder.score.setText(problemSet.getScore());


            holder.solutionText.setText(Html.fromHtml(problemSet.getSolution()));


            //Log.d("해설은", holder.solutionText.getText().toString());
            if (!holder.solutionText.getText().toString().equals("NA")) {
                //Log.d("해설 결과는", "NA가 아닙니다.");
                holder.realText.setVisibility(VISIBLE);
                holder.problemRealAnswer.setText(problemSet.getAnswer());
                holder.problemRealAnswer.setVisibility(VISIBLE);
                holder.textTextView.setVisibility(VISIBLE);//듣기 문제 스크립트 보이기
                holder.solution_check.setVisibility(VISIBLE);
                holder.solution_check.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (holder.solution_check.isChecked()) {
                            holder.solutionText.setVisibility(VISIBLE);
                        } else {
                            holder.solutionText.setVisibility(GONE);
                        }
                    }
                });
            } else {
                holder.solution_check.setVisibility(GONE);
                holder.solutionText.setVisibility(GONE);
            }

            holder.problemRealAnswer.setText(problemSet.getAnswer());
            Log.d("real_Anwer",holder.problemRealAnswer.getText().toString());
            holder.problemUserAnswer.setText(problemSet.getUser_answer());

            String user_answer = problemSet.getUser_answer();
            String real_answer = problemSet.getAnswer();

            if (user_answer != null) {
                //O, X그리기
                O_drawing O_drawing = new O_drawing(convertView.getContext());
                X_drawing X_drawing = new X_drawing(convertView.getContext());
                if (Integer.parseInt(user_answer) == Integer.parseInt(real_answer)) {
                    ////Log.d("O","O");
                    holder.frameDraw.addView(O_drawing);
                } else {
                    ////Log.d("X","X");
                    holder.frameDraw.addView(X_drawing);
                }

                //solution 용
                if (user_answer.equals("1")) {
                    holder.choice1Radio.setChecked(TRUE);
                    holder.choice2Radio.setChecked(FALSE);
                    holder.choice3Radio.setChecked(FALSE);
                    holder.choice4Radio.setChecked(FALSE);
                } else if (user_answer.equals("2")) {
                    holder.choice1Radio.setChecked(FALSE);
                    holder.choice2Radio.setChecked(TRUE);
                    holder.choice3Radio.setChecked(FALSE);
                    holder.choice4Radio.setChecked(FALSE);
                } else if (user_answer.equals("3")) {
                    holder.choice1Radio.setChecked(FALSE);
                    holder.choice2Radio.setChecked(FALSE);
                    holder.choice3Radio.setChecked(TRUE);
                    holder.choice4Radio.setChecked(FALSE);
                } else {
                    holder.choice1Radio.setChecked(FALSE);
                    holder.choice2Radio.setChecked(FALSE);
                    holder.choice3Radio.setChecked(FALSE);
                    holder.choice4Radio.setChecked(TRUE);
                }
            }
            int prob_index = Integer.parseInt((String) holder.arranged_num.getText())-1;
            //듣기 수정
            holder.bPlay.setVisibility(holder.bPlay.getVisibility());
            holder.bPause.setVisibility(holder.bPause.getVisibility());
            holder.bRestart.setVisibility(holder.bRestart.getVisibility());
            holder.bStop.setVisibility(holder.bStop.getVisibility());

            if(played_num !=0) {
                holder.sb.setProgress(lAdapter.getPosition(played_num - 1));
            }

            holder.bPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.d("시작 버튼 시작", "시작");
                    int prob_num = 0;
                    if (!holder.solutionText.getText().toString().equals("NA")) {
                        prob_num = 1;
                    }else{
                        prob_num = Integer.parseInt(holder.arranged_num.getText().toString());
                    }
                    int prob_index = prob_num - 1;
                    //Log.d("시작 prob_num ", String.valueOf(prob_num - 1));
                    //Log.d("문제 번호", String.valueOf(prob_num));
                    //아직 로드가 안 된 상태에서 시작했을 떄, 그냥 가비지 값 넣어주고 시작하기.

                    //Log.d("lAdpater", lAdapter.getProb_num(prob_index));
                    //Log.d("lAdpater", lAdapter.getStr_mp3(prob_index));
                    String url = lAdapter.getUrl(prob_index);
                    //Log.d("url 번호", (String) url);

                    //Log.d("last played num", String.valueOf(played_num));
                    if (played_num == 0) {
                    } else {
                        if (played_num != prob_num) {
                            //Log.d("check1", "1");
                            lAdapter.setIsPlaying(played_num - 1, false); //멈추고
                            //Log.d("isPlaying,played)num",String.valueOf(lAdapter.getIsPlaying(played_num - 1)));
                            mp.pause();
                            //Log.d("check1", "2");
                            lAdapter.setPosition(played_num - 1, mp.getCurrentPosition()); // 위치 기억
                            //Log.d("played_position",String.valueOf(lAdapter.getPosition(played_num-1)));
                            //Log.d("check1", "3");
                            lAdapter.setFlag_restart(played_num - 1, true); // 다시 시작
                            //Log.d("check1", "4");
                            played_num = Integer.parseInt((String) holder.arranged_num.getText());
                            //Log.d("check1", "5");
                            //mp에 url, sb 설정
//                        mp.release();
//                        mp = null;
                            mp = new MediaPlayer();
                            //Log.d("check1", "6");
                            try {
                                mp.setDataSource(url);
                                //Log.d("check1", "7");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Log.d("포지션 가져오기",String.valueOf(lAdapter.getPosition(prob_index)));

                            mp.seekTo(lAdapter.getPosition(prob_index));
                            //Log.d("check1", "8");
                            holder.sb.setMax(mp.getDuration());
                            //Log.d("check1", "9");
                            holder.sb.setProgress(lAdapter.getPosition(prob_index));
                        }
                    }
                    //Log.d("check1", "10");

                    ////Log.d("문제 url",  lAdapter.getItem(compare_num-1).getUrl());
                    //Log.d("문제 str_mp3",  lAdapter.getItem(prob_index).getStr_mp3());
                    // //Log.d("문제 isPlaying",  String.valueOf(lAdapter.getIsPlaying(compare_num-1)));

                    if (!lAdapter.getIsPlaying(prob_index) || lAdapter.getFlag_first(prob_index)) {
                        lAdapter.setIsPlaying(prob_index, true);
                        //Log.d("check1", "11");

                        ////Log.d("문제 isPlaying", String.valueOf(lAdapter.getIsPlaying(compare_num-1)));
                        if (lAdapter.getFlag_first(prob_index) != true) {
                            ////Log.d("flag_first", "false");
                            if (mp.getDuration() == holder.sb.getMax()) {
                                //Log.d("check1", "12");
                                mp.seekTo(0);
                                //Log.d("check1", "13");
                                holder.sb.setProgress(0);
                            }
                        }

                        if (lAdapter.getFlag_restart(prob_index) != true) {
                            //첫 시작일 때
                            //Log.d("check1", "14");
                            lAdapter.setFlag_first(prob_index, false);
                            // //Log.d("flag_first false 바뀌었나?",  String.valueOf(lAdapter.getFlag_first(compare_num-1)));
                            //첫 실행이면 플레이어 새로 생성
                            //Log.d("check1", "15");
                            mp = new MediaPlayer();

                            //데이터 집어 넣기
                            try {
                                //Log.d("check1", "16");

                                mp.setDataSource(url);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                //Log.d("check1", "17");
                                mp.prepare();
                            } catch (IOException e) {
                            }
                            //Log.d("check1", "18");
                            int a = mp.getDuration(); // 노래의 재생시간(miliSecond)
                            //Log.d("check1", "19");
                            holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정

                        } else {
                            //Log.d("check1", "20");
                            lAdapter.setFlag_restart(prob_index, false);
                            //Log.d("check1", "21");
                            //lAdapter.getItem(compare_num-1).setFlag_restart(false);
                            //Log.d("restart false바뀌었나?", String.valueOf(lAdapter.getFlag_restart(prob_index)));

                        }
                        //포지션 넣기
                        if (lAdapter.getPosition(prob_index) != 0) {
                            //Log.d("check1", "22");
                            mp.seekTo(lAdapter.getPosition(prob_index));
                            //Log.d("check1", "23");
                            //Log.d("position!!", String.valueOf(lAdapter.getPosition(prob_index)));
                        }
                        //Log.d("check1", "24");
                        lAdapter.setIsPlaying(prob_index, true);

                        mp.setLooping(false); // true:무한반복
                        //Log.d("check1", "25");
                        mp.start();
                        //Log.d("check1", "26");
                        new MyThread().start(); // 씨크바 그려줄 쓰레드 시작

//                    holder.bPlay.setVisibility(GONE);
//                    holder.bPause.setVisibility(VISIBLE);
                    }
                    played_num = prob_num;
                }
            });

            holder.bPause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int prob_num = 0;
                    if (!holder.solutionText.getText().toString().equals("NA")) {
                        prob_num = 1;
                    }else{
                        prob_num = Integer.parseInt(holder.arranged_num.getText().toString());
                    }
                    int prob_index = prob_num - 1;

                    if (played_num == 0) {
                    } else {
                        if (played_num == prob_num) {
                            ////Log.d("문제 번호", (String) arrangedNumList.get_num(position));
                            //Log.d("pause isPlaying", String.valueOf(lAdapter.getItem(prob_index).getIsPlaying()));
                            if (lAdapter.getIsPlaying(prob_index)) {
                                lAdapter.setIsPlaying(played_num - 1, false);
                                // //Log.d("pause isPlaying:",  String.valueOf(lAdapter.getItem(compare_num-1).getIsPlaying()));
                                if (mp != null) {
                                    lAdapter.setPosition(prob_index, mp.getCurrentPosition());
                                    mp.pause();
                                }
                                lAdapter.setIsPlaying(prob_index, false);// 쓰레드 정지
                                lAdapter.setFlag_restart(prob_index, true);
//                    holder.bPause.setVisibility(GONE);
//                    holder.bPlay.setVisibility(VISIBLE);
                            }
                        }
                    }
                }
            });

            holder.bRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int prob_num = 0;
                    if (!holder.solutionText.getText().toString().equals("NA")) {
                        prob_num = 1;
                    }else{
                        prob_num = Integer.parseInt(holder.arranged_num.getText().toString());
                    }
                    int prob_index = prob_num - 1;
                    //Log.d("문제 번호", holder.arranged_num.getText().toString());

                    if (mp == null) {
                        //Log.d("mp가 없습니다", "없음!");
                        //mp 생성하기
                        //Log.d("mp 생성 first_flag:", String.valueOf(lAdapter.getFlag_first(prob_index)));
                        mp = new MediaPlayer();
                        lAdapter.setFlag_first(prob_index, false);

                        //Log.d("first_flag:", String.valueOf(lAdapter.getFlag_first(prob_index)));

                        //데이터 넣기
                        try {
                            String url = lAdapter.getUrl(prob_index);
                            //Log.d("url", url);
                            mp.setDataSource(url);
                            //Log.d("데이터 넣기 완료", "!");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //준비하기
                        try {
                            mp.prepare();
                            //Log.d("mp 준비 완료", "!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        int a = mp.getDuration(); // 노래의 재생시간(miliSecond)
                        //Log.d("mp duration 완료", String.valueOf(a));
                        holder.sb.setMax(a);// 씨크바의 최대 범위를 노래의 재생시간으로 설정
                        //Log.d("sb max치 완료", String.valueOf(a));
                    }
                    //Log.d("mp가 null 아닐 때 실행되는 것 ㅎㅎ", "!");
                    holder.sb.setProgress(0); // 씨크바 초기화
                    mp.setLooping(false);
                    mp.seekTo(0);
                    //Log.d("sb 초기화 완료", "!");
                    mp.start();
                    //Log.d("시작 완료", "!");
                    lAdapter.setIsPlaying(prob_index, true); // 씨크바 쓰레드 반복 하도록
                    lAdapter.setFlag_restart(prob_index, true);
                    new MyThread().start(); // 씨크바 그려줄 쓰레드 시작
                    //Log.d("끝 isPlaying:", String.valueOf(lAdapter.getIsPlaying(prob_index)));

//                holder.bPlay.setVisibility(GONE);
//                holder.bPause.setVisibility(VISIBLE);
                }
            });

            holder.bStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //stopAudio();
                }
            });
        }

//        if(count_load>=10) {
//            int present_num = Integer.parseInt(holder.arranged_num.getText().toString());
//            //Log.d("presnet num", holder.arranged_num.getText().toString());
//            if (played_num != 0) {
//                //Log.d("num", String.valueOf(played_num - 1));
//                //Log.d("null///////", String.valueOf(mp.getCurrentPosition()));
//                //Log.d("isPlaying", String.valueOf(lAdapter.getIsPlaying(played_num - 1)));
//                while (lAdapter.getIsPlaying(played_num - 1)) {
//                    if (played_num != present_num) {
//                        holder.sb.setProgress(lAdapter.getPosition(present_num - 1));
//                    }
//                }
//            }
//        }
        return convertView;
    }

    public UserAdapter return_uAdapter(){
        final UserAdapter uAdapter = this.uAdapter;
        return uAdapter;
    }

//    private void playAudio() {
//        try {
//            closePlayer();
//            player = new MediaPlayer();
//            player.setDataSource(String.valueOf(url));
//            player.prepare();
//            player.start();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 현재 일시정지가 되었는지 중지가 되었는지 헷갈릴 수 있기 때문에 스위치 변수를 선언해 구분할 필요가 있다. (구현은 안했다.)
//    private void pauseAudio() {
//        if (player != null) {
//            position = player.getCurrentPosition();
//            player.pause();
//
//        }
//    }
//
//    private void resumeAudio() {
//        if (player != null && !player.isPlaying()) {
//            player.seekTo(position);
//            player.start();
//
//        }
//    }
//
//    private void stopAudio() {
//        if(player != null && player.isPlaying()){
//            player.stop();
//
//        }
//    }
//
//    /* 녹음 시 마이크 리소스 제한. 누군가가 lock 걸어놓으면 다른 앱에서 사용할 수 없음.
//     * 따라서 꼭 리소스를 해제해주어야함. */
//    public void closePlayer() {
//        if (player != null) {
//            player.release();
//            player = null;
//        }
//    }

}