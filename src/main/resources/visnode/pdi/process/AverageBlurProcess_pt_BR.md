# Média Blur

Filtros de média são utilizadas para fins de suavização de imagens, sendo utilizados para o borramento e a redução de ruídos. A ideia por trás desta técnica é aplicar uma operação estatística na vizinhança de uma máscara, calculando um novo valor do pixel analisado (GONZALEZ; WOODS, 2008).

O filtro de média calcula a média aritmética da máscara em processamento, o valor resultante será aplicado no pixel central. Este filtro diminui variações da imagem, removendo, desta forma, seu ruído desta (GONZALEZ; WOODS, 2008).

#### Parâmetros
* __image:__ Imagem original

#### Saída
* __image:__ Imagem suavizada
