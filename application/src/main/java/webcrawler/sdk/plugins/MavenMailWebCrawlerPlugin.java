package webcrawler.sdk.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import webcrawler.constants.DownloadMechanism;
import webcrawler.core.WebCrawler;
import webcrawler.pojo.MailArchiveSetting;
import webcrawler.sdk.WebCrawlerPlugin;
import webcrawler.sdk.core.DownloadEmails;

public class MavenMailWebCrawlerPlugin extends WebCrawlerPlugin {
    private final static Logger SLF4J_LOGGER = LoggerFactory.getLogger(MavenMailWebCrawlerPlugin.class);
    private final static String pluginName = "maven";
    public MavenMailWebCrawlerPlugin() {}


    @Override
    public String getPluginName() {
        return pluginName;
    }

    @Override
    public void process(MailArchiveSetting mailArchiveSetting, DownloadMechanism downloadMechanism) {
        try {
            mailArchiveSetting.setYear("2006");
            SLF4J_LOGGER.info("Processing Mailing List : " + mailArchiveSetting.getName());
            Set<String> linkSet = (new WebCrawler(this)).crawl(mailArchiveSetting);
            DownloadEmails downloadEmails = new DownloadEmails(downloadMechanism, this);
            downloadEmails.download(linkSet, mailArchiveSetting, ".html");
        } catch (Exception e) {
            SLF4J_LOGGER.error("EXCEPTION-OCCURRED WHILE DOWNLOADING MAILS FOR [{}]: STACK-TRACE:\n{}", mailArchiveSetting.getName(), e);
        }
    }


    @Override
    public void onMessage(Object message, String threadID) {
        SLF4J_LOGGER.debug("[" + threadID + "]" +  message);
    }

    @Override
    public void onComplete(Object message, String threadID) {
        SLF4J_LOGGER.debug("[" + threadID + "]" +  message);
    }

    @Override
    public void onCompleteAll() {
        SLF4J_LOGGER.debug("["+ getPluginName() + "] Processing Complete");
    }
}
