package webcrawler.config.persist;

import org.jsoup.Jsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

import webcrawler.config.persist.factory.Download;


public class JSOUPDownloader implements Download {

    @Override
    public void downloadIndividualFile(URL url, String fileName) throws IOException {
       String html = Jsoup.connect(url.toString()).get().html();
        try (PrintStream out = new PrintStream(new FileOutputStream(fileName), false ,"UTF-8")) {
            out.print(html);
        }
    }
    
}
