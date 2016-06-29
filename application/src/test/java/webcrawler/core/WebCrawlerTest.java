package webcrawler.core;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import webcrawler.config.CrawlerConfiguration;
import webcrawler.core.observer.Listener;
import webcrawler.pojo.MailArchiveSetting;
import webcrawler.utils.FileUtil;

public class WebCrawlerTest {

    private CrawlerConfiguration config;
    private Listener listener;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webCrawler.config.file", CrawlerConfiguration.class.getResource("/config.properties").getPath());
        config = CrawlerConfiguration.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        System.clearProperty("webCrawler.config.file");
    }

    @Test
    public void testMavenMailListCrawling2002() throws Exception {
        MailArchiveSetting mavenMailSettings = config.getMailArchiveConfigurationMap().get("maven");
        mavenMailSettings.setYear("2002");
        int totalMails = 374; //Total mails in 2002 is 359 + 15 = 374 . Checked from net.
        Assert.assertFalse(mavenMailSettings.getGroupByYearURL().contains("userInputYear"));
        Assert.assertFalse(mavenMailSettings.getValidMailURL().contains("userInputYear"));

        FileUtil.deleteAndCreateDirectory(mavenMailSettings.getDestinationFolder());
        Set<String> results = (new WebCrawler(new Listener() {
            @Override
            public void onMessage(Object message, String threadID) {
                System.out.println("onMessage:[" + threadID + "] " + message);
            }

            @Override
            public void onComplete(Object message, String threadID) {
                System.out.println("onComplete:[" + threadID + "] " + message);
            }

            @Override
            public void onCompleteAll() {
                System.out.println("onCompleteAll");
            }
        })).crawl(mavenMailSettings);
        Assert.assertNotNull(results);
        Assert.assertTrue(results.size() == totalMails);

    }
}
