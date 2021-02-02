package org.ecs160.a2.items.wires;

import com.codename1.ui.*;

import java.io.IOException;

import org.ecs160.a2.Direction;
import org.ecs160.a2.items.wires.Wire;


public class WireT extends Wire {
    private boolean state;
    protected int Xport3;
    protected int Yport3;


    public WireT(int x, int y) {
        this.setPosition(x,y);
        this.setItemID("wireT");
        try {
            this.offImage= Image.createImage("/wire_T_blue.png");
            this.onImage = Image.createImage("/wire_T_green.png");
            this.nullImage = Image.createImage("/wire_T_red.png");
        } catch (IOException e){
            e.printStackTrace();
        }
        this.setImage(nullImage);
    }

    @Override
    public WireT deepCopy(){
        WireT newWire = new WireT(this.getPositionX(), this.getPositionY());
        newWire.setItemID(this.itemID);
        newWire.setImage(this.getImage());
        newWire.setDirection(this.getDirection());
        newWire.setInputCell1(this.inputCell1);
        newWire.setInputCell2(this.inputCell2);
        newWire.setOutputCell(this.outputCell);
        return newWire;
    }

    @Override
    protected void setDirection(Direction d) {
        super.direction = d;
    }
}
