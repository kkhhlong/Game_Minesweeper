package com.example.nhl71.myfirstgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created by nhl71 on 28/06/2017.
 */

public class OptionActivity extends AppCompatActivity implements View.OnClickListener {
    NumberPicker boomPicker,sizePicker;
    Button cancel,apply;
    Toolbar toolbar;
    boolean result=false;
    final int MIN_SIZE = 6;
    final int MAX_SIZE = 15;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_layout);
        connectView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        boomPicker.setMinValue(MIN_SIZE);
        sizePicker.setMaxValue(MAX_SIZE);
        sizePicker.setMinValue(MIN_SIZE);
        sizePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                boomPicker.setMaxValue((int) ((newVal*newVal)*0.5));
            }
        });
        Intent i = getIntent();
        sizePicker.setValue(i.getIntExtra("size",10));
        boomPicker.setMaxValue((int) ((sizePicker.getValue()*sizePicker.getValue())*0.5));
        boomPicker.setValue(i.getIntExtra("boom",20));

        cancel.setOnClickListener(this);
        apply.setOnClickListener(this);


    }
    void connectView(){
        cancel = (Button) findViewById(R.id.cancel);
        apply = (Button) findViewById(R.id.apply);
        boomPicker = (NumberPicker) findViewById(R.id.pickerBoom);
        sizePicker = (NumberPicker) findViewById(R.id.pickerSize);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cancel){

            this.finish();
        }
        else if (v.getId() == R.id.apply){
            Intent i = getIntent();
            i.putExtra("size",sizePicker.getValue());
            i.putExtra("boom",boomPicker.getValue());
            setResult(0,i);
            result = true;
            finish();
        }
    }

    @Override
    public void finish() {
        if(!result) {
            Intent i = getIntent();

            setResult(1, i);

        }
        super.finish();
    }


}
