package com.example.kalkulator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.Expression;

public class MainActivity extends AppCompatActivity {
    private int[] numericButtons = {R.id.nol, R.id.satu, R.id.dua, R.id.tiga, R.id.empat, R.id.lima,
            R.id.enam, R.id.tujuh, R.id.delapan, R.id.sembilan};
    private int [] operatorButtons = {R.id.tambah, R.id.kurang, R.id.kali, R.id.bagi};
    private TextView hasil;
    private boolean lastNumeric;
    private boolean stateError;
    private boolean lastDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Kalkulator Sederhana");
        this.hasil = (TextView)findViewById(R.id.hasil);
        setNumericOnClickListener();
        setOperetaorOnClickListener();
    }
    private void setNumericOnClickListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if (stateError){
                    hasil.setText(button.getText());
                    stateError = false;
                } else {
                    hasil.append(button.getText());
                }
                lastNumeric = true;
            }
        };
        for (int id : numericButtons){
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void setOperetaorOnClickListener(){
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError){
                    Button button = (Button) v;
                    hasil.append(button.getText());
                    lastNumeric = false;
                    lastDot = false;
                }
            }
        };
        for (int id : operatorButtons){
            findViewById(id).setOnClickListener(listener);
        }

        findViewById(R.id.koma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot){
                    hasil.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });

        findViewById(R.id.hapus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasil.setText("");
                lastNumeric = false;
                stateError = false;
                lastDot = false;
            }
        });

        findViewById(R.id.total).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                samadengan();
            }
        });
    }
    private void samadengan(){
        if (lastNumeric && !stateError) {
            String hasiltambah = hasil.getText().toString();
            Expression expression = new ExpressionBuilder(hasiltambah).build();
            try {
                double result = expression.evaluate();
                hasil.setText(Double.toString(result));
                lastDot = true;
            } catch (ArithmeticException ex){
                hasil.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }
}
