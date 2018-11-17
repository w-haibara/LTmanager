#include <stdio.h>
#include <stdlib.h>

int main(){
	printf("\n\n\tnode-red : start\n\n");

	system("node-red -u ./get_tweet &");

	printf("\n\n\tprocessing : export\n\n");
	system("processing-java --sketch=$(pwd)/nico_com/ --platform=linux --output=$(pwd)/nico_com/output --no-java --force --export");

	printf("\n\n\tprocessing : application run\n\n");
	system("./nico_com/output/nico_com");
	
	printf("\n\n\tnode-red : end\n\n");
	system("killall node-red");

	return 0;
}
