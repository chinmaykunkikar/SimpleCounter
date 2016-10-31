package chinmay.simplecounter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainCounter extends AppCompatActivity {

    private static final int INITIAL_COUNTER = 0;
    private static final String INCREMENT_BUTTON = "+";
    private static final String DECREMENT_BUTTON = "-";
    private static int INCREMENT_VALUE = 1;
    private int counter;
    private TextView counterView;
    private Button masterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_counter);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Intro Activity
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                if (isFirstStart) {
                    Intent i = new Intent(MainCounter.this, IntroActivity.class);
                    startActivity(i);

                    SharedPreferences.Editor e = getPrefs.edit();
                    e.putBoolean("firstStart", false);
                    e.apply();
                }
            }
        });
        t.start();

        // Declarations
        counter = INITIAL_COUNTER;
        counterView = (TextView) findViewById(R.id.main_counter);
        masterButton = (Button) findViewById(R.id.master_button);

        // Custom Typeface
        Typeface robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        counterView.setTypeface(robotoLight);
        masterButton.setTypeface(robotoLight);

        // Set Master Button Text
        masterButton.setText(INCREMENT_BUTTON);

        sanityCheck();

        // Click Listeners
        masterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (masterButton.getText().toString().equals(INCREMENT_BUTTON)) {
                    masterButton.setText(DECREMENT_BUTTON);
                } else if (masterButton.getText().toString().equals(DECREMENT_BUTTON)) {
                    masterButton.setText(INCREMENT_BUTTON);
                } else {
                    masterButton.setText(INCREMENT_BUTTON);
                }
            }
        });

        counterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (masterButton.getText().toString().equals(INCREMENT_BUTTON)) {
                    counter = counter + INCREMENT_VALUE;
                } else if (masterButton.getText().toString().equals(DECREMENT_BUTTON)) {
                    counter = counter - INCREMENT_VALUE;
                }
                Vibrator counterClick = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                counterClick.vibrate(25);
                counterView.setText(String.valueOf(counter));
                sanityCheck();
            }
        });

        counterView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                counter = INITIAL_COUNTER;
                counterView.setText(String.valueOf(counter));
                sanityCheck();
                Snackbar snackbar = Snackbar.make(counterView, R.string.message_reset, Snackbar.LENGTH_LONG);
                snackbar.setAction(null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO Undo action here
                    }
                });
                snackbar.show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_counter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent prefs = new Intent(this, Prefs.class);
                startActivity(prefs);
                return true;
            case R.id.action_edit:
                showEditDialog();
                break;
            case R.id.action_increment:
                showIncrementDialog();
                break;
            default:
                return true;
        }
        return true;
    }

    private void showEditDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View editCounter = layoutInflater.inflate(R.layout.counter_edit, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(editCounter);
        final EditText counterInput = (EditText) editCounter.findViewById(R.id.counter_edit_text);
        counterInput.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        counterInput.setText(counterView.getText().toString());
        counterInput.setSelectAllOnFocus(true);
        counterInput.requestFocus();
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (counterInput.getText().toString().trim().equals("")) {
                                    return;
                                } else if (counterInput.getText().toString().matches("^[-0-9]*$")) {
                                    counter = Integer.parseInt(counterInput.getText().toString());
                                } else {
                                    return;
                                }
                                counterView.setText(String.valueOf(counter));
                                sanityCheck();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();
    }

    private void showIncrementDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View editCounter = layoutInflater.inflate(R.layout.counter_increment, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(editCounter);
        final EditText counterInput = (EditText) editCounter.findViewById(R.id.counter_edit_text);
        counterInput.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        counterInput.setText(String.valueOf(INCREMENT_VALUE));
        counterInput.setSelectAllOnFocus(true);
        counterInput.requestFocus();
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (counterInput.getText().toString().trim().equals("")) {
                                    return;
                                } else if (counterInput.getText().toString().matches("^[0-9]*$")) {
                                    INCREMENT_VALUE = Integer.parseInt(counterInput.getText().toString());
                                } else {
                                    return;
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alertDialog.show();
    }

    private void sanityCheck() {

        if (counter > 99) {
            counterView.setTextSize(196);
        }
        if (counter < 100) {
            counterView.setTextSize(240);
        }
        if (counter > 999) {
            counterView.setTextSize(146);
        }
        if (counter < -9) {
            counterView.setTextSize(196);
        }
        if (counter < -99) {
            counterView.setTextSize(146);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", counter);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("counter");
    }
}
