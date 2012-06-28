/**
 * 
 */
package com.prat.gooo;

import javax.microedition.khronos.opengles.GL10;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.gl.Animation;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;
import com.badlogic.androidgames.framework.impl.GLGraphics;

/**
 * @author Prat
 *
 */
public class AnimationTest extends GLGame {

	public Screen getStartScreen() {
		return new AnimationScreen(this);
	}
	
	static final float WORLD_WIDTH = 4.8f;
    static final float WORLD_HEIGHT = 3.2f;
    
    static class MrGooo extends DynamicGameObject {
    	public static final int GOOO_STATE_RUN = 0;        
        public static final int GOOO_STATE_HIT = 1;
        public static final float GOOO_MOVE_VELOCITY = 20;
        public static final float GOOO_WIDTH = 1f;
        public static final float GOOO_HEIGHT = 1f;
        
        int state;
        float stateTime;
    	//public float walkingTime = 0;
    	
		public MrGooo(float x, float y, float width, float height) {
			super(x, y, GOOO_WIDTH, GOOO_HEIGHT);
			state = GOOO_STATE_RUN;
	        stateTime = 0; 
			this.position.set((float)0.5f * WORLD_WIDTH, (float)0.5f * WORLD_HEIGHT);
			this.velocity.set(-1f, 0);
//			this.position.set((float)Math.random() * WORLD_WIDTH,
//                    (float)Math.random() * WORLD_HEIGHT);
			//this.velocity.set(Math.random() > 0.5f?-0.5f:0.5f, 0);
//            this.walkingTime = (float)Math.random() * 10;
		}
    	
		public void update(float deltaTime) {
			float newVelo = velocity.x * deltaTime * (-1);
            position.add(newVelo, velocity.y * deltaTime);
            if(position.x < 0) position.x = WORLD_WIDTH;
            if(position.x > WORLD_WIDTH) position.x = 0;
//            walkingTime += deltaTime;
            stateTime += deltaTime;
        }
		
		public void hit() {
	        velocity.set(0,0);
	        state = GOOO_STATE_HIT;        
	        stateTime = 0;
	    }
		
    }
	
	class AnimationScreen extends Screen {
		static final int NUM_MRGOOO = 1;
        GLGraphics glGraphics;        
        SpriteBatcher batcher;
        Camera2D camera;
        Texture texture;
        Animation walkAnim;
        MrGooo mrGooo;
        
		public AnimationScreen(Game game) {
			super(game);
			glGraphics = ((GLGame)game).getGLGraphics();
			mrGooo = new MrGooo((float)Math.random(), (float)Math.random(), 1, 1);
			
			batcher = new SpriteBatcher(glGraphics, NUM_MRGOOO);
            camera = new Camera2D(glGraphics, WORLD_WIDTH, WORLD_HEIGHT);
		}

		@Override
		public void update(float deltaTime) {
			mrGooo.update(deltaTime);			
		}

		@Override
		public void present(float deltaTime) {
			GL10 gl = glGraphics.getGL();
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glClearColor(0.2f, 0.3f, 0.4f, 0);
            camera.setViewportAndMatrices();            
            gl.glEnable(GL10.GL_BLEND);            
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glEnable(GL10.GL_TEXTURE_2D);            
            batcher.beginBatch(texture);                        
            MrGooo mrg = mrGooo;
            TextureRegion keyFrame = walkAnim.getKeyFrame(mrg.stateTime , Animation.ANIMATION_LOOPING);
            batcher.drawSprite(mrg.position.x, mrg.position.y, mrg.velocity.x < 0?1:-1, 1, keyFrame);
            //batcher.drawSprite(x, y, width, height, region)
            batcher.endBatch();
			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			texture = new Texture(((GLGame)game), "Image7.png");
            walkAnim = new Animation( 0.1f,
                                      new TextureRegion(texture, 0, 0, 64, 64),
                                      new TextureRegion(texture, 64, 0, 64, 64),
                                      new TextureRegion(texture, 128, 0, 64, 64),
                                      new TextureRegion(texture, 192, 0, 64, 64),
                                      new TextureRegion(texture, 256, 0, 64, 64),
                                      new TextureRegion(texture, 320, 0, 64, 64));
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
