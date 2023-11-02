package shacl;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileUtils;
import org.junit.Test;
import org.topbraid.jenax.util.JenaUtil;
import org.topbraid.shacl.util.ModelPrinter;
import org.topbraid.shacl.validation.ValidationUtil;

import java.io.InputStream;

public class ShaclTest {

    @Test
    public void testShacl() {

        // Load the main data model
        Model dataModel = JenaUtil.createMemoryModel();
        InputStream stream = ShaclTest.class.getResourceAsStream("/transfer-start-message.json");
        dataModel.read(stream, "urn:dummy", "JSON-LD");

        Model shapesModel = JenaUtil.createMemoryModel();
        dataModel.read(ShaclTest.class.getResourceAsStream("/transfer-start-message-shape.ttl"), "urn:dummy", FileUtils.langTurtle);


        // Perform the validation of everything, using the data model
        // also as the shapes model - you may have them separated
        Resource report = ValidationUtil.validateModel(dataModel, shapesModel, true);

        // Print violations
        System.out.println(ModelPrinter.get().print(report.getModel()));
    }
}
