package com.example.bittu.braintrainer;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {
    CountDownTimer timer;
    Button startButton, button0, button1, button2, button3, playAgain;
    RelativeLayout gameScreen;
    int numberOfQuestions = 0;
    int score = 0;
    int answerIndex;
    TextView questionTextView, resultTextView, scoreTextView, timerTextView;
    Boolean gameIsActive = false;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    public void generateQuestion(){
        if(gameIsActive) {
            Random rand = new Random();
            int a = rand.nextInt(21);
            int b = rand.nextInt(21);
            answerIndex = rand.nextInt(4);
            answers.clear();

            for (int i = 0; i < 4; i++) {
                if (i == answerIndex)
                    answers.add(a + b);
                else {
                    int random = rand.nextInt(41);
                    while(answers.contains(random))
                        random = rand.nextInt(41);
                    answers.add(random);

                }

            }
            questionTextView.setText(String.valueOf(a) + " + " + String.valueOf(b) + " ?");
            button0.setText(String.valueOf(answers.get(0)));
            button1.setText(String.valueOf(answers.get(1)));
            button2.setText(String.valueOf(answers.get(2)));
            button3.setText(String.valueOf(answers.get(3)));
            numberOfQuestions++;
        }
    }

    public void playAgain(View view){
        gameScreen.setEnabled(true);
        playAgain.setVisibility(View.INVISIBLE);
        resultTextView.setText("");
        gameIsActive = true;
        generateQuestion();
        scoreTextView.setText("0/0");
        timer();
    }

    public void chooseAnswer(View view){
        if(gameIsActive) {
            if (view.getTag().toString().equals(String.valueOf(answerIndex))) {
                score++;
                resultTextView.setText("Correct!");
            } else {
                resultTextView.setText("Wrong!");
            }
            scoreTextView.setText(String.valueOf(score) + "/" + String.valueOf(numberOfQuestions));

            generateQuestion();
        }
    }

    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameScreen.setVisibility(View.VISIBLE);

        gameIsActive = true;
        generateQuestion();
        timer();
    }

    public void timer(){
        timer = new CountDownTimer(30100, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                String secondString = String.valueOf(millisUntilFinished / 1000);
                if(Integer.parseInt(secondString) <= 9)
                    secondString = "0" + secondString;
                timerTextView.setText("0:" + secondString);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("0:00");
                resultTextView.setText("Your score is: " + String.valueOf(score) + "/" + String.valueOf(numberOfQuestions));
                gameScreen.setEnabled(false);
                playAgain.setVisibility(View.VISIBLE);
                score = 0;
                numberOfQuestions = 0;
                timer.cancel();
                gameIsActive = false;
            }
        }.start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.startButton);
        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playAgain = (Button) findViewById(R.id.playAgain);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        gameScreen = (RelativeLayout)findViewById(R.id.gameScreen);



    }
}
