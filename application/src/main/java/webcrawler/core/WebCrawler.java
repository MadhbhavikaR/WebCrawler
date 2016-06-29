package webcrawler.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import webcrawler.core.observer.Listener;
import webcrawler.pojo.MailArchiveSetting;

public class WebCrawler {

    private final static Logger SLF4J_LOGGER = LoggerFactory.getLogger(WebCrawler.class);
    private Listener listener;

    public WebCrawler(Listener listener) {
        this.listener = listener;
    }

    public Set<String> crawl(MailArchiveSetting mailArchiveSetting) {
        long t1 = System.currentTimeMillis();
        long counter = 0;
        Set<String> setOfMailUrls = new HashSet<>();
        listener.onMessage("Starting to Crawl : [0] " + mailArchiveSetting.getSeedURL(), mailArchiveSetting.getName());
        try {
            Document doc = Jsoup.connect(mailArchiveSetting.getSeedURL()).get();
            Elements links = doc.select("a[href]");
            listener.onMessage("+ Located : " + links.size() + " Links", mailArchiveSetting.getName());
            for (Element link : links) {
                String url = link.attr("abs:href");
                if (url.matches(mailArchiveSetting.getGroupByYearURL())) {
                    counter++;
                    listener.onMessage("+ Processing URL : [0." + counter + "] " + url, mailArchiveSetting.getName());
                    extractEmailsByYear(setOfMailUrls, mailArchiveSetting, url);
                }
            }
        } catch (IOException exception) {
            SLF4J_LOGGER.error("EXCEPTION-OCCURRED WHILE CRAWLING: {}  STACK-TRACE:\n {}", mailArchiveSetting.getSeedURL(), exception);

        } finally {
            long t2 = System.currentTimeMillis();
            SLF4J_LOGGER.debug("CRAWLING: " + mailArchiveSetting.getSeedURL() + " took " + (t2 - t1) / 1000 + " seconds to retrieve " + setOfMailUrls.size() + " e-mails");
        }
        listener.onComplete("Crawling Complete, Total URL's Returned : " + setOfMailUrls.size(), mailArchiveSetting.getName());
        return setOfMailUrls;

    }

    private void extractEmailsByYear(Set<String> setOfMailUrls, MailArchiveSetting mailArchiveSetting, String url) throws IOException {
        listener.onMessage("Starting to Crawl : [1] " + mailArchiveSetting.getSeedURL(), mailArchiveSetting.getName());
        Document doc = Jsoup.connect(url).get();
        long counter=0;
        Elements links = doc.select("a[href]");
        listener.onMessage("\t+ Located : " + links.size() + " Links", mailArchiveSetting.getName());
        for (Element link : links) {
            String url2 = link.attr("abs:href");
            if (url2.matches(mailArchiveSetting.getValidMailURL())) {
                counter++;
                listener.onMessage("\t+ Processing URL : [1." + counter + "] " + url2, mailArchiveSetting.getName());
                setOfMailUrls.add(url2);
            }
            extractResultIfMailsArePaged(setOfMailUrls, mailArchiveSetting, url2);
        }
    }

    private void extractResultIfMailsArePaged(Set<String> setOfMailUrls, MailArchiveSetting mailArchiveSetting, String pageURL) throws IOException {
        if (!"NA".equals(pageURL) && pageURL.matches(mailArchiveSetting.getTabURLifMailsArePaginated())) {
            long counter =0;
            listener.onMessage("Starting to Crawl : [2] " + mailArchiveSetting.getSeedURL(), mailArchiveSetting.getName());
            Document doc = Jsoup.connect(pageURL).get();
            Elements links = doc.select("a[href]");
            listener.onMessage("\t\t+ Located : " + links.size() + " Links", mailArchiveSetting.getName());
            for (Element link : links) {
                String url = link.attr("abs:href");
                if (url.matches(mailArchiveSetting.getValidMailURL())) {
                    counter++;
                    listener.onMessage("\t\t+ Processing URL : [2." + counter + "] " + url, mailArchiveSetting.getName());
                    setOfMailUrls.add(url);
                }
            }
        }
    }
}
