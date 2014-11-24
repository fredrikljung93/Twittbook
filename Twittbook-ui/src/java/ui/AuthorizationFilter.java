package ui;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/authorized/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {    
        HttpServletRequest req = (HttpServletRequest) request;
        StoredUser user = (StoredUser) req.getSession().getAttribute("user");

        if (user!=null) {
            System.out.println("AFilter got this user in session: "+user.getUsername());
            // User is logged in, so just continue request.
            chain.doFilter(request, response);
        } else {
            // User is not logged in, so redirect to index.
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    // You need to override init() and destroy() as well, but they can be kept empty.

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}