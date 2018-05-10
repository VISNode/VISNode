# Mediana Blur

O filtro de mediana é o filtro estatístico mais conhecido, onde substitui o valor de um determinado pixel pela mediana dos níveis de intensidade da vizinhança. Este tipo de filtro é utilizado quando se deseja reduzir ruídos aleatórios de uma imagem. O filtro propicia um menor borramento do que filtros lineares de suavização como o filtro de média (GONZALEZ; WOODS, 2008).

A mediana m de um conjunto contendo um número n de elementos é definida pelo valor central destes elementos, desta forma, a metade dos elementos situam-se acima de m e a outra metade abaixo. Quando n for par, é necessário realizar a média aritmética dos elementos mais próximos aos centro (CONCI; AZEVEDO; LETA, 2008). Este filtro realiza a ordenação das intensidades dos pixels existentes dentro da máscara, utilizando como valor para o pixel analizado o valor central dos elementos ordenados (CONCI; AZEVEDO; LETA, 2008).

O filtro de mediana possui resultados melhores do que o filtro de média. Isso ocorre devido ao fato de que, se existe um ruído entre os elementos da máscara, este valor estará presente nas primeiras ou últimas posições. Desta forma, pontos discrepantes têm grande chance de serem considerados ruídos e suavizados (CONCI; AZEVEDO; LETA, 2008).

#### Parâmetros
* __image:__ Imagem

#### Saída
* __image:__ Imagem
