package com.levkanter.kettle.app;

import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;
import codeanticode.glgraphics.GLConstants;

import com.levkanter.kettle.recording.ImageRecorder;
import com.levkanter.kettle.scenes.GLScene;
import com.levkanter.kettle.scenes.ParticleGarden;
import com.levkanter.kettle.scenes.Scene;
import com.levkanter.kettle.scenes.TypeCodeLogo;

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
 * Kettle (for Processing)
 * 
 * Lev Kanter 2010
 * http://levkanter.com
 * 
 * */
public class KApp 
	extends PApplet
{
	public static void main(String _args[]) {
		PApplet.main(new String[] {
				"--present", "--hide-stop", "--exclusive",
				com.levkanter.kettle.app.KApp.class.getName() 
			});
	}
	
	public String toString() {
		return "K_App";
	}
	
	ImageRecorder imrec;
	AppConsole console;
	HashMap<String, Scene> scenes;
	Scene currentScene;
	
	private static final long serialVersionUID = 1L;
	
	public void setup() {
		size(1024, 768, GLConstants.GLGRAPHICS);
		setupApp();
	}
	
	void setupApp() {
		smooth();
		noStroke();
		noCursor();
		
		imrec = new ImageRecorder(this, toString());
		console = new AppConsole();
		console.AUTO_TRACE = true;
		console.log("Welcome to " +  toString() + "!");
		
		scenes = new HashMap<String, Scene>(5);
		scenes.put("Particle Garden", new ParticleGarden(this));
		scenes.put("TypeCode Logo", new TypeCodeLogo(this));
		
		//setScene("Particle Garden");
		setScene("TypeCode Logo");
	}
	
	public void update() {
		currentScene.update();
	}
	
	public void draw() {
		update();
		currentScene.draw();
	}
	
	public void mousePressed() {
		currentScene.mousePressed();
	}
	
	public void mouseReleased() {
		currentScene.mouseReleased();
	}
	
	public void mouseDragged() {
		currentScene.mouseDragged();
	}
	
	public void keyPressed() {
		if (key == '1') {
			setScene("Particle Garden");
		}
		if (key == '2') {
			setScene("TypeCode Logo");
		}
		if (key == 's' || key == 'S') {
			saveImage();
		}
		currentScene.keyPressed(key, keyCode);
	}
	
	public void keyReleased() {
		currentScene.keyReleased(key, keyCode);
	}
	
	public void setScene(String sceneKey) {
		if (scenes.containsKey(sceneKey)) { 
			currentScene = scenes.get(sceneKey);
			console.log("Scene: " + sceneKey);
		} else {
			throw new AppException("setScene(" + sceneKey + ")...No such scene");
		}
	}
	
	public void saveImage() {
		PImage im;
		if (currentScene instanceof GLScene) {
			im = ((GLScene) currentScene).getImage(true);
		} else {
			im = g;
		}
		imrec.record(im);
		console.log("Saved image: Frame " + frameCount);
	}
		
	public void stop() {
		for (Scene scene : scenes.values()) {
			scene.stop();
		}
		console.log("goodbye!");
		super.stop();
	}

}
