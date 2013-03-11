/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author haofa.lu.sz
 */
public class XML_Operator {

    /**
     *
     * @param inStream
     * @throws Exception
     */
    public static NodeList XMLReader(InputStream inStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inStream);
            Element root = document.getDocumentElement();
            NodeList items = root.getElementsByTagName("msg");
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
