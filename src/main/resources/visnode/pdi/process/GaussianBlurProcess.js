function process(image) {
    var resultImage = ImageFactory.buildEmptyImage(image);
    convolution(image, resultImage);
    return resultImage;
}

var kernel = [
    [1, 2, 1],
    [2, 4, 2],
    [1, 2, 1]
];

function convolution(image, resultImage) {
    for (var channel = 0; channel < image.getChannelCount(); channel++) {
        for (var x = 0; x < image.getWidth(); x++) {
            for (var y = 0; y < image.getHeight(); y++) {
                processKernel(image, resultImage, channel, x, y);
            }
        }
    }
}

function processKernel(image, resultImage, channel, x, y) {
    var sumValue = 0;
    var valueKernel = 0;
    var ksize = kernel.length;
    for (var i = 0; i < ksize; i++) {
        for (var j = 0; j < ksize; j++) {
            var xPos = image.limitX(x + (i - 1));
            var yPos = image.limitY(y + (j - 1));
            sumValue += image.get(channel, xPos, yPos) * kernel[i][j];
            valueKernel += kernel[i][j];
        }
    }
    if (valueKernel > 0) {
        sumValue /= valueKernel;
    }
    var value = image.getPixelValueRange().limit(Math.floor(Math.round(sumValue)));
    resultImage.set(channel, x, y, value);
}



