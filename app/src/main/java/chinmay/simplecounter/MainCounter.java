package chinmay.simplecounter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainCounter extends AppCompatActivity {

    int counter;
    TextView counterView;
    Button increment;
    Button decrement;
    private ClipboardManager clipboardManager;
    private ClipData clipData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Declarations
        counter = 0;
        counterView = (TextView) findViewById(R.id.main_counter);
        increment = (Button) findViewById(R.id.increment);
        decrement = (Button) findViewById(R.id.decrement);
        final FloatingActionButton reset_fab = (FloatingActionButton) findViewById(R.id.reset_fab);
        clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        sanityCheck();
        // onClickListeners
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                counterView.setText(String.valueOf(counter));
                sanityCheck();
            }
        });

        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                counterView.setText(String.valueOf(counter));
                sanityCheck();
            }
        });

        counterView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clipData = ClipData.newPlainText("text", "The counter result is " + counterView.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), R.string.counter_clipboard, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        assert reset_fab != null;
        reset_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = 0;
                counterView.setText(String.valueOf(counter));
                sanityCheck();
                Snackbar snackbar = Snackbar.make(reset_fab, R.string.message_reset, Snackbar.LENGTH_LONG);
                snackbar.setAction(null, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO Undo action here
                    }
                });
                snackbar.show();
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
                return true;
            case R.id.action_edit:
                showEditDialog();
            default:
                return true;
        }
    }

    public void showEditDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View editCounter = layoutInflater.inflate(R.layout.counter_edit, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(editCounter);
        final EditText counterInput = (EditText) editCounter.findViewById(R.id.counter_edit_text);
        counterInput.setInputType(InputType.TYPE_CLASS_NUMBER);
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
                                } else {
                                    counter = Integer.parseInt(counterInput.getText().toString());
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

    public void sanityCheck() {
        if (counter <= 0) {
            decrement.setEnabled(false);
        } else if (counter > 0) {
            decrement.setEnabled(true);
        }
        if (counter > 99) {
            counterView.setTextSize(196);
        }
        if (counter < 100) {
            counterView.setTextSize(240);
        }
        if (counter > 999) {
            counterView.setTextSize(146);
        }
    }
}
