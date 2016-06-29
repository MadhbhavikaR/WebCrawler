package webcrawler.core.factory;

import webcrawler.constants.DownloadMechanism;
import webcrawler.pojo.MailArchiveSetting;

public interface Plugin {
    String getPluginName();
    void process(MailArchiveSetting mailArchiveSetting, DownloadMechanism downloadMechanism);
    void setMailArchiveSetting(MailArchiveSetting mailArchiveSetting);
    void setDownloadMechanism(DownloadMechanism downloadMechanism);
}
