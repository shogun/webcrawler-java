package com.wiprodigital.webcrawler;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Application {

    private CommandLine cmd;

    private String url;

    private String outputFile = "sitemap.xml";

    private String tempDir = "tmp/";

    static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {

        Application application = new Application(args);
        application.run();

    }

    public Application(String args[]) {

        this.cmd = configureCLI(args);

        url = cmd.getOptionValue("url");

        if (cmd.getOptionValue("output-file") != null) {
            outputFile = cmd.getOptionValue("output-file");
        }

        if (cmd.getOptionValue("temp-dir") != null) {
            tempDir = cmd.getOptionValue("temp-dir");
        }
    }

    public void run() {
        try {

            Crawler crawler = getCrawler();

            Response response = crawler.crawl(url);

            XMLResponseTransformer transformer = new XMLResponseTransformer();
            Document xmlDocument = transformer.transform(response);

            saveXMLDocument(xmlDocument, outputFile);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void saveXMLDocument(Document document, String outputFile) throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(outputFile));
        transformer.transform(source, result);
        logger.info("document saved to: " + outputFile);

    }

    private Crawler getCrawler() {
        Crawler crawler = new Crawler(tempDir);

        if (cmd.getOptionValue("crawlers") != null) {
            crawler.setCrawlers(Integer.valueOf(cmd.getOptionValue("crawlers")));
        }

        if (cmd.getOptionValue("delay") != null) {
            crawler.setDelay(Integer.valueOf(cmd.getOptionValue("delay")));
        }

        if (cmd.getOptionValue("max-pages") != null) {
            crawler.setMaxPages(Integer.valueOf(cmd.getOptionValue("max-pages")));
        }
        return crawler;
    }

    private static CommandLine configureCLI(String[] args) {

        Options options = new Options();

        options.addOption(
                Option.builder("u")
                        .longOpt("url")
                        .required(true)
                        .hasArg(true)
                        .desc("website to crawl (http://example.com)")
                        .build()
        );

        options.addOption(
                Option.builder("o")
                        .longOpt("output-file")
                        .hasArg(true)
                        .desc("output file - by default: sitemap.xml")
                        .build()
        );

        options.addOption(
                Option.builder("c")
                        .longOpt("crawlers")
                        .hasArg(true)
                        .desc("number of crawlers - by default: 1")
                        .build()
        );

        options.addOption(
                Option.builder("m")
                        .longOpt("max-pages")
                        .hasArg(true)
                        .desc("max pages to fetch - by default: 500")
                        .build()
        );

        options.addOption(
                Option.builder("t")
                        .longOpt("temp-dir")
                        .hasArg(true)
                        .desc("temporary directory - by default: tmp/")
                        .build()
        );
        options.addOption(
                Option.builder("d")
                        .longOpt("delay")
                        .hasArg(true)
                        .desc("delay in ms - by default: 200ms")
                        .build()
        );

        CommandLineParser parser = new DefaultParser();

        try {
            return parser.parse(options, args);
        } catch (ParseException e) {

            System.out.println("Parsing failed.  Reason: " + e.getMessage());

            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("webcrawler", options, true);

            System.exit(1);
        }

        return null;
    }

}
