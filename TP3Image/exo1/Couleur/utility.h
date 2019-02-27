typedef unsigned char OCTET;

#include <stdio.h>

#define true 1
#define false 0

// Dilatation = 0
// Erosion = 255

void occurrences_Gris(OCTET* Img, int nH, int nW, int* occurrences){
  for (int i = 0; i < nH; ++i){
    for (int j = 0; j < nW; ++j){
      occurrences[Img[i*nW+j]]++;
    }
  }
}

void occurrences_Couleur(OCTET* Img, int nTaille3, int* occR, int* occG, int* occB){
  for (int i = 0; i < nTaille3; i += 3) {
    occR[ Img[i] ]++;
    occG[ Img[i+1] ]++;
    occB[ Img[i+2] ]++;
  }
}

void ecritureOccurrences_Gris(char* saveFileName, int* occurrences){
  FILE* f = fopen(saveFileName, "w+");
  for (int k = 0; k < 256; ++k){
      fprintf(f, "%d %d\n", k, occurrences[k]);
  }
  fclose(f);
}

void ecritureOccurrences_Couleur(char* saveFileName, int* occR, int* occG, int* occB){
  FILE* f = fopen(saveFileName, "w+");
  for (int k = 0; k < 256; ++k){
      fprintf(f, "%d %d %d %d\n", k, occR[k], occG[k], occB[k]);
  }
  fclose(f);
}

void calculAMinAMax_Simple(int* occurrences, int* aMin, int* aMax){
  for (int i = 0; i < 256; ++i){
    if (occurrences[i] != 0){
      *aMin = i;
      break;
    }
  }
  for (int i = 255; i >= 0; --i){
    if (occurrences[i] != 0){
      *aMax = i;
      break;
    }
  }
}

void extensionDynamique(OCTET* ImgIn, OCTET* ImgOut, int nH, int nW, int aMin, int aMax){
  for (int i = 0; i < nH; ++i){
    for (int j = 0; j < nW; ++j){
      ImgOut[i*nW+j] = 255 / (aMax - aMin) * (-aMin + ImgIn[i*nW+j]);
    }
  }
}

void extensionDynamique_Couleur(OCTET* ImgIn, OCTET* ImgOut, int nTaille3, int aMinR, int aMaxR, int aMinG, int aMaxG, int aMinB, int aMaxB){
  for (int i = 0; i < nTaille3; i += 3) {
      ImgOut[i] = 255 / (aMaxR - aMinR) * (-aMinR + ImgIn[i]);
      ImgOut[i+1] = 255 / (aMaxG - aMinG) * (-aMinG + ImgIn[i+1]);
      ImgOut[i+2] = 255 / (aMaxB - aMinB) * (-aMinB + ImgIn[i+2]);
  }
}

void calculAlphaBeta(int aMin, int aMax, double* alpha, double* beta){
  *alpha = (255 * aMin)/(aMax - aMin);
  *beta = (255)/(aMax - aMin);
}





void calcul4(OCTET *ImgIn, OCTET *ImgOut, int nH, int nW, int valeur){

  for (int i = 0; i < nH; i++){
    for (int j = 0; j < nW; j++) {

      if (ImgIn[i * nW + j] == valeur){
        
        if (i != 0)
          ImgOut[(i-1) * nW + j] = valeur;

        if (i != nH-1)
          ImgOut[(i+1) * nW + j] = valeur;

        if (j != 0)
          ImgOut[i * nW + (j-1)] = valeur;

        if (j != nW-1)
          ImgOut[i * nW + (j+1)] = valeur;

      }

    }
  }

}

void calcul8(OCTET *ImgIn, OCTET *ImgOut, int nH, int nW, int valeur){

  for (int i = 0; i < nH; i++){
    for (int j = 0; j < nW; j++) {

      if (ImgIn[i * nW + j] == valeur){
        
        if (i != 0){
          ImgOut[(i-1) * nW + j] = valeur;

          if (j != 0)
            ImgOut[(i-1) * nW + (j-1)] = valeur;

          if (j != nW-1)
            ImgOut[(i-1) * nW + (j+1)] = valeur;

        }

        if (i != nH-1){
          ImgOut[(i+1) * nW + j] = valeur;

          if (j != 0)
            ImgOut[(i+1) * nW + (j-1)] = valeur;

          if (j != nW-1)
            ImgOut[(i+1) * nW + (j+1)] = valeur;

        }

        if (j != 0)
          ImgOut[i * nW + (j-1)] = valeur;

        if (j != nW-1)
          ImgOut[i * nW + (j+1)] = valeur;

      }

    }
  }

}

void traitement(OCTET *ImgIn, OCTET *ImgOut, int nH, int nW, int erosion, int voisins){

  int valeur = (erosion == true) ? 255 : 0 ;

  if (voisins == 4)
    calcul4(ImgIn, ImgOut, nH, nW, valeur);
  else if (voisins == 8)
    calcul8(ImgIn, ImgOut, nH, nW, valeur);
  
}