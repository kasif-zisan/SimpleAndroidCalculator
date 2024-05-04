package com.example.calculatorapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView inputTextView, outputTextView;
    private Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    private Button btnAdd,btnMinus,btnX,btnDiv,btnEqual,btnClear,btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        outputTextView = findViewById(R.id.outputTextView);
        inputTextView = findViewById(R.id.inputTextView);

        assignID(btn0, R.id.btn0);
        assignID(btn1, R.id.btn1);
        assignID(btn2, R.id.btn2);
        assignID(btn3, R.id.btn3);
        assignID(btn4, R.id.btn4);
        assignID(btn5, R.id.btn5);
        assignID(btn6, R.id.btn6);
        assignID(btn7, R.id.btn7);
        assignID(btn8, R.id.btn8);
        assignID(btn9, R.id.btn9);
        assignID(btnAdd, R.id.btnAdd);
        assignID(btnMinus, R.id.btnMinus);
        assignID(btnX, R.id.btnX);
        assignID(btnDiv, R.id.btnDiv);
        assignID(btnEqual, R.id.btnEqual);
        assignID(btnClear, R.id.btnClr);
        assignID(btnDot, R.id.btndot);


    }

    void assignID (Button btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Button clickedButton = (Button) v;
        String buttonText = clickedButton.getText().toString();
        String dataToCalculate = inputTextView.getText().toString();

        while (buttonText.equals("AC")){
            inputTextView.setText("");
            outputTextView.setText("0");
            return;
        }

        if (buttonText.equals("=")){
            inputTextView.setText(outputTextView.getText().toString());
            outputTextView.setText("0");
            return;
        }
        dataToCalculate+= buttonText;

        inputTextView.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Error")){
            if(finalResult.endsWith(".0")){finalResult = finalResult.substring(0, finalResult.length() - 2);}
            int dotIndex = finalResult.indexOf(".");
            if (dotIndex != -1 && finalResult.length() > dotIndex + 4) {
                finalResult = finalResult.substring(0, dotIndex + 4); 
            }
            outputTextView.setText(finalResult);
        }

    }
    String getResult (String data){
        try{
            data = data.replace("×", "*")
                    .replace("÷", "/")
                    .replace("–", "-");
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            return finalResult;
        }
        catch(Exception e){
            return "Error";
        }

    }
}