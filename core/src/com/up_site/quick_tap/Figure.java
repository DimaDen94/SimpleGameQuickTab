package com.up_site.quick_tap;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Dmitry on 16.06.2016.
 */
public class Figure {
    public Rectangle rectangle;
    int number;

    public Figure(Rectangle rectangle, int number) {
        this.rectangle = rectangle;
        this.number = number;
    }
}
