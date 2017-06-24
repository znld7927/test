package com.robpercival.doraemonrun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DoraemonRun extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] doraemons;
	int flapState = 0;
	float doraemonY = 0;
	float velocity = 0;
	int gameState = 0;
	float gravity =2;
	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		doraemons = new Texture[2];
		doraemons[0] = new Texture("doraemon.png");
		doraemons[1] = new Texture("doraemon2.png");
		doraemonY = Gdx.graphics.getHeight() / 2 - doraemons[flapState].getHeight() / 2;
	}

	@Override
	public void render() {

		if (gameState != 0) {

			if (Gdx.input.justTouched()) {
				velocity = -30;

			}
			if(doraemonY > 0 || velocity < 0) {

				velocity = velocity + gravity;
				doraemonY -= velocity;
			}
		}else{
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}
		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(doraemons[flapState], Gdx.graphics.getWidth() / 2 - doraemons[flapState].getWidth() / 2, doraemonY);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
