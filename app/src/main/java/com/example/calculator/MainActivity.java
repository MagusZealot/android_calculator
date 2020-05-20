package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.String;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private Double operator;
    private Double operand;
    private Double memory;
private ArrayList<Button> numButtonList;
private TextView output;
private Button btn0;
    private Button btn00;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
private Button btnAdd;
    private Button btnSubtract;
    private Button btnMultiply;
    private Button btnDivide;
    private Button btnEquals;
    private Button btnDecimal;
private Button btnRecallClearMemory;
    private Button btnMemoryAdd;
    private Button btnMemorySubtract;
    private Button btnInverter;
private Button btnClearAll;
    private Button btnClearLine;
    private Button btnBackSpace;
    private Button btnSquareRoot;
private boolean addOperationOn;
    private boolean subtractOperationOn;
    private boolean multiplyOperationOn;
    private boolean divideOperationOn;
private boolean showMemory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createViewLinks();
        setUpNumBtnListeners();
        setUpClearBtnListeners();
        setUpOperationBtnListeners();
        setUpMemoryBtnListeners();

        addOperationOn = false;
        subtractOperationOn = false;
        multiplyOperationOn = false;
        divideOperationOn = false;
        memory = null;
    }

    private void createViewLinks() {
        output = findViewById(R.id.txt_output);

        btn0 = findViewById(R.id.btn_zero);
        btn00 = findViewById(R.id.btn_zerozero);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);

        numButtonList = new ArrayList<>();
        numButtonList.add(btn0);
        numButtonList.add(btn00);
        numButtonList.add(btn1);
        numButtonList.add(btn2);
        numButtonList.add(btn3);
        numButtonList.add(btn4);
        numButtonList.add(btn5);
        numButtonList.add(btn6);
        numButtonList.add(btn7);
        numButtonList.add(btn8);
        numButtonList.add(btn9);

        btnAdd = findViewById(R.id.btn_add);
        btnSubtract = findViewById(R.id.btn_subtract);
        btnMultiply = findViewById(R.id.btn_multiply);
        btnDivide = findViewById(R.id.btn_divide);
        btnEquals = findViewById(R.id.btn_equals);
        btnDecimal = findViewById(R.id.btn_decimal);

        btnRecallClearMemory = findViewById(R.id.btn_recall_clear_memory);
        btnMemoryAdd = findViewById(R.id.btn_memory_add);
        btnMemorySubtract = findViewById(R.id.btn_memory_subtract);
        btnInverter = findViewById(R.id.btn_inverter);

        btnClearAll = findViewById(R.id.btn_clear_all);
        btnClearLine = findViewById(R.id.btn_clear_line);
        btnBackSpace = findViewById(R.id.btn_backspace);
        btnSquareRoot = findViewById(R.id.btn_sqare_root);
    }

    private void setUpClearBtnListeners() {
        btnClearLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
                memory = null;
            }
        });
    }

    private void clear() {
        output.setText("");
        output.setHint("");
        operand = null;
        operator = null;
        addOperationOn = false;
        subtractOperationOn = false;
        multiplyOperationOn = false;
        divideOperationOn = false;
    }

    private void setUpNumBtnListeners() {
        View.OnClickListener numButtonEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonName = ((Button) v).getText().toString();
                if (output.getText().toString().equals("") || output.getText().toString().equals("0") || output.getText().toString().equals("NaN")) {
                    output.setText(buttonName);
                } else {
                    output.setText(output.getText() + buttonName);
                }
                digitCounter();
            }
        };

        for (Button x: numButtonList) { x.setOnClickListener(numButtonEvent); }
    }

    private void setUpOperationBtnListeners() {
        btnDecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (output.getText().toString().equals("") || output.getText().toString().equals("0") || output.getText().toString().equals("NaN")) {
                    output.setText("0.");
                } else {
                    boolean decimalPointFound = false;
                    char[] numArray = output.getText().toString().toCharArray();
                    for(int i=0; i<numArray.length-1;i++) {
                        if (String.valueOf(numArray[i]).equals(".")) {
                            decimalPointFound = true;
                            break;
                        }
                    }
                    if (!decimalPointFound) {
                        output.setText(output.getText() + ".");
                    }
                    digitCounter();
                }
            }
        });

        btnBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (output.getText().toString().equals("") && operand!=null) {
                    output.setText(operand.toString().substring(0,removeZeroes(operand).length()-1));
                } else if (output.getText().equals("") || output.getText().equals("NaN") || output.getText().length()==1 ||
                        (output.getText().length()==2 && String.valueOf(output.getText().charAt(0)).equals("-")) ||
                        (output.getText().length()==3 && String.valueOf(output.getText().charAt(0)).equals("-") && String.valueOf(output.getText().charAt(2)).equals("."))) {
                    output.setText("0");
                } else if (String.valueOf(output.getText().charAt(output.getText().length()-2)).equals(".")) {
                    output.setText(output.getText().toString().substring(0,output.getText().length()-2));
                } else {
                    output.setText(output.getText().toString().substring(0,output.getText().length()-1));
                }
                if (String.valueOf(output.getText().charAt(output.getText().length()-1)).equals(".")) {
                    output.setText(output.getText().toString().substring(0, output.getText().length() - 1));
                }
            }
        });

        btnSquareRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!String.valueOf(output.getText()).equals("")) {
                    double squareRoot = Double.parseDouble(String.valueOf(output.getText()));
                    output.setText(removeZeroes(Math.sqrt(squareRoot)));
                } else {
                    Double squareRoot = operand;
                    output.setText(removeZeroes(Math.sqrt(squareRoot)));
                }
                digitCounter();
            }
        });

        btnInverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!output.getText().toString().equals("")) {
                    Double invert = Double.parseDouble(output.getText().toString()) * -1;
                    output.setText(removeZeroes(invert));
                } else if (output.getText().toString().equals("") && !output.getHint().toString().equals("")) {
                    double invert = Double.parseDouble(output.getHint().toString()) * -1;
                    output.setText(removeZeroes(invert));
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!output.getText().toString().equals("") && operand!=null) {
                    if (subtractOperationOn || multiplyOperationOn || divideOperationOn) {
                        if (subtractOperationOn) {
                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand-operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        } else if (multiplyOperationOn) {
                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand*operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        } else {
                            operator = Double.parseDouble(output.getText().toString());
                            if (operand==0 || operator==0) {
                                output.setText(String.valueOf(0));
                            } else {
                                output.setText(String.valueOf(operand/operator));
                            }
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        }
                        subtractOperationOn = false;
                        multiplyOperationOn = false;
                        divideOperationOn = false;
                    } else {
                        operator = Double.parseDouble(output.getText().toString());
                        output.setText(String.valueOf(operand+operator));
                        operand = Double.parseDouble(output.getText().toString());
                        output.setText("");
                        output.setHint(removeZeroes(operand));
                    }
                }
                if (!addOperationOn) {
                    addOperationOn = true;
                    subtractOperationOn = false;
                    multiplyOperationOn = false;
                    divideOperationOn = false;
                    if (!output.getText().toString().equals("")) {
                        operand = Double.parseDouble(output.getText().toString());
                    }
                    output.setText("");
                    if (operand==null) {
                        output.setHint("");
                    } else {
                        output.setHint(removeZeroes(operand));
                    }
                }
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!output.getText().toString().equals("") && operand!=null) {
                    if (addOperationOn || multiplyOperationOn || divideOperationOn) {
                        if (addOperationOn) {
                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand+operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        } else if (multiplyOperationOn) {
                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand*operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        } else {
                            operator = Double.parseDouble(output.getText().toString());
                            if (operand==0 || operator==0) {
                                output.setText(String.valueOf(0));
                            } else {
                                output.setText(String.valueOf(operand/operator));
                            }
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        }
                        addOperationOn = false;
                        multiplyOperationOn = false;
                        divideOperationOn = false;
                    } else {
                        operator = Double.parseDouble(output.getText().toString());
                        output.setText(String.valueOf(operand-operator));
                        operand = Double.parseDouble(output.getText().toString());
                        output.setText("");
                        output.setHint(removeZeroes(operand));
                    }
                }
                if (!subtractOperationOn) {
                    subtractOperationOn = true;
                    addOperationOn = false;
                    multiplyOperationOn = false;
                    divideOperationOn = false;
                    if (!output.getText().toString().equals("")) {
                        operand = Double.parseDouble(output.getText().toString());
                    }
                    output.setText("");
                    if (operand==null) {
                        output.setHint("");
                    } else {
                        output.setHint(removeZeroes(operand));
                    }
                }
            }
        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!output.getText().toString().equals("") && operand!=null) {
                    if (addOperationOn || subtractOperationOn || divideOperationOn) {
                        if (addOperationOn) {
                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand+operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        } else if (subtractOperationOn) {

                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand-operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        } else {
                            operator = Double.parseDouble(output.getText().toString());
                            if (operand==0 || operator==0) {
                                output.setText(String.valueOf(0));
                            } else {
                                output.setText(String.valueOf(operand/operator));
                            }
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        }
                        addOperationOn = false;
                        subtractOperationOn = false;
                        divideOperationOn = false;
                    } else {
                        operator = Double.parseDouble(output.getText().toString());
                        output.setText(String.valueOf(operand*operator));
                        operand = Double.parseDouble(output.getText().toString());
                        output.setText("");
                        output.setHint(removeZeroes(operand));
                    }
                }
                if (!multiplyOperationOn) {
                    multiplyOperationOn = true;
                    addOperationOn = false;
                    subtractOperationOn = false;
                    divideOperationOn = false;
                    if (!output.getText().toString().equals("")) {
                        operand = Double.parseDouble(output.getText().toString());
                    }
                    output.setText("");
                    if (operand==null) {
                        output.setHint("");
                    } else {
                        output.setHint(removeZeroes(operand));
                    }
                }
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!output.getText().toString().equals("") && operand!=null) {
                    if (addOperationOn || subtractOperationOn || multiplyOperationOn) {
                        if (addOperationOn) {
                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand+operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        } else if (subtractOperationOn) {
                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand-operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        } else {
                            operator = Double.parseDouble(output.getText().toString());
                            output.setText(String.valueOf(operand*operator));
                            operand = Double.parseDouble(output.getText().toString());
                            output.setText("");
                            output.setHint(removeZeroes(operand));
                        }
                        addOperationOn = false;
                        subtractOperationOn = false;
                        multiplyOperationOn = false;
                    } else {
                        operator = Double.parseDouble(output.getText().toString());
                        if (operand==0 || operator==0) {
                            output.setText(String.valueOf(0));
                        } else {
                            output.setText(String.valueOf(operand/operator));
                        }
                        operand = Double.parseDouble(output.getText().toString());
                        output.setText("");
                        output.setHint(removeZeroes(operand));
                    }
                }
                if (!divideOperationOn) {
                    divideOperationOn = true;
                    addOperationOn = false;
                    subtractOperationOn = false;
                    multiplyOperationOn = false;
                    if (!output.getText().toString().equals("")) {
                        operand = Double.parseDouble(output.getText().toString());
                    }
                    output.setText("");
                    if (operand==null) {
                        output.setHint("");
                    } else {
                        output.setHint(removeZeroes(operand));
                    }
                }
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalsFunction();
            }
        });
    }

    private void setUpMemoryBtnListeners() {
        btnMemoryAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalsFunction();
                if (memory == null) {
                    if (!output.getText().toString().equals("")) {
                        memory = Double.parseDouble(output.getText().toString());
                    } else {
                        memory = Double.parseDouble(output.getHint().toString());
                    }
                } else {
                    if (!output.getText().toString().equals("")) {
                        memory = memory + Double.parseDouble(output.getText().toString());
                    } else {
                        memory = memory + Double.parseDouble(output.getHint().toString());
                    }
                }
                output.setHint(output.getText().toString());
                output.setText("");
            }
        });

        btnMemorySubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equalsFunction();
                if (memory == null) {
                    if (!output.getText().toString().equals("")) {
                        memory = Double.parseDouble(output.getText().toString()) * -1;
                    } else {
                        memory = Double.parseDouble(output.getHint().toString()) * -1;
                    }
                } else {
                    if (!output.getText().toString().equals("")) {
                        memory = memory - Double.parseDouble(output.getText().toString());
                    } else {
                        memory = memory - Double.parseDouble(output.getHint().toString());
                    }
                }
                output.setHint(output.getText().toString());
                output.setText("");
            }
        });

        btnRecallClearMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showMemory) {
                    memory = null;
                    showMemory = false;
                }
                if (memory != null) {
                    output.setHint(removeZeroes(memory));
                    output.setText("");
                    showMemory = true;
                }
                System.out.println(memory);
            }
        });
    }

    private void digitCounter() {
        int counter = 0;
        StringBuilder numLimit = new StringBuilder();
        char[] numArray = output.getText().toString().toCharArray();
        for (char c : numArray) {
            if (counter == 12) {
                break;
            } else if (counter < 12 && String.valueOf(c).equals("-") || String.valueOf(c).equals(".")) {
                numLimit.append(c);
            } else if (counter < 12) {
                numLimit.append(c);
                counter++;
            }
        }
        output.setText(numLimit.toString());
    }

    private String removeZeroes(double x) {
        DecimalFormat format = new DecimalFormat("0.############");
        return format.format(x);
    }

    private void equalsFunction() {
        if (!output.getText().toString().equals("") && (addOperationOn || subtractOperationOn || multiplyOperationOn || divideOperationOn)) {
            operator = Double.valueOf(output.getText().toString());
            if (operand!=null) {
                if (addOperationOn) {
                    operand = operand + operator;
                    output.setText("");
                    output.setHint(removeZeroes(operand));
                    addOperationOn = false;
                } else if (subtractOperationOn) {
                    operand = operand - operator;
                    output.setText("");
                    output.setHint(removeZeroes(operand));
                    subtractOperationOn = false;
                } else if (multiplyOperationOn) {
                    operand = operand * operator;
                    output.setText("");
                    output.setHint(removeZeroes(operand));
                    multiplyOperationOn = false;
                } else if (divideOperationOn) {
                    if (operand != 0.0 || operator != 0.0) {
                        operand = operand / operator;
                    } else {
                        operand = 0.0;
                    }
                    output.setText("");
                    output.setHint(removeZeroes(operand));
                    divideOperationOn = false;
                }
            }
        }
    }
}