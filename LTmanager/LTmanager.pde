import websockets.*;
WebsocketClient ws;

PFont font;

String message[] = new String[256];
int num = 0;
int x[] = new int[256];
int y[] = new int[256];

void setup() {
  size(800, 800);
  ws = new WebsocketClient(this, "ws://localhost:1880/ws/tweet");

  for (int i = 0; i < 256; i++) {
    message[i] = "";
    x[i] = width;
    y[i] = ((height / 16) * int(random(1, 16))) + int(random(-20, 20));
  }
  /*
  int frag = 1;
  while (frag == 1) {
    frag = open_nodered();
    delay(1000);
  }
  */
}

void draw() {
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
        y[i] = ((height / 16) * int(random(1, 16))) + int(random(-20, 20));
      }
    }
  }
}

void webSocketEvent(String msg) {
  msg = msg.replaceAll("\n", " ");
  num = (num == 256)? 0: num;
  message[num] = msg + "\n";
  num++;
}

/*
int open_nodered() {
  Runtime runtime = Runtime.getRuntime();
  try {
    runtime.exec("node-red &");
  } 
  catch (IOException e) {
    e.printStackTrace();
    return 1;
  }
  return 0;
}
*/
