package mk.ukim.finki.emt.productcatalog.xport.client;

import mk.ukim.finki.emt.productcatalog.domain.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class OrderClient {
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public OrderClient(@Value("${app.order-management.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public boolean deleteItems(String id){
        try{
            restTemplate.exchange(
                    uri().path("/api/order/delete/"+id).build().toUri(), HttpMethod.POST,
                    null, new ParameterizedTypeReference<>() {}
            );
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
