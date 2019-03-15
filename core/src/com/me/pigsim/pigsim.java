package com.me.pigsim;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class pigsim implements ApplicationListener{
	
	private SpriteBatch batch;
	private Sprite floorSprite;
	private Sprite cageSpriteY;
	private Sprite cageSpriteX;
	
	private DrawIsoSprites drawSprites;
	
	private Pig pig; 

	Music background; 
	
	@Override
	public void create() {		
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		
		TextureAtlas myTextures = new TextureAtlas("data/pigsprites.txt");
		
		drawSprites=new DrawIsoSprites(w,h,10,20,myTextures);
		
		
		batch = new SpriteBatch();
		
		
		
		floorSprite=drawSprites.DrawCageFloor();
		
		cageSpriteX=drawSprites.DrawCageX();
		
		cageSpriteY=drawSprites.DrawCageY();
		
		pig=new Pig(drawSprites);
		
		Gdx.input.setInputProcessor(pig);
		
		background = Gdx.audio.newMusic(Gdx.files.internal("data/Route1.mp3"));
		background.setLooping(true);
		background.play();
	}

	@Override
	public void dispose() {
		//batch.dispose();
		//texture.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		/*
		 shapeRenderer.begin(ShapeType.Line);
		 shapeRenderer.setColor(1, 1, 1, 1);
		 shapeRenderer.line(100, 100, 132, 116);
		 shapeRenderer.line( 132, 116,  164, 100);
		 shapeRenderer.line(164, 100, 132, 84);
		 shapeRenderer.line(132, 84, 100, 100);
		 shapeRenderer.end();
		*/
		
		
		
		batch.begin();
		
		batch.disableBlending();
		floorSprite.setColor(Color.YELLOW);
		floorSprite.draw(batch);
		batch.enableBlending();
		
		cageSpriteX.setColor(Color.RED);
		cageSpriteX.setPosition(drawSprites.GetCageOffsetTop().x, drawSprites.GetCageOffsetTop().y);
		cageSpriteX.draw(batch);
		
		cageSpriteY.setColor(Color.RED);
		cageSpriteY.setPosition(drawSprites.GetCageOffsetRight().x, drawSprites.GetCageOffsetRight().y);
		cageSpriteY.draw(batch);
		
		//add stuff in cage
		
		pig.UpdateAI();
		pig.Update(batch);
		
		//drawSprites.DrawPigY(0,0).draw(batch);
		
		
		cageSpriteX.setPosition(drawSprites.GetCageOffsetBottom().x, drawSprites.GetCageOffsetBottom().y);
		cageSpriteX.draw(batch);
		
		cageSpriteY.setPosition(drawSprites.GetCageOffsetLeft().x, drawSprites.GetCageOffsetLeft().y);
		cageSpriteY.draw(batch);
		
		
		
		batch.end();
		
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

	
}
