package webcrawler.core.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gigadot.rebound.Rebound;
import webcrawler.config.CrawlerConfiguration;
import webcrawler.constants.DownloadMechanism;
import webcrawler.constants.WebCrawlerConstants;
import webcrawler.sdk.WebCrawlerPlugin;

public final class PluginFactory {
    private final static Logger SLF4J_LOGGER = LoggerFactory.getLogger(PluginFactory.class);
    private static PluginFactory instance = null;

    private Map<String, Class<? extends WebCrawlerPlugin>> loadedPluginMap;

    private PluginFactory() {
        Rebound rebound = new Rebound(WebCrawlerConstants.PLUGIN_PACKAGE);
        Set<Class<? extends WebCrawlerPlugin>> pluginClassSet = rebound.getSubClassesOf(WebCrawlerPlugin.class);
        loadedPluginMap = new HashMap<>();
        String className = "";
        for (Class<? extends WebCrawlerPlugin> klass : pluginClassSet) {
            className = klass.getName();
            try {
                Plugin plugin = klass.newInstance();
                loadedPluginMap.put(plugin.getPluginName(), klass);
            } catch (InstantiationException | IllegalAccessException e) {
                SLF4J_LOGGER.warn("ERROR INITIALIZING PLUGIN : [{}]", className);
            }
        }
    }

    public Set<String> getLoadedPlugins() {
        return loadedPluginMap.keySet();
    }

    //returns a new instance every time
    public Runnable getPlugin(String pluginName, DownloadMechanism downloadMechanism) {
        Plugin plugin = null;
        try {
            Class<? extends WebCrawlerPlugin> klass = loadedPluginMap.get(pluginName);
            if (klass != null) {
                plugin = klass.newInstance();
                plugin.setMailArchiveSetting(CrawlerConfiguration.getInstance().getMailArchiveSetting(pluginName));
                plugin.setDownloadMechanism(downloadMechanism);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            SLF4J_LOGGER.warn("ERROR INITIALIZING PLUGIN : [{}]", pluginName);
        }
        return plugin != null ? (Runnable) plugin : null;
    }

    public static PluginFactory getInstance() {
        if (instance != null) {
            return instance;
        } else {
            instance = new PluginFactory();
            return instance;
        }
    }
}
