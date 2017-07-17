package com.example.nhl71.myfirstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,Toolbar.OnMenuItemClickListener {
    GridLayout buttonPanel;
    CheckBox markFlag;
    TextView inform;
    Button reload,option;
    RelativeLayout lost,win;
    Toolbar toolBar;

    List<Integer> listIndexMine;// Position of buttons where booms are
    int SIZE = 10;

    boolean firstClick;
    int buttomNotOpen; // buttons not open it will decrease when
    int numBoom = 20; // number of booms in game
    int numFlag; // number of Flags equal to number of booms
    Button [][] map ;// save and process button

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectView();// connect views from XML
        markFlag.setOnClickListener(this);
        setSupportActionBar(toolBar);
        toolBar.setOnMenuItemClickListener(this);
        lost.setOnClickListener(this);
        win.setOnClickListener(this);
        loadGame();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void loadGame(){
        buttomNotOpen = SIZE*SIZE;
        numFlag = numBoom;
        buttonPanel.removeAllViews();
        buttonPanel.setRowCount(SIZE);
        buttonPanel.setColumnCount(SIZE);

        for (int i = 0 ; i < SIZE*SIZE ; i++){
            buttonPanel.addView(createButton(i),i);

        }
        map = new Button[SIZE][SIZE];
        createMap();
        changeTextInform();
        firstClick = false;
        lost.setVisibility(View.INVISIBLE);
        win.setVisibility(View.INVISIBLE);
    }
    void connectView(){
        markFlag = (CheckBox) findViewById(R.id.markFlag);
        buttonPanel = (GridLayout) findViewById(R.id.buttonPanel);
        inform = (TextView) findViewById(R.id.inform);
        lost = (RelativeLayout) findViewById(R.id.lost);
        win = (RelativeLayout) findViewById(R.id.win);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    // loading Game
    Button createButton(int index){
        final Button button = new Button(this);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(
                GridLayout.spec((index/SIZE), (float) 1.0),
                GridLayout.spec((index%SIZE), (float) 1.0));
        layoutParams.width = 0;
        layoutParams.height =0;
        layoutParams.setMargins(1,1,1,1);
        button.setTextSize(17);
        button.setPadding(1,1,1,1);

        button.setLayoutParams(layoutParams);
        button.setBackground(getResources().getDrawable(R.drawable.background_btn));
        button.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (button.getText().toString()){
                    case (1+""):
                        button.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case (2+""):
                        button.setTextColor(getResources().getColor(R.color.orange));
                        break;
                    case (3+""):
                        button.setTextColor(getResources().getColor(R.color.green));
                        break;
                    case (4+""):
                        button.setTextColor(getResources().getColor(R.color.cyan));
                        break;
                    case (5+""):
                        button.setTextColor(getResources().getColor(R.color.purple));
                        break;
                    default:
                        button.setTextColor(getResources().getColor(R.color.red));
                        break;

                }
            }
        }); // change color text


        button.setOnClickListener(this);
        return button;
    }
    void createMap(){
        for(int i = 0;i<(SIZE*SIZE);i++){
            map[(i/SIZE)][(i%SIZE)]= (Button) buttonPanel.getChildAt(i);
            map[(i/SIZE)][(i%SIZE)].setTag(Mine.getMine(i/SIZE,i%SIZE));

        }
    }
    void createBoom(int num,int x,int y){
        int indexFirstClick = x*SIZE+y;
        listIndexMine = new ArrayList<Integer>();
        listIndexMine.add(indexFirstClick);
        Random random = new Random();
        for(int i = 0 ; i < num; i++){
            int index = -1;
            while(index == -1 || listIndexMine.contains(index)){
                index = random.nextInt(SIZE*SIZE);
            }
            listIndexMine.add(index);
            ((Mine)map[index/SIZE][index%SIZE].getTag()).setBoom(true);

        }
        listIndexMine.remove(0);
    }
    // click Button
    void openButton(Button btn){



        if(buttomNotOpen == numBoom){
            winGame();
            return;
        }
        Mine mine = (Mine) btn.getTag();
        if(mine.isFlag()){

            return;
        }
        if(mine.isBoom()){
            openBoom();
            setClickableChildView(false,buttonPanel);
            return;
        }
        if(mine.isOpen()) return;
        mine.setOpen(true);
        buttomNotOpen--;
        int countBoom =0;
        int rowIndex = mine.getRowIndex();
        int columnIndex = mine.getColumnIndex();
        for(int i = rowIndex-1; i<= rowIndex+1;i++){
            for(int j = columnIndex-1; j<= columnIndex+1;j++){


                    try {
                        Mine nearMine = (Mine) map[i][j].getTag();
                        if (nearMine.isBoom()) {
                            countBoom++;
                        }
                    }
                    catch (Exception e){

                    }

            }
        }
        if(countBoom==0){
            for(int i = rowIndex-1; i<= rowIndex+1;i++){
                for(int j = columnIndex-1; j<= columnIndex+1;j++){

                        try{
                       openButton(map[i][j]);
                        }
                        catch (Exception e){



                    }
                }
            }

        }else {

            btn.setText(countBoom+"");
        }
        btn.setEnabled(false);
        if(buttomNotOpen == numBoom){
           winGame();
            return;
        }

    }
    void winGame(){
        openFlag();
        setClickableChildView(false,buttonPanel); // it's same win
        win.setVisibility(View.VISIBLE);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.markFlag:
                if(numFlag == 0){
                    markFlag.setChecked(false);
                    Toast.makeText(MainActivity.this,"Reach the limit of the number of flags",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.win:
                loadGame();
                break;
            case R.id.lost:
                loadGame();
                break;
            default:

                if (markFlag.isChecked()) {
                    markFlag((Button) v);
                    return;
                }
                if (((Mine) v.getTag()).isFlag()) {
                    unmarkFlag((Button) v);
                    return;
                }
                if(!firstClick){
                    createBoom(numBoom,((Mine)v.getTag()).getRowIndex(),((Mine)v.getTag()).getColumnIndex());
                    firstClick = true;
                }
                openButton((Button) v);
                break;
        }
    }

    void markFlag(Button btn){
        Mine mine = (Mine) btn.getTag();
            if(!mine.isFlag()) {
                mine.setFlag(true);
                markFlag.setChecked(false);
                btn.setBackground(getResources().getDrawable(R.drawable.flag_btn));
                numFlag--;
                changeTextInform();
            }
    }
    void unmarkFlag(Button btn){
        Mine mine = (Mine) btn.getTag();

        mine.setFlag(false);

        btn.setBackground(getResources().getDrawable(R.drawable.background_btn));
        numFlag++;
        changeTextInform();


    }
    void openBoom(){
        for (int i :listIndexMine) {
            map[i/SIZE][i%SIZE].setBackground(getResources().getDrawable(R.drawable.boom_background));
        }
        lost.setVisibility(View.VISIBLE);
    }
    void openFlag(){
        for (int i :listIndexMine) {
            map[i/SIZE][i%SIZE].setBackground(getResources().getDrawable(R.drawable.flag_btn));
        }

    }
    void setClickableChildView(boolean enableChildView, ViewGroup viewGroup){
        for (int i =0;i<viewGroup.getChildCount();i++){
            View v = viewGroup.getChildAt(i);
            v.setClickable(enableChildView);
        }
    }
    void changeTextInform(){
        inform.setText("FLAG     : "+numFlag+" \nBooM   : "+numBoom+"\nSize     : "+SIZE+"x"+SIZE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 0){
            SIZE  = data.getIntExtra("size",10);
            numBoom = data.getIntExtra("boom",10);
            loadGame();
        }
        else if (resultCode == 1){

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.reloadd:
                if(buttomNotOpen == SIZE*SIZE && numFlag==numBoom) return false;
                loadGame();
                break;
            case  R.id.optionn:
                Intent i = new Intent(MainActivity.this,OptionActivity.class);
                i.putExtra("size",SIZE);
                i.putExtra("boom",numBoom);
                startActivityForResult(i,0);
                break;
            case  R.id.cancell:
                this.finish();

        }
        return false;
    }
}
