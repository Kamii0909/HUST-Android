package edu.hust.kien;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.hust.kien.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private List<Button> numberButtons;
    private List<Button> operationButtons;
    private double lastNum, currNum;
    private View clickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);

        addButtonsToList();
        addListenerForNumberButtons(getNumberButtonListener());
        addListenerForOperationButtons(getOperationButtonListener());
    }

    private OnClickListener getNumberButtonListener() {
        return view -> {
            String s = getResultViewText().equals("0")
                    && view.getId() != R.id.buttonDot
                    ? "": getResultViewText();
            setResultViewText(s + getButtonText(view));
        };
    }

    private OnClickListener getOperationButtonListener() {
        return view -> {
            if(clickedButton != null){
                currNum = Double.parseDouble(getResultViewText().substring(1));
                switch (getButtonText(clickedButton)) {
                    case "+": lastNum += currNum; break;
                    case "-": lastNum -= currNum; break;
                    case "*": lastNum *= currNum; break;
                    case "/": lastNum /= currNum; break;
                    case "=": clickedButton = null;
                    default:
                        throw new IllegalStateException(
                                "Undefined switch value: "
                                + getButtonText(clickedButton)
                        );
                }
                clickedButton = view;
                setResultViewText(String.valueOf(lastNum));

            }
            else {
                clickedButton = view;
                lastNum = Double.parseDouble(getResultViewText());
                binding.resultView.setText(getButtonText(view));
            }
        };
    }

    private void addListenerForNumberButtons(OnClickListener listener) {
        for(Button button: numberButtons){
            button.setOnClickListener(listener);
        }
    }
    private void addListenerForOperationButtons(OnClickListener listener) {
        for(Button button: operationButtons){
            button.setOnClickListener(listener);
        }
    }

    private void addButtonsToList() {
        numberButtons = new ArrayList<>();
        numberButtons.addAll(
                Arrays.asList(
                        binding.button0,
                        binding.button1,
                        binding.button2,
                        binding.button3,
                        binding.button4,
                        binding.button5,
                        binding.button6,
                        binding.button7,
                        binding.button8,
                        binding.button9,
                        binding.buttonDot
                ));

        operationButtons = new ArrayList<>();
        operationButtons.addAll(
                Arrays.asList(
                        binding.buttonPlus,
                        binding.buttonMinus,
                        binding.buttonMultiply,
                        binding.buttonDivide,
                        binding.buttonEqual
                ));
    }

    private String getResultViewText() {
        return binding.resultView.getText().toString();
    }
    private void setResultViewText(String string) {
        binding.resultView.setText(string);
    }

    private String getButtonText(View view) {
        if(view instanceof Button)
            return ((Button) view).getText().toString();
        else throw new UnsupportedOperationException("Given view is not a button");
    }
}
