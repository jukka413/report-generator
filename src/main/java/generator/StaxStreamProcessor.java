package generator;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;

public class StaxStreamProcessor implements AutoCloseable{
    private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();
    private static int go = 0;

    private final XMLStreamReader reader;

    public StaxStreamProcessor(InputStream is) throws XMLStreamException {
        reader = FACTORY.createXMLStreamReader(is);
    }

    public XMLStreamReader getReader() {
        return reader;
    }

    public boolean doUntil(int stopEvent, String value) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == stopEvent && value.equals(reader.getLocalName())) {
                return true;
            }
        }
        return false;
    }

    public boolean startElement(String element, String parent) throws XMLStreamException {
        while (reader.hasNext()) {
            int event = reader.next();

            if (event == XMLEvent.START_ELEMENT && parent.equals(reader.getLocalName())){
                go=1;
            }
            //if (event == XMLEvent.START_ELEMENT && parent.equals(reader.getLocalName())){
            if (parent != null && event == XMLEvent.END_ELEMENT &&
                    parent.equals(reader.getLocalName())) {
                go=0;
                //System.out.println(reader.getLocalName() + " " + event + " ");
                return false;
            }
            if (event == XMLEvent.START_ELEMENT &&
                    element.equals(reader.getLocalName())&&go==1) {
                //System.out.println(reader.getLocalName() + " " + event + " "+go);
                return true;
            }

        }
        return false;
    }

    public String getText() throws XMLStreamException {
        return reader.getElementText();
    }

    @Override
    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (XMLStreamException e) { // empty
            }
        }
    }
}
