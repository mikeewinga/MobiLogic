package org.ecs160.a2.items.gates;

import com.codename1.ui.Image;
import org.ecs160.a2.items.TriplePortItem;

import java.io.IOException;

public class ORgate extends TriplePortItem {

    public ORgate(Integer x, Integer y) {
        this.setPosition(x, y);
        this.setItemID("or");
        try {
            this.setImage(Image.createImage("/orGate.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public ORgate deepCopy(){
        ORgate newGate = new ORgate(this.getPositionX(), this.getPositionY());
        newGate.setDirection(this.getDirection());
        newGate.setImage(this.getImage());
        newGate.setItemID(this.itemID);
        newGate.setInputCell1(this.inputCell1);
        newGate.setInputCell2(this.inputCell2);
        newGate.setOutputCell(this.outputCell);
        return newGate;
    }

    @Override
    public boolean logic(boolean Input1, boolean Input2) {
        return Input1 || Input2;
    }
    public void test() {
        boolean test0 = logic(true, true);
        boolean test1 = logic(true, false);
        boolean test2 = logic(false, true);
        boolean test3 = logic(false, false);
        assert test0 : "Failing test case: T OR T = T";
        assert test1 : "Failing test case: T OR F = T";
        assert test2 : "Failing test case: F OR T = T";
        assert !test3 : "Failing test case: F OR F = F";
    }
}
