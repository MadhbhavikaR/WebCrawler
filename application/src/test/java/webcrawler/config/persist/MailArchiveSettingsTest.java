package webcrawler.config.persist;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import webcrawler.config.CrawlerConfiguration;
import webcrawler.pojo.MailArchiveSetting;

public class MailArchiveSettingsTest {
    
    static CrawlerConfiguration config = CrawlerConfiguration.getInstance();
    
    @Test
    public void testSetYear(){
        HashMap<String,MailArchiveSetting> mailArchiveConfigurations = config.getMailArchiveConfigurationMap();
        Assert.assertNotNull(mailArchiveConfigurations); 
        
        MailArchiveSetting mavenMailSettings = mailArchiveConfigurations.get("maven");
        
        Assert.assertNotNull(mavenMailSettings);
        
        mavenMailSettings.setYear("2002");
        
        Assert.assertFalse(mavenMailSettings.getGroupByYearURL().contains("userInputYear"));
        Assert.assertFalse(mavenMailSettings.getValidMailURL().contains("userInputYear"));
        
        
    }
    
}
