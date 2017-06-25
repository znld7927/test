package com.robpercival.doraemonrun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


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
	float tubeVelocity = 10;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;
	Circle doraemonCircle;
	ShapeRenderer shapeRenderer;
	Rectangle[] bottomtubeRectangles;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font;
	Texture gameOver;

	//Texture topTube;
	Texture bottomTube;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		doraemons = new Texture[2];
		doraemons[0] = new Texture("doraemon.png");
		doraemons[1] = new Texture("doraemon2.png");

		shapeRenderer = new ShapeRenderer();
		doraemonCircle = new Circle();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(5);
		gameOver = new Texture("gameover.png");

		//topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffSet = Gdx.graphics.getHeight()/2 - gap/2-100;
		randomGenerator = new Random();
		distanceBetweenTubes = Gdx.graphics.getWidth()*3/4;
		bottomtubeRectangles = new Rectangle[numberOfTubes];

		doraemonY = Gdx.graphics.getHeight() / 2 - doraemons[flapState].getHeight() / 2;
		startGame();


	}

	public void startGame(){
		doraemonY = Gdx.graphics.getHeight() / 2 - doraemons[flapState].getHeight() / 2;


		for(int i = 0; i<numberOfTubes; i++){
			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap -200);

			tubeX[i] =  Gdx.graphics.getWidth()+ i * distanceBetweenTubes;
			bottomtubeRectangles[i] = new Rectangle();
		}

	}
	@Override
	public void render() {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {
			if(tubeX[scoringTube] < Gdx.graphics.getWidth() / 2 ){
				score ++;
				Gdx.app.log("Score", String.valueOf(score));
				if(scoringTube < numberOfTubes - 1){
					scoringTube++;
				}else{
					scoringTube =0;
				}

			}
			if (Gdx.input.justTouched()) {
				velocity = -25;

			}
			for(int i = 0; i<numberOfTubes; i++) {

				if(tubeX[i] < -bottomTube.getWidth() ){

					tubeX[i] += numberOfTubes * distanceBetweenTubes;
					tubeOffset[i] = (randomGenerator.nextFloat()-0.5f)* (Gdx.graphics.getHeight()-gap-200);

				}else {
					tubeX[i] = tubeX[i] - tubeVelocity;

				}
				//batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
				batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				bottomtubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),
						bottomTube.getHeight());

			}

			if(doraemonY > 0 ) {

				velocity = velocity + gravity;
				doraemonY -= velocity;
			}else {
				gameState = 2;
			}
		}else if(gameState==0){

			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}else if( gameState == 2){
			batch.draw(gameOver,Gdx.graphics.getWidth()/2-gameOver.getWidth()/2,Gdx.graphics.getHeight()/2-gameOver.getHeight()/2);

			if (Gdx.input.justTouched()) {
				gameState = 1;
				startGame();
				score = 0;
				scoringTube = 0;
				velocity=0;
			}

		}
		if (flapState == 0) {
			flapState = 1;
		} else {
			flapState = 0;
		}

		batch.draw(doraemons[flapState], Gdx.graphics.getWidth() / 2 - doraemons[flapState].getWidth() / 2, doraemonY);

		font.draw(batch,String.valueOf(score),90,1000);
		batch.end();

		doraemonCircle.set(Gdx.graphics.getWidth()/2, doraemonY + doraemons[flapState].getHeight()/2, doraemons[flapState].getWidth()/2);


		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.RED);
		//shapeRenderer.circle(doraemonCircle.x, doraemonCircle.y, doraemonCircle.radius);
		for(int i = 0; i<numberOfTubes; i++) {
			//shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i],bottomTube.getWidth(),
			//bottomTube.getHeight());

			if(Intersector.overlaps(doraemonCircle, bottomtubeRectangles[i])){
				gameState = 2;
			}
		}
		//shapeRenderer.end();


	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}