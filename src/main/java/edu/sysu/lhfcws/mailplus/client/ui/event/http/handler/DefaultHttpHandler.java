package edu.sysu.lhfcws.mailplus.client.ui.event.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import edu.sysu.lhfcws.mailplus.client.ui.event.http.ParameterFilter;

import java.io.IOException;
import java.util.Map;

/**
 * @author lhfcws
 * @time 14-10-31.
 */
public class DefaultHttpHandler implements HttpHandler {
    protected Map<String, String> params;
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        params = (Map<String, String>) httpExchange.getAttribute(ParameterFilter.NAME);
    }
}
