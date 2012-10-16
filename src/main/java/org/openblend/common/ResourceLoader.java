package org.openblend.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomaz Cerar
 * @created 12.7.11 10:46
 */
public class ResourceLoader {
    private static final Logger log = LoggerFactory.getLogger(ResourceLoader.class);
    private static ResourceLoader resourceLoader;
    private static String loadPath;
    private static boolean localLoaderEnabled = true;
    private static Set<String> extensions = new HashSet<String>();

    /**
     * A list of extensions, which are filtered by default, if nothing is
     * specified in the filter configuration.
     */
    private final static String DEFAULT_EXTENSIONS = "js,jsp,css,html,htm,gif,jpg,jpeg,png,txt,xhtml,jsf";


    protected static void init(ServletContext context) {
        log.debug("creating resource resolver ");
        if (ResourceLoaderFilter.isProduction()) {
            return;
        }
        localLoaderEnabled = !ResourceLoaderFilter.isProduction();
        String ext = null;
        try {

            loadPath = context.getInitParameter("RELOAD_DEVELOPMENT_DIRECTORY");
            if (loadPath != null) {
                loadPath = loadPath.replaceAll("\\\\", "/");
            }
            log.debug("load path: {}", loadPath);
            ext = context.getInitParameter("RELOAD_FILE_EXTENSIONS");
        } catch (Exception e) {
            log.warn("Could not configure resource loader", e);
        }
        if (ext == null || "".equals(ext)) {
            ext = DEFAULT_EXTENSIONS;
        }
        log.debug("ext: {}", ext);
        String[] tokens = ext.split(",");

        extensions.addAll(Arrays.asList(tokens));
        log.debug("extensions: {}", extensions);

        if (localLoaderEnabled) {
            if (!new File(loadPath).exists()) {
                log.warn("Local load path: {}, does not exist, disabling loader", loadPath);
                localLoaderEnabled = false;
            }
        }
    }


    public URL resolveUrl(String file) {
        try {
            log.debug("sourceBasePath " + loadPath);
            log.debug("file to load: {}", loadPath + file);
            return new File(loadPath + file).toURI().toURL();
            //return new URL("file", "", loadPath + file);
        } catch (MalformedURLException e) {
            log.warn("could not load file " + e.getMessage());
            try {
                return new URL("file", "", file);
            } catch (MalformedURLException e1) {
                //log.error(e1.getMessage, e1)
                return null;
            }
        }
    }

    public static ResourceLoader getResourceLoader() {
        if (resourceLoader == null) {
            resourceLoader = new ResourceLoader();
        }
        return resourceLoader;
    }

    public String getLoadPath() {
        return loadPath;
    }

    public boolean isLocalLoaderEnabled() {
        return localLoaderEnabled;
    }

    public Set<String> getExtensions() {
        return extensions;
    }
}
