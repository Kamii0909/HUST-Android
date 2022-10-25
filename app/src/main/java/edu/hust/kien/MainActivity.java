package edu.hust.kien;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import edu.hust.kien.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    TextView resultView;

    OnClickListener numberButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil
                .setContentView(this, R.layout.activity_main);

        resultView = binding.resultView;

        numberButtonListener = view -> {
            String s = resultView.getText().toString();
            if(s.equals("0")){
                s = ((Button) view).getText().toString();
            }
            else s = resultView.getText().toString()
                    + ((Button) view).getText().toString();
            resultView.setText(s);
        };
        initNumberButtonListener();
    }

    private void initNumberButtonListener() {
        binding.button0.setOnClickListener(numberButtonListener);
        binding.button1.setOnClickListener(numberButtonListener);
        binding.button2.setOnClickListener(numberButtonListener);
        binding.button3.setOnClickListener(numberButtonListener);
        binding.button4.setOnClickListener(numberButtonListener);
        binding.button5.setOnClickListener(numberButtonListener);
        binding.button6.setOnClickListener(numberButtonListener);
        binding.button7.setOnClickListener(numberButtonListener);
        binding.button8.setOnClickListener(numberButtonListener);
        binding.button9.setOnClickListener(numberButtonListener);
    }

    private static CharSequence joinSpannables(String delimiter,
                                              CharSequence... text) {
        if (text.length == 0) {
            return "";
        }

        if (text.length == 1) {
            return text[0];
        }

        boolean spanned = false;
        for (CharSequence sequence : text) {
            if (sequence instanceof Spanned) {
                spanned = true;
                break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length; i++) {
            if (i > 0) {
                sb.append(delimiter);
            }
            sb.append(text[i]);
        }

        if (!spanned) {
            return sb.toString();
        }

        SpannableString ss = new SpannableString(sb);
        int off = 0;
        for (CharSequence charSequence : text) {
            int len = charSequence.length();

            if (charSequence instanceof Spanned) {
                TextUtils.copySpansFrom((Spanned) charSequence, 0, len,
                        Object.class, ss, off);
            }

            off += len + delimiter.length();
        }

        return new SpannedString(ss);
    }

}
