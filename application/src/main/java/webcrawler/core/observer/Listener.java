package webcrawler.core.observer;

public interface Listener {
    void onMessage(Object message, String threadID );
    void onComplete(Object message,String threadID);
    void onCompleteAll();
}
