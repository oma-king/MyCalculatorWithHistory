package com.example.mycalculatorwithhistory;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    double firstInput = 0,secondInput = 0,opResult = 0;
    boolean Addition, Subtract, Multiplication, Division, Decimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button axClear, axBack, axSep, opDiv, opMlt, opSub, opAdd, opEql, digitZero, digitOne, digitTwo, digitThree, digitFour, digitFive, digitSix, digitSeven, digitEight, digitNine;
        TextView txtOperation, txtResult;
        ListView lstHistory;

        /***************************** References *****************************/
        // ListView
        lstHistory = (ListView) findViewById(R.id.lsvHistory);

        // TextView
        txtOperation = (TextView) findViewById(R.id.OperationScreen);
        txtResult = (TextView) findViewById(R.id.ResultScreen);

        // Actions
        axClear = (Button) findViewById(R.id.btnClear);
        axBack = (Button) findViewById(R.id.btnBack);
        axSep = (Button) findViewById(R.id.btnSep);

        // Operators
        opDiv = (Button) findViewById(R.id.btnDiv);
        opMlt = (Button) findViewById(R.id.btnMul);
        opSub = (Button) findViewById(R.id.btnSub);
        opAdd = (Button) findViewById(R.id.btnAdd);
        opEql = (Button) findViewById(R.id.btnEql);

        // Digits
        digitZero = (Button) findViewById(R.id.btn0);
        digitOne = (Button) findViewById(R.id.btn1);
        digitTwo = (Button) findViewById(R.id.btn2);
        digitThree = (Button) findViewById(R.id.btn3);
        digitFour = (Button) findViewById(R.id.btn4);
        digitFive = (Button) findViewById(R.id.btn5);
        digitSix = (Button) findViewById(R.id.btn6);
        digitSeven = (Button) findViewById(R.id.btn7);
        digitEight = (Button) findViewById(R.id.btn8);
        digitNine = (Button) findViewById(R.id.btn9);

        /***************************** Digits Btn Actions *****************************/
        digitZero.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "0"));
        digitOne.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "1"));
        digitTwo.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "2"));
        digitThree.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "3"));
        digitFour.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "4"));
        digitFive.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "5"));
        digitSix.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "6"));
        digitSeven.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "7"));
        digitEight.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "8"));
        digitNine.setOnClickListener(view -> txtOperation.setText(txtOperation.getText() + "9"));

        /***************************** Actions Btn functions *****************************/
        //Clear All
        axClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtOperation.setText("");
                txtResult.setText("");
                firstInput = 0;
                secondInput =0;
            }
        });

        //Delete Last Entry
        axBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sTxtOperation = (String) txtOperation.getText();
                int n = sTxtOperation.length()-1;
                sTxtOperation= sTxtOperation.substring(0,n);
                txtOperation.setText(sTxtOperation);
            }
        });

        //Add separator for decimals
        axSep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Decimal){
                    return;
                }
                else {
                    txtOperation.setText(txtOperation.getText() + ".");
                    Decimal = true;
                }
            }
        });

        /***************************** Operations Btn functions *****************************/
        //Addition
        opAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtResult.getText().length()!=0){
                    txtOperation.setText(txtOperation.getText()+"");
                }
                if(txtOperation.getText().length()!=0){
                    Addition = true;
                    Decimal = false;
                    txtOperation.setText(txtOperation.getText() + "+");
                }
            }
        });

        //Subtraction
        opSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtResult.getText().length()!=0){
                    txtOperation.setText(txtResult.getText()+"");
                }
                if(txtOperation.getText().length()!=0){
                    Subtract = true;
                    Decimal = false;
                    txtOperation.setText(txtOperation.getText() + "-");
                }
            }
        });

        //Multiplication
        opMlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtResult.getText().length()!=0){
                    txtOperation.setText(txtResult.getText()+"");
                }
                if(txtOperation.getText().length()!=0){
                    Multiplication = true;
                    Decimal = false;
                    txtOperation.setText(txtOperation.getText() + "x");
                }
            }
        });

        //Division
        opDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtResult.getText().length()!=0){
                    txtOperation.setText(txtResult.getText()+"");
                }

                if(txtOperation.getText().length()!=0){
                    Division = true;
                    Decimal = false;
                    txtOperation.setText(txtOperation.getText() + "/");
                }
            }
        });

        // Equal
        opEql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Addition) {
                    String s = (String) txtOperation.getText();
                    int index = s.indexOf('+');
                    firstInput = Float.parseFloat(s.substring(0,index));
                    secondInput = Float.parseFloat(s.substring(index+1,s.length()));
                    opResult = firstInput + secondInput;
                    txtResult.setText( String.format("%.2f", opResult) + "");
                    Addition = false;
                }

                if (Subtract) {

                    String s = (String) txtOperation.getText();
                    int index = s.indexOf('-');
                    firstInput = Float.parseFloat(s.substring(0,index));
                    secondInput = Float.parseFloat(s.substring(index+1,s.length()));
                    opResult = firstInput - secondInput;
                    txtResult.setText( String.format("%.2f", opResult) + "");
                    Subtract = false;
                }

                if (Multiplication) {
                    String s = (String) txtOperation.getText();
                    int index = s.indexOf('x');
                    firstInput = Float.parseFloat(s.substring(0,index));
                    secondInput = Float.parseFloat(s.substring(index+1,s.length()));
                    opResult = firstInput * secondInput;
                    txtResult.setText( String.format("%.2f", opResult) + "");
                    Multiplication = false;
                }

                if (Division) {
                    String s = (String) txtOperation.getText();
                    int index = s.indexOf('/');
                    firstInput = Float.parseFloat(s.substring(0, index));
                    secondInput = Float.parseFloat(s.substring(index + 1, s.length()));
                    opResult = firstInput / secondInput;
                    txtResult.setText( String.format("%.2f", opResult) + "");
                    Division = false;
                }
            }
        });

    }


}