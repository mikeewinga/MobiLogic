package org.ecs160.a2.items;

import org.ecs160.a2.Cell;
import org.ecs160.a2.Direction;

public class TriplePortItem extends Item {

    public Cell getInputItem1() {
        return inputCell1;
    }

    public void setInputItem1(Cell inputItem1) {
        this.inputCell1 = inputItem1;
    }

    public Cell getInputItem2() {
        return inputCell2;
    }

    public void setInputItem2(Cell inputItem2) {
        this.inputCell2 = inputItem2;
    }

    public Cell getOutputItem() {
        return outputCell;
    }

    public void setOutputItem(Cell outputItem) {
        this.outputCell = outputItem;
    }

    public boolean logic(boolean input1, boolean input2){
        return false;
    }

    //handle when two inputs are connected
    @Override
    public void setOutput() {
        this.output = logic(inputCell1.getOutput(), inputCell2.getOutput());
    }

    @Override
    protected void setDirection(Direction d) {
        super.direction = d;
    }
}
