package org.jeecg.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
@Component
@Slf4j
public class RestTemplateLog implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        traceRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(response);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body) {
        Map<String, Object> map = new HashMap<>(16);
        if (request instanceof HttpServletRequest)
        {
            HttpServletRequest servletRequest=(HttpServletRequest)request;
            map.put("parameterMap",servletRequest.getParameterMap());
        }
        map.put("requestUri", request.getURI().getPath());
        map.put("headers", String.valueOf(request.getHeaders()));
        map.put("parameter", new String(body, StandardCharsets.UTF_8));
        log.info("request: {}", map);
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader =
                     new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("httpCode", response.getStatusCode().value());
        map.put("responseBody", String.valueOf(inputStringBuilder));
        log.info("response: {}", map);
    }

}


