package org.ecs160.a2.items.gates;

import com.codename1.ui.Image;
import org.ecs160.a2.items.DoublePortItem;

import java.io.IOException;

public class NOTgate extends DoublePortItem {

    public NOTgate(Integer x, Integer y) {
        this.setPosition(x, y);
        this.setItemID("not");
        try {
            this.setImage(Image.createImage("/notGate.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public NOTgate deepCopy(){
        NOTgate newGate = new NOTgate(this.getPositionX(), this.getPositionY());
        newGate.setDirection(this.getDirection());
        newGate.setImage(this.getImage());
        newGate.setItemID(this.itemID);
        newGate.setInputCell1(this.inputCell1);
        newGate.setInputCell2(this.inputCell2);
        newGate.setOutputCell(this.outputCell);
        return newGate;
    }

    @Override
    public boolean logic(boolean Input) {
        return !Input;
    }

    public void test() {
        boolean test0 = logic(true);
        boolean test1 = logic(false);
        assert !test0 : "Failing test case: NOT T = F";
        assert test1 : "Failing test case: NOT F = T";
    }
}