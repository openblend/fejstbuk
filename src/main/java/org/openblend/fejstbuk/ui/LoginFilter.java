package org.openblend.fejstbuk.ui;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Matej Lazar
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Inject
    Login login;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (!isAuthRequired(req) || isLoggedIn()) {
            chain.doFilter(request, response);
        } else {
            rememberLastView(req);
            res.sendRedirect(req.getContextPath() + "/login");
        }
    }

    private String getLastView(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("lastView");
    }

    private void rememberLastView(HttpServletRequest req) {
        req.getSession().setAttribute("lastView", req.getRequestURI());
    }

    private boolean isLoggedIn() {
        return login.isLogged();
    }

    private boolean isAuthRequired(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !uri.equals("/login") &&
               !uri.startsWith("/register") &&
               !uri.startsWith("/_common") &&
               !uri.startsWith("/javax.faces.resource/") &&
               !uri.startsWith("/h2/");

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
