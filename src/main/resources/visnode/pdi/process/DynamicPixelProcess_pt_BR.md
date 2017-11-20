# Dynamic Pixel

Este proceso tem como objetivo possibilitar a manipulação de pixels de uma imagem dinamicamente.

#### Exemplo:
```javascript
function process(channel, x, y, value) {
  return (value > 128) ? 0 : 255;
}
```

#### Parametros
* __image:__ Imagem
* __script:__ Script contendo o código de maniputação dos pixels

#### Saída
* __image:__ Imagem
