package org.ecs160.a2.items.wires;

import com.codename1.ui.*;

import java.io.IOException;

import org.ecs160.a2.Cell;
import org.ecs160.a2.Direction;
import org.ecs160.a2.items.DoublePortItem;

public class Wire extends DoublePortItem {
    private boolean state;
    protected Image onImage;
    protected Image offImage;
    protected Image nullImage;
    protected Cell port1;
    protected Cell port2;
    protected Cell port3;
    private Cell outputItem2 = null;

    public Wire(int x, int y) {
        this.setPosition(x,y);
        this.setItemID("wire");
        try {
            this.offImage= Image.createImage("/wire_straight.png");
            this.onImage = Image.createImage("/wire_straight.png");
            this.nullImage = Image.createImage("/wire_straight.png");
        } catch (IOException e){
            e.printStackTrace();
        }
        this.setImage(nullImage);
    }

    public void SetPort1(Cell p1) {
        this.port1 = p1;
    }

    public void SetPort2(Cell p2) {
        this.port2 = p2;
    }

    public void SetPort3(Cell p3) {
        this.port3 = p3;
    }

    public Cell GetPort1() { return this.port1; }

    public Cell GetPort2() {
        return this.port2;
    }

    public Cell GetPort3() {
        return this.port3;
    }

    public Cell getOutputItem2() {
        return outputItem2;
    }

    public void setOutputItem2(Cell outputItem) {
        this.outputItem2 = outputItem;
    }

    public void setState() {
        if (isNull){
            this.setImage(nullImage);
            return;
        }
        super.setOutput();
        if(super.getOutput()){
            this.setImage(onImage);
        } else {
            this.setImage(offImage);
        }
        redirect();
    }

    @Override
    public void setNull(boolean isNull) {
        super.isNull = isNull;
        if (isNull){
            this.setImage(nullImage);
            redirect();
        }
    }

    public void resetState() {
        state = false;
        this.setImage(offImage);
        redirect();
    }

    public Wire() {

    }

    public boolean getState() { return state; }

    public void redirect(){
        Direction finalDir = this.getDirection();
        Direction currentDir = Direction.EAST;
        while(currentDir != finalDir) {
            this.rotate();
            currentDir = currentDir.next();
        }
        this.setDirection(currentDir);
    }

    @Override
    public boolean logic(boolean Input) {
        return Input;
    }

    @Override
    public void printCell(){
        System.out.println(this.itemID +
                ": [Position: {X:" + this.getPositionX() +
                ", Y:" + this.getPositionY() +
                "}], [OutputDirection: " + this.direction +
                "], [Output: " + this.output +
                "]");
    }
}