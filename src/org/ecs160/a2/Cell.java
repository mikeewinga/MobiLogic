package org.ecs160.a2;

import org.ecs160.a2.items.gates.NANDgate;

public class Cell {
    private Integer positionX;
    private Integer positionY;
    protected String itemID = ("null");
    private int itemDelay = 0;
    private int propDelay = 0;
    public boolean isHighlighted = false;
    protected boolean output;

    public Cell() {
        positionX = -1;
        positionY = -1;
    }

    public Cell(Integer x,Integer y){
        positionX = x;
        positionY = y;
    }

    public Cell deepCopy(){
        Cell newCell = new Cell(this.getPositionX(), this.getPositionY());
        newCell.setItemID(this.itemID);
        return newCell;
    }

    public boolean containsItem(){
        return !itemID.equals("null");
    }

    public Integer getPositionX() {
        return positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPosition(Integer x, Integer y) {
        this.positionX = x;
        this.positionY = y;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public boolean getOutput(){
        return output;
    }

    public void setOutput(boolean b){
        output = b;
    }

    public void setDelay(int delayValue){
        this.itemDelay =delayValue;
    }

    public int getDelay(){
        return this.itemDelay;
    }

    public int getPropDelay(){
        return this.propDelay;
    }
    public void setPropDelay(int value){
        this.propDelay = value;
    }

    public void setOutput(){ output = false; }

    public void printCell(){
        System.out.println(this.itemID +
                ": [Position: {X:" + this.getPositionX() +
                ", Y:" + this.getPositionY() +
                "}");
    }
}