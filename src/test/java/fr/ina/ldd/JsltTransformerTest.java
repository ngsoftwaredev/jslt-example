package fr.ina.ldd;

import static org.junit.Assert.assertEquals;

import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JsltTransformerTest {

    public static final JsltTransformer jslt = new JsltTransformer();

    public static final String ADAJE_PEOPLE_TEMPLATE_ID = "adaje-people";

    @BeforeClass
    public static final void compileTemplate() throws IOException {
        jslt.register(ADAJE_PEOPLE_TEMPLATE_ID, IOUtils.resourceToString("/personne.jslt", StandardCharsets.UTF_8));
    }

    @Test
    public final void testAdajePeople() throws IOException {
        assertEquals(
                IOUtils.resourceToString("/output.json", StandardCharsets.UTF_8),
                jslt.apply(ADAJE_PEOPLE_TEMPLATE_ID, IOUtils.resourceToString("/input.json", StandardCharsets.UTF_8))
        );
    }
}
