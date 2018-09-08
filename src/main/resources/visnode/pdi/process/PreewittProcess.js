function process(image) {
    var resultImage = ImageFactory.buildEmptyImage(image);
    convolution(image, resultImage);
    return resultImage;
}

var xMask = [[1, 0, -1], [1, 0, -1], [1, 0, -1]];
var yMask = [[1, 1, 1], [0, 0, 0], [-1, -1, -1]];

function computePixel(image, channel, x, y) {
    var gradientX = 0;
    var gradientY = 0;
    for (var lx = 0; lx < 3; lx++) {
        for (var ly = 0; ly < 3; ly++) {
            var value = image.get(channel, x + lx - 1, y + ly - 1);
            gradientX += value * xMask[lx][ly];
            gradientY += value * yMask[lx][ly];
        }
    }
    var value = Math.floor(Math.sqrt(Math.pow(gradientX, 2) + Math.pow(gradientY, 2)));
    if (value > image.getPixelValueRange().getHigher()) {
        return image.getPixelValueRange().getHigher();
    }
    return value;
}

function convolution(image, resultImage) {
    for (var channel = 0; channel < image.getChannelCount(); channel++) {
        for (var x = 1; x < image.getWidth() - 1; x++) {
            for (var y = 1; y < image.getHeight() - 1; y++) {
                var newValue = computePixel(image, channel, x, y);
                resultImage.set(channel, x, y, newValue);
            }
        }
    }
}
