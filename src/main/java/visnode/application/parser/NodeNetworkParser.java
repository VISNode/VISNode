package visnode.application.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import visnode.application.ExceptionHandler;
import visnode.application.NodeNetwork;
import visnode.commons.ImageFactory;
import visnode.executor.EditNodeDecorator;
import visnode.executor.InputNode;
import visnode.executor.Node;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;

/**
 * Node network parser
 */
public class NodeNetworkParser {

    /** Parser */
    private final Gson gson;

    public NodeNetworkParser() {
        this.gson = new GsonBuilder().
                create();
    }

    /**
     * Parser to JSON
     *
     * @param network
     * @return String
     */
    public String toJson(NodeNetwork network) {
        Map<String, Object> data = new HashMap<>();
        List<Map> nodes = new ArrayList<>();
        network.getNodes().stream().filter((n) -> {
            return n.getDecorated() instanceof ProcessNode;
        }).forEach((node) -> {
            Map<String, Object> param = new HashMap<>();
            ProcessNode processNode = (ProcessNode) node.getDecorated();
            List input = node.getInputParameters().stream().filter((p) -> {
                return processNode.getConnector().getConnection(p.getName()) == null;
            }).map((t) -> {
                Map<String, Object> inputParam = new HashMap<>();
                inputParam.put("parameterName", t.getName());
                inputParam.put("parameterType", t.getType().getName());
                inputParam.put("value", node.getInput(t.getName()));
                return inputParam;
            }).collect(Collectors.toList());
            List connections = node.getConnector().getConnections().values().stream().map((c) -> {
                Map<String, Object> conn = new HashMap<>();
                conn.put("leftNode", c.getLeftNode().hashCode());
                conn.put("leftAttribute", c.getLeftAttribute());
                conn.put("rightNode", c.getRightNode().hashCode());
                conn.put("rightAttribute", c.getRightAttribute());
                return conn;
            }).collect(Collectors.toList());
            param.put("position", node.getPosition());
            param.put("processType", processNode.getProcessType().getName());
            param.put("input", input);
            param.put("connections", connections);
            param.put("hashCode", System.identityHashCode(node.getDecorated()));
            nodes.add(param);
        });
        data.put("nodes", nodes);
        data.put("input", System.identityHashCode(network.getNodes().get(network.getInputIndex()).getDecorated()));
        data.put("output", System.identityHashCode(network.getNodes().get(network.getOutputIndex()).getDecorated()));
        return gson.toJson(data);
    }

    /**
     * Parser from JSON
     *
     * @param json
     * @return NodeNetwork
     */
    public NodeNetwork fromJson(String json) {
        NodeNetwork network = new NodeNetwork();
        Map data = gson.fromJson(json, Map.class);
        try {
            Map<String, Node> mapHashCode = new HashMap<>();
            BufferedImage r = ImageIO.read(new File(getClass().getResource("/lena.jpg").getFile()));
            InputNode input = new InputNode(ImageFactory.buildRGBImage(r));
            OutputNode out = new OutputNode();
            mapHashCode.put(data.get("input").toString(), input);
            mapHashCode.put(data.get("input").toString(), out);
            // Define input/output node
            network.add(new EditNodeDecorator(input, new Point(10, 50)));
            network.add(new EditNodeDecorator(out, new Point(600, 50)));
            // Define nodes hashCodes
            List<Map> nodes = (List) data.get("nodes");
            nodes.forEach((node) -> {
                try {
                    ProcessNode processNode = new ProcessNode(Class.forName(node.get("processType").toString()));
                    mapHashCode.put(node.get("hashCode").toString(), processNode);
                } catch (ClassNotFoundException ex) {
                    ExceptionHandler.get().handle(ex);
                }
            });
            // Define node connections/parameters
            nodes.forEach((node) -> {
                ProcessNode processNode = (ProcessNode) mapHashCode.get(node.get("hashCode").toString());
//                List<Map> connections = (List) node.get("connections");
//                connections.forEach((c) -> {
//                    String attr = c.get("rightAttribute").toString();
//                    processNode.addConnection(attr, mapHashCode.get(c.get("rightNode").toString()), attr);
//                });
                Point point = new Point();
                Map position = (Map) node.get("position");
                point.setLocation(Float.parseFloat(position.get("x").toString()), Float.parseFloat(position.get("y").toString()));                
                network.add(new EditNodeDecorator(processNode, point));
            });
            
        } catch (IOException e) {
            ExceptionHandler.get().handle(e);
        }
        return network;
    }

}
