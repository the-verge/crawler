package com.verge.crawler.crawler;


import com.verge.crawler.email.EmailUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Component
@DisallowConcurrentExecution
public class PageRetriever implements Job {

    private static final String SONY_URL = "https://centresdirect.co.uk/c-66-refurbished_refurbished.aspx#pgnum=&sort=&rf=";
    private static final String PROXY_HOST = "";
    private static final Integer PROXY_PORT = 8000;
    private static final String SEARCH_TERM = "WH-H900";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        RestTemplate restTemplate = configureRestTemplate();

        String page = restTemplate.getForObject(SONY_URL, String.class);
        System.out.println(searchTermFound(page));

        if (searchTermFound(page)) {
            EmailUtils.sendNotification();
        }
    }

    private RestTemplate configureRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT));
        requestFactory.setProxy(proxy);

        return new RestTemplate(requestFactory);
    }

    private boolean searchTermFound(String page) {
        return page.contains(SEARCH_TERM);
    }

}
