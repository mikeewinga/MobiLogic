package org.ecs160.a2.items;

import org.ecs160.a2.Cell;

public class DoublePortItem extends Item {

    public Cell getInputItem() {
        return inputCell1;
    }

    public void setInputItem(Cell inputItem) {
        this.inputCell1 = inputItem;
    }

    public Cell getOutputItem() {
        return outputCell;
    }

    public void setOutputItem(Cell outputItem) {
        this.outputCell = outputItem;
    }

    public boolean logic(boolean input){
        return false;
    }

    //handle when two inputs are connected
    @Override
    public void setOutput() {
        this.output = (logic(inputCell1.getOutput()));
    }
}
