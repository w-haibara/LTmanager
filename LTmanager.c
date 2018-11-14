#include <stdlib.h>

int main(){

	system("node-red -u ./get_tweet &");

	system("~/processing-3.3.7/processing-java --sketch=$(pwd)/nico_com --output=$(pwd)/nico_com/output --force --run");

	system("killall node-red");

	return 0;
}
