package com.example.personallibrary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.example.personallibrary.R;

public class BookSettingActivity extends AppCompatActivity {

    private boolean checked = false;
    private Integer size = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_setting);
        SwitchCompat pricer = findViewById(R.id.pricer);
        pricer.setOnCheckedChangeListener((buttonView, isChecked) -> checked = isChecked);
        NumberPicker sizer = findViewById(R.id.sizer);
        String[] numbers = {"11","12","13","14","15","16","17","18","19","20"};
        sizer.setDisplayedValues(numbers);
        sizer.setMinValue(11);
        sizer.setMaxValue(20);
        sizer.setValue(15);
        sizer.setWrapSelectorWheel(false);
        sizer.setOnValueChangedListener((picker, oldVal, newVal) -> size = newVal);
    }

    public void set(View view) {
        SharedPreferences sp = getSharedPreferences("bookSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("checked", String.valueOf(checked));
        edit.putString("size", String.valueOf(size));
        edit.apply();
        Toast.makeText(this,"设置成功",Toast.LENGTH_SHORT).show();
    }
}