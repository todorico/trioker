typedef unsigned char OCTET;

#include <stdio.h>

#define true 1
#define false 0

// Dilatation = 0
// Erosion = 255

void occurrences(OCTET* Img, int nH, int nW, int* occurrences){
  for (int i = 0; i < nH; ++i){
    for (int j = 0; j < nW; ++j){
      occurrences[Img[i*nW+j]]++;
    }
  }
}

void ecritureOccurrences(char* saveFileName, int* occurrences){
  FILE* f = fopen(saveFileName, "w+");
  for (int k = 0; k < 256; ++k){
      fprintf(f, "%d %d\n", k, occurrences[k]);
  }
  fclose(f);
}

void calculAMinAMaxSimple(int* occurrences, int* aMin, int* aMax){
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