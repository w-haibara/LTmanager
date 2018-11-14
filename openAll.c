#include <stdlib.h>

int main(){

	system("node-red &");

	system("~/processing-3.3.7/processing-java --sketch=$(pwd)/LTmanager --output=$(pwd)/LTmanager/out --force --run");

	system("killall node-red");

	return 0;
}
