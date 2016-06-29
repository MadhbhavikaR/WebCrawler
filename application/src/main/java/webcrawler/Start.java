package webcrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import webcrawler.config.CrawlerConfiguration;
import webcrawler.constants.DownloadMechanism;
import webcrawler.core.factory.PluginFactory;
import webcrawler.pojo.MailArchiveSetting;

public class Start {

    private final static Logger SLF4J_LOGGER = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) throws Exception {
        CrawlerConfiguration crawlerConfiguration = CrawlerConfiguration.getInstance();
        HashMap<String, MailArchiveSetting> mailArchiveMap = crawlerConfiguration.getMailArchiveConfigurationMap();
        ExecutorService pool = Executors.newFixedThreadPool(crawlerConfiguration.getSimultaneousSeeds());
        Iterator iterator = mailArchiveMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, MailArchiveSetting> entry = (Map.Entry<String, MailArchiveSetting>) iterator.next();
            Runnable plugin = PluginFactory.getInstance().getPlugin(entry.getKey(), DownloadMechanism.APACHE);
            if (plugin != null) {
                pool.submit(plugin);
            } else {
                SLF4J_LOGGER.error("NO PLUGIN FOUND FOR : [{}]", entry.getKey());
            }
        }
        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}
