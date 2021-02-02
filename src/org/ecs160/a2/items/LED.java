package org.ecs160.a2.items;

import com.codename1.ui.Image;
import org.ecs160.a2.Direction;
import org.ecs160.a2.Input;

import java.io.IOException;

public class LED extends SinglePortItem {
    private boolean state = false;

    public LED (int x, int y) {
        this.setPosition(x,y);
        this.isInput = false;
        this.setItemID("led");
        try {
            this.offImage= Image.createImage("/led_off.png");
            this.onImage = Image.createImage("/led_on.png");
        } catch (IOException e){
            e.printStackTrace();
        }
        this.setImage(offImage);
    }

    @Override
    public LED deepCopy(){
        LED newIO = new LED(this.getPositionX(), this.getPositionY());
        newIO.setDirection(this.getDirection());
        newIO.setImage(this.getImage());
        newIO.setItemID(this.itemID);
        newIO.setConnection(this.connectedItem.deepCopy());
        return newIO;
    }

    public void resetState() {
        state = false;
        this.setImage(offImage);
        redirect();
    }

    public void setState() {
        state = super.connectedItem.getOutput();
        if(state){
            this.setImage(onImage);
        } else {
            this.setImage(offImage);
        }
        redirect();
    }

    public boolean getState() {return state;}

    public void redirect(){
        Direction finalDir = this.getDirection();
        Direction currentDir = Direction.EAST;
        while(currentDir != finalDir) {
            this.rotate();
            currentDir = currentDir.next();
        }
        this.setDirection(currentDir);
    }
}
