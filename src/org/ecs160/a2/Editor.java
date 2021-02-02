package org.ecs160.a2;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.layouts.BoxLayout;
import org.ecs160.a2.items.Item;
import org.ecs160.a2.items.TriplePortItem;

public class Editor extends Container {
    Container editor =  new Container(BoxLayout.x(),"EditorContainer");
    Grid grid;

    public Editor(Grid g) {
        grid = g;
        Button delete = new Button("Delete", FontImage.MATERIAL_DELETE_FOREVER,5,"EditorButton");
        delete.addActionListener( evt -> deleteItem(grid));
        Button rotate = new Button("Rotate", FontImage.MATERIAL_ROTATE_RIGHT,5,"EditorButton");
        rotate.addActionListener( evt -> rotateItem(grid));
        Button mirror = new Button("Mirror", FontImage.MATERIAL_FLIP,5,"EditorButton");
        mirror.addActionListener( evt -> mirrorItem(grid));
        editor.add(delete);
        editor.add(rotate);
        editor.add(mirror);
    }

    private void rotateItem(Grid g) {
        if(!g.grid.currentCell.getItemID().equals("null")) {
            Item currentItem = (Item) g.grid.currentCell;
            currentItem.rotate();
            g.repaint();
        }
    }

    private void deleteItem(Grid g) {
        if(!g.grid.getCurrentCell().getItemID().equals("null")) {
            Cell current = g.grid.getCurrentCell();

            // Get the stored item's null status to propogate it along the circuit upon deletion
            Item stored = (Item)current;
            if (stored.getOutputCell() instanceof Item) {
                Item storedOut  = (Item)stored.getOutputCell();
                stored.setNull(true);
                g.grid.Connect(current,storedOut);
            }

            g.grid.cellMatrix[current.getPositionY()][current.getPositionX()] = new Cell(current.getPositionX(),current.getPositionY());
            g.repaint();

            // clear the other 3 spaces if the object is 2x2
            if(g.grid.currentCell instanceof TriplePortItem) {
                g.grid.cellMatrix[current.getPositionY()+1][current.getPositionX()].setItemID("null");
                g.grid.cellMatrix[current.getPositionY()][current.getPositionX()+1].setItemID("null");
                g.grid.cellMatrix[current.getPositionY()+1][current.getPositionX()+1].setItemID("null");
            }
        }
    }

    private void mirrorItem(Grid g) {
        if(!g.grid.currentCell.getItemID().equals("null")) {
            Item currentItem = (Item) g.grid.currentCell;
            currentItem.mirror();
            g.repaint();
        }
    }

    public Container getEditor(){
        return editor;
    }

}
