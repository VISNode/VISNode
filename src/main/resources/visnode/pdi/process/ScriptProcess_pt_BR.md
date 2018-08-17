# ScriptProcess

Com este processo é possível realizar qualquer manipulação com qualquer tipo de dado.

## Exemplo de manipulação de imagens

No exemplo abaixo é demonstrado um script para o cálculo da média de pixels de uma imagem

```javascript
function process(image) {
    var average = 0
    for (var c = 0; c < image.getChannelCount(); c++) {
        for (var x = 0; x < image.getWidth(); x++) {
            for (var y = 0; y < image.getHeight(); y++) {
                average += image.get(c, x, y)
            }
        }
    }
    var size = image.getWidth() * 
        image.getHeight() * 
        image.getChannelCount()
    return Math.floor(average / size)
}
```

## Criação de imagens

No exemplo abaixo é demonstrado um script utilizado para a criação de uma nova imagem. O exemplo é utilizado o valor 5 para aplicar um brilho em cada pixel da imagem.

```javascript
function process(image) {
    var resultImage = ImageFactory.buildEmptyImage(image)
    var brightness = 90
    for (var c = 0; c < image.getChannelCount(); c++) {
        for (var x = 0; x < image.getWidth(); x++) {
            for (var y = 0; y < image.getHeight(); y++) {
                // Calculate the brightness value
                var newPixel = image.get(c, x, y) + brightness;
                newPixel = resultImage.
                    getPixelValueRange().limit(newPixel)
                resultImage.set(c, x, y, newPixel)
            }
        }
    }
    return resultImage
}
```

#### Parâmetros
* __value:__ DynamicValue
* __script:__ Script contendo o código de maniputação dos pixels

#### Saída
* __value:__ DynamicValue