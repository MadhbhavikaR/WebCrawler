package webcrawler.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import webcrawler.constants.DownloadMechanism;
import webcrawler.core.factory.Plugin;
import webcrawler.core.observer.Listener;
import webcrawler.pojo.MailArchiveSetting;

public abstract class WebCrawlerPlugin implements Plugin, Runnable, Listener {
    private final static Logger SLF4J_LOGGER = LoggerFactory.getLogger(WebCrawlerPlugin.class);

    MailArchiveSetting mailArchiveSetting;
    DownloadMechanism downloadMechanism;

    public void setMailArchiveSetting(MailArchiveSetting mailArchiveSetting) {
        this.mailArchiveSetting = mailArchiveSetting;
    }

    public void setDownloadMechanism(DownloadMechanism downloadMechanism) {
        this.downloadMechanism = downloadMechanism;
    }

    public void run() {
        if(mailArchiveSetting == null || downloadMechanism == null){
            // this is expensive but need to be done to inform that something wrong with the plugin implementation
            throw new NullPointerException("'MailArchiveSetting' AND 'DownloadMechanism' NEEDS TO BE SET IN THE IMPLEMENTING CLASS : '" + new Exception().getStackTrace()[1].getClassName() + "' BY CALLING THE 'Setter Methods' METHOD");
        }
        try {
            onMessage("Processing Mailing List : ", mailArchiveSetting.getName());
            process(mailArchiveSetting, downloadMechanism);
        } catch (Exception e) {
            SLF4J_LOGGER.error("EXCEPTION-OCCURRED WHILE PROCESSING MAILS FOR [{}]: STACK-TRACE:\n{}", mailArchiveSetting.getName(), e);
        }
        onCompleteAll();
    }
}
