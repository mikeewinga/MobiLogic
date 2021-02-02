package org.ecs160.a2;

public enum Direction {
    EAST,
    SOUTH,
    WEST,
    NORTH;

    public Direction next() {
        if(ordinal()>=3){
            return values()[0];
        }
        return values()[ordinal() + 1];
    }
}

