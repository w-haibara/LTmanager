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

int timer_h = 0;
int timer_m = 0;
int timer_s = 0;

String twitter_word = "";

int Size = 256;
String message[] = new String[Size];
int num = 0;
int x[] = new int[Size];
int y[] = new int[Size];
int stringFill[] = new int[Size];

long old_time;
long now_sum = 0;
long sum = 0;
int h = 0, m = 0, s = 0;

public void setup() {
  
  ws = new WebsocketClient(this, "ws://localhost:1880/ws/tweet");

  if (args != null) {
    int len = args.length;
    int arg_t = -1;
    int arg_h = -1;
    int arg_m = -1;
    int arg_s = -1;

    for (int i = 0; i < len; i++) {
      if(args[i].charAt(0) == '-'){
        switch(args[i].charAt(1)){
          case 't':
            arg_t = i;
            break;
          case 'h': 
            arg_h = i;
            break;
          case 'm':
            arg_m = i;
            break;
          case 's':
            arg_s = i;
            break;
        }
      }
    }

    if(arg_t > -1){
      if((arg_t + 1) < len) twitter_word = args[arg_t + 1];
    }
    if(arg_h > -1){
      if((arg_h + 1) < len) timer_h = PApplet.parseInt(args[arg_h + 1]);
    }
    if(arg_m > -1){
      if((arg_m + 1) < len) timer_m = PApplet.parseInt(args[arg_m + 1]);
    }
    if(arg_s > -1){
      if((arg_s + 1) < len) timer_s = PApplet.parseInt(args[arg_s + 1]);
    }

    ws.sendMessage('#' + twitter_word);

    println("\ttwitter_word : \"" + twitter_word + "\"");
    println("\ttimer_h : " + timer_h);
    println("\ttimer_m : " + timer_m);
    println("\ttimer_s : " + timer_s);
  } else {
    println("args == null");
  }

  for (int i = 0; i < Size; i++) {
    message[i] = "";
    x[i] = width;
    y[i] = round((height/8) * random(1, 7));
    stringFill[i] = round(255 - random(80));
  }

  old_time = second() + (minute() * 60) +  (hour() * 3600);
}

public void draw() {  
  background(0);
  timer(timer_h, timer_m, timer_s);
  throw_comment(3, -5000);
}

public void webSocketEvent(String msg) {
  msg = msg.replaceAll('#' + twitter_word, "  ");
  msg = msg.replaceAll("\n", "  ");
  num = (num == 256)? 0: num;
  message[num] = msg;
  num++;
}

public void throw_comment(int comment_speed, int x_end){
  PFont font = createFont("Takao Pゴシック", 30, true);
  textFont(font);
  textSize(30);
  for (int i = 0; i < Size; i++) {
    if (message[i] != "") {
      x[i] -= PApplet.parseInt(comment_speed);
      fill(stringFill[i]);
      textAlign(LEFT);
      text(message[i], x[i], y[i]);
      if (x[i] <= x_end) {
        x[i] = width + 1000;
        message[i] = "";
        y[i] = round((height/100) * round(random(1, 100)) + random(-10, 10));
      }
    }
  }
}

public void timer(int end_h, int end_m, int end_s){
  long end_time = (end_h *3600) + (end_m *60) + end_s;

  int now_h = hour() * 3600;
  int now_m = minute() * 60;
  int now_s = second();

  long now_time = now_s + now_m  + now_h;
  long ds = now_time - old_time;
  now_sum += ds;

  if(end_time > now_sum){
    sum = end_time - now_sum; 
    h = floor(sum / 3600);
    m = floor((sum - (h * 3600)) / 60);
    s = floor(sum - (h * 3600) - (m * 60));
  }else{
    h = 0;
    m = 0;
    s = 0;
  }

  if(sum <= 10){
    fill(255, 0, 0);
  }
  else{
    fill(255);
  }

  PFont font = createFont("Takao Pゴシック", 100, true);
  textFont(font);
  textAlign(CENTER);
  textSize(100);
  text(nf(h, 2) + ":" + nf(m, 2) + ":" + nf(s, 2), width/2, height/2); 

  old_time = now_time;
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
