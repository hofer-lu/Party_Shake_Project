/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hofer.partyshake;

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
    public static Element XMLReader(InputStream inStream) {
        ArrayList persons = new ArrayList();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inStream);
            Element root = document.getDocumentElement();
            return root;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
