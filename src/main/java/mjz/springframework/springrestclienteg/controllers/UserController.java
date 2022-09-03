package mjz.springframework.springrestclienteg.controllers;


import lombok.extern.slf4j.Slf4j;
import mjz.springframework.springrestclienteg.services.ApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Controller
public class UserController {

    private ApiService apiService;

    public UserController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping({"", "/", "/index"})
    public String index(){
        return "index";
    }

    @PostMapping("/users")
    public String formPost(Model model, ServerWebExchange serverWebExchange){

        // this line gets the form data (we used to use the request parameters)
        // but the Reactive way is to use the ServerWebExchange, so we get the limit of API result from the form data
         MultiValueMap<String, String> map = serverWebExchange.getFormData().block();
        //Mono<MultiValueMap<String, String>>  monoMap = serverWebExchange.getFormData();
        /*MultiValueMap<String, String> map = monoMap.subscribe();*/

        // getting the data from the "limit" field in the form that is submitted
        Integer limit = Integer.valueOf(map.get("limit").get(0));

        log.debug("Received Limit value: " + limit);
        //default if null or zero
        if(limit == null || limit == 0){
            log.debug("Setting limit to default of 10");
            limit = 10;
        }

        model.addAttribute("users", apiService.getUsers(limit));

        return "userlist";


    }
}
