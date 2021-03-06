package fr.gouv.franceconnect.stub.api;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.Security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tchabaud on 15/10/16.
 * Stub for France Connect authorize endpoint.
 */
public class Authorize extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(Authorize.class);

    @Override
    public void init() {
        logger.info("Begin Bouncy Castle initialization ...");
        Security.addProvider(new BouncyCastleProvider());
        logger.info("Bouncy Castle initialization successful.");
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
            IOException {
        req.getSession().setAttribute("state", req.getParameter("state"));
        req.getSession().setAttribute("redirect_uri", req.getParameter("redirect_uri"));
        req.getSession().setAttribute("client_id", req.getParameter("client_id"));
        req.getSession().setAttribute("nonce", req.getParameter("nonce"));

        final String scope = URLEncoder.encode(req.getParameter("scope"), UTF_8.displayName());
        final String response_type = URLEncoder.encode(req.getParameter("response_type"), UTF_8.displayName());
        final String nonce = URLEncoder.encode(req.getParameter("nonce"), UTF_8.displayName());
        final String redirect_uri = URLEncoder.encode(req.getParameter("redirect_uri"), UTF_8.displayName());
        final String state = URLEncoder.encode(req.getParameter("state"), UTF_8.displayName());
        final String client_id = URLEncoder.encode(req.getParameter("client_id"), UTF_8.displayName());

        final String contextPath;
        if (StringUtils.isBlank(req.getContextPath())) {
            contextPath = "/";
        } else {
            contextPath = req.getContextPath() + "/";
        }

        final StringBuilder uri = new StringBuilder(contextPath).append("index.jsp").append("?") //
                .append("scope").append('=').append(scope) //
                .append("&response_type").append('=').append(response_type) //
                .append("&nonce").append('=').append(nonce) //
                .append("&redirect_uri").append('=').append(redirect_uri) //
                .append("&state").append('=').append(state) //
                .append("&client_id").append('=').append(client_id);
        final URI redirectUri;
        try {
            redirectUri = new URI(uri.toString());
        } catch (final URISyntaxException e) {
            throw new ServletException(e);
        }
        resp.setContentType("Content-type: text/html");
        resp.sendRedirect(redirectUri.toString());
    }
}
