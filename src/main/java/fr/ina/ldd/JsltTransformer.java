package fr.ina.ldd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.schibsted.spt.data.jslt.Expression;
import com.schibsted.spt.data.jslt.Function;
import com.schibsted.spt.data.jslt.Parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JsltTransformer {

    private static final List<Function> FUNCTIONS = Arrays.asList(
            new TextArrayDeduplicate()
    );

    private final ObjectMapper mapper;

    private Map<String, Expression> templates = new HashMap<>();

    public JsltTransformer() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public boolean isRegistered(final String templateId) {
        return templates.containsKey(templateId);
    }

    public void register(final String id, final String template) {
        templates.put(id, Parser.compileString(template, FUNCTIONS));
    }

    public String apply(final String id, final String json) throws IOException {
        if (!templates.containsKey(id)) {
            throw new IllegalArgumentException(String.format("Template %s is not defined", id));
        }
        return mapper.writeValueAsString(templates.get(id).apply(mapper.readTree(json)));
    }

}
