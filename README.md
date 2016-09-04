# Web crawler

## Task

 Web Crawler
Your task is to write a simple web crawler in a language of your choice.
The crawler should:

- be limited to one domain. Given a starting URL – say wiprodigital.com 
- it should visit all pages within the domain, but not follow the links to external sites such as Google or Twitter.

The output should be a simple site map, showing links to other pages under the same domain, links to static content such as images, and to external URLs.
We would like to see what you can produce in a couple of hours – please don’t spend much more than that. In addition, please

- ensure that what you do implement is production quality code
- briefly describe any tradeoffs you make through comments and / or in a README file
- include the steps needed to build and run your solution
   
Once done, please make your solution available on Github and forward the link with brief instruction on how to run it

## Description
This project is using **crawler4j** internally to crawl pages.

Main tradeoffs:

- crawler4j requires temp directory to store data. 
- It has limit set to 500 pages. It can be changed by setting --max-pages param
- No support for www sub-domains it will treat it as external url
- it is not build as one jar because I had some problems with signed dependent jars

## Setup
    git $ clone https://github.com/shogun/webcrawler-java.git
    $ cd webcrawler-java/
    $ mvn package

## Usage

Run:

    $ java -jar target/web-crawler-1.0-SNAPSHOT.jar

Mandatory parameter:

    -u,--url <arg>           website url
    
Optional parameters:

    -c,--crawlers <arg>      number of crawlers (1 by default)
    -d,--delay <arg>         delay in ms (200 by default)
    -m,--max-pages <arg>     max pages to fetch
    -o,--output-file <arg>   output file by default sitemap.xml
    -t,--temp-dir <arg>      temporary directory
    
    
Example:

    $ java -jar target/web-crawler-1.0-SNAPSHOT.jar --url="http://example.com"


## Example output XML file format

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<response>
    <pageLinks>
        <url>http://wiprodigital.com/</url>
        ...
    </pageLinks>
    <mediaLinks> 
        <url>http://17776-presscdn-0-6.pagely.netdna-cdn.com/wp-content/themes/wiprodigital/images/designit_logo.png</url>
        ...
    </mediaLinks>
    <externalLinks>
        <url>https://xebialabs.com/</url>
        ...
    </externalLinks>
</response>
```

## Example console log output

    $ java -jar target/web-crawler-1.0-SNAPSHOT.jar --url="http://wiprodigital.com" --output-file="sitemap.xml" --temp-dir="tmp/"
    Sep 04, 2016 8:24:45 PM edu.uci.ics.crawler4j.url.TLDList <init>
    INFO: Obtained 6791 TLD from packaged file tld-names.txt
    Sep 04, 2016 8:24:45 PM edu.uci.ics.crawler4j.crawler.CrawlController <init>
    INFO: Deleted contents of: tmp/frontier ( as you have configured resumable crawling to false )
    Sep 04, 2016 8:24:46 PM edu.uci.ics.crawler4j.crawler.CrawlController start
    INFO: Crawler 1 started
    Sep 04, 2016 8:24:47 PM com.wiprodigital.webcrawler.Spider visit
    INFO: visiting: http://wiprodigital.com/
    Sep 04, 2016 8:24:47 PM com.wiprodigital.webcrawler.Spider visit
    INFO: visiting: http://wiprodigital.com/events/dma-customer-engagement/
    Sep 04, 2016 8:24:48 PM com.wiprodigital.webcrawler.Spider visit
    INFO: visiting: http://wiprodigital.com/news/a-new-way-of-working-by-pierre-audoin-consultants-pac/
    ... 
    Sep 04, 2016 8:13:06 PM edu.uci.ics.crawler4j.crawler.CrawlController$1 run
    INFO: It looks like no thread is working, waiting for 10 seconds to make sure...
    Sep 04, 2016 8:13:16 PM edu.uci.ics.crawler4j.crawler.CrawlController$1 run
    INFO: No thread is working and no more URLs are in queue waiting for another 10 seconds to make sure...
    Sep 04, 2016 8:13:26 PM edu.uci.ics.crawler4j.crawler.CrawlController$1 run
    INFO: All of the crawlers are stopped. Finishing the process...
    Sep 04, 2016 8:13:26 PM edu.uci.ics.crawler4j.crawler.CrawlController$1 run
    INFO: Waiting for 10 seconds before final clean up...
    Sep 04, 2016 8:13:37 PM com.wiprodigital.webcrawler.Application saveXMLDocument
    INFO: document saved to: sitemap.xml
  