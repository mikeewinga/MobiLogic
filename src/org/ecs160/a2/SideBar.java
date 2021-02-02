package org.ecs160.a2;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;

public class SideBar {
    private final Toolbar bar;

    public SideBar(Form f) {
        bar = f.getToolbar();
    }

    // Adds a header to the menu
    public void addHeader(boolean leftSide, String text, char icon) {
        Container top = new Container(new BorderLayout(),"BarHeader");
        Label header = new Label(text, "BarHeaderText");
        FontImage materialImg = FontImage.createFixed(String.valueOf(icon),FontImage.getMaterialDesignFont(),0xA3E8FF,150,150);
        top.add(BorderLayout.CENTER, header);
        top.add(BorderLayout.WEST, materialImg);
        if(leftSide){
            bar.addComponentToLeftSideMenu(top);
        } else {
            bar.addComponentToRightSideMenu(top);
        }
    }

    // Add an Image from the src folder
    public void addImage(boolean leftSide, String text, String imagePath, Command fn){
        Container item = new Container(new BorderLayout(), "BarItem");
        Label textLabel = new Label(text,"BarItemText");
        try {
            if (leftSide) {
                item.add(BorderLayout.WEST, Image.createImage(imagePath));
            } else {
                item.add(BorderLayout.EAST, Image.createImage(imagePath));
            }

            item.add(BorderLayout.CENTER, textLabel);
            if(leftSide){
                bar.addComponentToLeftSideMenu(item, fn);
            } else {
                bar.addComponentToRightSideMenu(item, fn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a Material Font image
    public void addImage(boolean leftSide, String text, char icon, Command fn){
        Container item = new Container(new BorderLayout(), "BarItem");
        FontImage iconImg = FontImage.createFixed(String.valueOf(icon),FontImage.getMaterialDesignFont(),0xDFDFDF,100,100);
        Label textLabel = new Label(text,"BarItemText");
        if (leftSide) {
            item.add(BorderLayout.WEST, iconImg);
        } else {
            item.add(BorderLayout.EAST, iconImg);
        }
        item.add(BorderLayout.CENTER, textLabel);
        if(leftSide){
            bar.addComponentToLeftSideMenu(item, fn);
        } else {
            bar.addComponentToRightSideMenu(item, fn);
        }
    }

    public void close() {
        this.bar.closeSideMenu();
    }
}
