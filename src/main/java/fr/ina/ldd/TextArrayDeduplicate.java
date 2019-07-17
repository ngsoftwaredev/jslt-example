package fr.ina.ldd;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.TextNode;
import com.schibsted.spt.data.jslt.Function;

import java.util.LinkedHashSet;

public class TextArrayDeduplicate implements Function {

    @Override
    public String getName() {
        return "text-array-deduplicate";
    }

    @Override
    public int getMinArguments() {
        return 1;
    }

    @Override
    public int getMaxArguments() {
        return 1;
    }

    @Override
    public JsonNode call(JsonNode jsonNode, JsonNode[] jsonNodes) {
        final JsonNode value = jsonNodes[0];
        if (value == null) {
            return null;
        }

        if (!value.isArray()) {
            throw new IllegalArgumentException("Parameter 1 should be an array");
        }

        final ArrayNode values = (ArrayNode) value;
        final LinkedHashSet<String> dedup = new LinkedHashSet(values.size());
        values.forEach(s -> dedup.add(s.asText()));

        final ArrayNode result = new ArrayNode(JsonNodeFactory.instance, dedup.size());
        dedup.forEach(s -> result.add(new TextNode(s)));
        return result;
    }

}
