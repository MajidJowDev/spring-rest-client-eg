package mjz.springframework.springrestclienteg.services;

import mjz.springframework.api.domain.User;
import mjz.springframework.api.domain.UserData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private RestTemplate restTemplate;

    //because we have a constructor, Spring will automatically autowires a RestTemplate for us
    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<User> getUsers(Integer limit) {


        //UserData userData = restTemplate.getForObject("http://apifaketory.com/api/user?limit=" + limit, UserData.class); // this uri is no longer working

        // does a get request to the uri and bind the result to the class we want
        // the result returned by API (JSON Object) will bind to the properties of our domain classes based on the name of the properties
        // and Jackson will do the bindings automatically
        UserData userData = restTemplate.getForObject("https://dummyjson.com/users?limit=" + limit, UserData.class);
        return userData.getUsers();
    }
}
