package com.wiprodigital.webcrawler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLResponseTransformer {

    public Document transform(Response response) throws ParserConfigurationException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("response");

        Element el;

        el = doc.createElement("pageLinks");
        for (String link : response.pageLinks) {
            el.appendChild(buildSitemapXmlItem(doc, link));
        }
        root.appendChild(el);

        el = doc.createElement("madiaLinks");
        for (String link : response.madiaLinks) {
            el.appendChild(buildSitemapXmlItem(doc, link));
        }
        root.appendChild(el);

        el = doc.createElement("externalLinks");
        for (String link : response.externalLinks) {
            el.appendChild(buildSitemapXmlItem(doc, link));
        }
        root.appendChild(el);


        doc.appendChild(root);
        return doc;
    }

    private Element buildSitemapXmlItem(Document doc, String link) {
        Element url = doc.createElement("url");
        url.setTextContent(link);
        return url;
    }
}
