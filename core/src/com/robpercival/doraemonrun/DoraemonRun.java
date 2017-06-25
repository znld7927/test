package com.robpercival.doraemonrun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class DoraemonRun extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture[] doraemons;
	int flapState = 0;
	float doraemonY = 0;
	float velocity = 0;
	int gameState = 0;
	float gravity =2;
	float gap = 400;
	float maxTubeOffSet;
	Random randomGenerator;
	float tubeOffset;

	Texture topTube;
	Texture bottomTube;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		doraemons = new Texture[2];
		doraemons[0] = new Texture("doraemon.png");
		doraemons[1] = new Texture("doraemon2.png");
		doraemonY = Gdx.graphics.getHeight() / 2 - doraemons[flapState].getHeight() / 2;

		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffSet = Gdx.graphics.getHeight()/2 - gap/2-100;
		randomGenerator = new Random();
	}

	@Override
	public void render() {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState != 0) {
			if (Gdx.input.justTouched()) {
				velocity = -25;
				tubeOffset = (randomGenerator.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-gap-200);



			}

			batch.draw(topTube,Gdx.graphics.getWidth() /2 - topTube.getWidth()/2, Gdx.graphics.getHeight()/2 + gap/2 + tubeOffset);
			batch.draw(bottomTube,Gdx.graphics.getWidth() / 2 - bottomTube.getWidth()/2, Gdx.graphics.getHeight()/2 - gap/2 - bottomTube.getHeight() + tubeOffset);



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

		batch.draw(doraemons[flapState], Gdx.graphics.getWidth() / 2 - doraemons[flapState].getWidth() / 2, doraemonY);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
