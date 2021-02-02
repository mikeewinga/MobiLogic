package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import org.ecs160.a2.items.DoublePortItem;
import org.ecs160.a2.items.Item;
import org.ecs160.a2.items.LED;
import org.ecs160.a2.items.TriplePortItem;
import org.ecs160.a2.items.gates.NOTgate;
import org.ecs160.a2.items.wires.Wire;
import org.ecs160.a2.items.wires.WireL;
import org.ecs160.a2.items.wires.WireS;
import org.ecs160.a2.items.wires.WireT;

public class GridObject {
    public Cell[][] cellMatrix;
    public Cell currentCell;
    private final Integer scale = 100;
    private final Integer border = 50;
    private final Integer width = 10;
    private final Integer height = 20;

    public GridObject(){
        cellMatrix = new Cell[height][width];
        clearGrid();
    }

    // converts cell value to pixel
    public Integer getPixel(Integer cell){
        return(border+(cell*scale));
    }

    // converts pixel value to cell
    public Integer getCell(Integer pixel) {
        return((pixel-border)/scale);
    }

    public Cell getCurrentCell(){
        return currentCell;
    }

    // Adds item to the grid
    public void addToGrid(Item item){
        cellMatrix[item.getPositionY()][item.getPositionX()] = item;
        if (!(item instanceof Wire))

            // For 2x2 items:
            if (item instanceof TriplePortItem) {
                cellMatrix[item.getPositionY()+1][item.getPositionX()].setItemID("BL"); // bottom left
                cellMatrix[item.getPositionY()][item.getPositionX()+1].setItemID("TR"); // top right
                cellMatrix[item.getPositionY()+1][item.getPositionX()+1].setItemID("BR"); // bottom right
            }

    }

    public void setPropDelay(Item item){
        Label popup = new Label("text");
        Dialog d = new Dialog("Add Delay Value");
        d.setLayout(BoxLayout.yCenter());
        Picker stringPicker = new Picker();
        stringPicker.setType(Display.PICKER_TYPE_STRINGS);
        stringPicker.setStrings("10", "20", "30", "40", "50");
        Button showDialog = new Button("done");
        d.add(stringPicker);
        d.add(showDialog);
        showDialog.addActionListener((e) -> item.setDelay(Integer.parseInt(stringPicker.getSelectedString())));
        d.showPopupDialog(popup);
    }

    // propagation delay for double port
    public void calcPropdelay_d(Cell currentItem, Cell NextItem){
        int cur_delay = currentItem.getPropDelay();
        int delay_value = NextItem.getDelay();
        NextItem.setPropDelay(cur_delay + delay_value);
    }

    // propagation delay for triple port
    public void calcPropdelay_t(TriplePortItem NextItem){
        adjustConnections(NextItem);
        Cell from1 = find2x2Space(NextItem.getInputCell1());
        Cell from2 = find2x2Space(NextItem.getInputCell2());
        int delay1 = from1.getPropDelay();
        int delay2 = from2.getPropDelay();
        int delay_value = NextItem.getDelay();
        NextItem.setPropDelay(Math.max(delay1, delay2) + delay_value);
    }

    // This determines the next item to connect/compute the 0/1 state
    public void Connect(Cell currentItem, Cell NextItem) {

        if (NextItem instanceof NOTgate) {
            DoublePortItem n = (DoublePortItem)NextItem;
            DoublePortConnect(n);
        }

        if (NextItem instanceof Wire) {
            Wire n = (Wire)NextItem;
            WireConnect(n,(Item)currentItem);
        }

        if (NextItem instanceof TriplePortItem) {
            TriplePortItem n = (TriplePortItem)NextItem;
            TriplePortConnect(n);
        }

        if (NextItem instanceof Circuit) {
            Circuit circ = (Circuit)NextItem;
            CircuitConnect(circ);
        }

        if (NextItem instanceof LED) {
            LED led = (LED)NextItem;
            LEDConnect(led);
        }
    }

