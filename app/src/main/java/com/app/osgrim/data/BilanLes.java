package com.app.osgrim.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BilanLes implements Bilan {

    private BilanFonc bilanFonc;
    private int noneBody;
    private Map<String, List<Lesion>> lesions;
    private String movesDone;

    public BilanLes(BilanFonc bilanFonc) {
        this.bilanFonc = bilanFonc;
        noneBody = 0;
        lesions = new HashMap<>();
    }

    public BilanFonc getBilanFonc() {
        return bilanFonc;
    }

    public BilanCir getBilanCir() {
        return bilanFonc.getBilanCir();
    }

    public String getDateTime() {
        return bilanFonc.getDateTime();
    }

    public void setBilanFonc(BilanFonc bilanFonc) {
        this.bilanFonc = bilanFonc;
    }

    public int getNoneBody() {
        return noneBody;
    }

    public void setNoneBody(int noneBody) {
        this.noneBody = noneBody;
    }

    public Map<String, List<Lesion>> getLesions() {
        return lesions;
    }

    public String getMovesDone() {
        return movesDone;
    }

    public void setMovesDone(String movesDone) {
        this.movesDone = movesDone;
    }

    public void setLesions(Map<String, List<Lesion>> lesions) {
        this.lesions = lesions;
    }

    public void removeLesion(String bodyPart, Lesion lesion) {
        List<Lesion> les = lesions.get(bodyPart);
        if (les != null) {
            for (Lesion l : les) {
                if (l.getName().equals(lesion.getName())) {
                    lesions.get(bodyPart).remove(l);

                    if (lesions.get(bodyPart).isEmpty()) {
                        lesions.remove(bodyPart);
                    }
                    return;
                }
            }
        }
    }

    public void addLesion(String bodyPart, Lesion lesion) {
        List<Lesion> les = lesions.get(bodyPart);
        if (les == null) {
            les = new ArrayList<Lesion>();
        }
        les.add(lesion);
        lesions.put(bodyPart, les);
    }

    public String getRecap() {
        StringBuilder recap = new StringBuilder();

        for (String bodyPart : lesions.keySet()) {
            for (Lesion lesion : Objects.requireNonNull(lesions.get(bodyPart))) {
                recap.append("- ").append(bodyPart).append(" : ").append(lesion.getName()).append("\n");
            }
        }

        return recap.toString();
    }

    public JSONObject getBilanLesJson() throws JSONException {
        JSONObject res = new JSONObject();
        JSONArray les = new JSONArray();

        for (String bPart : getLesions().keySet()) {
            for (Lesion l : getLesions().get(bPart)) {
                JSONObject obj = new JSONObject();
                obj.put(bPart, l.getId());
                les.put(obj);
            }
        }

        res.put("lesions", les);
        res.put("moves", getMovesDone());
        return res;
    }
}
