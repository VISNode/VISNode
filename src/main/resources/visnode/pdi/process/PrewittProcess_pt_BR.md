# Prewitt

O operador de Prewith faz uso de duas máscaras de tamanho 3 x 3, sendo que
uma é aplicada no sentido horizontal e a outro é aplicada no sentido vertical (PEDRINI;
SCHWARTZ, 2008).

![equation](http://latex.codecogs.com/gif.latex?G_x%20%5Cbegin%7Bbmatrix%7D%20-1%20%26%200%20%26%201%20%5C%5C%20-1%20%26%200%20%26%201%20%5C%5C%20-1%20%26%200%20%26%201%20%5C%5C%20%5Cend%7Bbmatrix%7D%20G_y%20%5Cbegin%7Bbmatrix%7D%20-1%20%26%20-1%20%26%20-1%20%5C%5C%200%20%26%200%20%26%200%20%5C%5C%201%20%26%201%20%26%201%20%5C%5C%20%5Cend%7Bbmatrix%7D)  

Este filtro faz uso do mesmo conceito de Sobel e Roberts. Considerando z0 a z8 os tons de cinza em torno do pixel que está sendo analisado, este filtro é definido por:

![equation](http://latex.codecogs.com/gif.latex?Gx%20%3D%20%28z_6%20&plus;%20z_7%20&plus;%20z_8%29%20-%20%28z_0%20&plus;%20z_1%20&plus;%20z_2%29)  

![equation](http://latex.codecogs.com/gif.latex?Gy%20%3D%20%28z_2%20&plus;%20z_5%20&plus;%20z_8%29%20-%20%28z_0%20&plus;%20z_3%20&plus;%20z_6%29)  

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
