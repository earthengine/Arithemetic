package com.example.arithmetic;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Addition extends Activity {
	
	private Random r;
	private int number1;
	private int number2;
	
	private void setNumber(int number, TextView v1, TextView v2){
        if(v2!=null)
        	v2.setText("" + (number%10));
        if(v1!=null) 
        	if(number >= 10)
        		v1.setText("" + (number/10));
        	else
        		v1.setText(" ");
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition);
        
        if(savedInstanceState==null || !savedInstanceState.containsKey("Random")){
        	r = new Random();
        	newChallenge();
        } else {
        	r = (Random)savedInstanceState.getSerializable("Random");
        	number1 = (Integer)savedInstanceState.getInt("Number 1");
        	number2 = (Integer)savedInstanceState.getInt("Number 2");
        }
        
        showChallenge();

        TextView v1 = (TextView)findViewById(R.id.TextNum2Digit1);
    	v1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Addition.this.onClick(v);
			}
    		
    	});
        
        setTextChangedListener(R.id.editTextDigit1);
        setTextChangedListener(R.id.editTextDigit2);
    }

	private void newChallenge() {
		number1 = r.nextInt(98) + 1;
		number2 = r.nextInt(98 - number1) + 1;
		
		TextView v = (TextView)findViewById(R.id.editTextDigit1);
		v.setText("");
		v = (TextView)findViewById(R.id.editTextDigit2);
		v.setText("");
		v.requestFocus();
		
		v = (TextView)findViewById(R.id.textViewCarry);
		v.setVisibility(View.INVISIBLE);
	}

	private void showChallenge() {
		TextView v2 = (TextView)findViewById(R.id.TextNum1Digit2);
        TextView v1 = (TextView)findViewById(R.id.TextNum1Digit1);
        setNumber(number1, v1, v2);
        
        v2 = (TextView)findViewById(R.id.TextNum2Digit2);
        v1 = (TextView)findViewById(R.id.TextNum2Digit1);
        setNumber(number2, v1, v2);
	}    
    

	private void setTextChangedListener(int id) {
		EditText et = (EditText) findViewById(id);
		et.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				Addition.this.afterTextChanged(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
        	
        });
	}


	protected void afterTextChanged(Editable s) {
		
		EditText et1 = (EditText) findViewById(R.id.editTextDigit1);
		Editable et11 = et1.getText();
		EditText et2 = (EditText) findViewById(R.id.editTextDigit2);
		Editable et21 = et2.getText();
		TextView c = (TextView) findViewById(R.id.textViewCarry);
		if(et11.length()==1){
			et2.requestFocus();
		} else if(et21.length()==1) {
			et1.requestFocus();
		}
		if(et11.length()==0||et21.length()==0)
			return;

		checkAnswer(Integer.parseInt(et1.getText().charAt(0) + "" +
				et2.getText().charAt(0)), c.isShown());
	}



	private void checkAnswer(int answer, boolean carry) {
		if(answer==number1 + number2 && carry == (number1 % 10 + number2 % 10>9)){
			newChallenge();
			showChallenge();
		}
	}

	private void onClick(View v){
    	View v1 = findViewById(R.id.textViewCarry);
  		v1.setVisibility(v1.isShown() ? View.INVISIBLE : View.VISIBLE);
    }


    @Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if(outState!=null){
			outState.putSerializable("Random", r);
			outState.putInt("Number 1", number1);
			outState.putInt("Number 2", number2);
		}
	}
    
}
