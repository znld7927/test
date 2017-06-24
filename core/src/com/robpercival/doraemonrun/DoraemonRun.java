package com.robpercival.doraemonrun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DoraemonRun extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture doraemon;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		doraemon = new Texture("doraemon.png");

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(doraemon,Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight()/2);
		batch.end();
}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
