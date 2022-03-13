package kr.revfactory.election.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final int MAX_POOL = 100;
    private static final int MAX_POOL_PER_ROUTE = 30;
    private static final int CONNECTION_TIMEOUT = 1000 * 5;
    private static final int CONNECTION_REQUEST_TIMEOUT = 1000 * 5;
    private static final int READ_TIMEOUT = 1000 * 10;
    private static final int SOCKET_TIMEOUT = 1000 * 10;

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(CONNECTION_TIMEOUT);
        httpRequestFactory.setReadTimeout(READ_TIMEOUT);
        httpRequestFactory.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT);
        httpRequestFactory.setHttpClient(createDefaultHttpClient());
        return new RestTemplate(httpRequestFactory);
    }

    private CloseableHttpClient createDefaultHttpClient() {
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
                new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(MAX_POOL);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(MAX_POOL_PER_ROUTE);

        return HttpClientBuilder.create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultSocketConfig(SocketConfig.copy(SocketConfig.DEFAULT)
                        .setSoKeepAlive(true)
                        .setSoReuseAddress(true)
                        .setSoTimeout(SOCKET_TIMEOUT)
                        .build())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                        .setConnectTimeout(CONNECTION_TIMEOUT)
                        .setSocketTimeout(SOCKET_TIMEOUT)
                        .build())
                .setMaxConnTotal(MAX_POOL)
                .useSystemProperties()
                .build();
    }
}
