package com.wiprodigital.webcrawler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.NavigableSet;

public class XMLResponseTransformer {

    public Document transform(Response response) throws ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("response");

        root.appendChild(
                buildXmlUrlList(doc, response.pageLinks, "pageLinks")
        );

        root.appendChild(
                buildXmlUrlList(doc, response.mediaLinks, "mediaLinks")
        );

        root.appendChild(
                buildXmlUrlList(doc, response.externalLinks, "externalLinks")
        );


        doc.appendChild(root);
        return doc;
    }

    private Element buildXmlUrlList(Document doc, NavigableSet<String> list, String name) {

        Element el = doc.createElement(name);
        for (String link : list) {
            el.appendChild(buildUrlXmlItem(doc, link));
        }

        return el;
    }

    private Element buildUrlXmlItem(Document doc, String link) {
        Element url = doc.createElement("url");
        url.setTextContent(link);
        return url;
    }
}
