#include <stdio.h>
#include "image_ppm.h"
#include "utility.h"

/***** Alpha = 0.0 &&&&& Beta = 23.0 *****/

int main(int argc, char* argv[]) {
	int nH, nW, nTaille;

	char cNomImgLue[250];
	char cNomImgEcrite[250];
	char cNomImgLueSaveFileName[250];
	char cNomImgEcriteSaveFileName[250];

	if (argc != 5){
		printf("Usage: %s ImageIn.pgm ImageOut.pgm HistoIn.dat HistoOut.dat \n", argv[0]); 
		exit(1);
	}
	 
	sscanf(argv[1],"%s",cNomImgLue);
	sscanf(argv[2],"%s",cNomImgEcrite);
	sscanf(argv[3],"%s",cNomImgLueSaveFileName);
	sscanf(argv[4],"%s",cNomImgEcriteSaveFileName);

	OCTET *ImgIn, *ImgOut;
	 
	lire_nb_lignes_colonnes_image_pgm(cNomImgLue, &nH, &nW);
	nTaille = nH * nW;
	
	allocation_tableau(ImgIn, OCTET, nTaille);
	lire_image_pgm(cNomImgLue, ImgIn, nH * nW);

	allocation_tableau(ImgOut, OCTET, nTaille);

	// Calcul occurrences et création histogramme image expansion
	int occIn[256] = {0};
	occurrences(ImgIn, nH, nW, occIn);
	ecritureOccurrences(cNomImgLueSaveFileName, occIn);

  	// Calcul aMin et aMax simpliste
	int aMin = 0, aMax = 256;
	calculAMinAMaxSimple(occIn, &aMin, &aMax);

	// Calcul et affichage alpha et beta
	double alpha, beta;
	calculAlphaBeta(aMin, aMax, &alpha, &beta);
	printf("Alpha : %f\n", alpha);
	printf("Beta : %f\n", beta);

	// Extension dynamique
	extensionDynamique(ImgIn, ImgOut, nH, nW, aMin, aMax);

	// Calcul occurrences et création histogramme image expansion
	int occOut[256] = {0};
	occurrences(ImgOut, nH, nW, occOut);
	ecritureOccurrences(cNomImgEcriteSaveFileName, occOut);

	// Ecriture
	ecrire_image_pgm(cNomImgEcrite, ImgOut, nH, nW);

	free(ImgIn);
	free(ImgOut);

	return 0;
}