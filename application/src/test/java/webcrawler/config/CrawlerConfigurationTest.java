package webcrawler.config;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;

import webcrawler.pojo.MailArchiveSetting;

public class CrawlerConfigurationTest {
    
    CrawlerConfiguration crawlerConfiguration;

    @After
    public void tearDown() throws Exception {
        System.clearProperty("webCrawler.config.file");
    }

    @Before
    public void setUp() throws Exception {
        System.setProperty("webCrawler.config.file", CrawlerConfiguration.class.getResource("/config.properties").getPath());
        crawlerConfiguration = CrawlerConfiguration.getInstance();
    }

    @Test
    public void testCrawlerConfigurations() throws Exception {
        int totalMailArchiveConfigurations =1; // Needs to be changed in new Configuratins are added !

        HashMap<String,MailArchiveSetting> mailArchiveConfigurations = crawlerConfiguration.getMailArchiveConfigurationMap();
        Assert.assertNotNull(mailArchiveConfigurations);
        Assert.assertTrue(mailArchiveConfigurations.size()==totalMailArchiveConfigurations);
        Iterator<String> websiteName = mailArchiveConfigurations.keySet().iterator();

        while(websiteName.hasNext()){
            MailArchiveSetting mailArchive = mailArchiveConfigurations.get(websiteName.next());
            Assert.assertNotNull(mailArchive);
            Assert.assertNotNull(mailArchive.getSeedURL());
            Assert.assertNotEquals(mailArchive.getSeedURL().trim(), "");
            Assert.assertNotNull(mailArchive.getGroupByYearURL());
            Assert.assertNotEquals(mailArchive.getGroupByYearURL().trim(), "");
            Assert.assertNotNull(mailArchive.getValidMailURL());
            Assert.assertNotEquals(mailArchive.getValidMailURL().trim(), "");
            Assert.assertNotNull(mailArchive.getDestinationFolder());
            Assert.assertNotEquals(mailArchive.getDestinationFolder().trim(), "");
        }
    }
}
