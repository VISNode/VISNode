package visnode.application.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import visnode.application.ExceptionHandler;
import visnode.application.NodeNetwork;
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
            nodes.add(toJson(node));
        });
        data.put("nodes", nodes);
        data.put("input", toJsonInput(network.getNodes().get(network.getInputIndex())));
        data.put("output", toJson(network.getNodes().get(network.getOutputIndex())));
        return gson.toJson(data);
    }

    /**
     * Parser the input node
     * 
     * @param node
     * @return Map
     */
    private Map toJsonInput(EditNodeDecorator node) {
        Map map = toJson(node);
        File file = ((InputNode) node.getDecorated()).getFile();
        if (file != null) {
            map.put("file", file.getPath());
        }
        return map;
    }

    /**
     * Parser edit node
     *
     * @param node
     * @return Map
     */
    private Map toJson(EditNodeDecorator node) {
        Map<String, Object> param = new HashMap<>();
        Node nodeDecorated = (Node) node.getDecorated();
        List input = node.getInputParameters().stream().filter((p) -> {
            return nodeDecorated.getConnector().getConnection(p.getName()) == null;
        }).map((t) -> {
            Map<String, Object> inputParam = new HashMap<>();
            inputParam.put("parameterName", t.getName());
            inputParam.put("parameterType", t.getType().getName());
            inputParam.put("value", node.getInput(t.getName()));
            return inputParam;
        }).collect(Collectors.toList());
        List connections = node.getConnector().getConnections().values().stream().map((c) -> {
            Map<String, Object> conn = new HashMap<>();
            Node n = c.getLeftNode();
            if (n instanceof EditNodeDecorator) {
                n = ((EditNodeDecorator) n).getDecorated();
            }
            conn.put("leftNode", System.identityHashCode(n));
            conn.put("leftAttribute", c.getLeftAttribute());
            return conn;
        }).collect(Collectors.toList());
        param.put("input", input);
        param.put("position", node.getPosition());
        param.put("connections", connections);
        param.put("hashCode", System.identityHashCode(node.getDecorated()));
        if (nodeDecorated instanceof ProcessNode) {
            param.put("processType", ((ProcessNode) nodeDecorated).getProcessType().getName());
        }
        return param;
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
            InputNode input = new InputNode();
            OutputNode out = new OutputNode();
            Map inputMap = (Map) data.get("input");
            Map outputMap = (Map) data.get("output");
            mapHashCode.put(inputMap.get("hashCode").toString(), input);
            mapHashCode.put(outputMap.get("hashCode").toString(), out);
            // The input node has a file
            if (inputMap.get("file") != null) {
                input.setFile(new File(inputMap.get("file").toString()));
            }
            // Define input/output node
            network.add(new EditNodeDecorator(input, fromJson(inputMap.get("position"))));
            network.add(new EditNodeDecorator(out, fromJson(outputMap.get("position"))));
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
                List<Map> connections = (List) node.get("connections");
                connections.forEach((c) -> {
                    String attr = c.get("leftAttribute").toString();
                    processNode.addConnection(attr, mapHashCode.get(c.get("leftNode").toString()), attr);
                });
                List<Map> inputList = (List) node.get("input");
                inputList.forEach((c) -> {
                    try {
                        String attr = c.get("parameterName").toString();
                        Object obj = null;
                        if (c.get("value") != null) {
                            obj = gson.fromJson(gson.toJson(c.get("value")), Class.forName(c.get("parameterType").toString()));
                        }
                        processNode.setInput(attr, obj);
                    } catch (ClassNotFoundException ex) {
                        ExceptionHandler.get().handle(ex);
                    }
                });
                network.add(new EditNodeDecorator(processNode, fromJson(node.get("position"))));
            });
            // Define output connection
            List<Map> connections = (List) outputMap.get("connections");
            connections.forEach((c) -> {
                String attr = c.get("leftAttribute").toString();
                out.addConnection(attr, mapHashCode.get(c.get("leftNode").toString()), attr);
            });
        } catch (Exception e) {
            ExceptionHandler.get().handle(e);
        }
        return network;
    }

    /**
     * From json point
     *
     * @param objPosition
     * @return Point
     */
    private Point fromJson(Object objPosition) {
        Map position = (Map) objPosition;
        Point point = new Point();
        point.setLocation(Float.parseFloat(position.get("x").toString()), Float.parseFloat(position.get("y").toString()));
        return point;
    }

}
