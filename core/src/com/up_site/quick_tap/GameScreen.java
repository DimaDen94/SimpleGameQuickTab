package com.up_site.quick_tap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    SpriteBatch batch;

    Texture back;
    Rectangle recBack;

    Rectangle circ;
    Texture circularIMG;

    Rectangle trig;
    Texture trigonIMG;

    Rectangle rect;
    Texture rectangelIMG;

    Rectangle star;
    Texture starIMG;


    Array<Figure> rectangles;

    int[] listX = {0, 128, 256, 384};
    int[] listY = {0, 128, 256, 384, 512};
    Array<Point> points;

    long lastTime;


    int dropCount;




    void addRect() {
        Rectangle random = new Rectangle();
        int numer = MathUtils.random(1, 4);
        Figure figure = new Figure(random, numer);


        int x = listX[MathUtils.random(0, 3)];
        int y = listY[MathUtils.random(0, 4)];
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
        rectangles.add(figure);

        lastTime = TimeUtils.nanoTime();


        points.add(point);
    }

    Vector3 touch;


    public  GameScreen(final QuickTap game) {
        this.game = game;
        points = new Array<Point>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 512, 784);
        batch = new SpriteBatch();
        touch = new Vector3();
        createBack();
        createCirc();
        createTrigon();
        createRect();
        createStar();
        rectangles = new Array<Figure>();
        addRect();
    }

    private void createBack() {
        back = new Texture(Gdx.files.internal("back.jpg"));
        recBack = new Rectangle();
        recBack.x = 0;
        recBack.y = 764 - 128;
        recBack.width = 128;
        recBack.height = 128;
    }

    private void createCirc() {
        circularIMG = new Texture(Gdx.files.internal("circular.png"));
        circ = new Rectangle();
        circ.x = 0;
        circ.y = 764 - 128;
        circ.width = 128;
        circ.height = 128;
    }

    private void createRect() {
        rectangelIMG = new Texture(Gdx.files.internal("rect.png"));
        rect = new Rectangle();
        rect.x = 256;
        rect.y = 764 - 128;
        rect.width = 128;
        rect.height = 128;
    }

    private void createTrigon() {
        trigonIMG = new Texture(Gdx.files.internal("trigon.png"));
        trig = new Rectangle();
        trig.x = 128;
        trig.y = 764 - 128;
        trig.width = 128;
        trig.height = 128;
    }


    private void createStar() {
        starIMG = new Texture(Gdx.files.internal("star.png"));
        star = new Rectangle();
        star.x = 384;
        star.y = 764 - 128;
        star.width = 128;
        star.height = 128;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.batch.draw(back, recBack.x, recBack.y);
        game.batch.draw(circularIMG, circ.x, circ.y);

        game.batch.draw(back, recBack.x + 128, recBack.y);
        game.batch.draw(trigonIMG, trig.x, trig.y);

        game.batch.draw(back, recBack.x + 256, recBack.y);
        game.batch.draw(rectangelIMG, rect.x, rect.y);

        game.batch.draw(back, recBack.x + 384, recBack.y);
        game.batch.draw(starIMG, star.x, star.y);

        for (Figure r : rectangles) {
            int n = r.number;
            if (n == 1)
                game.batch.draw(circularIMG, r.rectangle.x, r.rectangle.y);
            if (n == 2)
                game.batch.draw(trigonIMG, r.rectangle.x, r.rectangle.y);
            if (n == 3)
                game.batch.draw(rectangelIMG, r.rectangle.x, r.rectangle.y);
            if (n == 4)
                game.batch.draw(starIMG, r.rectangle.x, r.rectangle.y);

        }
        game.font.draw(game.batch,"you catch :" + dropCount+" figures",5,780);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);


            System.out.println(touch.x + " - " + touch.y);
            for (Figure rectangle : rectangles) {
                if (rectangle.rectangle.contains(touch.x, touch.y)) {
                    dropCount++;
                    rectangles.removeValue(rectangle, true);


                    int x = (int) rectangle.rectangle.x;
                    int y = (int) rectangle.rectangle.y;
                    for (Point point1 : points) {
                        if (point1.x == x && point1.y == y) {
                            points.removeValue(point1,false);
                        }
                    }

                }
            }
        }

        if (TimeUtils.nanoTime() - lastTime > 1000000000) {
            addRect();
        }
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
}
