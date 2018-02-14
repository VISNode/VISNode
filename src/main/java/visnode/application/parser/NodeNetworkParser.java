package visnode.application.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.awt.Point;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import visnode.application.ExceptionHandler;
import visnode.application.NodeNetwork;
import visnode.executor.EditNodeDecorator;
import visnode.executor.Node;
import visnode.executor.OutputNode;
import visnode.executor.ProcessNode;
import visnode.pdi.process.ImageInput;

/**
 * Node network parser
 */
public class NodeNetworkParser {

    /** Parser */
    private final Gson gson;

    public NodeNetworkParser() {
        this.gson = createBareGsonBuilder()
                .registerTypeHierarchyAdapter(ImageInput.class, new InterfaceAdapter<>())
                .create();
    }
    
    /**
     * Creates a bare GSon builder
     * 
     * @return GsonBuilder
     */
    private GsonBuilder createBareGsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapter(File.class, new FileAdapter());
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
        int output = network.getOutputIndex();
        if (output >= 0) {
            data.put("output", toJson(network.getNodes().get(output)));
        }
        return gson.toJson(data);
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
            conn.put("rightAttribute", c.getRightAttribute());
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
            OutputNode out = new OutputNode();
            Map outputMap = (Map) data.get("output");
            if (outputMap != null) {
                mapHashCode.put(outputMap.get("hashCode").toString(), out);
                network.add(new EditNodeDecorator(out, fromJson(outputMap.get("position"))));
            }
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
                    String leftAttr = c.get("leftAttribute").toString();
                    String rightAttr = c.get("rightAttribute").toString();
                    processNode.addConnection(rightAttr, mapHashCode.get(c.get("leftNode").toString()), leftAttr);
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
            if (outputMap != null) {
                // Define output connection
                List<Map> connections = (List) outputMap.get("connections");
                connections.forEach((c) -> {
                    String attr = c.get("leftAttribute").toString();
                    String attrRight = c.get("rightAttribute").toString();
                    out.addConnection(attrRight, mapHashCode.get(c.get("leftNode").toString()), attr);
                });
            }
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

    /**
     * Adapter for serializing interfaces
     * 
     * @param <T> 
     */
    public class InterfaceAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {

        @Override
        public JsonElement serialize(T object, Type interfaceType, JsonSerializationContext context) {
            final JsonObject wrapper = new JsonObject();
            wrapper.addProperty("type", object.getClass().getName());
            wrapper.add("data", createBareGsonBuilder().create().toJsonTree(object));
            return wrapper;
        }

        @Override
        public T deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
            final JsonObject wrapper = (JsonObject) elem;
            final JsonElement typeName = get(wrapper, "type");
            final JsonElement data = get(wrapper, "data");
            final Type actualType = typeForName(typeName);
            return createBareGsonBuilder().create().fromJson(data, actualType);
        }

        private Type typeForName(final JsonElement typeElem) {
            try {
                return Class.forName(typeElem.getAsString());
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        private JsonElement get(final JsonObject wrapper, String memberName) {
            final JsonElement elem = wrapper.get(memberName);
            if (elem == null) {
                throw new JsonParseException("no '" + memberName + "' member found in what was expected to be an interface wrapper: " + wrapper.toString());
            }
            return elem;
        }

    }

}
