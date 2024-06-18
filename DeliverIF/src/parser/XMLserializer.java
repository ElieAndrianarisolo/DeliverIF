package parser;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.List;

import model.*;

/**
 * This class provides methods for serializing delivery requests to XML format.
 * @author H4114
 */
public class XMLserializer {
    
    /**
     * Constructor for XMLserializer class.
     */
    public XMLserializer(){
        
    }
    
    /**
     * Saves a list of delivery requests to an XML file.
     *
     * @param filePath 						The path where the XML file will be saved.
     * @param listDeliveryRequests 			The list of delivery requests to be serialized.
     * @throws TransformerException 		If an error occurs during XML transformation.
     * @throws ParserConfigurationException If an error occurs during XML parsing configuration.
     */
    public void saveDeliveryRequestsToFile(String filePath, List<Intersection> listDeliveryRequests) throws TransformerException, ParserConfigurationException {
        
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("deliveryRequests");
        doc.appendChild(rootElement);
        
        if(listDeliveryRequests.get(0) instanceof Warehouse) {
            
            Warehouse warehouse = (Warehouse) listDeliveryRequests.get(0);
            Element warehouseElement = doc.createElement("warehouse");
            warehouseElement.setAttribute("idIntersection", Long.toString(warehouse.getIdIntersection()));
            rootElement.appendChild(warehouseElement);
            
        }
        
        for (Intersection deliveryRequest : listDeliveryRequests) {
            
            if(deliveryRequest instanceof DeliveryRequest) {
                
                Element deliveryRequestElement = doc.createElement("deliveryRequest");
                deliveryRequestElement.setAttribute("idIntersection", Long.toString(deliveryRequest.getIdIntersection()));
                deliveryRequestElement.setAttribute("timeWindow", Integer.toString(((DeliveryRequest) deliveryRequest).getTimeWindow()));
                rootElement.appendChild(deliveryRequestElement);
                
            }
            
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filePath));
        transformer.transform(source, result);
        
    }
    
}