    public Cell find2x2Space(Cell port) {
        Cell space = checkCell(port.getPositionY(), port.getPositionX());
        switch (space.getItemID()) {
            case "BL":
                space = cellMatrix[port.getPositionY() - 1][port.getPositionX()];
                break;
            case "BR":
                space = cellMatrix[port.getPositionY() - 1][port.getPositionX() - 1];
                break;
            case "TR":
                space = cellMatrix[port.getPositionY()][port.getPositionX() - 1];
                break;
        }
        return space;
    }

    // Need to make sure the output item actually has the current item as its input
    public boolean OutputIsConnected(Item current, Item next) {
        if (next instanceof Wire) {
            Wire n = (Wire)next;
            adjustWireConnections(n);
            return n.GetPort1() == current ||
                    n.GetPort2() == current ||
                    n.GetPort3() == current;
        } else {
            adjustConnections(next);
            return next.getInputCell1() == current ||
                    next.getInputCell2() == current;
        }
    }

    public void CircuitConnect(Circuit circ) {
        // Get the input and output connections
        adjustConnections(circ);
        Cell from1 = find2x2Space(circ.getInputCell1());
        Cell from2 = find2x2Space(circ.getInputCell2());
        Cell to = find2x2Space(circ.getOutputCell());

        // Calculate the state
        if (from1.containsItem() && from2.containsItem() && to.containsItem()) {
            circ.setInputItem1(from1);
            circ.connectInput1();
            circ.setInputItem2(from2);
            circ.connectInput2();
            InputSend(circ.getInternalInput1());
            InputSend(circ.getInternalInput2());
            circ.setOutput();
            circ.setOutputItem();
            circ.setOutputItem(to);
            Connect(circ, to);
        }
    }

    // The 0/1 starts at the input, so we take the input item and send its state
    // to the next item
    public void InputSend (Input input) {
        adjustConnections(input);
        Cell reciever = find2x2Space(input.getOutputCell());

        if (reciever.containsItem() && OutputIsConnected(input, (Item)reciever)) {
            input.setOutputItem(reciever);
            Connect(input, reciever);
        }
    }

    public boolean samePos(Cell a, Cell b) {
        return a.getPositionX() == b.getPositionX() && a.getPositionY() == b.getPositionY();
    }

    public boolean checkForOutput(Item current, Cell portOfNextItem) {
        if (!portOfNextItem.getItemID().equals("null")) {
            Item temp = (Item)portOfNextItem;
            if (temp instanceof Wire == false) {
                adjustConnections(temp);
                return samePos(temp.getOutputCell(), current);
            }
        }
        return false;
    }

    public void TwireForward(Wire wire, Cell portA, Cell portB) {
        if (checkForOutput(wire, portA) || checkForOutput(wire, portB))
            wire.setNull(true);
        if (portB.containsItem() && OutputIsConnected(wire, (Item)portB)){
            wire.setOutputItem(portB);
            Connect(wire, portB);
        }
        //wire.setNull(checkForOutput(wire, portB));
        if (portA.containsItem() && OutputIsConnected(wire, (Item)portA)){
            wire.setOutputItem2(portA);
            Connect(wire, portA);
        }
        //wire.setNull(checkForOutput(wire, portA));
    }

    public void WireConnect(Wire wire, Item Previous) {
        adjustWireConnections(wire);
        Cell port1 = find2x2Space(wire.GetPort1());
        Cell port2 = find2x2Space(wire.GetPort2());

        wire.setInputItem(Previous);
        wire.setOutput();
        wire.setState();

        wire.setNull(Previous.getNull());

        if (wire instanceof WireT) {
            Cell port3 = find2x2Space(wire.GetPort3());
            if (samePos(Previous, port1)) {
                TwireForward(wire,port2,port3);
            }
            else if (samePos(Previous, port2)) {
                TwireForward(wire, port3, port1);
            }
            else if (samePos(Previous, port3)) {
                TwireForward(wire,port1, port3);
            }
        } else {
            if (checkForOutput(wire, port2) && checkForOutput(wire, port1))
                wire.setNull(true);
            if (samePos(Previous, port1) && port2.containsItem() && OutputIsConnected(wire, (Item)port2)) {
                wire.setOutputItem(port2);
                Connect(wire, port2);
            }
            else if (samePos(Previous, port2) && port1.containsItem() && OutputIsConnected(wire, (Item)port1)) {
                wire.setOutputItem(port1);
                Connect(wire, port1);
            }
        }
    }

