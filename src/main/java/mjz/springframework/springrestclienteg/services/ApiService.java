package mjz.springframework.springrestclienteg.services;

import mjz.springframework.api.domain.User;

import java.util.List;

public interface ApiService {

    List<User> getUsers(Integer limit);
}
