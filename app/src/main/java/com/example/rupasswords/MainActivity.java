package com.example.rupasswords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String[] russians;
    private String[] latins;
    private PasswordsHelper helper;
    private EditText sourceEditText;
    private TextView resultTextView;
    private View copyButton;
    private TextView passwordRating;
    private ImageView reliabilityIndicator;
    private CompoundButton checkCaps;
    private CompoundButton checkDigits;
    private CompoundButton checkSymbols;
    private SeekBar seekBarSymbols;
    private TextView countOfSymbols;
    private TextView textGenerated;
    private View copyButtonGenerated;
    private Button buttonGenerated;

    private void initUI() {
        sourceEditText = findViewById(R.id.inputText_editText);
        resultTextView = findViewById(R.id.result_textView);
        copyButton = findViewById(R.id.result_copy_button);
        passwordRating = findViewById(R.id.level_password_textView);
        reliabilityIndicator = findViewById(R.id.indicator_imageView);
        checkCaps = findViewById(R.id.caps_checkBox);
        checkDigits = findViewById(R.id.digits_checkBox);
        checkSymbols = findViewById(R.id.special_checkBox);
        seekBarSymbols = findViewById(R.id.seekBar);
        countOfSymbols = findViewById(R.id.password_Length_textView);
        textGenerated = findViewById(R.id.generated_result_textView);
        copyButtonGenerated = findViewById(R.id.generated_result_copy_button);
        buttonGenerated = findViewById(R.id.generated_button);
        russians = getResources().getStringArray(R.array.russians);
        latins = getResources().getStringArray(R.array.latin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.mainActivityTitle_string);

        initUI();
        helper = new PasswordsHelper(russians, latins);



        countOfSymbols.setText("8 " + getResources().getQuantityString(R.plurals.symbols_count, 8) + " + " +
                "0 " + getResources().getQuantityString(R.plurals.symbols_count, 0));

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (manager != null) {
                    manager.setPrimaryClip(ClipData.newPlainText(getString(R.string.clipboard_title), resultTextView.getText().toString()));
                    Toast.makeText(MainActivity.this, getString(R.string.toast_copied), Toast.LENGTH_SHORT).show();
                }
            }
        });


        sourceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resultTextView.setText(helper.convert(charSequence));
                copyButton.setEnabled(charSequence.length() > 0);
                passwordRating.setText(helper.getRating(charSequence.length(), reliabilityIndicator));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        seekBarSymbols.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                countOfSymbols.setText("8 " + getResources().getQuantityString(R.plurals.symbols_count, 8) + " + " +
                        seekBar.getProgress() + " " +
                        getResources().getQuantityString(R.plurals.symbols_count, seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        copyButtonGenerated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (manager != null) {
                    manager.setPrimaryClip(ClipData.newPlainText(getString(R.string.clipboard_title), textGenerated.getText().toString()));
                    Toast.makeText(MainActivity.this, getString(R.string.toast_copied), Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonGenerated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textGenerated.setText(helper.generatePassword(8 + seekBarSymbols.getProgress(),
                        checkCaps.isChecked(), checkDigits.isChecked(), checkSymbols.isChecked()));
            }
        });
    }
}


