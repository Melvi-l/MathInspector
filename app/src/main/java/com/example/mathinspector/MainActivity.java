package com.example.mathinspector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements UserGuideFragment.FragmentCallback {

    private int dividend;
    private int divider;
    private int quotient;
    private int reminder;
    private TextView displayText;
    private Button moduloButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        displayText = findViewById(R.id.display);
        moduloButton = findViewById(R.id.moduloButton);
        setLongClickListener();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.about) {
            showAboutDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setLongClickListener() {
        int [] buttonIdList = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8,R.id.button9, R.id.button0, R.id.buttonC, R.id.buttonDEL};
        for (int buttonId: buttonIdList) {
            Button button = findViewById(buttonId);

            button.setOnLongClickListener(view -> {
                onLongButtonClick(view);
                return true;
            });
        }
    }
    public boolean onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        if (buttonText.equals("C")) {
            dividend = 0;
        } else if (buttonText.equals("DEL")) {
            dividend = dividend / 10;
        } else {
            int digit = Integer.parseInt(buttonText);
            dividend = dividend * 10 + digit;
        }
        displayDividend();
        return true;
    }
    private void displayDividend() {
        displayText.setText(String.valueOf(dividend));
    }
    public boolean onLongButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        if (buttonText.equals("C")) {
            divider = 0;
        } else if (buttonText.equals("DEL")) {
            divider = divider / 10;
        } else {
            int digit = Integer.parseInt(buttonText);
            divider = divider * 10 + digit;
        }
        displayDivider();
        return true;
    }
    private void displayDivider() {
        moduloButton.setText(getString(R.string.modulo) + " " + divider);
    }

    public void displayModulo(View view) {
        if (divider == 0) {
            Snackbar.make(MainActivity.this, view, getString(R.string.snackbar_divider), Snackbar.LENGTH_SHORT).show();
            return;
        }
        computeModulo();
        displayReminder();
        Snackbar.make(MainActivity.this, view, getString(R.string.snackbar_quotient) + String.valueOf(quotient) + " !", Snackbar.LENGTH_SHORT).show();
    }
    private void displayReminder() {
        dividend = 0;
        displayText.setText(String.valueOf(reminder));
    }
    private void computeModulo() {
        quotient = dividend / divider;
        reminder = dividend % divider;
    }
    public void displayIsPrime(View view) {
        String text  = "";
        if (isPrime(dividend)) {
            text = getString(R.string.snackbar_number) + String.valueOf(dividend) + getString(R.string.snackbar_is_prime);
        } else {
            text = getString(R.string.snackbar_number) + String.valueOf(dividend) + getString(R.string.snackbar_isnt_prime);
        }
        Snackbar.make(MainActivity.this, view, text, Snackbar.LENGTH_SHORT).show();
    }
    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        if (number <= 3) {
            return true;
        }
        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

    public void displayUserGuide(View view) {
        UserGuideFragment fragment = new UserGuideFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(
                        R.id.user_guide_fragment_container,
                        fragment,
                        "user-guide"
                )
                .commit();
    }

    public void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.about_title);
        builder.setMessage(R.string.about_text);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("user-guide");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
    }
}