package webcrawler.sdk.core;

import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import webcrawler.constants.DownloadMechanism;
import webcrawler.core.observer.Listener;
import webcrawler.pojo.MailArchiveSetting;
import webcrawler.threads.DownloaderThread;
import webcrawler.utils.FileUtil;

public class DownloadEmails {
    DownloadMechanism mechanism;
    Listener listener;

    public DownloadEmails(DownloadMechanism mechanism, Listener listener) {
        this.mechanism = mechanism;
        this.listener = listener;
    }

    public void download(Set<String> urlList, MailArchiveSetting mailArchive, String fileExtension) throws Exception {
        long t1 = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(mailArchive.getThreadPoolSize());
        listener.onMessage("Initiating Download" , mailArchive.getName());
        for (String url : urlList) {
            listener.onMessage("Downloading : [" + url + "]" , mailArchive.getName());
            URL urlObject = new URL(url);
            String fileName = urlObject.getFile().toLowerCase();
            fileName = fileName.replace("/", "_");
            fileName = fileName.replace(":", "_");
            if (fileName.startsWith("_")) {
                fileName = fileName.substring(1);
            }
            if (fileExtension != null && !fileExtension.isEmpty()) {
                fileName += fileExtension;
            }
            String effectiveFileName = mailArchive.getDestinationFolder() + File.separator + fileName;
            pool.submit(new DownloaderThread(urlObject, effectiveFileName, mechanism));
        }
        pool.shutdown();
        pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        long t2 = System.currentTimeMillis();
        long timeTaken = (t2 - t1) / 1000;
        int numberOfFilesDownloaded = FileUtil.findNumberOfFilesInADirectory(mailArchive.getDestinationFolder());
        listener.onMessage("Downloading batch complete" , mailArchive.getName());
        listener.onComplete("took :"+timeTaken+" seconds for :"+numberOfFilesDownloaded+" files",  mailArchive.getName());
    }
}
