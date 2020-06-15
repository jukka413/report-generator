package generator;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Column {

    List<String> title = new ArrayList<String>();
    List<String> width = new ArrayList<String>();
    {
        try {
            width = ReadXml.getValues("width","columns");
            title = ReadXml.getValues("title","columns");
            //System.out.println(width.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
