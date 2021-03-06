package requests;

import model.Token;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

public class AuthRequests {

    private String host;

    public AuthRequests() {
        if(System.getenv("authDomain") == null){
            host = "http://localhost:3004/validate";
        } else {
            host = "http://" + System.getenv("authDomain") + ":3004/validate";
        }
    }

    public boolean postCheckAuth(String tokenValue){
        Token token = new Token(tokenValue);

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Token> httpEntity = new HttpEntity<Token>(token, requestHeaders);

        try{
            ResponseEntity<String> response = restTemplate.exchange(host, HttpMethod.POST, httpEntity, String.class);
            if(response.getStatusCodeValue() == 200){
                return true;
            } else {
                return false;
            }
        } catch (HttpClientErrorException e){
            return false;
        }
    }

}
