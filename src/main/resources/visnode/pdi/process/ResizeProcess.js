function process(input) {
    var image = input
    var resultImage = ImageFactory.buildEmptyImage(image)
    var sizeX = 2
    var sizeY = 2
    transform(
        image,
        resultImage,
        [
            [1 / sizeX, 0, 0],
            [0, 1 / sizeY, 0],
            [0, 0, 1]
        ]
    )
    return resultImage
}

function transform(image, resultImage, kernel) {
    for (var c = 0; c < image.getChannelCount(); c++) {
        for (var x = 0; x < image.getWidth(); x++) {
            for (var y = 0; y < image.getHeight(); y++) {
                applyKernel(
                    image,
                    resultImage,
                    kernel,
                    c,
                    x,
                    y,
                    image.get(c, x, y)
                )
            }
        }
    }
}

function applyKernel(image, resultImage, kernel, channel, x, y, value) {
    var halfX = image.getWidth() / 2
    var halfY = image.getHeight() / 2
    var tmpX = x - halfX
    var tmpY = y - halfY
    var newX = Math.round(tmpX * kernel[0][0] + tmpY * kernel[0][1] + 1 * kernel[0][2])
    var newY = Math.round(tmpX * kernel[1][0] + tmpY * kernel[1][1] + 1 * kernel[1][2])
    newX += halfX
    newY += halfY
    // Pixel position is right
    if (newX < image.getWidth() && newY < image.getHeight() && newX >= 0 && newY >= 0) {
        resultImage.set(channel, x, y, image.get(channel, newX, newY))
    }
}
