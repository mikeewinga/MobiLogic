package org.ecs160.a2;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;

public class Canvas extends Form{
    private Form canvas;
    public Grid grid;
    private StorageClass storage;

    public Canvas(){
        grid = new Grid();
        storage = new StorageClass(grid);
        canvas = new Form("MobiLogic", new BorderLayout());
        canvas.add(BorderLayout.CENTER, grid);
        new MenuBar(canvas, grid, storage);
        new ItemBar(canvas, grid, storage);
        canvas.add(BorderLayout.SOUTH,new Editor(grid).getEditor());
        canvas.show();
    }

}
