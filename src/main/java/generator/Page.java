package generator;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Page {
    int width;
    int height;
    {
        try {
            width = Integer.parseInt (ReadXml.getValue("width","page"));
            height = Integer.parseInt (ReadXml.getValue("height","page"));
            //System.out.println(width+" "+height);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
