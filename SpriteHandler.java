import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteHandler {
	int ts;
	BufferedImage osrc,src;
	int[] margin = {0,0};
	int[] size = {0,0};
	int maxlayers = 10;
	Color tintcolor = new Color(0,0,0,0);
	ArrayList<BufferedImage> layers = new ArrayList<BufferedImage>(); //FIX ALL ARRAY REFERENCING
	
	public SpriteHandler(BufferedImage gSrc, int gTs) {
		ts = gTs; 
		osrc = gSrc;
		src = dupe(osrc);
		size[0] = src.getWidth(); size[1] = src.getHeight();
	}
	
	public SpriteHandler(BufferedImage gSrc, int gTs, int gOffx, int gOffy) {
		ts = gTs;
		osrc = gSrc.getSubimage(gOffx, gOffy, gSrc.getWidth()-gOffx, gSrc.getHeight()-gOffy);
		src = dupe(osrc);
		size[0] = src.getWidth(); size[1] = src.getHeight();
	}
	
	public SpriteHandler(BufferedImage gSrc, int gTs, int gOffx, int gOffy, int gSizex, int gSizey) {
		ts = gTs;
		osrc = gSrc.getSubimage(gOffx, gOffy, gSizex, gSizey);
		src = dupe(osrc);
		size[0] = src.getWidth(); size[1] = src.getHeight();
	}
	
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
	
	public void removeColor(Color rem){
		for(int c=0;c<osrc.getWidth();c++){ for(int r=0;r<osrc.getHeight();r++){
			if(new Color(osrc.getRGB(c, r)).equals(rem)){ osrc.setRGB(c, r, (int) 0x00000000); }
		}}
	}
	
	public void replaceColor(Color replace, Color replacement){
		Graphics g = src.createGraphics();
		g.setColor(replacement);
		for(int c=0;c<src.getWidth();c++){ for(int r=0;r<src.getHeight();r++){
			if(new Color(src.getRGB(c, r)).equals(replace)){ g.fillRect(c, r, 1, 1); }
		}}
		g.dispose();
	}
	
	public void addTint(Color tint){
		tintcolor = tint;
	}
	
	public void removeTint(){
		tintcolor = new Color(0,0,0,0);
	}
	
	public BufferedImage drawTint(BufferedImage tintme){
		Graphics g = tintme.createGraphics();
		g.setColor(tintcolor);
		for(int c=0;c<src.getWidth();c++){ for(int r=0;r<src.getHeight();r++){
			int alpha = (src.getRGB(c,r) >> 24) & 0xFF; //records alpha channel of pixel
			if(alpha!=0){ g.fillRect(c, r, 1, 1); }
		}}g.dispose();
		return tintme;
	}
	
	public void removeLayer(BufferedImage layer){ 
		if(layers.indexOf(layer)!=-1){
			layers.remove(layers.indexOf(layer));
		}
		updateLayer();
	}
		
	public void removeLayer(int val){ 
		layers.remove(val);
		updateLayer();
	}
	
	public void addLayer(BufferedImage layer){ 
		layers.add(layer);
		updateLayer();
	}
	
	public void updateLayer(){
		src = dupe(osrc);
		src = drawTint(src);
		Graphics g = src.createGraphics();
		for(BufferedImage temp:layers){
			g.drawImage(temp,0,0,temp.getWidth(),temp.getHeight(),null);
		}
		g.dispose();
	}
	
	public void drawSprite(Graphics g, int dx, int dy, int sx, int sy, boolean flip){
		sx*=ts; sy*=ts;
		if(flip){ g.drawImage(src,dx,dy,dx+ts,dy+ts,sx+ts,sy,sx,sy+ts,null); }
		else    { g.drawImage(src,dx,dy,dx+ts,dy+ts,sx,sy,sx+ts,sy+ts,null); }
	}
	
	public void drawSprite(Graphics g, int dx, int dy, int sx, int sy, boolean flip, int mag){
		sx*=ts; sy*=ts;
		int tm = ts*mag;
		if(flip){ g.drawImage(src,dx,dy,dx+tm,dy+tm,sx+ts,sy,sx,sy+ts,null); }
		else    { g.drawImage(src,dx,dy,dx+tm,dy+tm,sx,sy,sx+ts,sy+ts,null); }
	}
	
}
