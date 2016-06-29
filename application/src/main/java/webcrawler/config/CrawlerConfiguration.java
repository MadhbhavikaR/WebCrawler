package webcrawler.config;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import webcrawler.constants.WebCrawlerConstants;
import webcrawler.pojo.MailArchiveSetting;

public final class CrawlerConfiguration {

    private final static Logger SLF4J_LOGGER = LoggerFactory.getLogger(CrawlerConfiguration.class);
    private static int simultaneousSeeds;
    private static CrawlerConfiguration instance;

    private HashMap<String, MailArchiveSetting> mailArchiveConfigurationMap;

    private CrawlerConfiguration() throws RuntimeException {
        String configFilePath = System.getProperty(WebCrawlerConstants.CONFIG_PATH);

        mailArchiveConfigurationMap = new HashMap<>();

        try (InputStream inputStream = new FileInputStream(configFilePath)) {
            Properties properties = new Properties();
            simultaneousSeeds = Integer.parseInt(properties.getProperty("simultaneousSeeds", "2").trim());
            properties.load(inputStream);

            Enumeration enumeration = properties.propertyNames();
            while (enumeration.hasMoreElements()) {
                String propertyName = (String) enumeration.nextElement();

                String website = propertyName.split("\\.")[0];
                if (!website.equals("simultaneousSeeds") && !mailArchiveConfigurationMap.containsKey(website)) {
                    MailArchiveSetting mailArchive = new MailArchiveSetting(website,
                            properties.getProperty(website + ".seedURL"),
                            properties.getProperty(website + ".groupByYearURL"),
                            properties.getProperty(website + ".tabURLifMailsArePaginated"),
                            properties.getProperty(website + ".validMailURL"),
                            properties.getProperty(website + ".destination.folder"));
                    mailArchive.setThreadPoolSize(Integer.parseInt(properties.getProperty(website + ".threadPoolSize").trim()));
                    mailArchiveConfigurationMap.put(website, mailArchive);
                }
            }
        } catch (IOException | NumberFormatException exception) {
            SLF4J_LOGGER.error("EXCEPTION-OCCURRED WHILE LOADING PROPERTIES: STACK-TRACE:\n{}", exception);
            throw new RuntimeException("EXCEPTION-OCCURRED WHILE LOADING PROPERTIES", exception);
        }
    }

    public HashMap<String, MailArchiveSetting> getMailArchiveConfigurationMap() {
        return mailArchiveConfigurationMap;
    }

    public MailArchiveSetting getMailArchiveSetting(String name) {
        return mailArchiveConfigurationMap.get(name);
    }

    public static CrawlerConfiguration getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new CrawlerConfiguration();
            return instance;
        }
    }

    public int getSimultaneousSeeds() {
        return simultaneousSeeds;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
