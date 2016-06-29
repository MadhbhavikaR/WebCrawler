
package webcrawler.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

import webcrawler.config.persist.factory.Download;
import webcrawler.config.persist.factory.DownloadFactory;
import webcrawler.constants.DownloadMechanism;


public class DownloaderThread extends Thread{
    
    private final static Logger SLF4J_LOGGER = LoggerFactory.getLogger(DownloaderThread.class);
    Download persistingMechanism;
    URL url;
    String fileName;

    public DownloaderThread(URL url, String fileName, DownloadMechanism mechanism) {
        this.url = url;
        this.fileName = fileName;
        persistingMechanism = DownloadFactory.getPersistenceMechanism(mechanism);
    }

    @Override
    public void run() {
        try {
            persistingMechanism.downloadIndividualFile(url, fileName);
        } catch (IOException exception) {
            SLF4J_LOGGER.error("EXCEPTION OCCURED WHILE DOWNLOADING : {}  STACK-TRACE:\n {}", url, exception);
        }
    }
}
