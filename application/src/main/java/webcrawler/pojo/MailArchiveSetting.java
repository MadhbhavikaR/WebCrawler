package webcrawler.pojo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MailArchiveSetting {
    private String name;
    private String seedURL;
    private String groupByYearURL;
    private String tabURLifMailsArePaginated;
    private String validMailURL;
    private String destinationFolder;
    private int threadPoolSize;

    public MailArchiveSetting(String name, String seedURL, String groupByYearURL, String tabURLifMailsArePaginated, String validMailURL, String destinationFolder) {
        this.name = name;
        this.seedURL = seedURL;
        this.groupByYearURL = groupByYearURL;
        this.tabURLifMailsArePaginated = tabURLifMailsArePaginated;
        this.validMailURL = validMailURL;
        this.destinationFolder = destinationFolder;
        this.threadPoolSize = 10;
    }

    public String getName() {
        return name;
    }

    public void setYear(String searchYear) {
        groupByYearURL = groupByYearURL.replaceAll("userInputYear", searchYear);
        validMailURL = validMailURL.replaceAll("userInputYear", searchYear);
    }


    public String getSeedURL() {
        return seedURL;
    }

    public String getGroupByYearURL() {
        return groupByYearURL;
    }

    public String getValidMailURL() {
        return validMailURL;
    }

    public String getDestinationFolder() {
        return destinationFolder;
    }

    public String getTabURLifMailsArePaginated() {
        return tabURLifMailsArePaginated;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize > 1 ? threadPoolSize : 10;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
