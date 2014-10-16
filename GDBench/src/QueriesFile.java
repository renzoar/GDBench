/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class QueriesFile {
    
    public static Document doc = null;

    public static boolean open(String file) {
        try {
            File fXmlFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ArrayList<String> getQuery(String Q) {
        ArrayList<String> querys = new ArrayList<String>();

        NodeList nList = doc.getElementsByTagName(Q);
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Element eElement = (Element) nList.item(temp);
            NodeList nNode = eElement.getElementsByTagName("INPUT");
            querys.add(nNode.item(0).getTextContent());
        }

        return querys;
    }    
    
}
