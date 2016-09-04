package com.wiprodigital.webcrawler;

import java.util.concurrent.ConcurrentSkipListSet;


public class Response {

    ConcurrentSkipListSet<String> pageLinks = new ConcurrentSkipListSet<String>();
    ConcurrentSkipListSet<String> externalLinks = new ConcurrentSkipListSet<String>();
    ConcurrentSkipListSet<String> madiaLinks = new ConcurrentSkipListSet<String>();


    public void addPageLink(String url) {
        pageLinks.add(url);
    }
    public void addExternalLink(String url) {
        externalLinks.add(url);
    }
    public void addMediaLink(String url) {
        madiaLinks.add(url);
    }

    @Override
    public String toString() {
        return "Response{" + "\n" +
                "pageLinks=" + pageLinks + "\n" +
                ", externalLinks=" + externalLinks + "\n" +
                ", madiaLinks=" + madiaLinks + "\n" +
                '}';
    }
}
