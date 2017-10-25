/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visnode.application;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Version finder
 */
public class VersionFinder {
    
    /**
     * Returns the version
     * 
     * @return String
     */
    public static String getVersion() {
        String jarImplementation = VISNode.class.getPackage().getImplementationVersion();
        if (jarImplementation != null) {
            return jarImplementation;
        }
        String file = VISNode.class.getResource(".").getFile();
        String pack = VISNode.class.getPackage().getName().replace(".", "/") + '/';
        File pomXml = new File(file.replace("target/classes/" + pack, "pom.xml"));
        if (pomXml.exists()) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new FileReader(pomXml)));
                XPath xPath = XPathFactory.newInstance().newXPath();
                return (String) xPath.evaluate("/project/version", document, XPathConstants.STRING);
            } catch (IOException | ParserConfigurationException | XPathExpressionException | SAXException e) { }
        }
        return "Unknown version";
    }
    
}
