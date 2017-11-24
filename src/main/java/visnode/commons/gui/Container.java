/*
 * TesteInterface
 * CopyRight Rech Informática Ltda. Todos os direitos reservados.
 */
package visnode.commons.gui;

/**
 * Container
 * 
 * @param <T>
 */
public interface Container<T extends Container> extends Component<T> {

    /**
     * Adds a component
     *
     * @param component
     */
    public void put(Component component);

    /**
     * Returns a component by it's id
     *
     * @param id
     * @return Component
     */
    public Component findId(String id);

}
