package pers.diego.dns.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author kang.zhang
 * @date 2/1/2022 7:46 PM
 */

@Component
public class UpstreamDoh {

    @Value("${dns.servers.http.url}")
    private String  defaultHttpUrl;

    private String httpUrl;

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public URL getUrl() throws MalformedURLException {
        if(httpUrl != null){
            try {
                return new URL(httpUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        return new URL(defaultHttpUrl);
    }
}
