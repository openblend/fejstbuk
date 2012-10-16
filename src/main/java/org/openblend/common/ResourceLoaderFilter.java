package org.openblend.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Set;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.sun.faces.facelets.impl.DefaultResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Tomaz Cerar
 * @created 19.7.2010 21:19:20
 */
@WebFilter(urlPatterns = "/*", dispatcherTypes = {DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST})
public class ResourceLoaderFilter extends DefaultResourceResolver implements Filter {
    private static final Logger log = LoggerFactory.getLogger(ResourceLoaderFilter.class);

    /**
     * Base path to a directory where files will
     * be copied; it's a subdirectory of a deployment directory created by the
     * app server.
     */
    private String destBasePath;

    /**
     * Directory in the filesystem from which to read the files.
     */
    private String sourceBasePath;

    /**
     * A set of <code>java.lang.String</code>s, which are extensions, that are filtered.
     */
    private Set extensions;
    private boolean enabled = false;
    private boolean init = false;
    private static ProjectStage projectStage;

    protected static boolean isProduction() {
        /*FacesContext fc = FacesContext.getCurrentInstance();
        if (fc == null) {
            return true;
        }
        ProjectStage stage = fc.getApplication().getProjectStage();*/
        log.info("stage is: {}", projectStage);
        return projectStage == ProjectStage.Production;
    }

    public static ProjectStage getProjectStage() {
        return projectStage;
    }

    public void init(FilterConfig conf) {
        destBasePath = conf.getServletContext().getRealPath("");
        log.info("initializing filter with data: {}", Collections.list(conf.getInitParameterNames()));

        ServletContext sc = conf.getServletContext();
        projectStage = FacesContext.getCurrentInstance().getApplication().getProjectStage();
        if (projectStage == null) {
            String st = sc.getInitParameter("javax.faces.PROJECT_STAGE");
            projectStage = ProjectStage.valueOf(st);
            log.info("project stage: {}", projectStage);
        }

        ResourceLoader.init(sc);

        sc.setInitParameter("javax.faces.RESOURCE_RESOLVER", ResourceLoaderFilter.class.getName());
        sc.setInitParameter("javax.faces.REFRESH_PERIOD", "0");

        log.info("init params of application: {}", Collections.list(sc.getInitParameterNames()));

    }

    public URL resolveUrl(String s) {
        log.debug("have to resolve url: {}", s);
        if (!ResourceLoader.getResourceLoader().isLocalLoaderEnabled()) {
            return super.resolveUrl(s);
        }

        log.debug("loading from local disk...");
        return ResourceLoader.getResourceLoader().resolveUrl(s);
    }

    private void doInit() {
        if (init) { return; }
        sourceBasePath = ResourceLoader.getResourceLoader().getLoadPath();
        extensions = ResourceLoader.getResourceLoader().getExtensions();
        enabled = ResourceLoader.getResourceLoader().isLocalLoaderEnabled();
        init = true;
    }


    /**
     * Transfers all bytes from the given input stream to the given output
     * stream.
     *
     * @param is Input stream to read from.
     * @param os Output stream to write to.
     * @throws java.io.IOException In case of an IO exception.
     */
    private void transfer(InputStream is, OutputStream os) throws IOException {
        copy(is, os);

    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }


    public static long copyLarge(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024 * 4];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }


    private String safeToString(Object o) {
        if (o == null) {
            return null;
        }

        return o.toString();
    }

    private boolean checkExtension(String path) {
        int dotIndex = path.lastIndexOf('.');

        if (dotIndex != -1) {
            String extension = path.substring(dotIndex + 1);
            return extensions.contains(extension);
        } else {
            return false;
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!init) { doInit(); }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.debug("filtering for url: {}, enabled  {}", httpRequest.getRequestURI(), enabled);

        if (enabled) {


            /* Getting the name of the requested resource; first checking if
             * it is an included, then forwarded resource. Finally, checking
             * the request uri itself.
            */
            String requestedResource;
            requestedResource = safeToString(httpRequest.getAttribute("javax.servlet.include.servlet_path"));

            if (requestedResource == null) {
                requestedResource = httpRequest.getServletPath();
            }

            //log.debug("requestedResource: {}", requestedResource);

            // JSF check - we have to replace .jsf with .jsp.
            String realRequestedResource = requestedResource;
            if (realRequestedResource.endsWith(".jsf")) {
                realRequestedResource = realRequestedResource.replace(".jsf", ".xhtml");
            }

            // Filtering only some file extensions.
            if (!checkExtension(realRequestedResource)) {
                chain.doFilter(request, response);
                return;
            }

            File sourceFile = new File(sourceBasePath + realRequestedResource);
            if (!sourceFile.exists()) {
                chain.doFilter(request, response);
                return;
            }


            File destFile = new File(destBasePath + realRequestedResource);

            InputStream in = null;
            OutputStream out = null;

            try {
                destFile.getParentFile().mkdirs();
                destFile.setLastModified(System.currentTimeMillis());

                in = new FileInputStream(sourceFile);
                out = new FileOutputStream(destFile);

                transfer(in, out);
            } finally {
                if (in != null) {
                    in.close();
                }

                if (out != null) {
                    out.close();
                }
            }

        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }

    @Override
    public String toString() {
        return "ResourceLoaderFilter";
    }
}
