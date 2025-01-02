package com.forum.search.config;

import co.elastic.clients.transport.TransportUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        File certFile = new File("/home/lituizi/IdeaProjects/ahut_forum/search_mod/src/main/resources/http_ca.crt");
        SSLContext sslContext;
        try {
            sslContext = TransportUtils
                    .sslContextFromHttpCaCrt(certFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl(sslContext)
                .withBasicAuth("elastic","bhTtq1MLz93YE3MA7boP")
                .build();
    }
}