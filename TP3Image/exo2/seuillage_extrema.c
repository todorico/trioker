#include <stdio.h>
#include "image_ppm.h"
#include "utility.h"

// plot 'baboon.dat' using 2 title 'Rouge' lt rgb "red" with lines,'baboon.dat' using 3 title 'Vert' lt rgb "green" with lines,'baboon.dat' using 4 title 'Bleu' lt rgb "blue" with lines
// plot 'baboon_seuille.dat' using 2 title 'Rouge' lt rgb "red" with lines,'baboon_seuille.dat' using 3 title 'Vert' lt rgb "green" with lines,'baboon_seuille.dat' using 4 title 'Bleu' lt rgb "blue" with lines
// plot 'baboon_expansion.dat' using 2 title 'Rouge' lt rgb "red" with lines,'baboon_expansion.dat' using 3 title 'Vert' lt rgb "green" with lines,'baboon_expansion.dat' using 4 title 'Bleu' lt rgb "blue" with lines



/***** AlphaRouge = 0.0 &&&&& BetaRouge = 19.0 *****/
/***** AlphaVert = 0.0 &&&&& BetaVert = 21.0 *****/
/***** AlphaBleu = 0.0 &&&&& BetaBleu = 19.0 *****/

int main(int argc, char* argv[]) {
	int nH, nW, nTaille, nTaille3;

	char cNomImgOriginale[250];
	char cNomImgSeuille[250];
	char cNomImgExpansion[250];

	char datImgOriginale[250];
	char datImgSeuille[250];
	char datImgExpansion[250];

	int SminR, SmaxR;
	int SminG, SmaxG;
	int SminB, SmaxB;

	if (argc != 13){
		printf("Usage: %s ImageOriginale.ppm ImageSeuille.ppm ImageExpansion.ppm HistoOriginal.dat HistoSeuillee.dat HistoExpansion.dat SminR SmaxR SminG SmaxG SminB SmaxB\n", argv[0]); 
		exit(1);
	}
	 
	sscanf(argv[1],"%s",cNomImgOriginale);
	sscanf(argv[2],"%s",cNomImgSeuille);
	sscanf(argv[3],"%s",cNomImgExpansion);

	sscanf(argv[4],"%s",datImgOriginale);
	sscanf(argv[5],"%s",datImgSeuille);
	sscanf(argv[6],"%s",datImgExpansion);

	sscanf(argv[7],"%d",&SminR); //25
	sscanf(argv[8],"%d",&SmaxR); //225

	sscanf(argv[9],"%d",&SminG); // 40
	sscanf(argv[10],"%d",&SmaxG); // 210

	sscanf(argv[11],"%d",&SminB); // 40
	sscanf(argv[12],"%d",&SmaxB); // 225

	OCTET *ImgOriginale, *ImgSeuillee, *ImgExpansion;
	 
	lire_nb_lignes_colonnes_image_ppm(cNomImgOriginale, &nH, &nW);
	nTaille = nH * nW;
	nTaille3 = nTaille * 3;
	
	allocation_tableau(ImgOriginale, OCTET, nTaille3);
	lire_image_ppm(cNomImgOriginale, ImgOriginale, nH * nW);

	allocation_tableau(ImgSeuillee, OCTET, nTaille3);
	copy3(ImgOriginale, ImgSeuillee, nTaille3);

	allocation_tableau(ImgExpansion, OCTET, nTaille3);



	/***************** IMAGE ORIGINALE *****************/

	// Calcul occurrences et création histogramme image expansion
	int occOriginaleR[256] = {0};
	int occOriginaleG[256] = {0};
	int occOriginaleB[256] = {0};
	occurrences_Couleur(ImgOriginale, nTaille3, occOriginaleR, occOriginaleG, occOriginaleB);
	ecritureOccurrences_Couleur(datImgOriginale, occOriginaleR, occOriginaleG, occOriginaleB);



	/***************** IMAGE SEUILLEE *****************/

	// Seuillage
	seuillageMinMax_Couleur(ImgOriginale, ImgSeuillee, nTaille3, SminR, SmaxR, SminG, SmaxG, SminB, SmaxB);
	ecrire_image_ppm(cNomImgSeuille, ImgSeuillee, nH, nW);
	// Histogramme
	int occSeuilleeR[256] = {0};
	int occSeuilleeG[256] = {0};
	int occSeuilleeB[256] = {0};
	occurrences_Couleur(ImgSeuillee, nTaille3, occSeuilleeR, occSeuilleeG, occSeuilleeB);
	ecritureOccurrences_Couleur(datImgSeuille, occSeuilleeR, occSeuilleeG, occSeuilleeB);



	/***************** IMAGE EXPANSION DYNAMIQUE *****************/

	// Expansion dynamique
	expansionDynamique_Couleur(ImgSeuillee, ImgExpansion, nTaille3, SminR, SmaxR, SminG, SmaxG, SminB, SmaxB);

	// Calcul occurrences et création histogramme image expansion
	int occExpansionR[256] = {0};
	int occExpansionG[256] = {0};
	int occExpansionB[256] = {0};
	occurrences_Couleur(ImgExpansion, nTaille3, occExpansionR, occExpansionG, occExpansionB);

	ecritureOccurrences_Couleur(datImgExpansion, occExpansionR, occExpansionG, occExpansionB);

	// Ecriture
	ecrire_image_ppm(cNomImgExpansion, ImgExpansion, nH, nW);

	free(ImgOriginale);
	free(ImgSeuillee);
	free(ImgExpansion);

	return 0;
}