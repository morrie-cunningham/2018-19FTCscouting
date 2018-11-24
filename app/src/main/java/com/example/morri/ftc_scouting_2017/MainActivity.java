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

//        Spinner spinnerJewel = (Spinner) findViewById(R.id.jewelSpinner);
//        ArrayAdapter<CharSequence> Jadapt = ArrayAdapter.createFromResource(this, R.array.ASN, android.R.layout.simple_spinner_item);
//        Jadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerJewel.setAdapter(Jadapt);

//        Spinner spinnerGlyph = (Spinner) findViewById(R.id.glyphSpinner);
//        ArrayAdapter<CharSequence> Gadapt = ArrayAdapter.createFromResource(this, R.array.ASR, android.R.layout.simple_spinner_item);
//        Gadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerGlyph.setAdapter(Gadapt);

//        Spinner spinnerSZone = (Spinner) findViewById(R.id.safeZoneSpinner);
//        ArrayAdapter<CharSequence> SZadapt = ArrayAdapter.createFromResource(this, R.array.ASN, android.R.layout.simple_spinner_item);
//        SZadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerSZone.setAdapter(SZadapt);

        Spinner spinnerRZone = (Spinner) findViewById(R.id.relicZoneSpinner);
        ArrayAdapter<CharSequence> RZadapt = ArrayAdapter.createFromResource(this, R.array.RZ, android.R.layout.simple_spinner_item);
        RZadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRZone.setAdapter(RZadapt);

        Spinner spinnerRUp = (Spinner) findViewById(R.id.relicUprightSpinner);
        ArrayAdapter<CharSequence> RUadapt = ArrayAdapter.createFromResource(this, R.array.YN, android.R.layout.simple_spinner_item);
        RUadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRUp.setAdapter(RUadapt);

        Spinner spinnerBB = (Spinner) findViewById(R.id.balancingBoardSpinner);
        ArrayAdapter<CharSequence> BBadapt = ArrayAdapter.createFromResource(this, R.array.YN, android.R.layout.simple_spinner_item);
        BBadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBB.setAdapter(BBadapt);

        Spinner spinnerDT = (Spinner) findViewById(R.id.driveTeamSpinner);
        ArrayAdapter<CharSequence> DTadapt = ArrayAdapter.createFromResource(this, R.array.DTR, android.R.layout.simple_spinner_item);
        DTadapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDT.setAdapter(DTadapt);

        Spinner spinnerTeams = (Spinner) findViewById(R.id.teamNumberSpinner);
        ArrayAdapter<CharSequence> TeamsAdapt = ArrayAdapter.createFromResource(this, R.array.SRTeams, android.R.layout.simple_spinner_item);
        TeamsAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeams.setAdapter(TeamsAdapt);

        Switch allianceSwitch = (Switch) findViewById(R.id.allianceColor);

        EditText numGlyphs = (EditText) findViewById(R.id.numOfGlyphs);
        EditText numRows = (EditText) findViewById(R.id.numOfRows);
        EditText numColumns = (EditText) findViewById(R.id.numOfColumns);
        EditText scoutName = (EditText) findViewById(R.id.scoutName);
        EditText matchNum = (EditText) findViewById(R.id.matchNumber);

        CheckBox autoJewel = (CheckBox) findViewById(R.id.checkBox_jewel);
        CheckBox autoGlyph = (CheckBox) findViewById(R.id.checkBox_glyph);
        CheckBox autoColumn = (CheckBox) findViewById(R.id.checkBox_correctColumn);
        CheckBox autoSafeZone = (CheckBox) findViewById(R.id.checkBox_safezone);
        CheckBox boxPattern = (CheckBox) findViewById(R.id.checkBox);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        Button DGButton = (Button) findViewById(R.id.buttonDownGlyph);
        Button DRButton = (Button) findViewById(R.id.buttonDownRow);
        Button DCButton = (Button) findViewById(R.id.buttonDownColumn);
        Button UGButton = (Button) findViewById(R.id.buttonUpGlyph);
        Button URButton = (Button) findViewById(R.id.buttonUpRow);
        Button UCButton = (Button) findViewById(R.id.buttonUpColumn);

        UGButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numGlyphs.getText().toString());
            if(start < 24) {
                start++;
                String result = String.valueOf(start);
                numGlyphs.setText(result);
            }
        });
        URButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numRows.getText().toString());
            if(start < 8) {
                start++;
                String result = String.valueOf(start);
                numRows.setText(result);
            }
        });
        UCButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numColumns.getText().toString());
            if(start < 6) {
                start++;
                String result = String.valueOf(start);
                numColumns.setText(result);
            }
        });

        DGButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numGlyphs.getText().toString());
            if(start > 0) {
                start--;
                String result = String.valueOf(start);
                numGlyphs.setText(result);
            }
        });
        DRButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numRows.getText().toString());
            if(start > 0) {
                start--;
                String result = String.valueOf(start);
                numRows.setText(result);
            }
        });
        DCButton.setOnClickListener(v -> {
            int start = Integer.parseInt(numColumns.getText().toString());
            if(start > 0) {
                start--;
                String result = String.valueOf(start);
                numColumns.setText(result);
            }
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
                    output.put("auto_jewel", autoJewel.isChecked());
                    output.put("auto_glyph", autoGlyph.isChecked());
                    output.put("auto_glyphColumn", autoColumn.isChecked());
                    output.put("auto_safezone", autoSafeZone.isChecked());
                    output.put("tele_glyphs", Integer.parseInt(numGlyphs.getText().toString()));
                    output.put("tele_rows", Integer.parseInt(numRows.getText().toString()));
                    output.put("tele_columns", Integer.parseInt(numColumns.getText().toString()));
                    output.put("tele_completePattern", boxPattern.isChecked());
                    output.put("end_reliczone", spinnerRZone.getSelectedItem().toString());
                    output.put("end_relicupright", spinnerRUp.getSelectedItem().toString());
                    output.put("end_balancing", spinnerBB.getSelectedItem().toString());
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
                autoJewel.setChecked(false);
                autoGlyph.setChecked(false);
                autoColumn.setChecked(false);
                autoSafeZone.setChecked(false);
                numGlyphs.setText("0");
                numRows.setText("0");
                numColumns.setText("0");
                boxPattern.setChecked(false);
                spinnerRZone.setSelection(0);
                spinnerRUp.setSelection(0);
                spinnerBB.setSelection(0);
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
