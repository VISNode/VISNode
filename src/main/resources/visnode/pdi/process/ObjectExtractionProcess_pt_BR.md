# Extração de objetos

Este proceso tem como objetivo possibilitar a extração de objetos de uma imagem.

## Exemplo de filtragem de objetos

No exemplo abaixo é demonstrado um script que realiza um filtro nos objetos extraidos da image. Neste exemplo, somente os objetos com tamanho superior a 250 pixels são retornados.

```javascript
function process(objectList) {
    var list = []
    for (var i = 0; i < objectList.lengh; i++) {
        if (objectList[i].size() > 250) {
            list.push(objectList[i])
        }
    }
    return list;
}
```

#### Parâmetros
* __objectList:__ Lista de objetos extraidos da imagem
* __script:__ Script para extração de objetos   

#### Saída
* __image:__ BinaryImage
