# Sobel

O operador de Sobel realiza a aproximação da magnitude do gradiente através da diferença de valores ponderados dos níveis de cinza da imagem. Para isso, são utilizados duas máscaras de tamanho 3 x 3, sendo que uma é aplicada em relação ao eixo x e a outro é aplicada no eixo y (PEDRINI; SCHWARTZ, 2008). Este operador realiza um suavização, e ao mesmo tempo, o realce de bordas.

![equation](http://latex.codecogs.com/gif.latex?G_x%20%5Cbegin%7Bbmatrix%7D%20-1%20%26%200%20%26%201%20%5C%5C%20-2%20%26%200%20%26%201%20%5C%5C%20-1%20%26%200%20%26%201%20%5C%5C%20%5Cend%7Bbmatrix%7D%20G_y%20%5Cbegin%7Bbmatrix%7D%20-1%20%26%20-2%20%26%20-1%20%5C%5C%200%20%26%200%20%26%200%20%5C%5C%201%20%26%202%20%26%201%20%5C%5C%20%5Cend%7Bbmatrix%7D)  

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
