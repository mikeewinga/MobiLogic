package org.ecs160.a2.items;

import com.codename1.ui.Image;
import org.ecs160.a2.Cell;

public class SinglePortItem extends Item {
    public boolean isInput;
    protected Image onImage;
    protected Image offImage;
    protected Cell connectedItem = null;

    public Cell getConnection() {
        return connectedItem;
    }

    public void setConnection(Cell connectedItem) {
        this.connectedItem = connectedItem;
        this.output = connectedItem.getOutput();
    }

    //If isInput, handle when toggled
    @Override
    public void setOutput() {
        this.output = !this.output;
    }
}
