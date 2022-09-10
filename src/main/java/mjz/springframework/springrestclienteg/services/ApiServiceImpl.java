package mjz.springframework.springrestclienteg.services;

import mjz.springframework.api.domain.User;
import mjz.springframework.api.domain.UserData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.function.Consumer;

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

    @Override
    public Flux<User> getUsers(Mono<Integer> limit) {


        return WebClient
                .create(api_url) // create a request
                .get() // specifying that this a get method
                // since the blocking does not work in this app, I set the limit value directly
                .uri(uriBuilder -> uriBuilder.queryParam("limit", 7 /*limit.subscribe(Integer::valueOf) *//*limit.subscribeOn(Schedulers.boundedElastic())*//*limit.block()*/).build()) // build the query uri
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(resp -> resp.bodyToMono(UserData.class)) // mapping the response object to UserData class
                .flatMapIterable(UserData::getUsers); // creating a flux of users

        /*
        // we should use this method since using exchange() method is deprecated
        return WebClient
                .create(api_url) // create a request
                .get() // specifying that this a get method
                .uri(uriBuilder -> uriBuilder..queryParam("limit", limit).build()) // build the query uri
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(resp -> resp.bodyToMono(UserData.class))
                //.publishOn(Schedulers.elastic())
                .flatMapIterable(UserData::getUsers); // creating a flux of users

         */

    }
}
