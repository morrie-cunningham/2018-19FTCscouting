package com.example.morri.ftc_scouting_2017;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinnerDT = (Spinner) findViewById(R.id.driveTeamSpinner);
        ArrayAdapter<CharSequence> DTadapt = ArrayAdapter.createFromResource(this, R.array.DTR, android.R.layout.simple_spinner_item);
        DTadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDT.setAdapter(DTadapt);

        Spinner spinnerTeams = (Spinner) findViewById(R.id.teamNumberSpinner);
        ArrayAdapter<CharSequence> TeamsAdapt = ArrayAdapter.createFromResource(this, R.array.AllendaleQualifierTeams, android.R.layout.simple_spinner_item);
        TeamsAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeams.setAdapter(TeamsAdapt);

        Switch allianceSwitch = (Switch) findViewById(R.id.allianceColor);

        EditText numGold = (EditText) findViewById(R.id.numOfGoldInLander);
        EditText numSilver = (EditText) findViewById(R.id.numOfSilverInLander);
        EditText scoutName = (EditText) findViewById(R.id.scoutName);
        EditText matchNum = (EditText) findViewById(R.id.matchNumber);

        CheckBox autoDescend = (CheckBox) findViewById(R.id.checkBox_descend);
        CheckBox autoMarker = (CheckBox) findViewById(R.id.checkBox_marker);
        CheckBox autoGoldMinMoved = (CheckBox) findViewById(R.id.checkBox_goldMinMoved);
        CheckBox autoGoldInLander = (CheckBox) findViewById(R.id.checkBox_goldInLander);
        CheckBox autoParked = (CheckBox) findViewById(R.id.checkBox_parked);

        CheckBox endClimbing = (CheckBox) findViewById(R.id.checkBox_climbing);
        CheckBox endInCrater = (CheckBox) findViewById(R.id.checkBox_inCrater);
        CheckBox endCompletelyInCrater = (CheckBox) findViewById(R.id.checkBox_completelyInCrater);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button DGButton = (Button) findViewById(R.id.buttonDownGold);
        Button DSButton = (Button) findViewById(R.id.buttonDownSilver);
        Button UGButton = (Button) findViewById(R.id.buttonUpGold);
        Button USButton = (Button) findViewById(R.id.buttonUpSilver);

        DGButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numGold.getText().toString());
            if(start > 0) {
                start--;
                String result = String.valueOf(start);
                numGold.setText(result);
            }
        });
        DSButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numSilver.getText().toString());
            if(start > 0) {
                start--;
                String result = String.valueOf(start);
                numSilver.setText(result);
            }
        });
        UGButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numGold.getText().toString());
            start++;
            String result = String.valueOf(start);
            numGold.setText(result);
        });
        USButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numSilver.getText().toString());
            start++;
            String result = String.valueOf(start);
            numSilver.setText(result);
        });

        submitButton.setOnClickListener(v -> {
            if (scoutName.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.myConstraintLayout), "Please enter Scout Name", Snackbar.LENGTH_SHORT).show();
            } else if (spinnerTeams.getSelectedItem().equals("")) {
                Snackbar.make(findViewById(R.id.myConstraintLayout), "Please enter Team Number", Snackbar.LENGTH_SHORT).show();
            } else if (matchNum.getText().toString().equals("")) {
                Snackbar.make(findViewById(R.id.myConstraintLayout), "Please enter Match Number", Snackbar.LENGTH_SHORT).show();
            } else {
                JSONObject output = new JSONObject();
                try {
                    output.put("name", scoutName.getText());
                    output.put("team_number", spinnerTeams.getSelectedItem().toString());
                    output.put("match_number", matchNum.getText());
                    output.put("auto_descend", autoDescend.isChecked());
                    output.put("auto_marker", autoMarker.isChecked());
                    output.put("auto_goldMinMoved", autoGoldMinMoved.isChecked());
                    output.put("auto_parked", autoParked.isChecked());
                    output.put("auto_goldInLander", autoGoldInLander.isChecked());
                    output.put("tele_gold", Integer.parseInt(numGold.getText().toString()));
                    output.put("tele_silver", Integer.parseInt(numSilver.getText().toString()));
                    output.put("end_climbing", endClimbing.isChecked());
                    output.put("end_inCrater", endInCrater.isChecked());
                    output.put("end_completelyInCrater", endCompletelyInCrater.isChecked());
                    output.put("drive_team", spinnerDT.getSelectedItem().toString());
                    Log.d("output", output.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Write JSON to file
                String outStr = matchNum.getText() + "-" + spinnerTeams.getSelectedItem().toString() + ".json";

                File dir = new File(Environment.getExternalStorageDirectory() + "/scoutingFiles/");
                if(!dir.exists()){
                    dir.mkdir();
                }

                try{
                    File outFile = new File(dir, outStr);
                    FileOutputStream out = new FileOutputStream(outFile);
                    out.write(output.toString().getBytes());
                    out.close();
                } catch (IOException e) {
                    Log.e("Exception", "File write failed: " + e.toString());
                }

                Snackbar.make(findViewById(R.id.myConstraintLayout), "Successfully Saved: " + outStr, Snackbar.LENGTH_SHORT).show();

                scoutName.setText("");
                spinnerTeams.setSelection(0);
                allianceSwitch.setChecked(false);
                matchNum.setText("");
                autoDescend.setChecked(false);
                autoMarker.setChecked(false);
                autoGoldMinMoved.setChecked(false);
                autoParked.setChecked(false);
                autoGoldInLander.setChecked(false);
                numGold.setText("0");
                numSilver.setText("0");
                endClimbing.setChecked(false);
                endInCrater.setChecked(false);
                endCompletelyInCrater.setChecked(false);
                spinnerDT.setSelection(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_files) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
