package visnode.application;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.NodeParameter;
import visnode.executor.ProcessNode;
import visnode.gui.EventHelper;
import visnode.gui.IconFactory;
import visnode.gui.JNode;
import visnode.gui.Selection;
import visnode.gui.UIHelper;

/**
 * View for the node
 */
public class NodeView extends JNode {

    /** Model */
    private final EditNodeDecorator model;
    /** Pop up menu */
    private JPopupMenu popup;

    /**
     * Creates the view
     *
     * @param model
     */
    public NodeView(EditNodeDecorator model) {
        super(model.getName());
        this.model = model;
        initGui();
        initEvents();
    }

    /**
     * Initializes the interface
     */
    private void initGui() {
        setBounds(model.getPosition().x, model.getPosition().y, 50, 50);
        addHierarchyListener((HierarchyEvent e) -> {
            SwingUtilities.invokeLater(this::revalidate);
        });
        EventHelper.addMoveListener(this, (ComponentEvent e) -> {
            model.setPosition(getLocation());
        });
        createConnectors();
        buildPopUpMenu();
    }

    /**
     * Creates the pop up menu
     */
    private void buildPopUpMenu() {
        popup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem(IconFactory.get().create("fa:trash"));
        Messages.get().message("delete").subscribe((msg) -> {
            menuItem.setText(msg);
        });
        menuItem.addActionListener((ev) -> {
            Selection<NodeView> selection = VISNode.get().getNetworkEditor().getSelection();
            List<EditNodeDecorator> nodes = selection.stream().
                    map((view) -> view.getModel()).
                    collect(Collectors.toList());
            VISNode.get().getModel().getNetwork().remove(nodes);
        });
        popup.add(menuItem);
    }

    /**
     * Initializes the events
     */
    private void initEvents() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (model.getDecorated() instanceof ProcessNode
                        && e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(NodeView.this, e.getX(), e.getY());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

    }

    /**
     * Creates the connector
     */
    private void createConnectors() {
        createConnectors(model);
    }

    /**
     * Creates the connectors
     *
     * @param node
     */
    private void createConnectors(Node node) {
        boolean first = true;
        for (NodeParameter inputParameter : node.getInputParameters()) {
            setupParameterConnector(node, inputParameter, ConnectionType.INPUT, first);
            first = false;
        }
        for (NodeParameter inputParameter : node.getOutputParameters()) {
            setupParameterConnector(node, inputParameter, ConnectionType.OUTPUT, first);
            first = false;
        }
    }

    /**
     * Setups the parameter connector
     *
     * @param node
     * @param parameter
     * @param type
     * @param first
     */
    private void setupParameterConnector(Node node, NodeParameter parameter, ConnectionType type, boolean first) {
        if (!first) {
            add(buildSeparator());
        }
        add(buildLabel(parameter.getName()));
        NodeConnectorView view = new NodeConnectorView(node, parameter, type);
        view.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        add(view);
    }

    /**
     * Builds a separator for the node parameters
     *
     * @return JComponent
     */
    private JComponent buildSeparator() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setBorder(new EmptyBorder(0, 0, 0, 25));
        separator.setForeground(UIHelper.getColor("Node.separetor"));
        separator.setBackground(UIHelper.getColor("Node.separetor"));
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(0, 10, 0, 10));
        container.add(separator);
        container.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        container.setOpaque(false);
        return container;
    }

    /**
     * Build the label for the parameter
     *
     * @param text
     * @return JComponent
     */
    private JComponent buildLabel(String text) {
        text = text.replaceAll("([a-z])([A-Z])", "$1 $2").toLowerCase();
        text = text.substring(0, 1).toUpperCase() + text.substring(1);
        JLabel label = new JLabel(text, JLabel.LEFT);
        label.setBorder(new EmptyBorder(0, 15, 0, 0));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        JPanel container = new JPanel(new BorderLayout(5, 0));
        container.add(label, BorderLayout.WEST);
        container.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        container.setOpaque(false);
        return container;
    }

    /**
     * Returns the model
     *
     * @return EditNodeDecorator
     */
    public EditNodeDecorator getModel() {
        return model;
    }

    /**
     * Returns a connector for the attribute
     *
     * @param attribute
     * @param type
     * @return NodeConnectorView
     */
    public NodeConnectorView getConnectorFor(String attribute, ConnectionType type) {
        for (int i = 0; i < getComponentCount(); i++) {
            Component component = getComponent(i);
            if (component instanceof NodeConnectorView) {
                NodeConnectorView connector = (NodeConnectorView) component;
                if (connector.getAttribute().equals(attribute) && connector.getType() == type) {
                    return connector;
                }
            }
        }
        return null;
    }

}
