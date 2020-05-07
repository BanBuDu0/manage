package model;

import java.util.Date;

public class Prescription {
    private int id;
    private String history;
    private String sign;
    private String tongue;
    private String pulse_left;
    private String pulse_right;
    private String diagnosis;
    private String prescribe;
    private int patient_id;
    private Date create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTongue() {
        return tongue;
    }

    public void setTongue(String tongue) {
        this.tongue = tongue;
    }

    public String getPulse_left() {
        return pulse_left;
    }

    public void setPulse_left(String pulse_left) {
        this.pulse_left = pulse_left;
    }

    public String getPulse_right() {
        return pulse_right;
    }

    public void setPulse_right(String pulse_right) {
        this.pulse_right = pulse_right;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescribe() {
        return prescribe;
    }

    public void setPrescribe(String prescribe) {
        this.prescribe = prescribe;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