    // This sets the input and the output of a double-port gate (NOT, S-Wires)
    public void DoublePortConnect (DoublePortItem g) {
        // Get the input and output connections
        adjustConnections(g);
        Cell from = find2x2Space(g.getInputCell1());
        Cell to = find2x2Space(g.getOutputCell());

        if (from.containsItem() && to.containsItem()) {
            // Calculate the state
            g.setInputItem(from);
            Item fromItem = (Item)from;
            if (!fromItem.getNull()){
                g.setOutput();
                g.setOutputItem(to);
                g.setNull(false);
            } else g.setNull(true);
        } else {
            g.setNull(true);
        }
        Connect(g, to);
    }

    // This sets the input and the output of a triple-port gate (Logic gates excluding NOT gates)
    public void TriplePortConnect (TriplePortItem g) {

        // Get the input and output connections
        adjustConnections(g);
        Cell from1 = find2x2Space(g.getInputCell1());
        Cell from2 = find2x2Space(g.getInputCell2());
        Cell to = find2x2Space(g.getOutputCell());

        // Calculate the state
        if (from1.containsItem() && from2.containsItem() && to.containsItem()) {
            g.setInputItem1(from1);
            g.setInputItem2(from2);
            Item from1Item = (Item)from1;
            Item from2Item = (Item)from2;
            if (!from1Item.getNull() && !from2Item.getNull()) {
                g.setOutput();
                g.setOutputItem(to);
                g.setNull(false);
            } else g.setNull(true);
        } else {
            g.setNull(true);
        }
        Connect(g, to);
    }

    // This determines the state for the LED based on its input
    public void LEDConnect(LED current) {
        // Find coordinates for ports
        adjustConnections(current);
        Cell from = find2x2Space(current.getInputCell1());

        // Calculate the state
        if (from.containsItem()) {
            Item fromItem = (Item)from;
            if (!fromItem.getNull()) {
                current.setConnection(from);
                current.setState();
            } else current.resetState();
        } else {
            // The LED is not connected to anything, so it should be OFF
            current.resetState();
        }
    }

    // Checks if the cell is outside of the grid
    private Cell checkCell(int y, int x) {
        try{
            return cellMatrix[y][x];
        }catch (ArrayIndexOutOfBoundsException e) {
            return new Cell();
        }
    }

    /*
     * Updates the inputs and output for a given Item
     */
    public void adjustConnections(Item item) {
        if (item instanceof TriplePortItem && !(item instanceof WireT)) {
            if (item.getDirection() == Direction.EAST) {
                item.setOutputCell(checkCell(item.getPositionY(),item.getPositionX()+2));
                item.setInputCell1(checkCell(item.getPositionY(),item.getPositionX()-1));
                item.setInputCell2(checkCell(item.getPositionY()+1,item.getPositionX()-1));
            }
            else if (item.getDirection() == Direction.WEST) {
                item.setOutputCell(checkCell(item.getPositionY()+1,item.getPositionX()-1));
                item.setInputCell1(checkCell(item.getPositionY()+1,item.getPositionX()+2));
                item.setInputCell2(checkCell(item.getPositionY(),item.getPositionX()+2));
            }
            else if (item.getDirection() == Direction.NORTH) {
                item.setOutputCell(checkCell(item.getPositionY()-1,item.getPositionX()));
                item.setInputCell1(checkCell(item.getPositionY()+2,item.getPositionX()));
                item.setInputCell2(checkCell(item.getPositionY()+2,item.getPositionX()+1));
            }
            else if (item.getDirection() == Direction.SOUTH) {
                item.setOutputCell(checkCell(item.getPositionY()+2,item.getPositionX()+1));
                item.setInputCell1(checkCell(item.getPositionY()-1,item.getPositionX()));
                item.setInputCell2(checkCell(item.getPositionY()-1,item.getPositionX()+1));
            }
        }
        else {
            if (item.getDirection() == Direction.EAST) {
                item.setOutputCell(checkCell(item.getPositionY(),item.getPositionX()+1));
                item.setInputCell1(checkCell(item.getPositionY(),item.getPositionX()-1));
            }
            else if (item.getDirection() == Direction.WEST) {
                item.setOutputCell(checkCell(item.getPositionY(),item.getPositionX()-1));
                item.setInputCell1(checkCell(item.getPositionY(),item.getPositionX()+1));
            }
            else if (item.getDirection() == Direction.NORTH) {
                item.setOutputCell(checkCell(item.getPositionY()-1,item.getPositionX()));
                item.setInputCell1(checkCell(item.getPositionY()+1,item.getPositionX()));
            }
            else if (item.getDirection() == Direction.SOUTH) {
                item.setOutputCell(checkCell(item.getPositionY()+1,item.getPositionX()));
                item.setInputCell1(checkCell(item.getPositionY()-1,item.getPositionX()));
            }
        }
    }

