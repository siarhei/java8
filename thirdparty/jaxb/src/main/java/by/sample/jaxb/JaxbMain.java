package by.sample.jaxb;

import by.sample.dto.QuoteComparisonLayout;
import org.eclipse.persistence.jaxb.JAXBContextProperties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sshchahratsou
 */
public class JaxbMain {
    private static final String SOURCE = "quoteComparisonLayout.xml";

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        InputStream iStream = classLoader.getResourceAsStream("metadata/base-bindings.xml");
        Map<String, Object> properties = new HashMap<>();
        //properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, iStream);

        JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {QuoteComparisonLayout.class}, properties);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Marshaller marshaller = jaxbContext.createMarshaller();

        try (InputStream xmlInput = classLoader.getResourceAsStream(SOURCE)) {
            Object unmarshalled = unmarshaller.unmarshal(xmlInput);
            marshaller.marshal(unmarshalled, System.out);
        }
    }
}
