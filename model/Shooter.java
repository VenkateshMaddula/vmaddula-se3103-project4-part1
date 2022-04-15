package model;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;

public class Shooter extends GameElement {

    public static final int UNIT_MOVE = 10;
    public static final int MAX_BULLETS = 3;

    private ArrayList<GameElement> components = new ArrayList<>();
    private ArrayList<GameElement> weapons = new ArrayList<>();

    public Shooter(int x, int y){
          super(x,y,0,0);
          var size = ShooterElement.SIZE;
          var s1= new ShooterElement(x - size,y -  size,Color.white,false);
          var s2= new ShooterElement(x,y - size,Color.white,false);
          var s3= new ShooterElement(x - size,y,Color.white,false);
          var s4= new ShooterElement(x,y,Color.white,false);
          components.add(s1);
          components.add(s2);
          components.add(s3);
          components.add(s4);
          
    }


    public void moveRight(){
        super.x += UNIT_MOVE;
        for (var c: components){
            c.x += UNIT_MOVE;
        }
    }

    public void moveLeft(){
        super.x -= UNIT_MOVE;
        for (var c: components){
            c.x -= UNIT_MOVE;
        }
    }

    public boolean canFireMoreBullet(){
        return weapons.size() < MAX_BULLETS;
    }

    public void removeBulletsOutOfBound(){
        var remove = new ArrayList<GameElement>();
        for (var w:weapons) {
            if(w.y < 0) remove.add(w);
        }
        weapons.removeAll(remove);
    }
    
    public void processCollision(ArrayList<GameElement> bombs) {
    	ArrayList<GameElement> removeBombs = new ArrayList<>();
    	ArrayList<GameElement> removeShooters = new ArrayList<>();
    	
    	for(var c: components) {
    		for (var b: bombs) {
    			if(c.collideWith(b)) {
    				c.color = Color.red;
    				c.filled = true;
    				c.exploting = true;
    				removeBombs.add(b);
    			}
    		}
    		
    		if(c.exploting)
    			c.effectTimer += 1;
    		
    		if(c.effectTimer >= GameElement.MAX_EFFECT_TIME)
    			removeShooters.add(c);
    	}
    	
    	//Remove all
    	bombs.removeAll(removeBombs);
    	components.removeAll(removeShooters);
    	
    }
    public ArrayList<GameElement> getWeapons(){
        return weapons;
    }
    
    public ArrayList<GameElement> getComponents() {
		return components;
	}
    
     @Override
    public void render(Graphics2D g2){
       for(var c: components){
           c.render(g2);
       }
       for(var w: weapons){
        w.render(g2);
    }
    }
    
    @Override
    public void animate(){
        for(var w: weapons){
            w.animate();
        }

    }
}
