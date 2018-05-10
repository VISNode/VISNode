# Frei Chen

O operador Frei-Chein utiliza um conjunto de nove máscaras de tamanho 3 x 3. As máscara M1 a M4 são utilizadas para realizar a detecção de bordas, já a M5 a M8 realizam a detecção de retas, e a M9 representa a média dos pixels na região da matriz (PEDRINI; SCHWARTZ, 2008). 

![equation](http://latex.codecogs.com/svg.latex?M1%20%5Cbegin%7Bbmatrix%7D%201%20%26%20%5Csqrt%7B2%7D%20%26%201%20%5C%5C%200%20%26%200%20%26%200%20%5C%5C%20-1%20%26%20-%5Csqrt%7B2%7D%20%26%20-1%20%5C%5C%20%5Cend%7Bbmatrix%7D%20M2%20%5Cbegin%7Bbmatrix%7D%200%20%26%201%20%26%20-1%20%5C%5C%20%5Csqrt%7B2%7D%20%26%200%20%26%20-%5Csqrt%7B2%7D%20%5C%5C%201%20%26%200%20%26%20-1%20%5C%5C%20%5Cend%7Bbmatrix%7D%20M3%20%5Cbegin%7Bbmatrix%7D%200%20%26%20-1%20%26%20%5Csqrt%7B2%7D%20%5C%5C%201%20%26%200%20%26%20-1%20%5C%5C%20-%5Csqrt%7B2%7D%20%26%201%20%26%200%20%5C%5C%20%5Cend%7Bbmatrix%7D)  

![equation](http://latex.codecogs.com/svg.latex?M4%20%5Cbegin%7Bbmatrix%7D%20%5Csqrt%7B2%7D%20%26%20-1%20%26%200%20%5C%5C%20-1%20%26%200%20%26%201%20%5C%5C%200%20%26%201%20%26%20-%5Csqrt%7B2%7D%20%5C%5C%20%5Cend%7Bbmatrix%7D%20M5%20%5Cbegin%7Bbmatrix%7D%200%20%26%201%20%26%200%20%5C%5C%20-1%20%26%200%20%26%20-1%20%5C%5C%200%20%26%201%20%26%200%20%5C%5C%20%5Cend%7Bbmatrix%7D%20M6%20%5Cbegin%7Bbmatrix%7D%20-1%20%26%200%20%26%201%20%5C%5C%200%20%26%200%20%26%200%20%5C%5C%201%20%26%200%20%26%20-1%20%5C%5C%20%5Cend%7Bbmatrix%7D)  

![equation](http://latex.codecogs.com/svg.latex?M7%20%5Cbegin%7Bbmatrix%7D%201%20%26%20-2%20%26%201%20%5C%5C%20-2%20%26%204%20%26%20-2%20%5C%5C%201%20%26%20-2%20%26%201%20%5C%5C%20%5Cend%7Bbmatrix%7D%20M8%20%5Cbegin%7Bbmatrix%7D%20-2%20%26%201%20%26%20-2%20%5C%5C%201%20%26%204%20%26%201%20%5C%5C%20-2%20%26%201%20%26%20-2%20%5C%5C%20%5Cend%7Bbmatrix%7D%20M9%20%5Cbegin%7Bbmatrix%7D%201%20%26%201%20%26%201%20%5C%5C%201%20%26%201%20%26%200%20%5C%5C%201%20%26%201%20%26%20-1%20%5C%5C%20%5Cend%7Bbmatrix%7D)


#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
