package generator;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadXml {
    private static String s;
    private static String fileName;

    public static void setFileName(String fileName) {
        ReadXml.fileName = fileName;
    }

    public static String getValue(String element, String parent) throws IOException, XMLStreamException {

        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(fileName)))) {
            while (processor.startElement(element, parent)) {
                 s = processor.getText();
            }
            return s;
        }
    }
    public static List<String> getValues(String element, String parent) throws IOException, XMLStreamException {
        List values = new ArrayList<String>();

        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get(fileName)))) {
            while (processor.startElement(element, parent)) {
                values.add(processor.getText());
            }
        }
        return values;
    }
}
