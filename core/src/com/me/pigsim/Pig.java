package com.me.pigsim;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Pig implements InputProcessor{

	enum Facing
	{
		LEFT,
		RIGHT,
		UP,
		DOWN
		
	}
	

	long stateStart;
	
	Facing curFacing;
	Sound sound;
	
	static final double WALK_VELOCITY=.05;
	static final double VELOCITY=1;
	static final double RUN_VELOCITY=1.5;
	
	
	public double xVel;
	public double yVel;
	
	public double x;
	public double y;
	
	public DrawIsoSprites drawSprites;
	
	
	final double HACK_LIMIT_POSX=1.4;
	final double HACK_LIMIT_POSY=.5;
	final double HACK_LIMIT_NEGX=1;
	final double HACK_LIMIT_NEGY=2;
	
	
	Pig(DrawIsoSprites drawSprites)
	{
		 sound= Gdx.audio.newSound(Gdx.files.internal("data/FeedMe.mp3"));
			

		this.drawSprites=drawSprites;
		
	}
	
	boolean panicked;
	
	final long stateDuration=2000;
	final long runStateDuration=100;
	long runStateStart;
	
	void UpdateAI()
	{
		
		
		
		if(System.currentTimeMillis()>stateStart+stateDuration)
		{
			Random random=new Random();
			
			double val=random.nextDouble();
			
			panicked=false;
			
			if(val<.15)
			{
				yVel=WALK_VELOCITY;
				xVel=0;
				curFacing=Facing.DOWN;
			}else if(val<.3)
			{
				yVel=-WALK_VELOCITY;
				xVel=0;
				curFacing=Facing.UP;
			}else if(val<.45)
			{
				xVel=-WALK_VELOCITY;
				yVel=0;
				curFacing=Facing.LEFT;
			}else if(val<.6)
			{
				xVel=+WALK_VELOCITY;
				yVel=0;
				curFacing=Facing.RIGHT;
			}else if(val<.68)
			{
				sound.play(1.0f);
				panicked=true;
			}
			else
			{
				xVel=0;
				yVel=0;
			}
			stateStart=System.currentTimeMillis();
		}
		
		if(panicked&&System.currentTimeMillis()>runStateStart+runStateDuration)
		{
			Random random=new Random();
			
			double val=random.nextDouble();
			
			if(val<.25)
			{
				yVel=RUN_VELOCITY;
				xVel=0;
				curFacing=Facing.DOWN;
			}else if(val<.5)
			{
				yVel=-RUN_VELOCITY;
				xVel=0;
				curFacing=Facing.UP;
			}else if(val<.75)
			{
				xVel=-RUN_VELOCITY;
				yVel=0;
				curFacing=Facing.LEFT;
			}else
			{
				xVel=+RUN_VELOCITY;
				yVel=0;
				curFacing=Facing.RIGHT;
			}
			runStateStart=System.currentTimeMillis();
		}
		
	}
	
	public void Update(SpriteBatch batch)
	{
		x+=xVel;
		y+=yVel;
		

		x=Math.max(x, HACK_LIMIT_POSX);
		y=Math.max(y, HACK_LIMIT_POSY);
		x=Math.min(x,drawSprites.xTiles-HACK_LIMIT_NEGX);
		y=Math.min(y, drawSprites.yTiles-HACK_LIMIT_NEGY);
		
		
		
		if(curFacing==Facing.LEFT)
		{
			drawSprites.DrawPigNegX(x,y).draw(batch);
		}	
		else if(curFacing==Facing.RIGHT)
		{
			drawSprites.DrawPigPosX(x,y).draw(batch);
		}
		else if(curFacing==Facing.DOWN)
		{
			drawSprites.DrawPigPosY(x,y).draw(batch);
		}
		else
		{
			drawSprites.DrawPigNegY(x,y).draw(batch);
		}
		
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		
		switch(keycode)
		{
		case Keys.DOWN:
			yVel+=VELOCITY;
			curFacing=Facing.DOWN;
			break;
		case Keys.UP:
			yVel-=VELOCITY;
			curFacing=Facing.UP;
			break;
		case Keys.LEFT:
			xVel-=VELOCITY;
			curFacing=Facing.LEFT;
			break;
		case Keys.RIGHT:
			xVel+=VELOCITY;
			curFacing=Facing.RIGHT;
			break;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {

		switch(keycode)
		{
		case Keys.DOWN:
			yVel-=VELOCITY;
			break;
		case Keys.UP:
			yVel+=VELOCITY;
			break;
		case Keys.LEFT:
			xVel+=VELOCITY;
			break;
		case Keys.RIGHT:
			xVel-=VELOCITY;
			break;
		}
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
