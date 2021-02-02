package org.ecs160.a2;

import java.util.ArrayList;

public class StorageClass {
    private Grid grid;
    private ArrayList<GridObject> grids;
    private final int numRegs = 4;
    private MenuBar menu;

    public StorageClass(Grid g) {
        grid = g;
        grids = initGrids();
    }

    // key = corresponding save button / register
    public void SaveToRegister(int key, Grid gridtest) {
        GridObject newgrid = new GridObject();
        for (int i = 0; i < 20; i++){
            for (int j = 0; j < 10; j++){
                newgrid.cellMatrix[i][j] = gridtest.grid.cellMatrix[i][j].deepCopy();
            }
        }
        grids.set(key, newgrid);
        gridtest.repaint();
    }

    public void LoadRegisterToGrid(int key, Grid gridtest) {
       if (grids.get(key) == null) {
           System.out.println("Nothing here!");
        } else {
           GridObject newgrid = grids.get(key);
            for (int i = 0; i < 20; i++){
                for (int j = 0; j < 10; j++){
                    gridtest.grid.cellMatrix[i][j] = newgrid.cellMatrix[i][j];
                }
            }
        }
        gridtest.repaint();
    }

    // clears specified save button / register
    public void ClearRegister(int key) {
        grids.set(key,null);
    }

    //initialized all grids to specified size (should change to get the grid class width/height)
    public ArrayList<GridObject> initGrids() {
        ArrayList<GridObject> initial_grids = new ArrayList<GridObject>(numRegs);
        for (int i = 0; i < numRegs; i++) {
            initial_grids.add(null);
        }
        return initial_grids;
    }

    // Clears contents of ALL registers and saves this to the persistent storage
    public void ClearAllRegisters() {
        grids = initGrids();
    }

    public GridObject getStoredGrid(int register){
        return grids.get(register);
    }

}
