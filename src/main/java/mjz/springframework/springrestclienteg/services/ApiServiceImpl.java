package mjz.springframework.springrestclienteg.services;

import mjz.springframework.api.domain.User;
import mjz.springframework.api.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private RestTemplate restTemplate;

    private final String api_url;

    //because we have a constructor, Spring will automatically autowires a RestTemplate for us
    public ApiServiceImpl(RestTemplate restTemplate, @Value("${api.url}") String api_url) {
        this.restTemplate = restTemplate;
        this.api_url = api_url;
    }

    @Override
    public List<User> getUsers(Integer limit) {


        //UserData userData = restTemplate.getForObject("http://apifaketory.com/api/user?limit=" + limit, UserData.class); // this uri is no longer working

        // does a get request to the uri and bind the result to the class we want
        // the result returned by API (JSON Object) will bind to the properties of our domain classes based on the name of the properties
        // and Jackson will do the bindings automatically

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(api_url)
                .queryParam("limit", limit);

        UserData userData = restTemplate.getForObject(uriBuilder.toUriString(), UserData.class);


       // UserData userData = restTemplate.getForObject("https://dummyjson.com/users?limit=" + limit, UserData.class);
        return userData.getUsers();
    }
}
