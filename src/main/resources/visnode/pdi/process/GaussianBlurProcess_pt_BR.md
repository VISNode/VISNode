# Gaussian Blur

Filtros Gaussianos possuem características que são úteis para o processamento de imagens. As funções Gaussianas são simétricas, o que significa que o grau de suavização é aplicado da mesma forma em todas as direções (isotrópico). A suavização da imagem é obtida através da substituição de cada pixel da imagem pela média ponderada dos pixels vizinhos (PEDRINI; SCHWARTZ, 2008).

Este filtro é utilizado para reduzir quantidades de variação de intensidades entre o pixel e seus vizinhos, minimizando e até eliminado informações indesejadas. É um dos filtros de passa-baixa mais importantes, pois seu nível de suavização ocorre de maneira uniforme, o que não ocorre nos outros filtros, como, por exemplo, no filtro de média. Este filtro é adequado para a aplicação em conjunto com outros filtros em aplicações de detecção de bordas (CONCI; AZEVEDO; LETA, 2008).

#### Parâmetros
* __image:__ Imagem
* __sigma:__ Sigma
* __maskSize:__ Tamanho da máscara

#### Saída
* __image:__ Imagem
