package com.up_site.quick_tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {
    final QuickTap game;
    OrthographicCamera camera;
    Vector3 touch;
    SpriteBatch batch;

    Texture back;
    Rectangle recBack;

    Rectangle circle;
    Texture circleIMG;

    Rectangle triangle;
    Texture triangleIMG;

    Rectangle rect;
    Texture rectangleIMG;

    Rectangle star;
    Texture starIMG;

    Array<Point> points;
    Array<Figure> figures;

    final int[] listX = {0, 128, 256, 384};
    final int[] listY = {0, 128, 256, 384, 512};


    long lastTime;
    int dropCount;

    private Texture getRandomTexture() {
        int r = MathUtils.random(1, 4);
        switch (r) {
            case 1:
                return circleIMG;
            case 2:
                return triangleIMG;
            case 3:
                return rectangleIMG;
            case 4:
                return starIMG;
        }
        return circleIMG;
    }

    private Color getRandomColor() {

        int r = MathUtils.random(1, 4);
        switch (r) {
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.CYAN;
            case 4:
                return Color.ORANGE;
        }
        return Color.BLUE;
    }

    void addRect() {
        Rectangle random = new Rectangle();
        int x = listX[MathUtils.random(0, 3)];
        int y = listY[MathUtils.random(0, 4)];

        Figure figure = new Figure(random, getRandomTexture(), getRandomColor());
        random.x = x;
        random.y = y;

        random.width = 128;
        random.height = 128;

        Point point = new Point();
        point.x = x;
        point.y = y;

        for (Point point1 : points) {
            if (point1.x == x && point1.y == y) {
                return;
            }
        }
        figures.add(figure);
        lastTime = TimeUtils.nanoTime();
        points.add(point);
    }


    public GameScreen(final QuickTap game) {
        this.game = game;
        points = new Array<Point>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 512, 784);
        batch = new SpriteBatch();
        touch = new Vector3();
        createField();
        figures = new Array<Figure>();
        addRect();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        drawField();

        for (Figure r : figures) {

            game.batch.setColor(r.getColor());
            game.batch.draw(r.getTexture(), r.getRectangle().x, r.getRectangle().y);

        }
        game.font.draw(game.batch, "you caught :" + dropCount + " figures", 5, 780);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);


            System.out.println(touch.x + " - " + touch.y);
            for (Figure rectangle : figures) {
                if (rectangle.getRectangle().contains(touch.x, touch.y)) {
                    dropCount++;
                    figures.removeValue(rectangle, true);


                    int x = (int) rectangle.getRectangle().x;
                    int y = (int) rectangle.getRectangle().y;
                    for (Point point1 : points) {
                        if (point1.x == x && point1.y == y) {
                            points.removeValue(point1, false);
                        }
                    }

                }
            }
        }

        if (TimeUtils.nanoTime() - lastTime > 1000000000) {
            addRect();
        }
    }


    void drawField() {
        game.batch.setColor(Color.WHITE);

        game.batch.draw(back, recBack.x, recBack.y);
        game.batch.draw(back, recBack.x + 128, recBack.y);
        game.batch.draw(back, recBack.x + 256, recBack.y);
        game.batch.draw(back, recBack.x + 384, recBack.y);


        game.batch.setColor(Color.LIGHT_GRAY);

        game.batch.draw(circleIMG, circle.x, circle.y);
        game.batch.draw(triangleIMG, triangle.x, triangle.y);
        game.batch.draw(rectangleIMG, rect.x, rect.y);
        game.batch.draw(starIMG, star.x, star.y);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }
    private void createField() {
        createBack();
        createCircle();
        createTriangle();
        createRect();
        createStar();
    }

    private void createBack() {
        back = new Texture(Gdx.files.internal("back.jpg"));
        recBack = new Rectangle();
        recBack.x = 0;
        recBack.y = 764 - 128;
        recBack.width = 128;
        recBack.height = 128;
    }
    private void createCircle() {
        circleIMG = new Texture(Gdx.files.internal("circular.png"));
        circle = new Rectangle();
        circle.x = 0;
        circle.y = 764 - 128;
        circle.width = 128;
        circle.height = 128;
    }
    private void createRect() {
        rectangleIMG = new Texture(Gdx.files.internal("rect.png"));
        rect = new Rectangle();
        rect.x = 256;
        rect.y = 764 - 128;
        rect.width = 128;
        rect.height = 128;
    }
    private void createTriangle() {
        triangleIMG = new Texture(Gdx.files.internal("trigon.png"));
        triangle = new Rectangle();
        triangle.x = 128;
        triangle.y = 764 - 128;
        triangle.width = 128;
        triangle.height = 128;
    }
    private void createStar() {
        starIMG = new Texture(Gdx.files.internal("star.png"));
        star = new Rectangle();
        star.x = 384;
        star.y = 764 - 128;
        star.width = 128;
        star.height = 128;
    }
}
