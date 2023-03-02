import entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Client {
    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static final User USER_FOR_POST = new User(3L, "James", "Brown", (byte) 33);
    private static final String NEW_NAME = "Thomas";
    private static final String NEW_LAST_NAME = "Shelby";


    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseForGet = restTemplate.getForEntity(URL, String.class);
        String cookie = responseForGet.getHeaders().getFirst("Set-Cookie");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);

        HttpEntity<User> postEntity = new HttpEntity<>(USER_FOR_POST, httpHeaders);
        ResponseEntity<String> responseForPost = restTemplate.exchange(URL, HttpMethod.POST, postEntity, String.class);
        String firstPart = responseForPost.getBody();

        USER_FOR_POST.setName(NEW_NAME);
        USER_FOR_POST.setLastName(NEW_LAST_NAME);

        HttpEntity<User> putEntity = new HttpEntity<>(USER_FOR_POST, httpHeaders);
        ResponseEntity<String> responseForPut = restTemplate.exchange(URL, HttpMethod.PUT, putEntity, String.class);
        String secondPart = responseForPut.getBody();

        HttpEntity<User> deleteEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseForDelete = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, deleteEntity, String.class);
        String thirdPart = responseForDelete.getBody();

        System.out.println(firstPart + secondPart + thirdPart);
    }
}
