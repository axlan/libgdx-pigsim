package com.me.pigsim;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class DrawIsoSprites {

	
	DrawIsoSprites(int screenWidth,int screenHeight,int xTiles,int yTiles,TextureAtlas myTextures)
	{
		
		int baseHeightFromHeight=screenHeight/((xTiles+yTiles+8)/2);
		int baseHeightFromWidth=screenWidth/(xTiles+yTiles);
		
		this.baseHeight=Math.min(baseHeightFromHeight,baseHeightFromWidth)/2*2;
		
		this.xTiles=xTiles;
		this.yTiles=yTiles;
		
		
		sprites=myTextures.createSprites();
		spriteFlipped=new boolean[sprites.size];
		
		float spriteSize=(int)sprites.get(0).getWidth();
		
		float scale=((float)baseHeight*2)*pigXSize/spriteSize;
		
		for(Sprite sprite: sprites)
		{
			sprite.setScale(scale);
		}
		
	
	}
	
	private static final int pigXSize=2;
	private static final int pigYSize=pigXSize*2;
	private Array<Sprite> sprites;
	
	public int xTiles;
	public int yTiles;
	private int baseHeight;
	
	private final double HACK_Y_OFFSET=3.5;
	
	
	private boolean[] spriteFlipped;
	
	private void DrawTile(Pixmap pixmap,int xOffset,int yOffset)
	{
		
		pixmap.setColor(Color.WHITE);
		
		for(int x=0;x<baseHeight;x++)
		{
			pixmap.drawLine(xOffset+x,yOffset+ baseHeight/2+x/2,xOffset+ x, yOffset+baseHeight/2-x/2);
		}
		for(int x=0;x<baseHeight;x++)
		{
			pixmap.drawLine(xOffset+2*baseHeight-1-x, yOffset+baseHeight/2+x/2, xOffset+2*baseHeight-1-x, yOffset+baseHeight/2-x/2);
		}
		pixmap.drawPixel(xOffset+baseHeight-1, yOffset);
		pixmap.drawPixel(xOffset+baseHeight, yOffset);
		
		
		
	}
	
	
	private void DrawCageTileY(Pixmap pixmap,int xOffset,int yOffset)
	{
		
		pixmap.setColor(Color.WHITE);
		
		for(int i=0;i<=4;i++)
		{
			pixmap.drawLine(xOffset,yOffset-baseHeight*i,xOffset+baseHeight-1, yOffset+baseHeight/2-baseHeight*i);
		}
		
		pixmap.drawLine(xOffset,yOffset,xOffset, yOffset-baseHeight*4);
		pixmap.drawLine(xOffset+baseHeight-1,yOffset+baseHeight/2,xOffset+baseHeight-1, yOffset+baseHeight/2-baseHeight*4);
		
		
		
	}
	private void DrawCageTileX(Pixmap pixmap,int xOffset,int yOffset)
	{
		
		pixmap.setColor(Color.WHITE);
		
		
		for(int i=0;i<=4;i++)
		{
			pixmap.drawLine(xOffset,yOffset-baseHeight*i,xOffset+baseHeight-1, yOffset-baseHeight/2-baseHeight*i);
		}
		
		pixmap.drawLine(xOffset,yOffset,xOffset, yOffset-baseHeight*4);
		pixmap.drawLine(xOffset+baseHeight-1,yOffset-baseHeight/2,xOffset+baseHeight-1, yOffset-baseHeight/2-baseHeight*4);
		
		
		
	}
	
	class Point<T>
	{
		Point(T x,T y)
		{
			this.x=x;
			this.y=y;
		}
		public T x;
		public T y;
	}

	
	public Point<Integer> GetCageOffsetTop()
	{
		return new Point<Integer>(0, xTiles*baseHeight);
	}
	
	public Point<Integer> GetCageOffsetRight()
	{
		return new Point<Integer>(yTiles*baseHeight/2,xTiles*baseHeight/2);
	}
	public Point<Integer> GetCageOffsetLeft()
	{
		return new Point<Integer>(0,0);
	}
	
	public Point<Integer> GetCageOffsetBottom()
	{
		return new Point<Integer>( yTiles*baseHeight,0);
	}
	
	
	public Sprite DrawCageX()
	{
		int width=baseHeight*(xTiles);
		int height=baseHeight*(xTiles/2+4);
		
		int textureHeight=Integer.highestOneBit(height-1)*2;
		int textureWidth=Integer.highestOneBit(width-1)*2;
		
		
		Pixmap pixmap = new Pixmap(textureWidth,textureHeight, Format.RGBA8888 );
		
		for(int i=0;i<xTiles;i++)
		{
			DrawCageTileX(pixmap, baseHeight*i, height-baseHeight/2*i);
		}
		
		
		
		Texture texture = new Texture( pixmap );
		pixmap.dispose();
		
		TextureRegion region= new TextureRegion(texture,0,0,width,height);
		
		return new Sprite(region);
		
	}
	
	public Sprite DrawPigPosY(double x,double y)
	{
		Sprite sprite=sprites.get(1);
		if(spriteFlipped[1])
		{
			spriteFlipped[1]=false;
			sprite.rotate(180);
			sprite.flip(false, true);
		}
		Point<Double> point=GetBoardPoint( y-HACK_Y_OFFSET, x);
		sprite.setPosition(point.x.intValue(),point.y.intValue());
		return sprite;
		
	}
	
	public Sprite DrawPigNegY(double x,double y)
	{
		Sprite sprite=sprites.get(0);
		if(!spriteFlipped[0])
		{
			spriteFlipped[0]=true;
			sprite.rotate(180);
			sprite.flip(false, true);
		}
		Point<Double> point=GetBoardPoint( y-HACK_Y_OFFSET, x);
		sprite.setPosition(point.x.intValue(),point.y.intValue());
		return sprite;
		
	}
	
	
	public Sprite DrawPigPosX(double x,double y)
	{
		Sprite sprite=sprites.get(0);
		if(spriteFlipped[0])
		{
			spriteFlipped[0]=false;
			sprite.rotate(180);
			sprite.flip(false, true);
		}
		Point<Double> point=GetBoardPoint( y-HACK_Y_OFFSET, x);
		sprite.setPosition(point.x.intValue(),point.y.intValue());
		return sprite;
		
	}
	
	public Sprite DrawPigNegX(double x,double y)
	{
		Sprite sprite=sprites.get(1);
		if(!spriteFlipped[1])
		{
			spriteFlipped[1]=true;
			sprite.rotate(180);
			sprite.flip(false, true);
		}
		Point<Double> point=GetBoardPoint( y-HACK_Y_OFFSET, x);
		sprite.setPosition(point.x.intValue(),point.y.intValue());
		return sprite;
		
	}
	
	
	public Sprite DrawCageY()
	{
		int width=baseHeight*(yTiles);
		int height=baseHeight*(yTiles/2+4);
		
		int textureHeight=Integer.highestOneBit(height-1)*2;
		int textureWidth=Integer.highestOneBit(width-1)*2;
		
		
		Pixmap pixmap = new Pixmap(textureWidth,textureHeight, Format.RGBA8888 );
		
		for(int i=0;i<yTiles;i++)
		{
			DrawCageTileY(pixmap, baseHeight*i,baseHeight*4+baseHeight/2*i);
		}
		
		
		
		Texture texture = new Texture( pixmap );
		pixmap.dispose();
		
		TextureRegion region= new TextureRegion(texture,0,0,width,height);
		
		return new Sprite(region);
		
	}
	
	
	Point<Double> GetBoardPoint(double x,double y)
	{
		double yOffset=(xTiles-1)*baseHeight/2;
		return new Point<Double>(x*baseHeight+y*baseHeight,-x*baseHeight/2+y*baseHeight/2+ yOffset);
	}
	
	public Sprite DrawCageFloor()
	{
		int width=baseHeight*(xTiles+yTiles);
		int height=width/2;
		
		
		
		int textureHeight=Integer.highestOneBit(height-1)*2;
		int textureWidth=Integer.highestOneBit(width-1)*2;
		
		
		Pixmap pixmap = new Pixmap(textureWidth,textureHeight, Format.RGBA8888 );
		
		for(int x=0;x<xTiles;x++)
		{
			for(int y=0;y<yTiles;y++)
			{
				Point<Double> point=GetBoardPoint( x, y);
				DrawTile(pixmap, point.x.intValue(),point.y.intValue());
			}
		}
		
		
		/*pixmap.setColor(Color.RED);
		pixmap.drawRectangle(0, 0, width, height);
		*/
		
		/*
		for(int i=0;i<yTiles;i++)
		{
			DrawCageTileY(pixmap,baseHeight, baseHeight*i, yOffset+baseHeight/2+baseHeight/2*i);
		}
		DrawCageTileX(pixmap,baseHeight, 0, yOffset+baseHeight/2);
		*/
		Texture texture = new Texture( pixmap );
		pixmap.dispose();
		
		TextureRegion region= new TextureRegion(texture,0,0,width,height);
		
		return new Sprite(region);
		
	}
	
	
}
