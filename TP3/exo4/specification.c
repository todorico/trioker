#include <stdio.h>
#include "image_ppm.h"
#include "utility.h"

/***** Alpha = 0.0 &&&&& Beta = 23.0 *****/

int main(int argc, char* argv[]) {
	int nH, nW, nTaille;

	char cNomImgRef[250];
	char datImgRef[250];

	char cNomImg[250];
	char datImg[250];

	char cNomImgEgalise[250];
	char datImgEgalise[250];

	char cNomImgSpecifie[250];
	char datImgSpecifie[250];

	if (argc != 9){
		printf("Usage: %s ImageR.pgm HistoR.dat Image.pgm Histo.dat ImageEgalise.dat HistoEgalise.dat ImageSpecifie.pgm HistoSpecifie.dat \n", argv[0]); 
		exit(1);
	}

	sscanf(argv[1],"%s",cNomImgRef);
	sscanf(argv[2],"%s",datImgRef);
	 
	sscanf(argv[3],"%s",cNomImg);
	sscanf(argv[4],"%s",datImg);

	sscanf(argv[5],"%s",cNomImgEgalise);
	sscanf(argv[6],"%s",datImgEgalise);

	sscanf(argv[7],"%s",cNomImgSpecifie);
	sscanf(argv[8],"%s",datImgSpecifie);

	OCTET *Img, *ImgEgalise, *ImgSpecifie;
	 
	lire_nb_lignes_colonnes_image_pgm(cNomImg, &nH, &nW);
	nTaille = nH * nW;
	
	allocation_tableau(Img, OCTET, nTaille);
	lire_image_pgm(cNomImg, Img, nH * nW);

	allocation_tableau(ImgEgalise, OCTET, nTaille);



	/****** IMAGE REFERENCE ******/
	OCTET *ImgRef;
	int nHR, nWR, nTailleR;
	lire_nb_lignes_colonnes_image_pgm(cNomImgRef, &nHR, &nWR);
	nTailleR = nHR * nWR;
	
	allocation_tableau(ImgRef, OCTET, nTailleR);
	lire_image_pgm(cNomImgRef, ImgRef, nHR * nWR);

	int occR[256] = {0};
	occurrences_Gris(ImgRef, nHR, nWR, occR);
	ecritureOccurrences_Gris(datImgRef, occR);



	/****** IMAGE DEPART ******/
	int occ[256] = {0};
	occurrences_Gris(Img, nH, nW, occ);
	ecritureOccurrences_Gris(datImg, occ);


	/****** IMAGE EGALISE ******/
	/** DPP **/
	double ddp[256];
	for (int i = 0; i < 256; ++i)
		ddp[i] = ((double)occ[i]) / ((double)nTaille);

	/** Courbe de F(a) **/ //Fonction répartition
	double fdea[256];
	double last = 0.0;
	for (int i = 0; i < 256; ++i){
		fdea[i] = ddp[i] + last;
		last += ddp[i];
	}

	/** Courbe de T(a) **/
	double transformation[256];
	for (int i = 0; i < 256; ++i){
		transformation[i] = fdea[i] * 255.0;
	}

	/** Egalisation **/ // T(a)
	for (int i = 0; i < nH; ++i){
   		for (int j = 0; j < nW; ++j){
      		ImgEgalise[i*nW+j] = transformation[Img[i*nW+j]];
    	}
  	}

	/** Ecriture Image Egalisation + Histogramme **/
	int occEgalise[256] = {0};
	occurrences_Gris(ImgEgalise, nH, nW, occEgalise);
	ecritureOccurrences_Gris(datImgEgalise, occEgalise);
  	ecrire_image_pgm(cNomImgEgalise, ImgEgalise, nH, nW);



  	/****** IMAGE SPECIFIE ******/
	/** DPP Ref **/
	double ddpR[256];
	for (int i = 0; i < 256; ++i)
		ddpR[i] = ((double)occR[i]) / ((double)nTaille);

	/** Courbe de F(a) Ref **/ //Fonction répartition
	double fdeaR[256];
	double lastR = 0.0;
	for (int i = 0; i < 256; ++i){
		fdeaR[i] = ddpR[i] + lastR;
		lastR += ddpR[i];
	}

	/** Courbe de T(a) Ref **/
	double transformationR[256];
	for (int i = 0; i < 256; ++i)
		transformationR[i] = fdeaR[i] * 255.0;

	/** Courbe de F'(a) Ref **/ //Fonction répartition
	double transformationInverse[256];
	for (int i = 0; i < 256; ++i)
		transformationInverse[i] = getInverseValue(transformationR, i);
	
	/** Egalisation Inverse **/ // T'(a) -- F'(a) Ref
	allocation_tableau(ImgSpecifie, OCTET, nTaille);
	for (int i = 0; i < nH; ++i){
   		for (int j = 0; j < nW; ++j){
      		ImgSpecifie[i*nW+j] = transformationInverse[Img[i*nW+j]];
    	}
  	}

	/** Ecriture Image Specifiee + Histogramme **/
	int occSpecifie[256] = {0};
	occurrences_Gris(ImgSpecifie, nH, nW, occSpecifie);
	ecritureOccurrences_Gris(datImgSpecifie, occSpecifie);
  	ecrire_image_pgm(cNomImgSpecifie, ImgSpecifie, nH, nW);

	free(Img);
	free(ImgEgalise);
	free(ImgSpecifie);

	return 0;
}