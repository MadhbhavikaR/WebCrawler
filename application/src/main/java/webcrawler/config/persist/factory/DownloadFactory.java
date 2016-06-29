package webcrawler.config.persist.factory;

import webcrawler.config.persist.ApacheDownloader;
import webcrawler.config.persist.JSOUPDownloader;
import webcrawler.config.persist.NIODownloader;
import webcrawler.config.persist.StreamDownloader;
import webcrawler.constants.DownloadMechanism;

public class DownloadFactory {

    private DownloadFactory() {}

    public static Download getPersistenceMechanism(DownloadMechanism mechanism) {

        Download downloader = null;
        switch (mechanism) {
            case JSOUP:
                downloader = new JSOUPDownloader();
                break;
            case APACHE:
                downloader = new ApacheDownloader();
                break;
            case NIO:
                downloader = new NIODownloader();
                break;
            case STREAM:
                downloader = new StreamDownloader();
                break;
        }

        return downloader;
    }


}
