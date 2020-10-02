package com.app.osgrim;

import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.osgrim.data.Bilan;
import com.app.osgrim.data.BilanFonc;
import com.app.osgrim.data.BilanLes;
import com.app.osgrim.data.Lesion;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BilanLesActivity extends AppCompatActivity {

    private Button btnHead, btnNeck, btnBackPart, btnThorax, btnStomach, btnLArm, btnRArm,
                    btnLHand, btnRHand, btnLLeg, btnRLeg, btnLFoot, btnRFoot, btnSave, btnBack,
                    btnBackSkull, btnFace, btnSkull, btnNose, btnMouth, btnLEye, btnREye, btnLEar,
                    btnREar, btnBackBody, btnSubmitPopup, btnCancelPopup;
    private CheckBox cbNone;
    private EditText etRecap, etMoves;

    private Map<String, CheckBox> cbLes;
    private Map<String, Lesion> stringLesionMap;
    private View view;
    private LinearLayout llLesions;
    private MainActivity mainAct;
    private BilanLes bilanLes;
    private Map<Button, Boolean> buttons;
    private int nbHeadLesions;
    private BilanFoncActivity bilanFoncAct;
    private BilanCirActivity bilanCirAct;
    private Bilan bilan;
    private BilanFonc bilanFonc;
    private static BilanLesActivity bilanLesActivity;
    private int editMode;
    private int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilan_les);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        nbHeadLesions = 0;
        buttons = new HashMap<>();

        btnHead = findViewById(R.id.btnHead);
        btnNeck = findViewById(R.id.btnNeck);
        btnBackPart = findViewById(R.id.btnBackPart);
        btnThorax = findViewById(R.id.btnThorax);
        btnStomach = findViewById(R.id.btnStomach);
        btnLArm = findViewById(R.id.btnLArm);
        btnRArm = findViewById(R.id.btnRArm);
        btnLHand = findViewById(R.id.btnLHand);
        btnRHand = findViewById(R.id.btnRHand);
        btnLLeg = findViewById(R.id.btnLLeg);
        btnRLeg = findViewById(R.id.btnRLeg);
        btnLFoot = findViewById(R.id.btnLFoot);
        btnRFoot = findViewById(R.id.btnRFoot);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        btnBackSkull = findViewById(R.id.btnBackSkull);
        btnFace = findViewById(R.id.btnFace);
        btnSkull = findViewById(R.id.btnSkull);
        btnNose = findViewById(R.id.btnNose);
        btnMouth = findViewById(R.id.btnMouth);
        btnLEye = findViewById(R.id.btnLEye);
        btnREye = findViewById(R.id.btnREye);
        btnLEar = findViewById(R.id.btnLEar);
        btnREar = findViewById(R.id.btnREar);
        btnBackBody = findViewById(R.id.btnBackBody);

        buttons.put(btnNeck, false);
        buttons.put(btnBackPart, false);
        buttons.put(btnThorax, false);
        buttons.put(btnStomach, false);
        buttons.put(btnLArm, false);
        buttons.put(btnRArm, false);
        buttons.put(btnLHand, false);
        buttons.put(btnRHand, false);
        buttons.put(btnLLeg, false);
        buttons.put(btnRLeg, false);
        buttons.put(btnLFoot, false);
        buttons.put(btnRFoot, false);

        buttons.put(btnBackSkull, true);
        buttons.put(btnFace, true);
        buttons.put(btnSkull, true);
        buttons.put(btnNose, true);
        buttons.put(btnMouth, true);
        buttons.put(btnLEye, true);
        buttons.put(btnREye, true);
        buttons.put(btnLEar, true);
        buttons.put(btnREar, true);

        cbNone = findViewById(R.id.cbNone);

        etRecap = findViewById(R.id.etRecap);
        etMoves = findViewById(R.id.etMoves);

        mainAct = MainActivity.getInstance();

        Gson gson = new Gson();
        bilanFonc = gson.fromJson(getIntent().getStringExtra("bilanFonc"), BilanFonc.class);
        bilanLes = new BilanLes(bilanFonc);

        InitializeElements();
        bilanFoncAct = BilanFoncActivity.getInstance();
        bilanCirAct = BilanCirActivity.getInstance();

        editMode = getIntent().getIntExtra("edit", -1);
        if (editMode == 1) {
            Gson gsonBil = new Gson();
            bilan = gsonBil.fromJson(getIntent().getStringExtra("bilan"), BilanLes.class);
            displayBilan(bilan);
            pos = getIntent().getIntExtra("pos", -1);
        }

        for (final Button b : buttons.keySet())
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPopUp(b);
                }
            });

        bilanLesActivity = this;

        cbNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clear();
                    cbNone.setEnabled(false);
                }
            }
        });
    }

    private void clear() {
        bilanLes.setLesions(new HashMap<String, List<Lesion>>());
        etRecap.setText("");
        btnNeck.setBackgroundResource(R.drawable.green_button);
        btnBackPart.setBackgroundResource(R.drawable.green_button);
        btnThorax.setBackgroundResource(R.drawable.green_button);
        btnStomach.setBackgroundResource(R.drawable.green_button);
        btnLArm.setBackgroundResource(R.drawable.green_button);
        btnRArm.setBackgroundResource(R.drawable.green_button);
        btnLHand.setBackgroundResource(R.drawable.green_button);
        btnRHand.setBackgroundResource(R.drawable.green_button);
        btnLLeg.setBackgroundResource(R.drawable.green_button);
        btnRLeg.setBackgroundResource(R.drawable.green_button);
        btnLFoot.setBackgroundResource(R.drawable.green_button);
        btnRFoot.setBackgroundResource(R.drawable.green_button);
        btnBackSkull.setBackgroundResource(R.drawable.green_button);
        btnHead.setBackgroundResource(R.drawable.green_button);
        btnSkull.setBackgroundResource(R.drawable.green_button);
        btnHead.setBackgroundResource(R.drawable.green_button);
        btnFace.setBackgroundResource(R.drawable.green_button);
        btnHead.setBackgroundResource(R.drawable.green_button);
        btnREye.setBackgroundResource(R.drawable.green_button);
        btnHead.setBackgroundResource(R.drawable.green_button);
        btnLEye.setBackgroundResource(R.drawable.green_button);
        btnHead.setBackgroundResource(R.drawable.green_button);
        btnMouth.setBackgroundResource(R.drawable.green_button);
        btnHead.setBackgroundResource(R.drawable.green_button);
        btnNose.setBackgroundResource(R.drawable.green_button);
        btnHead.setBackgroundResource(R.drawable.green_button);
    }

    protected static BilanLesActivity getInstance() { return bilanLesActivity; }

    private void displayBilan(Bilan bilan) {
        bilanLes = (BilanLes) bilan;
        bilanLes.setBilanFonc(bilanFonc);

        etRecap.setText(bilanLes.getRecap());
        etMoves.setText(bilanLes.getMovesDone());

        Map<String, List<Lesion>> lesions = bilanLes.getLesions();

        //for ()

        if (lesions.get(mainAct.labels.get("neck")) != null)
            btnNeck.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("back")) != null)
            btnBackPart.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("chest")) != null)
            btnThorax.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("stomach")) != null)
            btnStomach.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("lArm")) != null)
            btnLArm.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("rArm")) != null)
            btnRArm.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("lHand")) != null)
            btnLHand.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("rHand")) != null)
            btnRHand.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("lLeg")) != null)
            btnLLeg.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("rLeg")) != null)
            btnRLeg.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("lFoot")) != null)
            btnLFoot.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("rFoot")) != null)
            btnRFoot.setBackgroundResource(R.drawable.red_button);
        if (lesions.get(mainAct.labels.get("backSkull")) != null) {
            nbHeadLesions += lesions.get(mainAct.labels.get("backSkull")).size();
            btnBackSkull.setBackgroundResource(R.drawable.red_button);
            btnHead.setBackgroundResource(R.drawable.red_button);
        }
        if (lesions.get(mainAct.labels.get("skull")) != null) {
            nbHeadLesions += lesions.get(mainAct.labels.get("skull")).size();
            btnSkull.setBackgroundResource(R.drawable.red_button);
            btnHead.setBackgroundResource(R.drawable.red_button);
        }
        if (lesions.get(mainAct.labels.get("face")) != null) {
            nbHeadLesions += lesions.get(mainAct.labels.get("face")).size();
            btnFace.setBackgroundResource(R.drawable.red_button);
            btnHead.setBackgroundResource(R.drawable.red_button);
        }
        if (lesions.get(mainAct.labels.get("rEye")) != null) {
            nbHeadLesions += lesions.get(mainAct.labels.get("rEye")).size();
            btnREye.setBackgroundResource(R.drawable.red_button);
            btnHead.setBackgroundResource(R.drawable.red_button);
        }
        if (lesions.get(mainAct.labels.get("lEye")) != null) {
            nbHeadLesions += lesions.get(mainAct.labels.get("lEye")).size();
            btnLEye.setBackgroundResource(R.drawable.red_button);
            btnHead.setBackgroundResource(R.drawable.red_button);
        }
        if (lesions.get(mainAct.labels.get("mouth")) != null) {
            nbHeadLesions += lesions.get(mainAct.labels.get("mouth")).size();
            btnMouth.setBackgroundResource(R.drawable.red_button);
            btnHead.setBackgroundResource(R.drawable.red_button);
        }
        if (lesions.get(mainAct.labels.get("nose")) != null) {
            nbHeadLesions += lesions.get(mainAct.labels.get("nose")).size();
            btnNose.setBackgroundResource(R.drawable.red_button);
            btnHead.setBackgroundResource(R.drawable.red_button);
        }
    }


    private void InitializeElements() {
        btnHead.setText(mainAct.labels.get("head"));
        btnNeck.setText(mainAct.labels.get("neck"));
        btnBackPart.setText(mainAct.labels.get("back"));
        btnThorax.setText(mainAct.labels.get("chest"));
        btnStomach.setText(mainAct.labels.get("stomach"));
        btnLArm.setText(mainAct.labels.get("lArm"));
        btnRArm.setText(mainAct.labels.get("rArm"));
        btnLHand.setText(mainAct.labels.get("lHand"));
        btnRHand.setText(mainAct.labels.get("rHand"));
        btnLLeg.setText(mainAct.labels.get("lLeg"));
        btnRLeg.setText(mainAct.labels.get("rLeg"));
        btnLFoot.setText(mainAct.labels.get("lFoot"));
        btnRFoot.setText(mainAct.labels.get("rFoot"));
        btnBackSkull.setText(mainAct.labels.get("backSkull"));
        btnSkull.setText(mainAct.labels.get("skull"));
        btnFace.setText(mainAct.labels.get("face"));
        btnREye.setText(mainAct.labels.get("rEye"));
        btnLEye.setText(mainAct.labels.get("lEye"));
        btnREar.setText(mainAct.labels.get("rEar"));
        btnLEar.setText(mainAct.labels.get("lEar"));
        btnMouth.setText(mainAct.labels.get("mouth"));
        btnNose.setText(mainAct.labels.get("nose"));
        btnBackBody.setText(mainAct.labels.get("backBody"));
        btnSave.setText(mainAct.labels.get("save"));
        btnBack.setText(mainAct.messages.get("back"));
        cbNone.setText(mainAct.labels.get("none"));

        TextView tvRecap, tvMoves;
        tvRecap = findViewById(R.id.tvRecap);
        tvMoves = findViewById(R.id.tvMoves);

        tvRecap.setText(mainAct.labels.get("recap"));
        tvMoves.setText(mainAct.labels.get("movesDone"));

        btnHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout rlBody = findViewById(R.id.rlBody);
                RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) rlBody.getLayoutParams();
                rlParams.height = 1;
                rlBody.setLayoutParams(rlParams);

                RelativeLayout rlHead = findViewById(R.id.rlHead);
                rlParams = (RelativeLayout.LayoutParams) rlHead.getLayoutParams();
                rlParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rlHead.setLayoutParams(rlParams);
            }
        });

        btnBackBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout rlHead = findViewById(R.id.rlHead);
                RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) rlHead.getLayoutParams();
                rlParams.height = 1;
                rlHead.setLayoutParams(rlParams);

                RelativeLayout rlBody = findViewById(R.id.rlBody);
                rlParams = (RelativeLayout.LayoutParams) rlBody.getLayoutParams();
                rlParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                rlBody.setLayoutParams(rlParams);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                finish();
                bilanCirAct.finish();
                bilanFoncAct.finish();

                if (editMode == 1) {
                    mainAct.bilanLesList.remove(pos);
                }
                mainAct.bilanLesList.add(bilanLes);
                // Indication that the report has been saved.
                // Indication indiquant que le rapport a été enregistré.
                Toast.makeText(mainAct, mainAct.messages.get("saveBilanComplete"), Toast.LENGTH_LONG).show();
                mainAct.bilanAdapter.notifyDataSetChanged();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                bilanFoncAct.setBilanLes(bilanLes);
                moveTaskToBack(true);
            }
        });

        stringLesionMap = new HashMap<>();
        for (Lesion l : mainAct.lesions)
            stringLesionMap.put(l.getName(), l);
    }

    private void save() {
        bilanLes.setMovesDone(etMoves.getText().toString());

        //mainAct.bilanLesList.add(bilanLes);
    }

    private void openPopUp(final Button btn) {
        fillPopUp();
        final String name = btn.getText().toString();
        TextView tvTitle = view.findViewById(R.id.tvTitleLesion);
        tvTitle.setText(btn.getText());


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(view);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        List<Lesion> checkedLes = bilanLes.getLesions().get(name);

        if (checkedLes != null) {
            for (Lesion l : checkedLes) {
                cbLes.get(l.getName()).setChecked(true);
            }
        }


        btnSubmitPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String l : cbLes.keySet()) {
                    if (cbLes.get(l).isChecked()) {
                        bilanLes.addLesion(name, stringLesionMap.get(l));
                    } else {
                        bilanLes.removeLesion(name, stringLesionMap.get(l));
                    }
                }

                if (bilanLes.getLesions().containsKey(name)) {
                    btn.setBackgroundResource(R.drawable.red_button);
                    if (buttons.get(btn)) {
                        nbHeadLesions++;
                        btnHead.setBackgroundResource(R.drawable.red_button);
                    }
                } else {
                    btn.setBackgroundResource(R.drawable.green_button);
                    if (buttons.get(btn)) {
                        nbHeadLesions--;
                    }
                }

                if (nbHeadLesions == 0)
                    btnHead.setBackgroundResource(R.drawable.green_button);

                etRecap.setText(bilanLes.getRecap());
                cbNone.setChecked(false);
                cbNone.setEnabled(true);
                alertDialog.dismiss();
            }
        });


        btnCancelPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void fillPopUp() {
        // Get the popup to display, which the layout is pop_up_lesion.xml
        // Récupération de la popup à afficher, qui a comme layout pop_up_lesion.xml
        view = LayoutInflater.from(this).inflate(R.layout.pop_up_lesion,
                new LinearLayout(this), true);

        llLesions = view.findViewById(R.id.llLesions);
        btnSubmitPopup = view.findViewById(R.id.btnSubmit);
        btnCancelPopup = view.findViewById(R.id.btnCancel);

        btnSubmitPopup.setText(mainAct.messages.get("ok"));
        btnCancelPopup.setText(mainAct.messages.get("cancel"));

        cbLes = new HashMap<>();

        for (Lesion l : mainAct.lesions) {
            CheckBox cb = new CheckBox(getApplicationContext());
            cb.setText(l.getName());
            cb.setTextSize(15);
            llLesions.addView(cb);
            cbLes.put(l.getName(), cb);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration config) {
        super.onConfigurationChanged(config);
    }
}
