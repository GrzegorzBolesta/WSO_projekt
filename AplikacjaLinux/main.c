#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <linux/input.h>

int main(int argc,char *argv[]){
    int fd;
    struct input_event ev;

    FILE *plik;
    plik = fopen("klawiatura.txt", "w");

    fd = open("/dev/input/event2", O_RDONLY);
    if (fd<0){
        perror("Nie moge otworzyc pliku");
        return 1;
    }

    while (1){
        if (read(fd,&ev,sizeof(struct input_event)) < 0){
            perror("Nie moge odczytac");
            return 1;
        }
        if (ev.type == EV_KEY) {
            if(ev.value == 1) //zapisuj tylko wcisniety klawisz
            {
            fprintf(plik,"%d\n",ev.code); //zapisz znak
            fflush(stdout);
            }
        }
        if (ev.code == 1) { //sprawdz czy esc wcisniety
                //fprintf(plik,"%d\n",ev.code); //zapisz esc wcisniety
                fflush(stdout);
                perror("Koniec programu");
                return 1; //koniec petli
        }
    }
    fclose(plik); //zamknij plik
}
