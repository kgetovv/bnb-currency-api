package net.pb.currency.bnb.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

@Slf4j
@NoArgsConstructor
public class JAXBUtils {

    public static <T> Optional<String> marshalToXml(T object) {
        try {
            JAXBContext queryContext = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = queryContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);

            return Optional.of(writer.toString());
        } catch (JAXBException e) {
            log.error("Error marshalling object to XML: ", e);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> unmarshalFromXml(String xml, Class<T> tClass) {
        try {
            JAXBContext contextQueryResponse = JAXBContext.newInstance(tClass);
            Unmarshaller unmarshaller = contextQueryResponse.createUnmarshaller();
            StringReader reader = new StringReader(xml.replace("\uFEFF", ""));

            return (Optional<T>) Optional.of(unmarshaller.unmarshal(reader));
        } catch (JAXBException e) {
            log.error("Error during XML unmarshalling: ", e);
            return Optional.empty();
        }
    }
}