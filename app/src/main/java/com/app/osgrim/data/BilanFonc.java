package com.app.osgrim.data;

import org.json.JSONException;
import org.json.JSONObject;

public class BilanFonc implements Bilan {

    private BilanCir bilanCir;
    private int eval;
    private int consciousness;
    private int somnolence;
    private int agitation;
    private int answers;
    private int fainted;
    private int ventilation;
    private int sweat;
    private int pallor;
    private int firstTime;
    private int background;
    private int treatment;
    private String notFirstTime;
    private String notBackground;
    private String notTreatment;
    private int dsa;
    private DSA dsaSheet;

    public BilanFonc(BilanCir bilanCir) {
        this.bilanCir = bilanCir;
        eval = -1;
        consciousness = -1;
        somnolence = 1;
        agitation = 1;
        answers = 0;
        firstTime = 0;
        background = 1;
        treatment = 1;
        sweat = 1;
        pallor = 1;
        fainted = -1;
        ventilation = -1;
        dsa = 0;
    }

    public BilanCir getBilanCir() {
        return bilanCir;
    }

    public BilanFonc getBilanFonc() {
        return this;
    }

    public String getDateTime() {
        return bilanCir.getDateTime();
    }

    public void setBilanCir(BilanCir bilanCir) {
        this.bilanCir = bilanCir;
    }

    public int getEval() {
        return eval;
    }

    public void setEval(int eval) {
        this.eval = eval;
    }

    public int getConsciousness() {
        return consciousness;
    }

    public void setConsciousness(int consciousness) {
        this.consciousness = consciousness;
    }

    public int getSomnolence() {
        return somnolence;
    }

    public void setSomnolence(int somnolence) {
        this.somnolence = somnolence;
    }

    public int getAgitation() {
        return agitation;
    }

    public void setAgitation(int agitation) {
        this.agitation = agitation;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getFainted() {
        return fainted;
    }

    public void setFainted(int fainted) {
        this.fainted = fainted;
    }

    public int getVentilation() {
        return ventilation;
    }

    public void setVentilation(int ventilation) {
        this.ventilation = ventilation;
    }

    public int getSweat() {
        return sweat;
    }

    public void setSweat(int sweat) {
        this.sweat = sweat;
    }

    public int getPallor() {
        return pallor;
    }

    public void setPallor(int pallor) {
        this.pallor = pallor;
    }

    public int getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(int firstTime) {
        this.firstTime = firstTime;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getTreatment() {
        return treatment;
    }

    public void setTreatment(int treatment) {
        this.treatment = treatment;
    }

    public String getNotFirstTime() {
        return notFirstTime;
    }

    public void setNotFirstTime(String notFirstTime) {
        this.notFirstTime = notFirstTime;
    }

    public String getNotBackground() {
        return notBackground;
    }

    public void setNotBackground(String notBackground) {
        this.notBackground = notBackground;
    }

    public String getNotTreatment() {
        return notTreatment;
    }

    public void setNotTreatment(String notTreatment) {
        this.notTreatment = notTreatment;
    }

    public int getDsa() {
        return dsa;
    }

    public void setDsa(int dsa) {
        this.dsa = dsa;
    }

    public DSA getDsaSheet() {
        return dsaSheet;
    }

    public void setDsaSheet(DSA dsaSheet) {
        this.dsaSheet = dsaSheet;
    }

    public JSONObject getJsonBilanFonc() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("eval", getEval());
        obj.put("consciousness", getConsciousness());
        obj.put("somnolence", getSomnolence());
        obj.put("agitation", getAgitation());
        obj.put("answers", getAnswers());
        obj.put("lostCons", getFainted());
        obj.put("ventilation", getVentilation());
        obj.put("sweat", getSweat());
        obj.put("pallor", getPallor());
        obj.put("firstTime", getFirstTime());
        obj.put("background", getBackground());
        obj.put("treatment", getTreatment());
        obj.put("notFirstTime", getNotFirstTime());
        obj.put("notBackground", getNotBackground());
        obj.put("notTreatment", getNotTreatment());
        obj.put("dsa", getDsa());
        if (getDsa() == 1) {
            obj.put("dsaSheet", getDsaSheet().getJsonDsa());
        } else {
            obj.put("dsaSheet", new JSONObject());
        }

        return obj;
    }

    public JSONObject getJsonBilanLesDef() throws JSONException {
        JSONObject obj = new JSONObject();

        obj.put("moves", "");

        return obj;
    }
}
