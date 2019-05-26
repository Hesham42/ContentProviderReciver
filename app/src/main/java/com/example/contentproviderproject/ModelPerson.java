package com.example.contentproviderproject;

public class ModelPerson {
    private String CONTACT_NAME;
    private Integer CONTACT_AGE;
    private Integer CONTACT_IDS;

    public ModelPerson(String CONTACT_NAME, Integer CONTACT_AGE, Integer CONTACT_IDS) {
        this.CONTACT_NAME = CONTACT_NAME;
        this.CONTACT_AGE = CONTACT_AGE;
        this.CONTACT_IDS = CONTACT_IDS;
    }

    public String getCONTACT_NAME() {
        return CONTACT_NAME;
    }

    public void setCONTACT_NAME(String CONTACT_NAME) {
        this.CONTACT_NAME = CONTACT_NAME;
    }

    public Integer getCONTACT_AGE() {
        return CONTACT_AGE;
    }

    public void setCONTACT_AGE(Integer CONTACT_AGE) {
        this.CONTACT_AGE = CONTACT_AGE;
    }

    public Integer getCONTACT_IDS() {
        return CONTACT_IDS;
    }

    public void setCONTACT_IDS(Integer CONTACT_IDS) {
        this.CONTACT_IDS = CONTACT_IDS;
    }


}
