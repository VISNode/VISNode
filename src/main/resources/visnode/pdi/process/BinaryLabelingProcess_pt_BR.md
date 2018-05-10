# Binary Labeling

Este processo tem como objetivo extrair objetos da imagem através de um algortimo de crescimento de regiões. Este processo espera que a imagem de origem seja uma imagem binária, onde os objetos a serem extraidos sejam branco (255/1) e o fundo da imagem preto (0).

#### Parâmetros
* __image__ Image binária

#### Saída
* __objectList__: Lista de objetos extraidos da imagem (ExtractedObject)

#### ExtractedObject
  * __label__: Identificador do objeto
  * __matrix__: Imagem binária
  * __getPerimeter()__: Retorna o perímeto da imagem
  * __getCircularity()__: Retorna a circularidade da imagem
