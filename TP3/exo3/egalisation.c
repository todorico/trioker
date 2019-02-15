#include <stdio.h>
#include "image_ppm.h"
#include "utility.h"

/***** Alpha = 0.0 &&&&& Beta = 23.0 *****/

int main(int argc, char* argv[]) {
	int nH, nW, nTaille;

	char cNomImgOriginale[250];
	char datImgOriginale[250];
	char dppImgOriginale[250];
	char fdeaImgOriginale[250];

	char cNomImgEgalisee[250];
	char datImgEgalisee[250];

	if (argc != 7){
		printf("Usage: %s ImageOriginale.pgm HistoOriginale.dat DPPOriginale.dat fdeaOriginale.dat ImageEgalisee.pgm HistoEgalisee.dat \n", argv[0]); 
		exit(1);
	}
	 
	sscanf(argv[1],"%s",cNomImgOriginale);
	sscanf(argv[2],"%s",datImgOriginale);
	sscanf(argv[3],"%s",dppImgOriginale);
	sscanf(argv[4],"%s",fdeaImgOriginale);


	sscanf(argv[5],"%s",cNomImgEgalisee);
	sscanf(argv[6],"%s",datImgEgalisee);

	OCTET *ImgOriginale, *ImgEgalisee;
	 
	lire_nb_lignes_colonnes_image_pgm(cNomImgOriginale, &nH, &nW);
	nTaille = nH * nW;
	
	allocation_tableau(ImgOriginale, OCTET, nTaille);
	lire_image_pgm(cNomImgOriginale, ImgOriginale, nH * nW);

	allocation_tableau(ImgEgalisee, OCTET, nTaille);


	/****** IMAGE ORIGINALE ******/
	int occOriginale[256] = {0};
	occurrences_Gris(ImgOriginale, nH, nW, occOriginale);
	ecritureOccurrences_Gris(datImgOriginale, occOriginale);


	/****** DPP ******/
	double dppOriginale[256];
	for (int i = 0; i < 256; ++i)
		dppOriginale[i] = ((double)occOriginale[i]) / ((double)nTaille);
	ecritureDPP_Gris(dppImgOriginale, dppOriginale);


	/****** Courbe de F(a) ******/
	double fdeaOriginale[256];
	for (int i = 0; i < 256; ++i)
		fdeaOriginale[i] = fdea_i(dppOriginale, i);
	ecritureFdea_Gris(fdeaImgOriginale, fdeaOriginale);

	
	/****** IMAGE EGALISEE ******/
	for (int i = 0; i < nH; ++i){
   		for (int j = 0; j < nW; ++j){
      		ImgEgalisee[i*nW+j] = fdeaOriginale[ImgOriginale[i*nW+j]] * 255;
    	}
  	}
	
	int occEgalisee[256] = {0};
	occurrences_Gris(ImgEgalisee, nH, nW, occEgalisee);
	ecritureOccurrences_Gris(datImgEgalisee, occEgalisee);
  	ecrire_image_pgm(cNomImgEgalisee, ImgEgalisee, nH, nW);

	free(ImgOriginale);
	free(ImgEgalisee);

	return 0;
}