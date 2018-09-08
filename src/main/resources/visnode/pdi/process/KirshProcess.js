function process(image) {
    var resultImage = ImageFactory.buildEmptyImage(image);
    convolution(image, resultImage);
    return resultImage;
}

var masks = [
    [
        [5, 5, 5],
        [-3, 0, -3],
        [-3, -3, -3]

    ],
    [
        [-3, 5, 5],
        [-3, 0, 5],
        [-3, -3, -3]

    ],
    [
        [-3, -3, 5],
        [-3, 0, 5],
        [-3, -3, 5]

    ],
    [
        [-3, -3, -3],
        [-3, 0, 5],
        [-3, 5, 5]

    ],
    [
        [-3, -3, -3],
        [-3, 0, -3],
        [5, 5, 5]

    ],
    [
        [-3, -3, -3],
        [5, 0, -3],
        [5, 5, -3]

    ],
    [
        [5, -3, -3],
        [5, 0, -3],
        [5, -3, -3]
    ],
    [
        [5, 5, -3],
        [5, 0, -3],
        [-3, -3, -3]

    ]
];

function computePixel(image, channel, x, y) {
    var gradients = Array(masks.length);
    for (var lx = 0; lx < 3; lx++) {
        for (var ly = 0; ly < 3; ly++) {
            var valor = image.get(channel, x + lx - 1, y + ly - 1);
            for (var i = 0; i < gradients.length; i++) {
                if (!gradients[i]) {
                    gradients[i] = 0;
                }
                gradients[i] += valor * masks[i][lx][ly];
            }
        }
    }
    return processGradients(gradients) * 256 / 3840;
}

function getMaskWeights() {
    return [1, 1, 1, 1, 1, 1, 1, 1];
}

function processGradients(gradients) {
    var weights = getMaskWeights();
    var gradient = 0;
    for (var i = 0; i < gradients.length; i++) {
        gradient = Math.max(gradients[i] * weights[i], gradient);
    }
    return Math.floor(gradient);
}

function convolution(image, resultImage) {
    for (var channel = 0; channel < image.getChannelCount(); channel++) {
        for (var x = 1; x < image.getWidth() - 1; x++) {
            for (var y = 1; y < image.getHeight() - 1; y++) {
                var newValue = computePixel(image, channel, x, y);
                newValue = resultImage.getPixelValueRange().limit(newValue);
                resultImage.set(channel, x, y, newValue);
            }
        }
    }
}

