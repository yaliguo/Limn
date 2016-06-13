package my.phoenix.limn;

import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by pe on 2015/12/16.
 */
public class SimActivity extends AndroidApplication{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new FirstGame());
    }
    class FirstGame implements ApplicationListener{
        SpriteBatch batch;
        @Override
        public void create() {
            batch = new SpriteBatch(); //实例化
        }

        @Override
        public void resize(int i, int i1) {

        }

        @Override
        public void render() {
            Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT); //清屏
            batch.begin();
            batch.draw(new Texture(Gdx.files.internal("wip_bk_sunny.jpg")),200,200,200,200);
            batch.end();

        }

        @Override
        public void pause() {

        }

        @Override
        public void resume() {

        }

        @Override
        public void dispose() {

        }
    }
}
