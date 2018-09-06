function process(image) {
    var grayscaleImage = grayscale(image)
    var resultImage = ImageFactory.buildEmptyImage(grayscaleImage)
    var threshold = 100
    for (var c = 0; c < grayscaleImage.getChannelCount(); c++) {
        for (var x = 0; x < grayscaleImage.getWidth(); x++) {
            for (var y = 0; y < grayscaleImage.getHeight(); y++) {			 
                var newPixel = grayscaleImage.get(c, x, y) >= threshold ? 255 : 0
                resultImage.set(c, x, y, newPixel)
            }
        }
    }
    return resultImage
}

function grayscale(image) {
    var resultImage = ImageFactory.buildEmptyImage(
            1,
            image.getWidth(),
            image.getHeight(),
            image.getPixelValueRange()
    )
    for (var x = 0; x < image.getWidth(); x++) {
        for (var y = 0; y < image.getHeight(); y++) {
            var sum = 0
            for (var c = 0; c < image.getChannelCount(); c++) {
                sum += image.get(c, x, y)
            }
            var newPixel = Math.floor(sum / image.getChannelCount())
            resultImage.set(0, x, y, newPixel)
        }
    }
    return resultImage
}
