# Snake

Os métodos de contornos ativos, também conhecidos como Snakes, são técnicas
que tem como objetivo a extração das bordas de objetos da imagem. Estas técnicas são
caracterizadas para uma curva, que é ajustada de acordo com o contorno do objeto.
A técnica é iniciada a partir de um contorno arbitrário que é evoluído até a correta
identificação do objeto de interesse (CONCI; AZEVEDO; LETA, 2008).

O ajuste da curva é realizado levando em consideração forças internas e externas
à curva. O objetivo da energia interna é manter a continuidade e suavidade do contorno,
levando em consideração o formato do objeto. Já a energia externa é responsável por
buscar valores onde há maior variável entre o pixel e seus vizinhos, o que caracteriza uma
borda (KASS; WITKIN; TERZOPOULOS, 1988).

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
