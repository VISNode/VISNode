# Kirsh

O operador Kirsch (KIRSCH, 1971) consiste na utilização de oito máscaras orientadas em 45 o . Para cada pixel da imagem é realizada a aplicação de uma das oito máscaras e é mantido o valor máximo. Desta forma, o gradiente não é obtido através dos valoresde G x e G y separadamente, e sim, atrás do maior resultado do conjunto de 8 máscaras (PEDRINI; SCHWARTZ, 2008).

![equation](http://latex.codecogs.com/gif.latex?%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%26%20-1%20%5C%5C%202%20%26%200%20%26%20-2%20%5C%5C%201%20%26%200%20%26%20-1%20%5C%5C%20%5Cend%7Bbmatrix%7D%20%5Cbegin%7Bbmatrix%7D%200%20%26%20-1%20%26%20-2%20%5C%5C%201%20%26%200%20%26%20-1%20%5C%5C%202%20%26%201%20%26%200%20%5C%5C%20%5Cend%7Bbmatrix%7D%20%5Cbegin%7Bbmatrix%7D%20-1%20%26%20-2%20%26%20-1%20%5C%5C%200%20%26%200%20%26%200%20%5C%5C%201%20%26%202%20%26%201%20%5C%5C%20%5Cend%7Bbmatrix%7D%20%5Cbegin%7Bbmatrix%7D%20-2%20%26%20-1%20%26%200%20%5C%5C%20-1%20%26%200%20%26%201%20%5C%5C%200%20%26%201%20%26%202%20%5C%5C%20%5Cend%7Bbmatrix%7D)  

![equation](http://latex.codecogs.com/gif.latex?%5Cbegin%7Bbmatrix%7D%20-1%20%26%20-0%20%26%201%20%5C%5C%20-2%20%26%200%20%26%202%20%5C%5C%20-1%20%26%200%20%26%201%20%5C%5C%20%5Cend%7Bbmatrix%7D%20%5Cbegin%7Bbmatrix%7D%200%20%26%201%20%26%202%20%5C%5C%20-1%20%26%200%20%26%201%20%5C%5C%20-2%20%26%20-1%20%26%200%20%5C%5C%20%5Cend%7Bbmatrix%7D%20%5Cbegin%7Bbmatrix%7D%201%20%26%202%20%26%201%20%5C%5C%200%20%26%200%20%26%200%20%5C%5C%20-1%20%26%20-2%20%26%20-1%20%5C%5C%20%5Cend%7Bbmatrix%7D%20%5Cbegin%7Bbmatrix%7D%202%20%26%201%20%26%200%20%5C%5C%20-1%20%26%200%20%26%20-1%20%5C%5C%20-0%20%26%20-1%20%26%20-2%20%5C%5C%20%5Cend%7Bbmatrix%7D)  

Outras máscaras maiores podem ser aplicadas, como máscaras de tamanho 5 x 5 ou 7 x 7 pixel, mas estas abordagens são menos sensíveis a ruído e o tempo para a
realização dos cálculos destas máscaras aumenta significamente (PEDRINI; SCHWARTZ, 2008).

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
