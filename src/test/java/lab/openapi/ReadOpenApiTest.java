package lab.openapi;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.parser.OpenAPIParser;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.parser.ObjectMapperFactory;
import io.swagger.v3.parser.core.models.SwaggerParseResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ReadOpenApiTest {

  @Test
  public void read() {
    // parse a swagger description from the petstore and get the result
    SwaggerParseResult result = new OpenAPIParser()
        .readLocation("https://petstore3.swagger.io/api/v3/openapi.json", null, null);

    // the parsed POJO
    OpenAPI openAPI = result.getOpenAPI();

    if (result.getMessages() != null)
      result.getMessages().forEach(System.err::println); // validation errors and warnings

    Assertions.assertNotNull(openAPI);
  }

  @Test
  public void write() throws JsonProcessingException {

    ObjectMapper objectMapper = ObjectMapperFactory.createYaml();
    Schema<String> schema = new Schema<>();
    schema.setType("object");
    schema.required(Arrays.asList("id","name"));

    Schema<String> idSchema = new Schema<>();
    idSchema.setType("integer");
    idSchema.format(AnnotationsUtils.resolveSchemaFromType(ReadSpec.class,null,ReadSpec.class.getAnnotation(JsonView.class)).getFormat());
    schema.addProperties("id",idSchema);

    PrimitiveType.fromName("string");

    System.out.println(objectMapper.writeValueAsString(schema));
  }
}
