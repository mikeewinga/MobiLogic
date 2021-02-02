package org.ecs160.a2;
import com.codename1.ui.*;
import org.ecs160.a2.items.Item;
import org.ecs160.a2.items.LED;
import org.ecs160.a2.items.gates.*;
import org.ecs160.a2.items.wires.WireL;
import org.ecs160.a2.items.wires.WireS;
import org.ecs160.a2.items.wires.WireT;

public class ItemBar {
    private Grid mainGrid;
    private SideBar itemBar;
    private StorageClass storage;

    public ItemBar(Form f, Grid g, StorageClass s) {
        mainGrid = g;
        storage = s;
        final boolean onLeft = false;
        itemBar = new SideBar(f);
        itemBar.addHeader(onLeft, "ITEM LIST", FontImage.MATERIAL_ADD_CIRCLE_OUTLINE);

        itemBar.addImage(onLeft,"Input Gate","/input_white.png",Command.create("Menu",null,(ev)->
                addItem(new Input(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"LED","/led_white.png",Command.create("Menu",null,(ev)->
                addItem(new LED(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"NOT Gate","/notGate_white.png",Command.create("Menu",null,(ev)->
                addItem(new NOTgate(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"AND Gate","/andGate_white.png",Command.create("Menu",null,(ev)->
                addItem(new ANDgate(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"NAND Gate","/nandGate_white.png",Command.create("Menu",null,(ev)->
                addItem(new NANDgate(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"OR Gate","/orGate_white.png",Command.create("Menu",null,(ev)->
                addItem(new ORgate(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"NOR Gate","/norGate_white.png",Command.create("Menu",null,(ev)->
                addItem(new NORgate(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"XOR Gate","/xorGate_white.png",Command.create("Menu",null,(ev)->
                addItem(new XORgate(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"XNOR Gate","/xnorGate_white.png",Command.create("Menu",null,(ev)->
                addItem(new XNORgate(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"Straight Wire","/wire_straight_white.png",Command.create("Menu",null,(ev)->
                addItem(new WireS(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"L-Wire","/wire_L_white.png",Command.create("Menu",null,(ev)->
                addItem(new WireL(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"T-Wire","/wire_T_white.png",Command.create("Menu",null,(ev)->
                addItem(new WireT(getCurrentX(),getCurrentY())))
        );

        itemBar.addImage(onLeft,"Circuit Save 1","/circuit_white.png",Command.create("Menu",null,(ev)->
                addCircuit(0))
        );

        itemBar.addImage(onLeft,"Circuit Save 2","/circuit_white.png",Command.create("Menu",null,(ev)->
                addCircuit(1))
        );

        itemBar.addImage(onLeft,"Circuit Save 3","/circuit_white.png",Command.create("Menu",null,(ev)->
                addCircuit(2))
        );

        itemBar.addImage(onLeft,"Circuit Save 4","/circuit_white.png",Command.create("Menu",null,(ev)->
                addCircuit(3))
        );
    }

    private void addCircuit(int register){
        if(!(storage.getStoredGrid(register)==null)){
            addItem(new Circuit(storage.getStoredGrid(register),getCurrentX(),getCurrentY()));
        }
    }

    private int getCurrentX() {
        if(mainGrid.grid.getCurrentCell()!=null){
            return mainGrid.grid.getCurrentCell().getPositionX();
        }
        return 0;
    }

    private int getCurrentY() {
        if(mainGrid.grid.getCurrentCell()!=null){
            return mainGrid.grid.getCurrentCell().getPositionY();
        }
        return 0;
    }

    private void addItem(Item i) {
        if(mainGrid.grid.getCurrentCell()!=null){
            mainGrid.grid.addToGrid(i);
            mainGrid.repaint();
            itemBar.close();
        }
    }
}

