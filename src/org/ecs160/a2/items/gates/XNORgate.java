package org.ecs160.a2.items.gates;

import com.codename1.ui.Image;
import org.ecs160.a2.items.TriplePortItem;

import java.io.IOException;

public class XNORgate extends TriplePortItem {

    public XNORgate(Integer x, Integer y) {
        this.setPosition(x, y);
        this.setItemID("xnor");
        try {
            this.setImage(Image.createImage("/xnorGate.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public XNORgate deepCopy(){
        XNORgate newGate = new XNORgate(this.getPositionX(), this.getPositionY());
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
        return Input1 == Input2;
    }
    public void test() {
        boolean test0 = logic(true, true);
        boolean test1 = logic(true, false);
        boolean test2 = logic(false, true);
        boolean test3 = logic(false, false);
        assert test0 : "Failing test case: T XNOR T = T";
        assert !test1 : "Failing test case: T XNOR F = F";
        assert !test2 : "Failing test case: F XNOR T = F";
        assert test3 : "Failing test case: F XNOR F = T";
    }
}
