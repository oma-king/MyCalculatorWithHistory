package com.example.mycalculatorwithhistory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    /***************************** Declare Vars *****************************/

    private Button axClear, axBack, axSep, opDiv, opMlt, opSub, opAdd, opEql, digitZero, digitOne, digitTwo, digitThree, digitFour, digitFive, digitSix, digitSeven, digitEight, digitNine;
    private TextView txtOperation, txtResult;
    private ListView lstHistory;
    private String lastChrInTxtOperation;
    SQLiteDatabase myLocalCalcDb;
    private boolean equalRequested = false, decimalRequested = false;
    private final static int IsException = -1;
    private final static int IsDigit = 0;
    private final static int IsOperand = 1;
    private final static int IsSeparator = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***************************** initialize View Variables *****************************/
        // ListView
        lstHistory = (ListView) findViewById(R.id.lsvHistory);

        // TextView
        txtOperation = (TextView) findViewById(R.id.OperationScreen);
        txtResult = (TextView) findViewById(R.id.ResultScreen);

        // Actions
        axClear = (Button) findViewById(R.id.btnClear);
        axBack = (Button) findViewById(R.id.btnBack);
        axSep = (Button) findViewById(R.id.btnSep);

        // Operands
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

        /***************************** setOnClickListener for Digits Buttons  *****************************/
        digitZero.setOnClickListener(this);
        digitOne.setOnClickListener(this);
        digitTwo.setOnClickListener(this);
        digitThree.setOnClickListener(this);
        digitFour.setOnClickListener(this);
        digitFive.setOnClickListener(this);
        digitSix.setOnClickListener(this);
        digitSeven.setOnClickListener(this);
        digitEight.setOnClickListener(this);
        digitNine.setOnClickListener(this);

        /***************************** setOnClickListener for Actions Buttons *****************************/
        axClear.setOnClickListener(this);
        axBack.setOnClickListener(this);
        axSep.setOnClickListener(this);

        /***************************** setOnClickListener for Operands Buttons *****************************/
        opAdd.setOnClickListener(this);
        opSub.setOnClickListener(this);
        opMlt.setOnClickListener(this);
        opDiv.setOnClickListener(this);
        opEql.setOnClickListener(this);

        /***************************** Prepare Database *****************************/
        myLocalCalcDb = openOrCreateDatabase("myLocalCalcDb", Context.MODE_PRIVATE, null);
        myLocalCalcDb.execSQL("CREATE TABLE IF NOT EXISTS CalcHistoy(idHistory integer primary key autoincrement, Operation VARCHAR,Result VARCHAR);");
        displayLastHistoryEntry();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn0:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("0");
                equalRequested = false;
                break;
            case R.id.btn1:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("1");
                equalRequested = false;
                break;
            case R.id.btn2:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("2");
                equalRequested = false;
                break;
            case R.id.btn3:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("3");
                equalRequested = false;
                break;
            case R.id.btn4:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("4");
                equalRequested = false;
                break;
            case R.id.btn5:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("5");
                equalRequested = false;
                break;
            case R.id.btn6:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("6");
                equalRequested = false;
                break;
            case R.id.btn7:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("7");
                equalRequested = false;
                break;
            case R.id.btn8:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("8");
                equalRequested = false;
                break;
            case R.id.btn9:
                if (equalRequested == true) {
                    fnClear();
                }
                insertDigit("9");
                equalRequested = false;
                break;
            case R.id.btnSep:
                if (equalRequested == true) {
                    fnClear();
                }
                insertSeparator();
                equalRequested = false;
                break;
            case R.id.btnDiv:
                insertOperand("/");
                equalRequested = false;
                break;
            case R.id.btnMul:
                insertOperand("*");
                equalRequested = false;
                break;
            case R.id.btnSub:
                insertOperand("-");
                equalRequested = false;
                break;
            case R.id.btnAdd:
                insertOperand("+");
                equalRequested = false;
                break;
            case R.id.btnEql:
                if (equalRequested == false) {
                    chkInputToCalculate(txtOperation.getText().toString());

                }
                break;
            case R.id.btnClear:
                fnClear();
                break;
            case R.id.btnBack:
                fnBack();
                equalRequested = false;
                break;
            default:
                break;
        }

    }
    private boolean insertDigit(String Digit) {
        boolean executed = false;
        int txtOperationLength = txtOperation.getText().length();
        if (txtOperationLength > 0) {
            String lastChr = txtOperation.getText().charAt(txtOperationLength - 1) + "";
            int lastCharacterState = getLastChr(lastChr);

            if (txtOperationLength == 1 && lastCharacterState == IsDigit && lastChr.equals("0")) {
                txtOperation.setText(Digit);
                executed = true;
            } else if (lastCharacterState == IsDigit || lastCharacterState == IsOperand || lastCharacterState == IsSeparator) {
                txtOperation.setText(txtOperation.getText() + Digit);
                executed = true;
            }
        } else {
            txtOperation.setText(txtOperation.getText() + Digit);
            executed = true;
        }
        return executed;
    }

    private boolean insertOperand(String operand) {
        boolean executed = false;
        int txtOperationLength = txtOperation.getText().length();
        if (txtOperationLength > 0) {
            String lastChr = txtOperation.getText().charAt(txtOperationLength - 1) + "";

            if (lastChr.equals(".") || lastChr.equals("+") || lastChr.equals("-") || lastChr.equals("*") || lastChr.equals("/")) {
                Toast.makeText(getApplicationContext(), "Invalid format !", Toast.LENGTH_SHORT).show();
            } else if ((operand.equals("+") || operand.equals("-") || operand.equals("*") || operand.equals("/"))) {
                txtOperation.setText(txtOperation.getText() + operand);
                decimalRequested = false;
                equalRequested = false;
                lastChrInTxtOperation = "";
                executed = true;
            }
        } else {
            Toast.makeText(getApplicationContext(), "Invalid format !", Toast.LENGTH_SHORT).show();
        }
        return executed;
    }

    private boolean insertSeparator() {
        boolean executed = false;

        if (txtOperation.getText().length() == 0) {
            txtOperation.setText("0.");
            decimalRequested = true;
            executed = true;
        } else if (decimalRequested == true) {} else if (getLastChr(txtOperation.getText().charAt(txtOperation.getText().length() - 1) + "") == IsOperand) {
            txtOperation.setText(txtOperation.getText() + "0.");
            executed = true;
            decimalRequested = true;
        } else if (getLastChr(txtOperation.getText().charAt(txtOperation.getText().length() - 1) + "") == IsDigit) {
            txtOperation.setText(txtOperation.getText() + ".");
            executed = true;
            decimalRequested = true;
        }
        return executed;
    }

    private boolean fnClear() {
        boolean executed = false;
        txtOperation.setText("");
        txtResult.setText("");
        int txtOperationLength = txtOperation.getText().length();
        int txtResultLength = txtResult.getText().length();
        decimalRequested = false;
        equalRequested = false;
        if (txtOperationLength == 0 && txtResultLength == 0) {
            executed = true;
        }
        return executed;
    }
    private boolean fnBack() {
        boolean executed = false;
        int txtOperationLength = txtOperation.getText().length();
        String sTxtOperation = (String) txtOperation.getText();
        if (txtOperationLength == 0) {
            Toast.makeText(getApplicationContext(), "Nothing to remove !", Toast.LENGTH_SHORT).show();
            executed = true;
        } else if (txtOperationLength > 0) {
            String lastChr = sTxtOperation.charAt(txtOperationLength - 1) + "";
            sTxtOperation = sTxtOperation.substring(0, txtOperationLength - 1);
            if (lastChr.equals(".")) {
                decimalRequested = false;
                equalRequested = false;
                executed = true;
            }
            txtOperation.setText(sTxtOperation);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid format !", Toast.LENGTH_SHORT).show();
        }
        return executed;
    }
    private int getLastChr(String lastCharacter) {
        try {
            Integer.parseInt(lastCharacter);
            return IsDigit;
        } catch (NumberFormatException e) {}

        if ((lastCharacter.equals("+") || lastCharacter.equals("-") || lastCharacter.equals("*") || lastCharacter.equals("/")))
            return IsOperand;

        if (lastCharacter.equals("."))
            return IsSeparator;

        return IsException;
    }

    private void chkInputToCalculate(String inputToCalculate) {
        if (txtOperation.getText().toString() != null && !txtOperation.getText().toString().equals("")) {
            String lastOfExpression = inputToCalculate.charAt(inputToCalculate.length() - 1) + "";
            if (inputToCalculate.length() > 1) {
                if (getLastChr(lastOfExpression + "") == IsDigit) {
                    txtResult.setText(String.format("%s", doCalculation(inputToCalculate)));
                    equalRequested = true;
                    manageHistory(txtOperation.getText().toString(), txtResult.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Operation !", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    public static double doCalculation(final String toCalculate) {
        return new Object() {
            int pos = -1, ch;
            void nextChar() {
                ch = (++pos < toCalculate.length()) ? toCalculate.charAt(pos) : -1;
            }
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < toCalculate.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }
            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(toCalculate.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }
        }.parse();
    }

    private void manageHistory(String Operation, String Result) {
        if (Operation != null && !Operation.equals("") && Result != null && !Result.equals("")) {
            myLocalCalcDb.execSQL("INSERT INTO CalcHistoy(Operation,Result) VALUES('" + Operation + "','" + Result + "');");
            //Toast.makeText(getApplicationContext(), "Added toHistory !", Toast.LENGTH_SHORT).show();
            displayLastHistoryEntry();
        }
    }

    private void displayLastHistoryEntry() {

        try {
            Cursor cursor = myLocalCalcDb.rawQuery("SELECT idHistory AS _id, Operation, Result  FROM CalcHistoy ORDER BY idHistory asc", null);
            if (cursor.moveToFirst()) {

                lsvHistoryCursorAdapter adapter = new lsvHistoryCursorAdapter(this, cursor, 0);
                lstHistory.setAdapter(adapter);
                lstHistory.setSelection(cursor.getCount() - 1);

            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

}