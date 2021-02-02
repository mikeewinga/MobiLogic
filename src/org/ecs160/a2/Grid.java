package org.ecs160.a2;
import com.codename1.ui.*;

import org.ecs160.a2.items.Item;
import org.ecs160.a2.items.wires.Wire;

public class Grid extends Component {
    private final Integer width = 1000;
    private final Integer height = 2000;
    private final Integer scale = 100;
    private final Integer border = 50;
    private final Integer gridWidth = width/scale;
    private final Integer gridHeight = height/scale;
    public GridObject grid;

    // Default constructor builds the grid on initialization
    public Grid(){
        grid = new GridObject();
    }

    /*
     * Repaints the entire grid
     */
    @Override
    public void paint(Graphics g){

        // Builds the grid
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(0x3D3D3D);
        for(int y=border; y<=height+border; y+=scale){
            for(int x=border; x<=width+border; x+=scale){
                g.drawLine(x,y,x,height);
                g.drawLine(x,y+1,x,height);
                g.drawLine(x,y,width,y);
                g.drawLine(x+1,y,width,y);
            }
        }
        // Looks for any items in the grid and draws them
        for(Cell[] y: grid.cellMatrix){
            for(Cell x: y){
                if(x.isHighlighted){
                    drawHighlight(g,x);
                }
                if(!x.itemID.equals("null") && !x.itemID.equals("BR") && !x.itemID.equals("TR") && !x.itemID.equals("BL")){
                    Item item = (Item) x;
                    g.drawImage(item.getImage(), grid.getPixel(item.getPositionX()), grid.getPixel(item.getPositionY()));
                }
                if(x.itemID.equals("input")) {
                    Input item = (Input) x;
                    grid.InputSend(item);
                }
            }
        }
    }

    private boolean inBounds(int x, int y) {
        return (x > border && x < width && y > border && y < height);
    }

    private void drawHighlight(Graphics g, Cell cell) {
        int eastX = grid.getPixel(cell.getPositionX());
        int westX = grid.getPixel(cell.getPositionX())+scale;
        int northY = grid.getPixel(cell.getPositionY());
        int southY = grid.getPixel(cell.getPositionY())+scale;
        g.setColor(0xFF0000);
        // highlight EAST line
        g.drawLine(eastX,northY,eastX,southY);
        g.drawLine(eastX+1,northY,eastX+1,southY);
        // highlight WEST line
        g.drawLine(westX,northY,westX,southY);
        g.drawLine(westX-1,northY,westX-1,southY);
        // highlight NORTH line
        g.drawLine(eastX,northY,westX,northY);
        g.drawLine(eastX,northY+1,westX,northY+1);
        // highlight SOUTH line
        g.drawLine(eastX,southY,westX,southY);
        g.drawLine(eastX,southY-1,westX,southY-1);
    }

    /*
     * On press, will highlight the cell pressed and stores the cell data in currentCell
     */
    @Override
    public void pointerPressed(int x, int y) {
        int absX = x - getParent().getAbsoluteX();
        int absY = y - getParent().getAbsoluteY();
        if(inBounds(absX,absY)) {
            if (grid.currentCell!=null) {
                grid.cellMatrix[grid.currentCell.getPositionY()][grid.currentCell.getPositionX()].isHighlighted = false;
                grid.currentCell = null;
            }
            grid.cellMatrix[grid.getCell(absY)][grid.getCell(absX)].isHighlighted = true;
            grid.currentCell = grid.cellMatrix[grid.getCell(absY)][grid.getCell(absX)];
            if(grid.currentCell.getItemID().equals("input")){
                Input currentInput = (Input)grid.currentCell;
                currentInput.setState();
                grid.InputSend(currentInput);
            }
        }
//        grid.currentCell.printCell();
    }

    @Override
    public void longPointerPress(int x, int y){
        int absX = x - getParent().getAbsoluteX();
        int absY = y - getParent().getAbsoluteY();
        if(inBounds(absX,absY)) {
            grid.currentCell = grid.cellMatrix[grid.getCell(absY)][grid.getCell(absX)];
            if (grid.currentCell instanceof Item) {
                if (!(grid.currentCell instanceof Wire)) {
                    if (!grid.currentCell.getItemID().equals("led") && !grid.currentCell.getItemID().equals("input")) {
                        grid.setPropDelay((Item) grid.currentCell);
                    }
                }
            }
        }
    }

}
