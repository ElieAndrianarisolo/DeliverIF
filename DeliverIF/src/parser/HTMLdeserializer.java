package parser;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.Courier;
import model.DeliveryRequest;
import model.RoadSegment;
import model.Tour;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This class provides methods for deserializing a Tour into a road map HTML page.
 *
 * @author H4114
 */
public class HTMLdeserializer {


    /**
     * Constructor for HTMLSerializer class
     */
    public HTMLdeserializer() {

    }

    /**
     * Transform a tour into a HTML road map file.
     *
     * @param filePath          The path where the HTML file will be saved.
     * @param listTours 		The Tours to be transformed into a road map file.
     */
    public void createRoadMap(String filePath, LinkedHashMap<Courier, Tour> listTours, JPanel leftPanel) throws IOException, ExceptionHTML {

        String imgFilePath = "map";
        
        // Get the directory path without the file name
        int lastSeparatorIndex = filePath.lastIndexOf("\\");
        if (lastSeparatorIndex != -1) {
        	
            String directoryPath = filePath.substring(0, lastSeparatorIndex + 1); // Include the separator
            
            // Get the file name without extension
            String fileName = filePath.substring(lastSeparatorIndex + 1, filePath.lastIndexOf("."));
            
            // Get the extension of the original file 
            String extension = "";
            int extensionIndex = filePath.lastIndexOf(".");
            if (extensionIndex != -1) {
                extension = filePath.substring(extensionIndex);
            }
            
            // Create the modified path with the new file name, original file name, and extension
            imgFilePath = directoryPath + imgFilePath + "_" + fileName + ".jpeg";
            
        }

        StringBuilder navigation = new StringBuilder("<h2>Navigation : </h2><br/>");
        int lastDotIndex = filePath.lastIndexOf(".");
        
        listTours.forEach((courier, tour) -> {
        	
            if(!tour.getListDeliveryRequests().isEmpty()) {
            	
	            String filePathPerCourier = filePath.substring(0, lastDotIndex) + "_" + courier.getFirstName() + ".html";
	            navigation.append("<li style=\"list-style:none\"><span style=\"color: rgb("+courier.getColor().getRed()+", "+courier.getColor().getGreen()+", "+courier.getColor().getBlue()+");\">●</span><a href=\"").append(filePathPerCourier).append("\">").append(courier.getFirstName()).append("</a></li>");
	            
            }
            
        });

        for (Map.Entry<Courier, Tour> tourEntry : listTours.entrySet()) {
        	
        	
            String filePathPerCourier = filePath.substring(0, lastDotIndex) + "_" + tourEntry.getKey().getFirstName() + ".html";
            
            Tour tour = tourEntry.getValue();
            if (tour != null && tour.getCourier() != null && tour.getListDeliveryRequests() != null &&
                    tour.getLength() != 0.0 && tour.getListRoadSegments() != null && tour.getStartTime() != null && tour.getEndTime() != null
                    && !tour.getListDeliveryRequests().isEmpty() && !tour.getListRoadSegments().isEmpty()) {

                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
                List<RoadSegment> roads = tour.getListRoadSegments();
                List<DeliveryRequest> deliveries = tour.getListDeliveryRequests();
                int deliveryNumber = 0;

                StringBuilder roadMapContent = new StringBuilder("<!DOCTYPE html>");

                // Style
                roadMapContent.append("<style>h1{text-align: center;font-family: \"Oswald\";}h2{font-family: \"Oswald\";}body{margin-left: 10%;margin-right: 10%;font-family: \"Open Sans\";}ul{list-style-type: circle;}</style>");

                // Head
                roadMapContent.append("<head><title>RoadMap</title></head>");

                // Body
                roadMapContent.append("<body>");

                roadMapContent.append("<h1>").append(tour.getCourier().getFirstName()).append("'s roadmap</h1>");

                // Navigation
                roadMapContent.append(navigation);

                // General information
                roadMapContent.append("<h2>Information</h2>");
                String tourLengthString = String.valueOf(Math.round(tour.getLength()));
                String nbDeliveriesString = String.valueOf(tour.getListDeliveryRequests().size());
                String startTimeString = tour.getStartTime().format(timeFormat);
                String endTimeString = tour.getEndTime().format(timeFormat);
                String tempDirection = "";

                roadMapContent.append("<p>This tour of <b>").append(tourLengthString).append("</b> meters contains <b>").append(nbDeliveriesString).append("</b> deliveries, Starting at <b>").append(startTimeString).append("</b> Finishing at <b>").append(endTimeString).append("</b></p>");
                roadMapContent.append("<img src=\"" + imgFilePath + "\" alt=\"Map\"/>");
                roadMapContent.append("<h2>Instructions</h2>");
                roadMapContent.append("<p id=\"warehouse\">").append(startTimeString).append(" : leave the Warehouse</p>");
                roadMapContent.append("<br>");

                roadMapContent.append("<ul id=\"segments\">");
                LocalTime arrivingAtDeliveryPoint;
                LocalTime waitingAtDeliveryPoint;

                Double longitudePreviousPoint = null, latitudePreviousPoint = null;
                Double longitudeCurrentPoint, latitudeCurrentPoint;
                Double longitudeFuturePoint, latitudeFuturePoint;

                Double x1, x2, y1, y2, z;

                RoadSegment previousRoad = null;
                double previousLength = 0;

                for (RoadSegment road : roads) {
                	
                    // Road indication
                    longitudeCurrentPoint = road.getStartingIntersection().getLongitude();
                    latitudeCurrentPoint = road.getStartingIntersection().getLatitude();
                    longitudeFuturePoint = road.getEndingIntersection().getLongitude();
                    latitudeFuturePoint = road.getEndingIntersection().getLatitude();

                    if (longitudePreviousPoint != null && latitudePreviousPoint != null) {

                        //  Coordinates vector of the current road
                        x1 = longitudeCurrentPoint - longitudePreviousPoint;
                        y1 = latitudeCurrentPoint - latitudePreviousPoint;

                        // Coordinates vector of the road we want to cross
                        x2 = longitudeFuturePoint - longitudeCurrentPoint;
                        y2 = latitudeFuturePoint - latitudeCurrentPoint;

                        // Vector product
                        z = x1 * y2 - x2 * y1;

                        if (z > 0) {

                            tempDirection = "turn left on ";

                        } else if (z < 0) {

                            tempDirection = "turn right on ";

                        } else {

                            tempDirection = "continue straight on ";

                        }

                    } else {

                        tempDirection = "take ";

                    }

                    longitudePreviousPoint = longitudeCurrentPoint;
                    latitudePreviousPoint = latitudeCurrentPoint;

                    // Delivery indication
                    if ((deliveryNumber < tour.getListDeliveryRequests().size()) && (road.getStartingIntersection().getIdIntersection().toString().equals(deliveries.get(deliveryNumber).getIdIntersection().toString()))) {
                        
                    	roadMapContent.append("<li>");
                        roadMapContent.append(tempDirection).append(road.getStreetName()).append(" (").append(Math.round(previousLength)).append(" m)</li>");
                        previousLength = 0;
                        previousLength += road.getLength();

                        roadMapContent.append("</ul> <br>");
                        arrivingAtDeliveryPoint = deliveries.get(deliveryNumber).getArrivalTime();
                        waitingAtDeliveryPoint = deliveries.get(deliveryNumber).getWaitingTime();
                        roadMapContent.append("<p id=\"delivery\">").append(arrivingAtDeliveryPoint.format(timeFormat)).append(" : arrive at Delivery point n°").append(String.valueOf(deliveryNumber + 1));

                        if (waitingAtDeliveryPoint != LocalTime.ofSecondOfDay(0)) {

                            roadMapContent.append("<br>Wait for ").append(waitingAtDeliveryPoint.getMinute()).append(" minutes");

                        }

                        roadMapContent.append("<br>Deliver the package </p>");
                        roadMapContent.append("<br> <ul id=\"segments\">");
                        deliveryNumber++;

                    } else {
                        
                        if (previousRoad != null && previousRoad.getStreetName().equals(road.getStreetName())) {
                        	
                            previousLength += road.getLength();
                            
                        } else {
                        	
                            roadMapContent.append("<li>");
                            roadMapContent.append(tempDirection).append(road.getStreetName()).append(" (").append(Math.round(previousLength)).append(" m)</li>");
                            previousLength = 0;
                            previousLength += road.getLength();
                            
                        }
                        
                    }

                    previousRoad = road;
                    
                }

                roadMapContent.append("</ul> <br>");
                roadMapContent.append("<p id=\"warehouse\">").append(endTimeString).append(" : return to the Warehouse</p>");
                roadMapContent.append("</body>");
                roadMapContent.append("</html>");

                try {
                	
                    Files.writeString(Paths.get(filePathPerCourier), roadMapContent.toString(), StandardCharsets.UTF_8);
                    savePanelToImg(leftPanel, imgFilePath);
                    
                } catch (IOException e) {

                    throw new ExceptionHTML(e.getMessage());

                }

            }
            
        }

    }

    /**
     * Save a JPanel to an image.
     *
     * @param leftPanel
     * @param savePath
     */
    public void savePanelToImg(JPanel leftPanel, String savePath) {
    	
        BufferedImage imagebuf = null;
        
        try {
            imagebuf = new Robot().createScreenCapture(leftPanel.bounds());
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        
        Graphics2D graphics2D = imagebuf.createGraphics();
        leftPanel.paint(graphics2D);
        
        try {
            ImageIO.write(imagebuf, "jpeg", new File(savePath));
        } catch (Exception e) {
            return;
        }
        
    }
    
}
