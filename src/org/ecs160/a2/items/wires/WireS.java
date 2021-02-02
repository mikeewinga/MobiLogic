package org.ecs160.a2.items.wires;

import com.codename1.ui.*;

import java.io.IOException;

import org.ecs160.a2.Direction;
import org.ecs160.a2.items.wires.Wire;


public class WireS extends Wire {
    private boolean state;

    public WireS(int x, int y) {
        this.setPosition(x,y);
        this.setItemID("wire");
        try {
            this.offImage= Image.createImage("/wire_straight_blue.png");
            this.onImage = Image.createImage("/wire_straight_green.png");
            this.nullImage = Image.createImage("/wire_straight_red.png");
        } catch (IOException e){
            e.printStackTrace();
        }
        this.setImage(nullImage);
    }

    @Override
    public WireS deepCopy(){
        WireS newWire = new WireS(this.getPositionX(), this.getPositionY());
        newWire.setItemID(this.itemID);
        newWire.setImage(this.getImage());
        newWire.setDirection(this.getDirection());
        newWire.setInputCell1(this.inputCell1);
        newWire.setOutputCell(this.outputCell);
        return newWire;
    }

    @Override
    public boolean logic(boolean Input) {
        return Input;
    }

    @Override
    protected void setDirection(Direction d) {
        super.direction = d;
    }
}