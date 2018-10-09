function process(image) {
    var erosionResultImage = ImageFactory.buildEmptyImage(image);
    erosion(image, erosionResultImage);
    var dilationResultImage = ImageFactory.buildEmptyImage(image);
    dilation(erosionResultImage, dilationResultImage);
    return dilationResultImage;
}

function dilation(image, resultImage) {
    var computeCenter = function (neighbours) {
        var largest = 0;
        for (var x = 0; x < neighbours.length; x++) {
            for (var y = 0; y < neighbours[x].length; y++) {
                if (neighbours[x][y] > largest) {
                    largest = neighbours[x][y];
                }
            }
        }
        return largest;
    };
    convolution(image, resultImage, computeCenter);
}

function erosion(image, resultImage) {
    var computeCenter = function (neighbours) {
        var smallest = Number.MAX_VALUE;
        for (var x = 0; x < neighbours.length; x++) {
            for (var y = 0; y < neighbours[x].length; y++) {
                if (neighbours[x][y] < smallest) {
                    smallest = neighbours[x][y];
                }
            }
        }
        return smallest;
    };
    convolution(image, resultImage, computeCenter);
}


function convolution(image, resultImage, computeCenter) {
    for (var channel = 0; channel < image.getChannelCount(); channel++) {
        for (var x = 1; x < image.getWidth() - 1; x++) {
            for (var y = 1; y < image.getHeight() - 1; y++) {
                var neighbours = [];
                for (var lx = 0; lx < 3; lx++) {
                    neighbours.push([]);
                    for (var ly = 0; ly < 3; ly++) {
                        var ix = image.limitX(x + lx - 1);
                        var iy = image.limitY(y + ly - 1);
                        neighbours[lx][ly] = image.get(channel, ix, iy);
                    }
                }
                var newValue = computeCenter(neighbours);
                resultImage.set(channel, x, y, newValue);
            }
        }
    }
}