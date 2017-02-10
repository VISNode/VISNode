package visnode.executor;

import visnode.commons.Image;

/**
 * Input node representation
 */
public class InputNode implements Node {

    /** Input image */
    private final Image imagem;

    /**
     * Creates a new input node
     *
     * @param imagem
     */
    public InputNode(Image imagem) {
        this.imagem = imagem;
    }

    @Override
    public Object getAttribute(String attribute) {
        if (attribute.equals("image")) {
            return imagem;
        }
        return null;
    }

}
