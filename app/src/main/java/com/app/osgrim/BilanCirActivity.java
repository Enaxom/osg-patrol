package com.app.osgrim;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.app.osgrim.data.Destination;
import com.app.osgrim.data.State;
import com.app.osgrim.data.Transport;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BilanCirActivity extends AppCompatActivity {

    private EditText etLName, etFName, etAddress, etCircumstances;
    private AutoCompleteTextView etAge;
    private Spinner spinState, spinTransport, spinDestination;
    private RadioGroup rgGender;
    private RadioButton rbWoman, rbMan;
    private CheckBox cbDischarge;
    private Button btnNext, btnCancel, btnSave;
    private BilanCir bilanCir;
    private static BilanCirActivity bilanCirAct;
    protected boolean editMode;
    private Bilan bilan;
    private BilanFonc bilanFonc;
    private BilanLes bilanLes;
    private int pos;

    private MainActivity mainAct;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainAct = MainActivity.getInstance();
        setContentView(R.layout.bilan_cir);
        editMode = false;

        etLName = findViewById(R.id.etLName);
        etFName = findViewById(R.id.etFName);
        etAddress = findViewById(R.id.etAddress);
        etCircumstances = findViewById(R.id.etCircumstances);
        etAge = findViewById(R.id.etAge);

        spinState = findViewById(R.id.spinState);
        spinTransport = findViewById(R.id.spinTransport);
        spinDestination = findViewById(R.id.spinDestination);

        rgGender = findViewById(R.id.rgGender);
        rbWoman = findViewById(R.id.rbWoman);
        rbMan = findViewById(R.id.rbMan);

        cbDischarge = findViewById(R.id.cbDischarge);
        btnNext = findViewById(R.id.btnBilanFonc);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSaveBil);

        initializeElements();

        if (mainAct.bilanLevel == 1)
            btnSave.setVisibility(View.INVISIBLE);
        else
            btnSave.setVisibility(View.VISIBLE);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.FRANCE);
        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        Calendar calendar = Calendar.getInstance();
        bilanCir = new BilanCir(format.format(calendar.getTime()), formatTime.format(calendar.getTime()));

        bilanCirAct = this;

        pos = getIntent().getIntExtra("pos", -1);
        if (pos != -1) {
            editMode = true;
            switch (mainAct.bilanLevel) {
                case 1:
                    bilan = mainAct.bilanCirList.get(pos);
                    break;
                case 2:
                    bilan = mainAct.bilanFoncList.get(pos);
                    break;
                case 3:
                    bilan = mainAct.bilanLesList.get(pos);
                    break;
                default:
                    bilan = null;
                    break;
            }
            displayBilan(bilan);
        }
    }

    public static BilanCirActivity getInstance() {
        return bilanCirAct;
    }

    public void initializeElements() {
        TextView tvLName, tvFName, tvAddress, tvCircumstances, tvAge, tvState, tvTransport, tvDestination, tvGender;

        tvLName = findViewById(R.id.txtLName);
        tvFName = findViewById(R.id.txtFName);
        tvAddress = findViewById(R.id.txtAddress);
        tvCircumstances = findViewById(R.id.txtCircumstances);
        tvAge = findViewById(R.id.txtAge);
        tvState = findViewById(R.id.txtState);
        tvTransport = findViewById(R.id.txtTransport);
        tvDestination = findViewById(R.id.txtDestination);
        tvGender = findViewById(R.id.txtGender);

        tvLName.setText(mainAct.labels.get("lname"));
        tvFName.setText(mainAct.labels.get("fname"));
        tvAddress.setText(mainAct.labels.get("victimAddress"));
        tvCircumstances.setText(mainAct.labels.get("circumstances"));
        tvAge.setText(mainAct.labels.get("age"));
        tvState.setText(mainAct.labels.get("state"));
        tvTransport.setText(mainAct.labels.get("transport"));
        tvDestination.setText(mainAct.labels.get("destination"));
        tvGender.setText(mainAct.labels.get("gender"));
        cbDischarge.setText(mainAct.labels.get("discharge"));

        rbWoman.setText(mainAct.labels.get("woman"));
        rbMan.setText(mainAct.labels.get("man"));

        if (mainAct.bilanLevel == 1) {
            btnNext.setText(mainAct.labels.get("save"));
        } else {
            btnNext.setText(mainAct.labels.get("bilan2"));
        }

        btnSave.setText(mainAct.labels.get("save"));

        btnCancel.setText(mainAct.labels.get("cancel"));


        ArrayList<String> unknown = new ArrayList<>();
        unknown.add(mainAct.labels.get("unknown"));
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, unknown);
        etAge.setThreshold(1);
        etAge.setAdapter(ageAdapter);

        ArrayAdapter<State> stateAdapter = new ArrayAdapter<State>(this, R.layout.support_simple_spinner_dropdown_item, mainAct.states) {
            @Override
            public int getCount() {
                return mainAct.states.size() - 1;
            }
        };
        spinState.setAdapter(stateAdapter);
        spinState.setSelection(mainAct.states.size() -1);

        ArrayAdapter<Transport> transportAdapter = new ArrayAdapter<Transport>(this, R.layout.support_simple_spinner_dropdown_item, mainAct.transports) {
            @Override
            public int getCount() {
                return mainAct.transports.size() - 1;
            }
        };
        spinTransport.setAdapter(transportAdapter);
        spinTransport.setSelection(mainAct.transports.size() -1);

        ArrayAdapter<Destination> destinationAdapter = new ArrayAdapter<Destination>(this, R.layout.support_simple_spinner_dropdown_item, mainAct.destinations) {
            @Override
            public int getCount() {
                return mainAct.destinations.size() - 1;
            }
        };
        spinDestination.setAdapter(destinationAdapter);
        spinDestination.setSelection(mainAct.destinations.size() -1);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                if (!editMode) {
                    if (mainAct.bilanLevel > 1) {
                        // Send bilanCir to BilanFonc
                        Gson gson = new Gson();
                        String json = gson.toJson(bilanCir);

                        Intent i = new Intent(mainAct, BilanFoncActivity.class);
                        i.putExtra("edit", 0);
                        i.putExtra("bilanCir", json);
                        startActivity(i);
                    } else {
                        mainAct.bilanCirList.add(bilanCir);
                        // Indication that the report has been saved.
                        // Indication indiquant que le rapport a été enregistré.
                        Toast.makeText(mainAct, mainAct.messages.get("saveBilanComplete"), Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else if (mainAct.bilanLevel > 1) {
                    Gson gson = new Gson();

                    String json = gson.toJson(bilan);
                    String jsonBilC = gson.toJson(bilanCir);

                    Intent i = new Intent(mainAct, BilanFoncActivity.class);
                    i.putExtra("edit", 1);
                    i.putExtra("bilan", json);
                    i.putExtra("bilanCir", jsonBilC);
                    i.putExtra("pos", pos);
                    startActivity(i);
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                if (bilanFonc != null) {
                    bilanFonc.setBilanCir(bilanCir);
                } else {
                    bilanFonc = new BilanFonc(bilanCir);
                }
                if (mainAct.bilanLevel == 3) {
                    if (bilanLes != null) {
                        bilanLes.setBilanFonc(bilanFonc);
                    } else {
                        bilanLes = new BilanLes(bilanFonc);
                    }
                    if (editMode) {
                        editMode = false;
                    } else {
                        mainAct.bilanLesList.add(bilanLes);
                    }
                } else {
                    if (editMode) {
                        editMode = false;
                    } else {
                        mainAct.bilanFoncList.add(bilanFonc);
                    }
                }
                Toast.makeText(mainAct, mainAct.messages.get("saveBilanComplete"), Toast.LENGTH_LONG).show();
                finish();
                BilanFoncActivity bfA = BilanFoncActivity.getInstance();
                BilanLesActivity blA = BilanLesActivity.getInstance();

                if (bfA != null)
                    bfA.finish();
                if (blA != null)
                    blA.finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

                BilanFoncActivity bfA = BilanFoncActivity.getInstance();
                BilanLesActivity blA = BilanLesActivity.getInstance();

                if (bfA != null)
                    bfA.finish();
                if (blA != null)
                    blA.finish();
            }
        });
    }

    private void save() {
        bilanCir.setLname(etLName.getText().toString());
        bilanCir.setFname(etFName.getText().toString());
        bilanCir.setAge(etAge.getText().toString());

        if (rbMan.isChecked())
            bilanCir.setGender(1);

        bilanCir.setState((State) spinState.getSelectedItem());
        bilanCir.setAddress(etAddress.getText().toString());
        bilanCir.setCircumstance(etCircumstances.getText().toString());
        bilanCir.setTransport((Transport) spinTransport.getSelectedItem());
        bilanCir.setDestination((Destination) spinDestination.getSelectedItem());

        if (cbDischarge.isChecked())
            bilanCir.setDischarge(1);

        mainAct.bilanAdapter.notifyDataSetChanged();
    }

    private void displayBilan(Bilan bilan) {
        bilanCir = bilan.getBilanCir();

        etLName.setText(bilanCir.getLname());
        etFName.setText(bilanCir.getFname());
        etAge.setText(bilanCir.getAge());

        rbMan.setChecked(bilanCir.getGender() == 1);

        selectValue(spinState, bilanCir.getState());
        etAddress.setText(bilanCir.getAddress());
        etCircumstances.setText(bilanCir.getCircumstance());
        selectValue(spinTransport, bilanCir.getTransport());
        selectValue(spinDestination, bilanCir.getDestination());

        cbDischarge.setChecked(bilanCir.getDischarge() == 1);
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

    public void setBilanFonc(BilanFonc bilanFonc) {
        this.bilanFonc = bilanFonc;
    }

    public void setBilanLes(BilanLes bilanLes) {
        this.bilanLes = bilanLes;
    }

    /**
     * This method had to be override because it avoids to execute the onCreate() method when the
     * device is rotated. The user can rotate the device and nothing will change about the data
     * he entered. <br>
     * Cette methode devait être override car ça évite d'exécuter la méthode onCreate() quand
     * l'appareil est tourné. L'utilisateur peut tourner l'appareil et rien ne changera à propos
     * des données qu'il a déjà saisi.
     * @param config The Configuration.
     */
    @Override
    public void onConfigurationChanged(@NonNull Configuration config) {
        super.onConfigurationChanged(config);
    }
}
