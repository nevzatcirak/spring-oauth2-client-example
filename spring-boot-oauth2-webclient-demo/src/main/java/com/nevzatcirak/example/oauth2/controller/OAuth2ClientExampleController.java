package com.nevzatcirak.example.oauth2.controller;

import com.nevzatcirak.example.oauth2.model.Dummy;
import com.nevzatcirak.example.oauth2.model.Result;
import com.nevzatcirak.example.oauth2.support.HttpAPIResourceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;


import static com.nevzatcirak.example.oauth2.config.DefaultOAuth2AuthorizedClientExchangeFilter.authentication;
import static com.nevzatcirak.example.oauth2.config.DefaultOAuth2AuthorizedClientExchangeFilter.clientRegistrationId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Nevzat ÇIRAK,
 * @mail nevzatcirak17@gmail.com
 * Created by nevzatcirak at 25.12.2019.
 */
@Controller
public class OAuth2ClientExampleController extends HttpAPIResourceSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2ClientExampleController.class);

    @Value("${spring.security.oauth2.examples.resource-server-url}")
    private String resourceServerUrl;

    @Autowired
    private WebClient webClient;

    @GetMapping(value={"/", "/index"}, produces= MediaType.TEXT_HTML_VALUE)
    public String index() {
        LOGGER.info(">>> goto index");
        return "index";
    }

    @GetMapping(value = "/jokes", params="grant_type=client_credentials", produces=MediaType.TEXT_HTML_VALUE)
    public String getJokesByClientCredentials(Model model) {
        Map<String,Object> parameter = new HashMap<String,Object>();
        parameter.put("type", "");
        parameter.put("page", 1);
        parameter.put("count", 10);
        //OAuth2 - client_credentials
        List<Dummy> jokeList = getJokeList(parameter, clientRegistrationId("default-client-client-credentials").andThen(authentication(null)));
        model.addAttribute("jokeList", jokeList);
        return "jokes";
    }

    @GetMapping(value = "/jokes", params="grant_type=authorization_code", produces=MediaType.TEXT_HTML_VALUE)
    public String getJokesByAuthorizationCode(Model model) {
        Map<String,Object> parameter = new HashMap<String,Object>();
        parameter.put("type", "");
        parameter.put("page", 1);
        parameter.put("count", 10);
        //通过OAuth2 - authorization_code模式获取Joke列表,需要登录
        List<Dummy> jokeList = getJokeList(parameter, clientRegistrationId("default-client-authorization-code"));
        model.addAttribute("jokeList", jokeList);
        return "jokes";
    }

    @GetMapping(value = "/jokes", params="grant_type=password", produces=MediaType.TEXT_HTML_VALUE)
    public String getJokesByPassword(Model model) {
        Map<String,Object> parameter = new HashMap<String,Object>();
        parameter.put("type", "");
        parameter.put("page", 1);
        parameter.put("count", 10);
        //通过OAuth2 - authorization_code模式获取Joke列表,需要登录
        List<Dummy> jokeList = getJokeList(parameter, clientRegistrationId("default-client-password").andThen(authentication(null)));
        model.addAttribute("jokeList", jokeList);
        return "jokes";
    }

    protected List<Dummy> getJokeList(Map<String,Object> parameter, Consumer<Map<String, Object>> attributesConsumer) {
        LOGGER.info(">>> getJokeList({})", parameter);
        Result<List<Dummy>> result = this.webClient
                .get()
                .uri(resourceServerUrl + "/api/joke/list?type={type}&page={page}&count={count}", parameter)
                .attributes(attributesConsumer)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Result<List<Dummy>>>() {})
                .block();
        return result.getData();
    }

}
