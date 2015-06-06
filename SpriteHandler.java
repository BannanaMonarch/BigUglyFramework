import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteHandler {
	int ts; //tile size
	BufferedImage osrc,src; //original source (used to preserve spritesheet), source (used to draw)
	int[] size = {0,0}; //size of the spritesheet in pixels.
	Color tintcolor = new Color(0,0,0,0); //Color that the image should be tinted in.
	ArrayList<BufferedImage> layers = new ArrayList<BufferedImage>(); //Growable array of layers.
	
	/* Create a SpriteHandler straight from a spritesheet. */
	public SpriteHandler(BufferedImage gSrc, int gTs) {
		ts = gTs; 
		osrc = gSrc;
		src = dupe(osrc);
		size[0] = src.getWidth(); size[1] = src.getHeight();
	}
	
	/* Create a SpriteHandler and crop the spritesheet margins on the left and top sides. */
	public SpriteHandler(BufferedImage gSrc, int gTs, int gOffx, int gOffy) {
		ts = gTs;
		osrc = gSrc.getSubimage(gOffx, gOffy, gSrc.getWidth()-gOffx, gSrc.getHeight()-gOffy);
		src = dupe(osrc);
		size[0] = src.getWidth(); size[1] = src.getHeight();
	}
	
	/* Create a SpriteHandler and crop the spritesheet margins on all sides. */
	public SpriteHandler(BufferedImage gSrc, int gTs, int gOffx, int gOffy, int gSizex, int gSizey) {
		ts = gTs;
		osrc = gSrc.getSubimage(gOffx, gOffy, gSizex, gSizey);
		src = dupe(osrc);
		size[0] = src.getWidth(); size[1] = src.getHeight();
	}
	
	/* duplicates the source image into a new BufferedImage. This is useful for keeping BufferedImages from sharing
	references and manipulating eachother.*/
	public BufferedImage dupe(BufferedImage src){
		BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		g.drawImage(src,0,0,src.getWidth(),src.getHeight(),null);
		g.dispose();
		return img;
	}
	
	public int getWidth() { return size[0]; }
	public int getHeight() { return size[1]; }
	public int getTileSize() { return ts; }
	
	/* Removes a color from the spritesheet. If you use a transparent-representative color such as magenta or green,
	you can delete it from the image, which really replaces it with full alpha transparency color.*/
	public void removeColor(Color rem){
		for(int c=0;c<osrc.getWidth();c++){ for(int r=0;r<osrc.getHeight();r++){
			if(new Color(osrc.getRGB(c, r)).equals(rem)){ osrc.setRGB(c, r, (int) 0x00000000); }
		}}
	}
	
	/* This can be used to do alternate color palettes for sprites. */
	public void replaceColor(Color replace, Color replacement){
		Graphics g = src.createGraphics();
		g.setColor(replacement);
		for(int c=0;c<src.getWidth();c++){ for(int r=0;r<src.getHeight();r++){
			if(new Color(src.getRGB(c, r)).equals(replace)){ g.fillRect(c, r, 1, 1); }
		}}
		g.dispose();
	}
	
	/*Adds a color tint over sprites. Be careful though! Multiple objects referencing the sheet will
	share the tint. A new method needs to be created that tints a selected area.*/
	public void addTint(Color tint){tintcolor = tint;}
	
	public void removeTint(){tintcolor = new Color(0,0,0,0);}
	
	public BufferedImage drawTint(BufferedImage tintme){
		Graphics g = tintme.createGraphics();
		g.setColor(tintcolor);
		for(int c=0;c<src.getWidth();c++){ for(int r=0;r<src.getHeight();r++){
			int alpha = (src.getRGB(c,r) >> 24) & 0xFF; //records alpha channel of pixel
			if(alpha!=0){ g.fillRect(c, r, 1, 1); }
		}}g.dispose();
		return tintme;
	}
	
	//Removes the layer based on equality to an equal image.
	public void removeLayer(BufferedImage layer){ 
		if(layers.indexOf(layer)!=-1){
			layers.remove(layers.indexOf(layer));
		}
		updateLayer();
	}
		
	//Removes the layer based on index. Can be tricky if you dont record the indexes.
	public void removeLayer(int val){ 
		layers.remove(val);
		updateLayer();
	}
	
	/*Adds an image as a layer of the spritesheet. The primary usage of this would be for changing the looks
	of sprites such as with items and effects.*/
	public void addLayer(BufferedImage layer){ 
		layers.add(layer);
		updateLayer();
	}
	
	/*This is the performance saving function that stops a spritesheet from drawing layers every iteration.
	the way this works is that it loads all the images together but preserves them seperately, so changes 
	can be made.*/
	public void updateLayer(){
		src = dupe(osrc);
		src = drawTint(src);
		Graphics g = src.createGraphics();
		for(BufferedImage temp:layers){
			g.drawImage(temp,0,0,temp.getWidth(),temp.getHeight(),null);
		}
		g.dispose();
	}
	
	/* Draws the sprite onto the given Graphics context.*/
	public void drawSprite(Graphics g, int dx, int dy, int sx, int sy, boolean flip){
		sx*=ts; sy*=ts;
		if(flip){ g.drawImage(src,dx,dy,dx+ts,dy+ts,sx+ts,sy,sx,sy+ts,null); }
		else    { g.drawImage(src,dx,dy,dx+ts,dy+ts,sx,sy,sx+ts,sy+ts,null); }
	}
	
	/* Draws the sprite onto the given Graphics context, and magnifies it. */
	public void drawSprite(Graphics g, int dx, int dy, int sx, int sy, boolean flip, int mag){
		sx*=ts; sy*=ts;
		int tm = ts*mag;
		if(flip){ g.drawImage(src,dx,dy,dx+tm,dy+tm,sx+ts,sy,sx,sy+ts,null); }
		else    { g.drawImage(src,dx,dy,dx+tm,dy+tm,sx,sy,sx+ts,sy+ts,null); }
	}
	
}
