# Roberts
O operador de Roberts corresponde ao método mais simples de detecção de bordas. Como resultado, em regiões que possuem contraste bem definido, obtém-se uma
imagem com intensidades altas, e baixos valores em regiões com pouco contraste. Este operador caracteriza-se por um orientação a 45 o , desta forma, bordas inclinadas são mais realçadas que outras. Outra característica deste operador é a sensibilidade a ruído (CONCI;AZEVEDO; LETA, 2008).

O operador de Roberts faz uso de duas máscaras de tamanho 2 x 2, sendo que uma é aplicada no sentido horizontal e a outro é aplicada no sentido vertical (PEDRINI;SCHWARTZ, 2008).

![equation](http://latex.codecogs.com/gif.latex?G_x%20%5Cbegin%7Bbmatrix%7D%201%20%26%200%20%5C%5C%200%20%26%20-1%20%5C%5C%20%5Cend%7Bbmatrix%7D%20G_y%20%5Cbegin%7Bbmatrix%7D%200%20%26%20-1%20%5C%5C%201%20%26%200%20%5C%5C%20%5Cend%7Bbmatrix%7D)  

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
