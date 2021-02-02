package org.ecs160.a2.items;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BorderLayout;
import org.ecs160.a2.Cell;
import org.ecs160.a2.Direction;


public class Item extends Cell {
    private Image image;
    protected boolean isNull;
    protected Direction direction = Direction.EAST;  //direction of output
    protected Cell outputCell, inputCell1, inputCell2;

    public void rotate() {
        this.image = image.rotate(90);
        this.setDirection(this.direction.next());
    }

    protected void setDirection(Direction d) {
        this.direction = d;
    }

    public void mirror() {
        this.image = image.mirror();
        if(this.direction==Direction.EAST || this.direction==Direction.WEST){
            this.direction = this.direction.next();
            this.direction = this.direction.next();
        }
    }

    public void setNull(boolean n){ this.isNull = n; }

    public boolean getNull(){ return this.isNull; }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setOutputCell(Cell cell) { this.outputCell = cell; }

    public Cell getOutputCell() {return this.outputCell;}

    public void setInputCell1(Cell cell) {this.inputCell1 = cell;}

    public Cell getInputCell1() {return this.inputCell1;}

    public void setInputCell2(Cell cell) {this.inputCell2 = cell;}

    public Cell getInputCell2() {return this.inputCell2;}

    public Direction getDirection() { return direction; }

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