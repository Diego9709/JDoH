package pers.diego.dns.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author kang.zhang
 * @date 2/1/2022 7:46 PM
 */

@Component
public class UpstreamDoh {

    @Value("${dns.servers.http.url}")
    private String defaultHttpUrl;

    private URL url;


    public void setUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getUrl(){
        if(this.url == null){
            try {
                this.url = new URL(this.defaultHttpUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return this.url;

    }

}