    public void adjustWireConnections(Wire item) {
        if (item instanceof WireS){
            if (item.getDirection() == Direction.EAST || item.getDirection() == Direction.WEST) {
                item.SetPort1(checkCell(item.getPositionY(),item.getPositionX()+1));
                item.SetPort2(checkCell(item.getPositionY(),item.getPositionX()-1));
            }
            else if (item.getDirection() == Direction.NORTH || item.getDirection() == Direction.SOUTH) {
                item.SetPort1(checkCell(item.getPositionY()-1,item.getPositionX()));
                item.SetPort2(checkCell(item.getPositionY()+1,item.getPositionX()));
            }
        }
        else if (item instanceof WireL){
            if (item.getDirection() == Direction.EAST) {
                item.SetPort1(checkCell(item.getPositionY(),item.getPositionX()+1));
                item.SetPort2(checkCell(item.getPositionY()-1,item.getPositionX()));
            }
            else if (item.getDirection() == Direction.WEST) {
                item.SetPort1(checkCell(item.getPositionY(),item.getPositionX()-1));
                item.SetPort2(checkCell(item.getPositionY()+1,item.getPositionX()));
            }
            else if (item.getDirection() == Direction.NORTH) {
                item.SetPort1(checkCell(item.getPositionY()-1,item.getPositionX()));
                item.SetPort2(checkCell(item.getPositionY(),item.getPositionX()-1));
            }
            else if (item.getDirection() == Direction.SOUTH) {
                item.SetPort1(checkCell(item.getPositionY()+1,item.getPositionX()));
                item.SetPort2(checkCell(item.getPositionY(),item.getPositionX()+1));
            }
        }
        else if (item instanceof WireT) {
            if (item.getDirection() == Direction.EAST) {
                item.SetPort1(checkCell(item.getPositionY(),item.getPositionX()+1));
                item.SetPort2(checkCell(item.getPositionY()-1,item.getPositionX()));
                item.SetPort3(checkCell(item.getPositionY()+1,item.getPositionX()));
            }
            else if (item.getDirection() == Direction.NORTH) {
                item.SetPort1(checkCell(item.getPositionY()-1,item.getPositionX()));
                item.SetPort2(checkCell(item.getPositionY(),item.getPositionX()-1));
                item.SetPort3(checkCell(item.getPositionY(),item.getPositionX()+1));
            }
            else if (item.getDirection() == Direction.WEST) {
                item.SetPort1(checkCell(item.getPositionY(),item.getPositionX()-1));
                item.SetPort2(checkCell(item.getPositionY()+1,item.getPositionX()));
                item.SetPort3(checkCell(item.getPositionY()-1,item.getPositionX()));
            }
            else if (item.getDirection() == Direction.SOUTH) {
                item.SetPort1(checkCell(item.getPositionY()+1,item.getPositionX()));
                item.SetPort2(checkCell(item.getPositionY(),item.getPositionX()-1));
                item.SetPort3(checkCell(item.getPositionY(),item.getPositionX()+1));
            }
        }
    }

    public void clearGrid() {
        for(int y=0; y<height; y++){
            for(int x=0; x<width; x++){
                cellMatrix[y][x]= new Cell(x,y);
            }
        }
        currentCell = null;
    }
}
