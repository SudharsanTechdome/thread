package Saral.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonUtils {

    private static final String JSON_PATH =
            "src/test/resources/resources/test-data/testdata.json"; // TODO: read this url from
    // config.properties

    public static JsonNode getJsonData() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonData = null;
        try {
            jsonData = objectMapper.readTree(new File(JSON_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public static Map<String, String> getTestData(String key) {
        JsonNode jsonData = getJsonData();
        JsonNode testDataNode = jsonData.get(key);

        Iterator<Map.Entry<String, JsonNode>> fields = testDataNode.fields();
        Map<String, String> testData = new HashMap<>();

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            testData.put(entry.getKey(), entry.getValue().asText());
        }

        return testData;
    }
}