package com.example.mygameapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore,playerTwoScore,playerStatus;
    private Button[] buttons=new Button[9];
    private Button resetGame;

    private int playerOneScoreCount,playerTwoScoreCount,roundCount;
    boolean activePlayer;

    //player one=>0
    //player two => 1
    //empty =>2

    int [] gameState={2,2,2,2,2,2,2,2,2};

    int [][] winningPositions={
            {0,1,2},{3,4,5},{6,7,8}, //Row
            {0,3,6},{1,4,7},{2,5,8}, //column
            {0,4,8},{2,4,6}//cross
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore= (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore= (TextView) findViewById(R.id.playerTwoScore);
        playerStatus= (TextView) findViewById(R.id.playerStatus);
        resetGame= (Button) findViewById(R.id.resetGame);

        for(int i=0;i< buttons.length;i++){
            String buttonID = "btn_" + i;
            Log.i("start",buttonID);
            int resourceID=getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i]=(Button)findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }

    @Override
    public void onClick(View view) {
       if(!((Button)view).getText().toString().equals("")){

            return;

        }

        String buttonID=view.getResources().getResourceEntryName(view.getId()); //btn_2

        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1,buttonID.length())); //2


        if(activePlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }else{
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#07C4F4"));
            gameState[gameStatePointer] = 1;
        }
        roundCount ++;

    if(checkWinner()){
        if(activePlayer){
            playerOneScoreCount++;
            updatePlayerScore();
            Toast.makeText(this,"Player one won!", Toast.LENGTH_SHORT).show();
            playAgain();

        }else {
            playerTwoScoreCount++;
            updatePlayerScore();
            Toast.makeText(this,"Player Two won!", Toast.LENGTH_SHORT).show();
            playAgain();
        }


    }else if (roundCount == 9){
        playAgain();
        Toast.makeText(this,"No winner!", Toast.LENGTH_SHORT).show();

    }else{
    activePlayer = !activePlayer;
    }
        if(playerOneScoreCount > playerTwoScoreCount){
            Log.i("PlayerOneScoreCount", String.valueOf(playerOneScoreCount));
            playerStatus.setText("The player one is winner!");

        }else if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("The player Two is winner!");

        }else if((playerTwoScoreCount == playerOneScoreCount) &&(playerTwoScoreCount!=0)&&(playerOneScoreCount!=0)){
            playerStatus.setText("Same Score Try again!");
        }

        else{
            playerStatus.setText("");
        }

   resetGame.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            playAgain();
            activePlayer=true;
            playerOneScoreCount = 0;
            playerTwoScoreCount = 0;
            playerStatus.setText(" ");
            updatePlayerScore();
        }
    });
        }


        public boolean checkWinner(){
        boolean winnerResult=false;
        for(int[] winnerPosition:winningPositions){
            if(gameState[winnerPosition[0]] == gameState[winnerPosition[1]] &&
            gameState[winnerPosition[1]] == gameState[winnerPosition[2]] &&
            gameState[winnerPosition[0]] !=2){
                winnerResult = true;

            }

        }
        return winnerResult;
        }

    public void updatePlayerScore(){


        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));

    }

    public void playAgain(){
        roundCount=0;
               for(int i=0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");


        }
    }
    }
