package org.ecs160.a2;

import com.codename1.ui.*;

public class MenuBar {
    private StorageClass storage;
    private SideBar menuBar;
    Grid grid;

    public MenuBar(Form f, Grid g, StorageClass s) {
        storage = s;
        grid = g;
        final boolean onLeft = true;
        menuBar = new SideBar(f);
        menuBar.addHeader(onLeft, "MENU", FontImage.MATERIAL_MISCELLANEOUS_SERVICES);

        menuBar.addImage(onLeft, "Clear Circuit", FontImage.MATERIAL_MEMORY,Command.create("MENU",null,(ev)->{
            g.grid.clearGrid();
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "Save", FontImage.MATERIAL_SAVE,Command.create("MENU",null,(ev)->
                System.out.println("Save")
        ));

        menuBar.addImage(onLeft, "    Save Slot 1", FontImage.MATERIAL_LOOKS_ONE,Command.create("MENU",null, ev ->{
            System.out.println("Save 0");
            storage.SaveToRegister(0,grid);
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "    Save Slot 2", FontImage.MATERIAL_LOOKS_TWO,Command.create("MENU",null,(ev)->{
            System.out.println("Save 1");
            storage.SaveToRegister(1,grid);
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "    Save Slot 3", FontImage.MATERIAL_LOOKS_3,Command.create("MENU",null,(ev)->{
            System.out.println("Save 2");
            storage.SaveToRegister(2,grid);
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "   Save Slot 4", FontImage.MATERIAL_LOOKS_4,Command.create("MENU",null,(ev)->{
            System.out.println("Save 3");
            storage.SaveToRegister(3,grid);
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "Load", FontImage.MATERIAL_FOLDER_OPEN,Command.create("MENU",null,(ev)->
                System.out.println("Load")
        ));

        menuBar.addImage(onLeft, "    Load Slot 1", FontImage.MATERIAL_LOOKS_ONE,Command.create("MENU",null,(ev)->{
            System.out.println("Load 0");
            storage.LoadRegisterToGrid(0, grid);
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "    Load Slot 2", FontImage.MATERIAL_LOOKS_TWO,Command.create("MENU",null,(ev)->{
            System.out.println("Load 1");
            storage.LoadRegisterToGrid(1, grid);
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "    Load Slot 3", FontImage.MATERIAL_LOOKS_3,Command.create("MENU",null,(ev)->{
            System.out.println("Load 2");
            storage.LoadRegisterToGrid(2, grid);
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "   Load Slot 4", FontImage.MATERIAL_LOOKS_4,Command.create("MENU",null,(ev)->{
            System.out.println("Load 3");
            storage.LoadRegisterToGrid(3, grid);
            menuBar.close();
        }));

        menuBar.addImage(onLeft, "Exit", FontImage.MATERIAL_CLOSE,Command.create("MENU",null,(ev)->
                Display.getInstance().exitApplication()
        ));
    }

}