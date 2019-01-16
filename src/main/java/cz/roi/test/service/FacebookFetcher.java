package cz.roi.test.service;

import com.google.gson.Gson;
import cz.roi.test.dto.PaginatedResult;
import cz.roi.test.dto.UserInfo;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class FacebookFetcher {

    private static final String FB_API_URL = "https://graph.facebook.com/v3.2";

    public UserInfo getUserInfo(String fbId, String accessToken) {



        Client client = ClientBuilder.newClient();
        Response response = client.target(FB_API_URL + "/" + fbId
                + "?fields=id,name,gender,picture"
                + "&access_token=" + accessToken)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Integer status = response.getStatus();
        String body = response.readEntity(String.class);

        System.out.println(body)
        ;
        // todo handle error states - throw exceptions
        if (status != 200) {
            System.err.println("Call failed: [" + status + "] " + body);
            return null;
        }
        return new Gson().fromJson(body, UserInfo.class);
    }

    public PaginatedResult getPaginatedResult(String fbId, String accessToken) throws UnsupportedEncodingException {
        return getPaginatedResult(FB_API_URL + "/" + fbId + "/photos"
                + "?type=tagged"
                + "&fields="+URLEncoder.encode("id,name,link,album{name},images.limit(1)", "UTF-8") // {name} is taken as template token by Jersey client
                + "&access_token=" + accessToken);
    }

    public PaginatedResult getPaginatedResult(String url) throws UnsupportedEncodingException {
        Client client = ClientBuilder.newClient();
        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Integer status = response.getStatus();
        String body = response.readEntity(String.class);

        System.out.println(body);
        // todo handle error states - throw exceptions
        if (status != 200) {
            System.err.println("Call failed: [" + status + "] " + body);
            return null;
        }
        return new Gson().fromJson(body, PaginatedResult.class);
    }
}
