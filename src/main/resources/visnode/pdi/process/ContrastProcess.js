function process(image) {
    var resultImage = ImageFactory.buildEmptyImage(image)
    var contrast = 1.5
    for (var c = 0; c < image.getChannelCount(); c++) {
        for (var x = 0; x < image.getWidth(); x++) {
            for (var y = 0; y < image.getHeight(); y++) {
                var newPixel = image.get(c, x, y) * contrast;
                resultImage.set(c, x, y, resultImage.getPixelValueRange().limit(newPixel))
            }
        }
    }
    return resultImage
}
