package webcrawler.sdk.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import webcrawler.constants.DownloadMechanism;
import webcrawler.core.observer.Listener;
import webcrawler.pojo.MailArchiveSetting;

import static org.mockito.Mockito.when;

public class DownloadEmailsTest {
    MailArchiveSetting mailArchiveSetting;
    final static String FIlE_NAME = "/DownloadEmailsTest.txt";
    final static String FIlE_NAME_AFTER = "downloademailstest.txt.downloaded";
    final static String PATH = DownloadEmailsTest.class.getResource(FIlE_NAME).getPath();
    final static String SRC_FOLDER_PATH = "file://" + PATH;
    final static String DST_FOLDER_PATH = PATH.substring(0,PATH.lastIndexOf("/"));

    @Before
    public void setUp() throws Exception {
        mailArchiveSetting = Mockito.mock(MailArchiveSetting.class);
        when(mailArchiveSetting.getDestinationFolder()).thenReturn(DST_FOLDER_PATH);
        when(mailArchiveSetting.getSeedURL()).thenReturn(SRC_FOLDER_PATH);
        when(mailArchiveSetting.getValidMailURL()).thenReturn(SRC_FOLDER_PATH);
        when(mailArchiveSetting.getGroupByYearURL()).thenReturn(SRC_FOLDER_PATH);
        when(mailArchiveSetting.getTabURLifMailsArePaginated()).thenReturn("NA");
        when(mailArchiveSetting.getThreadPoolSize()).thenReturn(1);
    }

    @Test
    public void should_download_a_file_in_target_folder() throws Exception {
        DownloadEmails downloadEmails = new DownloadEmails(DownloadMechanism.APACHE, new Listener() {
            @Override
            public void onMessage(Object message, String threadID) {
                System.out.println("onMessage:[" + threadID + "] " +message);
            }

            @Override
            public void onComplete(Object message, String threadID) {
                System.out.println("onComplete:[" + threadID + "] " +message);
            }

            @Override
            public void onCompleteAll() {
                System.out.println("onCompleteAll");
            }
        });
        Set url = new HashSet<String>();
        url.add(SRC_FOLDER_PATH);
        downloadEmails.download(url, mailArchiveSetting, ".downloaded");
        // ideally target would be cleaned by maven :)
        File [] fileList = (new File(DST_FOLDER_PATH)).listFiles();
        boolean fileExists = false;
        for(File f: fileList){
            fileExists = f.getName().endsWith(FIlE_NAME_AFTER);
            if(fileExists) {
                break;
            }
        }
        Assert.assertTrue(fileExists);
    }
}