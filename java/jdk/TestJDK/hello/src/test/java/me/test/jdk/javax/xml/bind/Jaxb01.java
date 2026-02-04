package me.test.jdk.javax.xml.bind;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Jaxb01 {

    public static void main(String[] args) throws JAXBException {

        JAXBContext jc = JAXBContext.newInstance(User.class);

        // Xml -> JavaBean
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        User user = (User) unmarshaller.unmarshal(Jaxb01.class.getResourceAsStream("Jaxb01.xml"));

        // 可以在这里打断点，确认内容
        System.out.println(user);

        // JavaBean -> Xml
        System.out.println("============================= : JavaBean -> Xml");
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(user, System.out);
    }
}
