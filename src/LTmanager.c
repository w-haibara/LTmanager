#include <stdio.h>
#include <stdlib.h>

int main(int argc, char* argv[]){

	char args[8] = "";
	char word[20] = "";
	int m, s;

	if(argc > 1){
		for(int i = 1; i < argc; i++){
			printf("arg = \"%s\"\n", argv[i]);
			sprintf(args, "%s %s", args, argv[i]);	
		}
	}
	printf("\n\targs =\"%s\"\n", args);

	printf("\n\n\tnode-red : start\n\n");

	system("node-red -u ./get_tweet &");

	printf("\n\n\tprocessing : export\n\n");
	system("processing-java --sketch=$(pwd)/nico_com/ --platform=linux --output=$(pwd)/nico_com/output --no-java --force --export");

	printf("\n\n\tprocessing : application run\n\n");

	char open_nico_com[300];
	sprintf(open_nico_com, "./nico_com/output/nico_com %s", args);
	system(open_nico_com);

	printf("\n\n\tnode-red : end\n\n");
	system("killall node-red");

	return 0;
}
