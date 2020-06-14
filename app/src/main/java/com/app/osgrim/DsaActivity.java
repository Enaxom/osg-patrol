package com.app.osgrim;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.app.osgrim.data.Alert;
import com.app.osgrim.data.DSA;
import com.app.osgrim.data.QualityRCP;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DsaActivity extends AppCompatActivity {

    private RadioGroup rgWitness, rgRcp, rgPulse, rgMoves;
    private RadioButton rbWitYes, rbWitNo, rbRcpYes, rbRcpNo, rbPulseYes, rbPulseNo, rbMovesYes, rbMovesNo;
    private Spinner alert, rcpQuality;
    private CheckBox rcp1, rcp2, rcp3, rcp4, rcp5, carotid, radial;
    private EditText other, time, nbChocs, timePulse, frequency, frequencyMoves, comments;
    private Button btnSubmit, btnCancel;

    private DsaActivity myActivity;
    private Calendar myCalendar;

    private BilanFoncActivity bilanFonc;
    private MainActivity mainAct;
    private DSA dsa;
    private LinearLayout llPulse, llMoves, llPulse2;
    private LinearLayout.LayoutParams paramsOpen, paramsClose;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dsa_sheet);

        llPulse = findViewById(R.id.llPulse);
        llPulse2 = findViewById(R.id.llPulse2);
        llMoves = findViewById(R.id.llMoves);

        paramsOpen = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        paramsClose = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                0);

        rgWitness = findViewById(R.id.rgWitness);
        rgRcp = findViewById(R.id.rgRCP);
        rgPulse = findViewById(R.id.rgPulse);
        rgMoves = findViewById(R.id.rgMoves);
        rbWitYes = findViewById(R.id.rbYesWit);
        rbWitNo = findViewById(R.id.rbNoWit);
        rbRcpYes = findViewById(R.id.rbYesRCP);
        rbRcpNo = findViewById(R.id.rbNoRCP);
        rbPulseYes = findViewById(R.id.rbYesPulse);
        rbPulseNo = findViewById(R.id.rbNoPulse);
        rbMovesYes = findViewById(R.id.rbYesMoves);
        rbMovesNo = findViewById(R.id.rbNoMoves);

        alert = findViewById(R.id.spinAlert);
        rcpQuality = findViewById(R.id.spinRcpQuality);

        rcp1 = findViewById(R.id.cbRcp1);
        rcp2 = findViewById(R.id.cbRcp2);
        rcp3 = findViewById(R.id.cbRcp3);
        rcp4 = findViewById(R.id.cbRcp4);
        rcp5 = findViewById(R.id.cbRcp5);
        carotid = findViewById(R.id.cbCarotid);
        radial = findViewById(R.id.cbRadial);

        other = findViewById(R.id.etOther);
        time = findViewById(R.id.etTimeDsa);
        nbChocs = findViewById(R.id.etNbChoc);
        timePulse = findViewById(R.id.etTime);
        frequency = findViewById(R.id.etFrequence);
        frequencyMoves = findViewById(R.id.etFrequenceMoves);
        comments = findViewById(R.id.etComments);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);

        mainAct = MainActivity.getInstance();
        myActivity = this;
        initializeElements();

        bilanFonc = BilanFoncActivity.getInstance();
        dsa = new DSA();
    }

    private void initializeElements() {
        TextView tvWitness, tvAlert, tvRcpQuality, tvRcp, tvOther, tvDsaTime, tvNbChocs, tvPulse, tvMoves, tvComments, tvTime, tvFrequence, tvFrequenceMoves;

        tvWitness = findViewById(R.id.txtWitness);
        tvAlert = findViewById(R.id.txtAlert);
        tvRcpQuality = findViewById(R.id.txtRcpQuality);
        tvRcp = findViewById(R.id.txtRCP);
        tvOther = findViewById(R.id.txtOther);
        tvDsaTime = findViewById(R.id.txtTimeDsa);
        tvNbChocs = findViewById(R.id.txtNbChoc);
        tvPulse = findViewById(R.id.txtPulse);
        tvMoves = findViewById(R.id.txtMoves);
        tvComments = findViewById(R.id.txtComments);
        tvTime = findViewById(R.id.txtTime);
        tvFrequence = findViewById(R.id.txtFrequence);
        tvFrequenceMoves = findViewById(R.id.txtFrequenceMoves);

        tvWitness.setText(mainAct.labels.get("witness"));
        tvAlert.setText(mainAct.labels.get("alert"));
        tvRcpQuality.setText(mainAct.labels.get("rcpQuality"));
        tvRcp.setText(mainAct.labels.get("rcp"));
        tvOther.setText(mainAct.labels.get("other"));
        tvDsaTime.setText(mainAct.labels.get("timeDSA"));
        tvNbChocs.setText(mainAct.labels.get("chocsDSA"));
        tvPulse.setText(mainAct.labels.get("pulse"));
        tvMoves.setText(mainAct.labels.get("moves"));
        tvComments.setText(mainAct.labels.get("comment"));
        tvTime.setText(mainAct.labels.get("time"));
        tvFrequence.setText(mainAct.labels.get("frequence"));
        tvFrequenceMoves.setText(mainAct.labels.get("frequence"));

        rbWitYes.setText(mainAct.messages.get("yes"));
        rbWitNo.setText(mainAct.messages.get("no"));
        rbRcpYes.setText(mainAct.messages.get("yes"));
        rbRcpNo.setText(mainAct.messages.get("no"));
        rbPulseYes.setText(mainAct.messages.get("yes"));
        rbPulseNo.setText(mainAct.messages.get("no"));
        rbMovesYes.setText(mainAct.messages.get("yes"));
        rbMovesNo.setText(mainAct.messages.get("no"));

        btnSubmit.setText(mainAct.messages.get("ok"));
        btnCancel.setText(mainAct.messages.get("cancel"));

        rcp1.setText(mainAct.labels.get("dsa1"));
        rcp2.setText(mainAct.labels.get("dsa2"));
        rcp3.setText(mainAct.labels.get("dsa3"));
        rcp4.setText(mainAct.labels.get("dsa4"));
        rcp5.setText(mainAct.labels.get("dsa5"));

        carotid.setText(mainAct.labels.get("carotide"));
        radial.setText(mainAct.labels.get("radial"));


        rbPulseYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPulse.setLayoutParams(paramsOpen);
                llPulse2.setLayoutParams(paramsOpen);
            }
        });

        rbPulseNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPulse.setLayoutParams(paramsClose);
                llPulse2.setLayoutParams(paramsClose);
            }
        });

        rbMovesYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llMoves.setLayoutParams(paramsOpen);
            }
        });

        rbMovesNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llMoves.setLayoutParams(paramsClose);
            }
        });

        myCalendar = Calendar.getInstance();

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time.setError(null);
                TimePickerDialog timePickerDialog = new TimePickerDialog(myActivity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                        time.setText(String.format(Locale.getDefault(), "%02d:%02d", hours, minutes));
                    }
                }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        timePulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePulse.setError(null);
                TimePickerDialog timePickerDialog = new TimePickerDialog(myActivity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                        timePulse.setText(String.format(Locale.getDefault(), "%02d:%02d", hours, minutes));
                    }
                }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
            }
        });

        ArrayAdapter<Alert> alertAdapter = new ArrayAdapter<Alert>(this, R.layout.support_simple_spinner_dropdown_item, mainAct.alerts) {
            @Override
            public int getCount() {
                return mainAct.alerts.size() - 1;
            }
        };
        alert.setAdapter(alertAdapter);
        alert.setSelection(mainAct.alerts.size() - 1);

        ArrayAdapter<QualityRCP> qualityRCPAdapter = new ArrayAdapter<QualityRCP>(this, R.layout.support_simple_spinner_dropdown_item, mainAct.qualityRCPs) {
            @Override
            public int getCount() {
                return mainAct.alerts.size() - 1;
            }
        };
        rcpQuality.setAdapter(qualityRCPAdapter);
        rcpQuality.setSelection(mainAct.qualityRCPs.size() - 1);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bilanFonc.cancelDSA();
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                finish();
            }
        });

        Gson gson = new Gson();
        DSA dsa = gson.fromJson(getIntent().getStringExtra("dsa"), DSA.class);

        if (dsa != null) {
            display(dsa);
        }

    }

    private void save() {
        if (rbWitYes.isChecked())
            dsa.setWitness(1);

        dsa.setAlert((Alert) alert.getSelectedItem());

        if (rbRcpYes.isChecked())
            dsa.setRcp(1);

        dsa.setQualityRCP((QualityRCP) rcpQuality.getSelectedItem());

        List<String> dsaChecked = new ArrayList<>();

        if (rcp1.isChecked())
            dsaChecked.add(rcp1.getText().toString());

        if (rcp2.isChecked())
            dsaChecked.add(rcp2.getText().toString());

        if (rcp3.isChecked())
            dsaChecked.add(rcp3.getText().toString());

        if (rcp4.isChecked())
            dsaChecked.add(rcp4.getText().toString());

        if (rcp5.isChecked())
            dsaChecked.add(rcp5.getText().toString());

        dsa.setDsaChecked(dsaChecked);

        dsa.setTimeDsa(time.getText().toString());
        dsa.setNbChoc(nbChocs.getText().toString());
        dsa.setOther(other.getText().toString());

        if (rbPulseYes.isChecked()) {
            dsa.setPulse(1);
            dsa.setCarotid(carotid.isChecked() ? 1 : 0);
            dsa.setRadial(radial.isChecked() ? 1 : 0);
            dsa.setTimePulse(timePulse.getText().toString());
            dsa.setFrequencePulse(frequency.getText().toString());
        }

        if (rbMovesYes.isChecked()) {
            dsa.setBreath(1);
            dsa.setFrequenceBreath(frequencyMoves.getText().toString());
        }

        dsa.setComments(comments.getText().toString());
        bilanFonc.setDSA(dsa);
    }

    private void selectValue(Spinner spinner, Object value) {
        if (value == null)
            return;

		/*
		 We check the object name to see if it's the one to select because this method is used for
		 different types of object so the code isn't redundant.
		 On vérifie le nom de l'objet pour voir si c'est celui à sélectionner parce que cette
		 méthode est utilisée pour différents types d'objets afin que le code ne soit pas redondant.
		 */
        for (int i = 0; i < spinner.getCount(); i++) {
            if (value.toString().equals(spinner.getItemAtPosition(i).toString())) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    protected void display(DSA dsaEdit) {
        dsa = dsaEdit;
        rbWitYes.setChecked(dsa.getWitness() == 1);
        rbWitNo.setChecked(dsa.getWitness() == 0);
        selectValue(alert, dsa.getAlert());
        rbRcpYes.setChecked(dsa.getRcp() == 1);
        rbRcpNo.setChecked(dsa.getRcp() == 0);
        selectValue(rcpQuality, dsa.getQualityRCP());
        for (String s : dsa.getDsaChecked()) {
            if (rcp1.getText().toString().equals(s))
                rcp1.setChecked(true);
            if (rcp2.getText().toString().equals(s))
                rcp2.setChecked(true);
            if (rcp3.getText().toString().equals(s))
                rcp3.setChecked(true);
            if (rcp4.getText().toString().equals(s))
                rcp4.setChecked(true);
            if (rcp5.getText().toString().equals(s))
                rcp5.setChecked(true);
        }
        time.setText(dsa.getTimeDsa());
        nbChocs.setText(dsa.getNbChoc());
        other.setText(dsa.getOther());

        if (dsa.getPulse() == 1) {
            rbPulseYes.setChecked(true);
            carotid.setChecked(dsa.getCarotid() == 1);
            radial.setChecked(dsa.getRadial() == 1);
            timePulse.setText(dsa.getTimePulse());
            frequency.setText(dsa.getFrequencePulse());
            llPulse.setLayoutParams(paramsOpen);
            llPulse2.setLayoutParams(paramsOpen);
        }

        if (dsa.getBreath() == 1) {
            rbMovesYes.setChecked(true);
            frequencyMoves.setText(dsa.getFrequenceBreath());
            llMoves.setLayoutParams(paramsOpen);
        }

        comments.setText(dsa.getComments());
    }

}
