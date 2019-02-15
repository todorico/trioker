#include <stdio.h>
#include "image_ppm.h"
#include "utility.h"

// plot 'black_histo.dat' using 2 title 'Rouge' lt rgb "red" with lines,'black_histo.dat' using 3 title 'Vert' lt rgb "green" with lines,'black_histo.dat' using 4 title 'Bleu' lt rgb "blue" with lines
// plot 'black_prime_histo.dat' using 2 title 'Rouge' lt rgb "red" with lines,'black_prime_histo.dat' using 3 title 'Vert' lt rgb "green" with lines,'black_prime_histo.dat' using 4 title 'Bleu' lt rgb "blue" with lines

/***** AlphaRouge = 0.0 &&&&& BetaRouge = 19.0 *****/
/***** AlphaVert = 0.0 &&&&& BetaVert = 21.0 *****/
/***** AlphaBleu = 0.0 &&&&& BetaBleu = 19.0 *****/

int main(int argc, char* argv[]) {
	int nH, nW, nTaille, nTaille3;

	char cNomImgLue[250];
	char cNomImgEcrite[250];
	char cNomImgLueSaveFileName[250];
	char cNomImgEcriteSaveFileName[250];

	if (argc != 5){
		printf("Usage: %s ImageIn.ppm ImageOut.ppm HistoIn.dat HistoOut.dat \n", argv[0]); 
		exit(1);
	}
	 
	sscanf(argv[1],"%s",cNomImgLue);
	sscanf(argv[2],"%s",cNomImgEcrite);
	sscanf(argv[3],"%s",cNomImgLueSaveFileName);
	sscanf(argv[4],"%s",cNomImgEcriteSaveFileName);

	OCTET *ImgIn, *ImgOut;
	 
	lire_nb_lignes_colonnes_image_ppm(cNomImgLue, &nH, &nW);
	nTaille = nH * nW;
	nTaille3 = nTaille * 3;
	
	allocation_tableau(ImgIn, OCTET, nTaille3);
	lire_image_ppm(cNomImgLue, ImgIn, nH * nW);

	allocation_tableau(ImgOut, OCTET, nTaille3);

	// Calcul occurrences et création histogramme image expansion
	int occInR[256] = {0};
	int occInG[256] = {0};
	int occInB[256] = {0};
	occurrences_Couleur(ImgIn, nTaille3, occInR, occInG, occInB);
	ecritureOccurrences_Couleur(cNomImgLueSaveFileName, occInR, occInG, occInB);

	/*** ROUGE ***/

	  	// Calcul aMin et aMax simpliste
		int aMinR = 0, aMaxR = 256;
		calculAMinAMax_Simple(occInR, &aMinR, &aMaxR);

		// Calcul et affichage alpha et beta
		double alphaR, betaR;
		calculAlphaBeta(aMinR, aMaxR, &alphaR, &betaR);
		printf("AlphaRouge : %f\n", alphaR);
		printf("BetaRouge : %f\n", betaR);

	/*** Vert ***/

	  	// Calcul aMin et aMax simpliste
		int aMinG = 0, aMaxG = 256;
		calculAMinAMax_Simple(occInG, &aMinG, &aMaxG);

		// Calcul et affichage alpha et beta
		double alphaG, betaG;
		calculAlphaBeta(aMinG, aMaxG, &alphaG, &betaG);
		printf("AlphaVert : %f\n", alphaG);
		printf("BetaVert : %f\n", betaG);

	/*** Bleu ***/

	  	// Calcul aMin et aMax simpliste
		int aMinB = 0, aMaxB = 256;
		calculAMinAMax_Simple(occInB, &aMinB, &aMaxB);

		// Calcul et affichage alpha et beta
		double alphaB, betaB;
		calculAlphaBeta(aMinR, aMaxR, &alphaB, &betaB);
		printf("AlphaRouge : %f\n", alphaB);
		printf("BetaRouge : %f\n", betaB);

	// Extension dynamique
	extensionDynamique_Couleur(ImgIn, ImgOut, nTaille3, aMinR, aMaxR, aMinG, aMaxG, aMinB, aMaxB);

	// Calcul occurrences et création histogramme image expansion
	int occOutR[256] = {0};
	int occOutG[256] = {0};
	int occOutB[256] = {0};
	occurrences_Couleur(ImgOut, nTaille3, occOutR, occOutG, occOutB);

	ecritureOccurrences_Couleur(cNomImgEcriteSaveFileName, occOutR, occOutG, occOutB);

	// Ecriture
	ecrire_image_ppm(cNomImgEcrite, ImgOut, nH, nW);

	free(ImgIn);
	free(ImgOut);

	return 0;
}