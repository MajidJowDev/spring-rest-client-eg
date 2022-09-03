package mjz.springframework.springrestclienteg.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean // since the restTemplate method is dependent to RestTemplateBuilder, we need to add this also as a Bean
    public RestTemplateBuilder restTemplateBuilder() {

        return new RestTemplateBuilder();
    }

    // this is like a factory that we are going to request a RestTemplate from it, and this component will be auto-configured by spring boot
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.build();
    }

    /*
    // I do not know why but ApplicationContext does not recognized as a bean so in the integration test it can not be Autowired
    // it seems adding this bean here solves this issue
    @Bean
    public ApplicationContext applicationContext(){

        return new ApplicationContext(new StandardContext());
    }
    */

}
