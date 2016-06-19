package com.up_site.quick_tap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Dmitry on 16.06.2016.
 */
public class Figure {
    private Rectangle rectangle;
    private Texture texture;
    private Color color;
    public Figure(Rectangle rectangle, Texture texture, Color color) {
        this.rectangle = rectangle;
        this.texture = texture;
        this.color = color;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public Color getColor() {
        return color;
    }
}
