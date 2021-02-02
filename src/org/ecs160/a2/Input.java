package org.ecs160.a2;
import com.codename1.ui.*;
import org.ecs160.a2.items.SinglePortItem;

import java.io.IOException;

public class Input extends SinglePortItem {
    private boolean state = false;
    private Cell outputItem = null;

    public Input(int x, int y) {
        this.setPosition(x,y);
        this.isInput = true;
        this.setItemID("input");
        try {
            this.offImage= Image.createImage("/input_off.png");
            this.onImage = Image.createImage("/input_on.png");
        } catch (IOException e){
            e.printStackTrace();
        }
        this.setImage(offImage);
    }

    @Override
    public Input deepCopy(){
        Input newIO = new Input(this.getPositionX(), this.getPositionY());
        newIO.setDirection(this.getDirection());
        newIO.setImage(this.getImage());
        newIO.setItemID(this.itemID);
        newIO.setOutputItem(this.outputItem.deepCopy());
        return newIO;
    }

    public void setState(boolean s){
        state = s;
        super.setOutput();
        if(state){
            this.setImage(onImage);
        } else {
            this.setImage(offImage);
        }
        redirect();
    }

    public void setState() {
        state = !state;
        super.setOutput();
        if(state){
            this.setImage(onImage);
        } else {
            this.setImage(offImage);
        }
        redirect();
    }

    // This adjusts the swapped image to match the current direction
    public void redirect(){
        Direction finalDir = this.getDirection();
        Direction currentDir = Direction.EAST;
        while(currentDir != finalDir) {
            this.rotate();
            currentDir = currentDir.next();
        }
        this.setDirection(currentDir);
    }

    public boolean getState() { return state; }

    public Cell getOutputItem() {
        return outputItem;
    }

    public void setOutputItem(Cell outputItem) {
        this.outputItem = outputItem;
    }

}
