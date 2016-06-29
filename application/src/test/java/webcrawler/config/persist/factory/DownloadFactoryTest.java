package webcrawler.config.persist.factory;

import org.junit.Assert;
import org.junit.Test;

import webcrawler.config.persist.ApacheDownloader;
import webcrawler.config.persist.JSOUPDownloader;
import webcrawler.config.persist.NIODownloader;
import webcrawler.config.persist.StreamDownloader;
import webcrawler.constants.DownloadMechanism;

public class DownloadFactoryTest {
    @Test
    public void should_return_persistence_mechanism_as_JSOUPDownloader() throws Exception {
        Assert.assertTrue(DownloadFactory.getPersistenceMechanism(DownloadMechanism.JSOUP) instanceof JSOUPDownloader);
    }

    @Test
    public void should_return_persistence_mechanism_as_ApacheDownloader() throws Exception {
        Assert.assertTrue(DownloadFactory.getPersistenceMechanism(DownloadMechanism.APACHE) instanceof ApacheDownloader);
    }

    @Test
    public void should_return_persistence_mechanism_as_NIODownloader() throws Exception {
        Assert.assertTrue(DownloadFactory.getPersistenceMechanism(DownloadMechanism.NIO) instanceof NIODownloader);
    }

    @Test
    public void should_return_persistence_mechanism_as_StreamDownloader() throws Exception {
        Assert.assertTrue(DownloadFactory.getPersistenceMechanism(DownloadMechanism.STREAM) instanceof StreamDownloader);
    }
}