function process(image) {
    var resultImage = ImageFactory.buildEmptyImage(image)
    var brightness = 90
    for (var c = 0; c < image.getChannelCount(); c++) {
        for (var x = 0; x < image.getWidth(); x++) {
            for (var y = 0; y < image.getHeight(); y++) {
                // Calculate the brightness value
                var newPixel = image.get(c, x, y) + brightness;
                resultImage.set(c, x, y, resultImage.getPixelValueRange().limit(newPixel))
            }
        }
    }
    return resultImage
}