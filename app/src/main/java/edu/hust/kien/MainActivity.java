package edu.hust.kien;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.hust.kien.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private List<Button> numberButtons;
    private List<Button> operationButtons;

    private boolean hasDot = false;
    private double lastNum, currNum;
    private View lastClickedButton;
    private final NumberFormat formatter = new DecimalFormat("#.########");

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
            if (isButtonDot(view)) {
                if (!hasDot) {
                    setQueryViewText(getQueryViewText() + ".");
                    hasDot = true;
                } else {
                    Snackbar.make(
                            binding.resultView,
                            "Cannot have 2 dots in a number",
                            500).show();
                }
            } else {
                String prepend =
                        getQueryViewText().equals("0")
                                ? "" : getQueryViewText();
                setQueryViewText(prepend + getButtonText(view));
            }
        };
    }


    private OnClickListener getOperationButtonListener() {
        return view -> {


            if(lastClickedButton != null){
                currNum = Double.parseDouble(getQueryViewText().substring(1));
                switch (getButtonText(lastClickedButton)) {
                    case "+":
                        lastNum += currNum;
                        break;
                    case "-":
                        lastNum -= currNum;
                        break;
                    case "*":
                        lastNum *= currNum;
                        break;
                    case "/":
                        lastNum /= currNum;
                        break;
                    case "=":
                        break;
                    default:
                        throw new IllegalStateException(
                                "Undefined switch value: "
                                        + getButtonText(lastClickedButton)
                        );
                }
                setResultViewText(formatter.format(lastNum));
            }
            else{
                lastNum = Double.parseDouble(getQueryViewText());
            }



            if(!isButtonEqual(view)){
                setQueryViewText(getButtonText(view));
                lastClickedButton = view;
                hasDot = false;
            }
            else {
                lastClickedButton = null;
                setQueryViewText(formatter.format(lastNum));
                hasDot = getQueryViewText().contains(".");
            }
        };
    }

    private void addListenerForNumberButtons(OnClickListener listener) {
        for (Button button : numberButtons) {
            button.setOnClickListener(listener);
        }
    }

    private void addListenerForOperationButtons(OnClickListener listener) {
        for (Button button : operationButtons) {
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

    private String getQueryViewText() {
        return binding.queryView.getText().toString();
    }

    private void setQueryViewText(String string) {
        binding.queryView.setText(string);
    }

    private void setResultViewText(String string) {
        binding.resultView.setText(string);
    }

    private String getButtonText(View view) {
        if (view instanceof Button)
            return ((Button) view).getText().toString();
        else throw new UnsupportedOperationException("Given view is not a button");
    }

    private boolean isButtonDot(View view) {
        return binding.buttonDot.equals(view);
    }

    private boolean isButtonEqual(View view) {
        return binding.buttonEqual.equals(view);
    }
}
