package com.app.osgrim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.osgrim.data.Bilan;
import com.app.osgrim.data.BilanCir;
import com.app.osgrim.data.BilanFonc;
import com.app.osgrim.data.BilanLes;
import com.app.osgrim.data.DSA;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BilanFoncActivity extends AppCompatActivity {

    private Spinner spinEval;
    private RadioGroup rgConsciousness, rgSomnolence, rgAgitation, rgAnswers, rgLostCons,
            rgVentilation, rgSweat, rgPalor, rgFirstTime, rgBackground, rgTreatment;
    private RadioButton rbYesCons, rbNoCons, rbYesSom, rbNoSom, rbYesAgi, rbNoAgi, rbYesAns,
            rbNoAns, rbYesLost, rbNoLost, rbYesVent, rbNoVent, rbYesSweat, rbNoSweat,
            rbYesPalor, rbNoPalor, rbYesFirst, rbNoFirst, rbYesBackground, rbNoBackground,
            rbYesTreatment, rbNoTreatment;
    private EditText etFirstTime, etBackground, etTreatment;
    private CheckBox cbDSA;
    private Button btnNext, btnBack, btnSave, btnDsa;

    private BilanFonc bilanFoncObj;
    private MainActivity mainAct;
    private static BilanFoncActivity bilanFonc;
    private DSA dsaSheet;
    private BilanLes bilanLes;
    private Bilan bilan;
    protected int editMode;
    private int pos;
    BilanCirActivity bilanCirActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilan_fonc);

        Gson gson = new Gson();
        BilanCir bilanCir = gson.fromJson(getIntent().getStringExtra("bilanCir"), BilanCir.class);

        bilanFoncObj = new BilanFonc(bilanCir);

        spinEval = findViewById(R.id.spinEval);
        rgConsciousness = findViewById(R.id.rgConsciousness);
        rgSomnolence = findViewById(R.id.rgSomnolence);
        rgAgitation = findViewById(R.id.rgAgitation);
        rgAnswers = findViewById(R.id.rgAnswers);
        rgLostCons = findViewById(R.id.rgLostCons);
        rgVentilation = findViewById(R.id.rgVentilation);
        rgSweat = findViewById(R.id.rgSweat);
        rgPalor = findViewById(R.id.rgPalor);
        rgFirstTime = findViewById(R.id.rgFirstTime);
        rgBackground = findViewById(R.id.rgBackground);
        rgTreatment = findViewById(R.id.rgTreatment);

        rbYesCons = findViewById(R.id.rbYesCons);
        rbNoCons = findViewById(R.id.rbNoCons);
        rbYesSom = findViewById(R.id.rbYesSom);
        rbNoSom = findViewById(R.id.rbNoSom);
        rbYesAgi = findViewById(R.id.rbYesAgi);
        rbNoAgi = findViewById(R.id.rbNoAgi);
        rbYesAns = findViewById(R.id.rbYesAns);
        rbNoAns = findViewById(R.id.rbNoAns);
        rbYesLost = findViewById(R.id.rbYesLost);
        rbNoLost = findViewById(R.id.rbNoLost);
        rbYesVent = findViewById(R.id.rbYesVent);
        rbNoVent = findViewById(R.id.rbNoVent);
        rbYesSweat = findViewById(R.id.rbYesSweat);
        rbNoSweat = findViewById(R.id.rbNoSweat);
        rbYesPalor = findViewById(R.id.rbYesPalor);
        rbNoPalor = findViewById(R.id.rbNoPalor);
        rbYesFirst = findViewById(R.id.rbYesFirst);
        rbNoFirst = findViewById(R.id.rbNoFirst);
        rbYesBackground = findViewById(R.id.rbYesBackground);
        rbNoBackground = findViewById(R.id.rbNoBackground);
        rbYesTreatment = findViewById(R.id.rbYesTreatment);
        rbNoTreatment = findViewById(R.id.rbNoTreatment);

        etFirstTime = findViewById(R.id.etFirstTime);
        etTreatment = findViewById(R.id.etTreatment);
        etBackground = findViewById(R.id.etBackground);

        cbDSA = findViewById(R.id.cbDSA);
        btnNext = findViewById(R.id.btnBilanLes);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSaveBil);
        btnDsa = findViewById(R.id.btnDsa);

        mainAct = MainActivity.getInstance();
        initializeElements();
        bilanCirActivity = BilanCirActivity.getInstance();
        bilanFonc = this;

        if (mainAct.bilanLevel == 2)
            btnSave.setVisibility(View.INVISIBLE);
        else
            btnSave.setVisibility(View.VISIBLE);

        editMode = getIntent().getIntExtra("edit", -1);
        if (editMode == 1) {
            Gson gsonBil = new Gson();
            Type type = mainAct.bilanLevel == 2 ? BilanFonc.class : BilanLes.class;
            bilan = gsonBil.fromJson(getIntent().getStringExtra("bilan"), type);
            pos = getIntent().getIntExtra("pos", -1);
            displayBilan(bilan);
        }

    }

    private void displayBilan(Bilan bilan) {
        bilanFoncObj = bilan.getBilanFonc();

        selectValue(spinEval, bilanFoncObj.getEval());

        rbNoCons.setChecked(bilanFoncObj.getConsciousness() == 1);
        rbYesCons.setChecked(bilanFoncObj.getConsciousness() == 0);

        rbYesSom.setChecked(bilanFoncObj.getSomnolence() == 0);
        rbNoSom.setChecked(bilanFoncObj.getSomnolence() == 1);
        rbYesAgi.setChecked(bilanFoncObj.getAgitation() == 0);
        rbNoAgi.setChecked(bilanFoncObj.getAgitation() == 1);
        rbYesAns.setChecked(bilanFoncObj.getAnswers() == 0);
        rbNoAns.setChecked(bilanFoncObj.getAnswers() == 1);
        rbYesLost.setChecked(bilanFoncObj.getFainted() == 0);
        rbNoLost.setChecked(bilanFoncObj.getFainted() == 1);
        rbYesVent.setChecked(bilanFoncObj.getVentilation() == 0);
        rbNoVent.setChecked(bilanFoncObj.getVentilation() == 1);
        rbYesSweat.setChecked(bilanFoncObj.getSweat() == 0);
        rbNoSweat.setChecked(bilanFoncObj.getSweat() == 1);
        rbYesPalor.setChecked(bilanFoncObj.getPallor() == 0);
        rbNoPalor.setChecked(bilanFoncObj.getPallor() == 1);

        rbNoFirst.setChecked(bilanFoncObj.getFirstTime() == 1);
        rbYesFirst.setChecked(bilanFoncObj.getFirstTime() == 0);
        rbYesBackground.setChecked(bilanFoncObj.getBackground() == 0);
        rbNoBackground.setChecked(bilanFoncObj.getBackground() == 1);
        rbYesTreatment.setChecked(bilanFoncObj.getTreatment() == 0);
        rbNoTreatment.setChecked(bilanFoncObj.getTreatment() == 1);
        etFirstTime.setText(bilanFoncObj.getNotFirstTime());
        etBackground.setText(bilanFoncObj.getNotBackground());
        etTreatment.setText(bilanFoncObj.getNotTreatment());

        if (bilanFoncObj.getDsa() == 1) {
            cbDSA.setChecked(true);
            dsaSheet = bilanFoncObj.getDsaSheet();
            btnDsa.setVisibility(View.VISIBLE);
        } else {
            cbDSA.setChecked(false);
            btnDsa.setVisibility(View.INVISIBLE);
        }

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

    protected static BilanFoncActivity getInstance() {
        return bilanFonc;
    }

    private void initializeElements() {
        TextView tvEval, tvCons, tvSom, tvAgi, tvAns, tvLostCons, tvVent, tvSweat, tvPalor,
                tvFirstTime, tvBackground, tvTreatment;

        tvEval = findViewById(R.id.txtEval);
        tvCons = findViewById(R.id.txtConsciousness);
        tvSom = findViewById(R.id.txtSomnolence);
        tvAgi = findViewById(R.id.txtAgitation);
        tvAns = findViewById(R.id.txtAnswers);
        tvLostCons = findViewById(R.id.txtLostCons);
        tvVent = findViewById(R.id.txtVentilation);
        tvSweat = findViewById(R.id.txtSweat);
        tvPalor = findViewById(R.id.txtPalor);
        tvFirstTime = findViewById(R.id.txtFirstTime);
        tvBackground = findViewById(R.id.txtBackground);
        tvTreatment = findViewById(R.id.txtTreatment);

        tvEval.setText(mainAct.labels.get("evaluation"));
        tvCons.setText(mainAct.labels.get("consciousness"));
        tvAgi.setText(mainAct.labels.get("agitation"));
        tvAns.setText(mainAct.labels.get("answers"));
        tvLostCons.setText(mainAct.labels.get("fainted"));
        tvSom.setText(mainAct.labels.get("somnolence"));
        tvVent.setText(mainAct.labels.get("ventilation"));
        tvSweat.setText(mainAct.labels.get("sweat"));
        tvPalor.setText(mainAct.labels.get("pallor"));
        tvFirstTime.setText(mainAct.labels.get("firstTime"));
        tvBackground.setText(mainAct.labels.get("background"));
        tvTreatment.setText(mainAct.labels.get("treatments"));

        rbYesCons.setText(mainAct.messages.get("yes"));
        rbNoCons.setText(mainAct.messages.get("no"));
        rbYesSom.setText(mainAct.messages.get("yes"));
        rbNoSom.setText(mainAct.messages.get("no"));
        rbYesAgi.setText(mainAct.messages.get("yes"));
        rbNoAgi.setText(mainAct.messages.get("no"));
        rbYesAns.setText(mainAct.messages.get("yes"));
        rbNoAns.setText(mainAct.messages.get("no"));
        rbYesLost.setText(mainAct.messages.get("yes"));
        rbNoLost.setText(mainAct.messages.get("no"));
        rbYesSom.setText(mainAct.messages.get("yes"));
        rbNoSom.setText(mainAct.messages.get("no"));
        rbYesVent.setText(mainAct.messages.get("yes"));
        rbNoVent.setText(mainAct.messages.get("no"));
        rbYesSweat.setText(mainAct.messages.get("yes"));
        rbNoSweat.setText(mainAct.messages.get("no"));
        rbYesPalor.setText(mainAct.messages.get("yes"));
        rbNoPalor.setText(mainAct.messages.get("no"));
        rbYesFirst.setText(mainAct.messages.get("yes"));
        rbNoFirst.setText(mainAct.messages.get("no"));
        rbYesBackground.setText(mainAct.messages.get("yes"));
        rbNoBackground.setText(mainAct.messages.get("no"));
        rbYesTreatment.setText(mainAct.messages.get("yes"));
        rbNoTreatment.setText(mainAct.messages.get("no"));

        if (mainAct.bilanLevel == 2) {
            btnNext.setText(mainAct.labels.get("save"));
        } else {
            btnNext.setText(mainAct.labels.get("bilan3"));
        }

        btnBack.setText(mainAct.messages.get("back"));
        btnSave.setText(mainAct.labels.get("save"));
        btnDsa.setText(mainAct.messages.get("btndsa"));

        cbDSA.setText(mainAct.labels.get("dsa"));

        final List<String> eval = new ArrayList<>();
        for (int i = 0; i <=10; i++)
            eval.add("" + i);
        eval.add("");

        ArrayAdapter<String> evalAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, eval) {
            @Override
            public int getCount() {
                return eval.size() - 1;
            }
        };
        spinEval.setAdapter(evalAdapter);
        spinEval.setSelection(eval.size() - 1);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            save();
            bilanCirActivity.setBilanFonc(bilanFoncObj);
            if (mainAct.bilanLevel > 2) {
                bilanCirActivity.setBilanLes(bilanLes);
            }
            moveTaskToBack(true);
            }
        });

        cbDSA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (cbDSA.isChecked()) {
                Intent i = new Intent(mainAct,DsaActivity.class);
                startActivity(i);

                btnDsa.setVisibility(View.VISIBLE);
            } else {
                btnDsa.setVisibility(View.INVISIBLE);
            }
            }
        });

        btnDsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainAct, DsaActivity.class);
                Gson gson = new Gson();
                dsaSheet = bilanFoncObj.getDsaSheet();
                String dsaJson = gson.toJson(dsaSheet);
                i.putExtra("dsa", dsaJson);
                startActivity(i);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            save();
            if (editMode != 1) {
                if (mainAct.bilanLevel > 2) {
                    // Send bilanFonc to bilanLes
                    Gson gson = new Gson();
                    String json = gson.toJson(bilanFoncObj);

                    Intent i = new Intent(mainAct, BilanLesActivity.class);
                    i.putExtra("edit", 0);
                    i.putExtra("bilanFonc", json);
                    startActivity(i);
                } else {
                    mainAct.bilanFoncList.add(bilanFoncObj);
                    // Indication that the report has been saved.
                    // Indication indiquant que le rapport a été enregistré.
                    Toast.makeText(mainAct, mainAct.messages.get("saveBilanComplete"), Toast.LENGTH_LONG).show();
                    finish();
                }
            } else if (mainAct.bilanLevel > 2) {
                // Send bilanFonc to bilanLes
                Gson gson = new Gson();
                String json = gson.toJson(bilan);
                String jsonBilF = gson.toJson(bilanFoncObj);

                Intent i = new Intent(mainAct, BilanLesActivity.class);
                i.putExtra("edit", 1);
                i.putExtra("bilan", json);
                i.putExtra("pos", pos);
                i.putExtra("bilanFonc", jsonBilF);
                startActivity(i);
            }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                if (bilanLes != null) {
                    bilanLes.setBilanFonc(bilanFoncObj);
                } else {
                    bilanLes = new BilanLes(bilanFoncObj);
                }
                if (editMode == 1) {
                    editMode = 0;
                    mainAct.bilanLesList.remove(pos);
                }
                mainAct.bilanLesList.add(bilanLes);
                Toast.makeText(mainAct, mainAct.messages.get("saveBilanComplete"), Toast.LENGTH_LONG).show();
                bilanCirActivity.finish();
                finish();
            }
        });
    }

    private void save() {
        String eval = (String) spinEval.getSelectedItem();
        if (eval != "") {
            bilanFoncObj.setEval(Integer.parseInt(eval));
        }

        if (rbYesCons.isChecked())
            bilanFoncObj.setConsciousness(0);
        else if (rbNoCons.isChecked())
            bilanFoncObj.setConsciousness(1);

        if (rbYesSom.isChecked())
            bilanFoncObj.setSomnolence(0);
        else if (rbNoSom.isChecked())
            bilanFoncObj.setSomnolence(1);

        if (rbYesAgi.isChecked())
            bilanFoncObj.setAgitation(0);
        else if (rbNoAgi.isChecked())
            bilanFoncObj.setAgitation(1);

        if (rbNoAns.isChecked())
            bilanFoncObj.setAnswers(1);
        else if (rbYesAns.isChecked())
            bilanFoncObj.setAnswers(0);

        if (rbYesLost.isChecked())
            bilanFoncObj.setFainted(0);
        else if (rbNoLost.isChecked())
            bilanFoncObj.setFainted(1);

        if (rbYesVent.isChecked())
            bilanFoncObj.setVentilation(0);
        else if (rbNoVent.isChecked())
            bilanFoncObj.setVentilation(1);

        if (rbYesSweat.isChecked())
            bilanFoncObj.setSweat(0);
        else if (rbNoSweat.isChecked())
            bilanFoncObj.setSweat(1);

        if (rbYesPalor.isChecked())
            bilanFoncObj.setPallor(0);
        else if (rbNoPalor.isChecked())
            bilanFoncObj.setPallor(1);

        if (rbNoFirst.isChecked())
            bilanFoncObj.setFirstTime(1);
        else if (rbYesFirst.isChecked())
            bilanFoncObj.setFirstTime(0);

        if (rbYesBackground.isChecked())
            bilanFoncObj.setBackground(0);
        else if (rbNoBackground.isChecked())
            bilanFoncObj.setBackground(1);

        if (rbYesTreatment.isChecked())
            bilanFoncObj.setTreatment(0);
        else if (rbNoTreatment.isChecked())
            bilanFoncObj.setTreatment(1);

        bilanFoncObj.setNotFirstTime(etFirstTime.getText().toString());
        bilanFoncObj.setNotBackground(etBackground.getText().toString());
        bilanFoncObj.setNotTreatment(etTreatment.getText().toString());

        if (cbDSA.isChecked()) {
            bilanFoncObj.setDsa(1);
        } else {
            bilanFoncObj.setDsa(0);
        }
    }

    protected void setBilanLes(BilanLes bilanLes) {
        this.bilanLes = bilanLes;
    }

    protected void setDSA(DSA dsaSheet) {
        this.dsaSheet = dsaSheet;
        bilanFoncObj.setDsaSheet(this.dsaSheet);
    }

    protected void cancelDSA() {
        if (dsaSheet == null) {
            cbDSA.setChecked(false);
            btnDsa.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration config) {
        super.onConfigurationChanged(config);
    }
}
