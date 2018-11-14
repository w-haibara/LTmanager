import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import websockets.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class nico_com extends PApplet {


WebsocketClient ws;

PFont font;

String message[] = new String[256];
int num = 0;
int x[] = new int[256];
int y[] = new int[256];

public void setup() {
  
  ws = new WebsocketClient(this, "ws://localhost:1880/ws/tweet");

  for (int i = 0; i < 256; i++) {
    message[i] = "";
    x[i] = width;
    y[i] = ((height / 16) * PApplet.parseInt(random(1, 16))) + PApplet.parseInt(random(-20, 20));
  }
}

public void draw() {
  PFont font = createFont("Takao Pゴシック", 30, true);
  textFont(font);
  textSize(30);

  background(0);

  fill(255);
  for (int i = 0; i < 256; i++) {
    if (message[i] != "") {
      x[i] -= 2;

      text(message[i], x[i], y[i]);

      if (x[i] <= -100) {
        x[i] = width;
        message[i] = "";
        y[i] = ((height / 16) * PApplet.parseInt(random(1, 16))) + PApplet.parseInt(random(-20, 20));
      }
    }
  }
}

public void webSocketEvent(String msg) {
  msg = msg.replaceAll("\n", " ");
  num = (num == 256)? 0: num;
  message[num] = msg + "\n";
  num++;
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "nico_com" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
