package com.app.osgrim.data;

import com.app.osgrim.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DSA {

    private int witness;
    private Alert alert;
    private int rcp;
    private QualityRCP qualityRCP;
    private List<String> dsaChecked;
    private String other;
    private String timeDsa;
    private String nbChoc;
    private int pulse;
    private int breath;
    private String comments;
    private int carotid;
    private int radial;
    private String timePulse;
    private String frequencePulse;
    private String frequenceBreath;

    public DSA() {
        witness = 0;
        rcp = 0;
        pulse = 0;
        breath = 0;
    }

    public int getWitness() {
        return witness;
    }

    public void setWitness(int witness) {
        this.witness = witness;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public int getRcp() {
        return rcp;
    }

    public void setRcp(int rcp) {
        this.rcp = rcp;
    }

    public QualityRCP getQualityRCP() {
        return qualityRCP;
    }

    public void setQualityRCP(QualityRCP qualityRCP) {
        this.qualityRCP = qualityRCP;
    }

    public List<String> getDsaChecked() {
        return dsaChecked;
    }

    public void setDsaChecked(List<String> dsaChecked) {
        this.dsaChecked = dsaChecked;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getTimeDsa() {
        return timeDsa;
    }

    public void setTimeDsa(String timeDsa) {
        this.timeDsa = timeDsa;
    }

    public String getNbChoc() {
        return nbChoc;
    }

    public void setNbChoc(String nbChoc) {
        this.nbChoc = nbChoc;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getBreath() {
        return breath;
    }

    public void setBreath(int breath) {
        this.breath = breath;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getCarotid() {
        return carotid;
    }

    public void setCarotid(int carotid) {
        this.carotid = carotid;
    }

    public int getRadial() {
        return radial;
    }

    public void setRadial(int radial) {
        this.radial = radial;
    }

    public String getTimePulse() {
        return timePulse;
    }

    public void setTimePulse(String timePulse) {
        this.timePulse = timePulse;
    }

    public String getFrequencePulse() {
        return frequencePulse;
    }

    public void setFrequencePulse(String frequencePulse) {
        this.frequencePulse = frequencePulse;
    }

    public String getFrequenceBreath() {
        return frequenceBreath;
    }

    public void setFrequenceBreath(String frequenceBreath) {
        this.frequenceBreath = frequenceBreath;
    }

    public JSONObject getJsonDsa() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("witness", getWitness());
        obj.put("alert", getAlert().getId());
        obj.put("rcp", getRcp());
        obj.put("rcpQual", getQualityRCP().getId());
        obj.put("timeDsa", getTimeDsa());
        obj.put("other", getOther());
        obj.put("nbChoc", Integer.valueOf(getNbChoc()));
        obj.put("pulse", getPulse());
        obj.put("carotid", getCarotid());
        obj.put("radial", getRadial());
        obj.put("timePulse", getTimePulse());
        obj.put("frequencyPulse", Integer.valueOf(getFrequencePulse()));
        obj.put("movesBreath", getBreath());
        obj.put("frequencyBreath", Integer.valueOf(getFrequenceBreath()));
        obj.put("comments", getComments());

        JSONArray dsaJson = new JSONArray();

        String[] dsa = {"VAS", "Ventilation", "Oxygeno", "MCE_Man", "MCE_Car"};
        MainActivity mainAct = MainActivity.getInstance();

        for (int i = 0; i < dsa.length; i++) {
            int j = i + 1;
            JSONObject dsaObj = new JSONObject();
            int bit = getDsaChecked().contains(mainAct.labels.get("dsa" + j)) ? 1 : 0;
            dsaObj.put(dsa[i], bit);
            dsaJson.put(dsaObj);
        }

        obj.put("dsaChecked", dsaJson);
        return obj;
    }
}
