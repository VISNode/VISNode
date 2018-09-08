function process(image) {
    var resultImage = ImageFactory.buildEmptyImage(image);
    convolution(image, resultImage);
    return resultImage;
}

function computeCenter(neighbours) {
    var occurances = {};
    for (var x = 0; x < neighbours.length; x++) {
        for (var y = 0; y < neighbours[x].length; y++) {
            var value = neighbours[x][y];
            if (!occurances[value]) {
                occurances[value] = 0;
            }
            occurances[value] = occurances[value] + 1;
        }
    }
    var largest = -1;
    for (var entry in occurances) {
        if (largest == -1 || occurances[entry] > occurances[largest]) {
            largest = entry;
        }
    }
    // If the mode only occurs once, there is no mode and so returns the original pixel
    if (occurances[largest] == 1) {
        return neighbours[1][1];
    }
    return largest;
}

function convolution(image, resultImage) {
    for (var c = 0; c < image.getChannelCount(); c++) {
        for (var x = 0; x < image.getWidth(); x++) {
            for (var y = 0; y < image.getHeight(); y++) {
                var neighbours = [[], [], []];
                for (var lx = 0; lx < 3; lx++) {
                    for (var ly = 0; ly < 3; ly++) {
                        var ix = image.limitX(x + lx - 1);
                        var iy = image.limitY(y + ly - 1);
                        neighbours[lx][ly] = image.get(c, ix, iy);
                    }
                }
                var newValue = computeCenter(neighbours);
                resultImage.set(c, x, y, newValue);
            }
        }
    }
}
