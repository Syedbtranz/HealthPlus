package com.btranz.healthplus.model;

/**
 * Created by User_Sajid on 4/25/2016.
 */
public class MFSModel  {
    int _id;
     private String history;
    private String secondary;
    private String bedrest;
    private String crutches;
    private String furniture;

private String Heparinlock;
    private String normal;
    private String weak;
private String impaired;
    private String oriented;
    private String forgets;

    private String ambulatoryid;
    private String gaittransfer;
    private String mental;

    public MFSModel(){
    }

    public MFSModel(int _id,String history,String secondary,String ambulatoryid,String Heparinlock,String gaittransfer,String mental){

        this._id=_id;
        this.history=history;
        this.secondary=secondary;
        this.ambulatoryid=ambulatoryid;
        this.Heparinlock=Heparinlock;
        this.gaittransfer=gaittransfer;
        this.mental=mental;
    }
    public int get_id() {
        return _id;
    }

    public String getAmbulatoryaid() {
        return ambulatoryid;
    }

    public void setAmbulatoryaid(String ambulatoryaid) {
        this.ambulatoryid = ambulatoryaid;
    }

    public String getGaittransfer() {
        return gaittransfer;
    }

    public void setGaittransfer(String gaittransfer) {
        this.gaittransfer = gaittransfer;
    }

    public String getMental() {
        return mental;
    }

    public void setMental(String mental) {
        this.mental = mental;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getSecondary() {
        return secondary;
    }

    public void setSecondary(String secondary) {
        this.secondary = secondary;
    }

    public void setHeparinlock(String heparinlock) {
        Heparinlock = heparinlock;
    }

    public String getBedrest() {
        return bedrest;
    }

    public void setBedrest(String bedrest) {
        this.bedrest = bedrest;
    }

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    public String getCrutches() {
        return crutches;
    }

    public void setCrutches(String crutches) {
        this.crutches = crutches;
    }

    public String getHeparinlock() {
        return Heparinlock;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getWeak() {
        return weak;
    }

    public void setWeak(String weak) {
        this.weak = weak;
    }

    public String getImpaired() {
        return impaired;
    }

    public void setImpaired(String impaired) {
        this.impaired = impaired;
    }

    public String getOriented() {
        return oriented;
    }

    public void setOriented(String oriented) {
        this.oriented = oriented;
    }

    public String getForgets() {
        return forgets;
    }

    public void setForgets(String forgets) {
        this.forgets = forgets;
    }
}
