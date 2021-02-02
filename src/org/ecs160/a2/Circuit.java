package org.ecs160.a2;

import com.codename1.ui.Image;
import org.ecs160.a2.items.LED;
import org.ecs160.a2.items.TriplePortItem;

import java.io.IOException;

public class Circuit extends TriplePortItem {
    private GridObject circuit;
    private Input internalInput1 = null;
    private Input internalInput2 = null;
    private LED internalOutput = null;

    public Circuit(GridObject c, Integer x, Integer y) {
        this.circuit = c;
        this.setPosition(x, y);
        this.setItemID("circuit");
        try {
            this.setImage(Image.createImage("/circuit.png"));
        } catch (IOException e){
            e.printStackTrace();
        }
        initCircuit();
    }

    // This moves through the Cell matrix and finds the inputs and outputs
    // of the circuit and stores them in class variables
    private void initCircuit() {
        for(Cell[] y: circuit.cellMatrix){
            for(Cell x: y){
                if(x instanceof Input){
                    if(internalInput1 == null) {
                        internalInput1 = (Input) x;
                    } else {
                        internalInput2 = (Input) x;
                    }
                } else if(x instanceof LED) {
                    internalOutput = (LED) x;
                }
            }
        }
    }

    public void connectInput1() {
        this.internalInput1.setOutput( inputCell1.getOutput());
    }

    public void connectInput2() {
        this.internalInput2.setOutput( inputCell2.getOutput());
    }

    public void setOutputItem() {
        this.internalOutput.setOutput( outputCell.getOutput());
    }

    public Input getInternalInput1() {return this.internalInput1;}

    public Input getInternalInput2() {return this.internalInput2;}

    public LED getInternalOutput() {return this.internalOutput;}

    @Override
    public void setOutput() {
        this.output = this.internalOutput.getOutput();
    }

    @Override
    public void printCell(){
        System.out.println(this.itemID +
                ": [Position: {X:" + this.getPositionX() +
                ", Y:" + this.getPositionY() +
                "}], [OutputDirection: " + this.direction +
                "], [Output: " + this.output +
                "] ::: "+this);
    }

}
