package cz.roi.test;

import com.google.gson.Gson;
import cz.roi.test.model.Photo;
import cz.roi.test.model.User;
import cz.roi.test.repository.PhotoRepository;
import cz.roi.test.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ROITestApplicationIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    private static final String USER_ID = "666";

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/users");
    }

    @After
    public void tearDown() {
        userRepository.findById(USER_ID).ifPresent(user -> userRepository.delete(user));
    }

    /**
     * Check getting non-existing usr
     *
     * @throws Exception
     */
    @Test
    public void getNonExistingUser() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/123", String.class);
        assertThat("Response code is not 404", response.getStatusCodeValue() == 404);
    }

    /**
     * Check getting existing user
     *
     * @throws Exception
     */
    @Test
    public void getExistingUser() throws Exception {
        User user = new User();
        user.setId(USER_ID);
        user.setName("Pan ryba");
        user.setGender("fish");
        user.setProfilePictureUrl("http://sturgeon.cz");
        userRepository.save(user);

        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + USER_ID, String.class);
        assertThat("Response code is not 200", response.getStatusCodeValue() == 200);

        System.out.println(response.getBody());

        Map responseMap = new Gson().fromJson(response.getBody(), Map.class);
        assertThat(responseMap.get("id"), equalTo(user.getId()));
        assertThat(responseMap.get("name"), equalTo(user.getName()));
        assertThat(responseMap.get("gender"), equalTo(user.getGender()));
        assertThat(responseMap.get("profilePictureUrl"), equalTo(user.getProfilePictureUrl()));
    }

    /**
     * Check deleting user
     *
     * @throws Exception
     */
    @Test
    public void deleteUser() throws Exception {
        userRepository.save(new User(USER_ID));
        assertThat(userRepository.findById(USER_ID).isPresent(), equalTo(true));
        template.delete(base.toString() + "/" + USER_ID);
        assertThat(userRepository.findById(USER_ID).isPresent(), equalTo(false));
    }

    /**
     * Check getting existing user with no photos
     *
     * @throws Exception
     */
    @Test
    public void getExistingEmptyPhotos() throws Exception {
        User user = new User();
        user.setId(USER_ID);
        userRepository.save(user);

        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + USER_ID + "/photos", String.class);
        assertThat("Response code is not 200", response.getStatusCodeValue() == 200);
        assertThat(response.getBody(), equalTo("[]"));
    }

    /**
     * Check getting existing user with photos
     *
     * @throws Exception
     */
    @Test
    public void getExistingPhotos() throws Exception {
        User user = new User();
        user.setId(USER_ID);
        userRepository.save(user);

        Photo photo1 = new Photo("1");
        photo1.setUser(user);
        photo1.setImageUrl("img1");
        photo1.setAlbumName(null);
        photo1.setFbUrl("fburl");
        photoRepository.save(photo1);

        Photo photo2 = new Photo("2");
        photo2.setUser(user);
        photo2.setImageUrl("img2");
        photo2.setAlbumName("album2");
        photo2.setFbUrl("fburl2");
        photoRepository.save(photo2);

        ResponseEntity<String> response = template.getForEntity(base.toString() + "/" + USER_ID + "/photos", String.class);
        assertThat("Response code is not 200", response.getStatusCodeValue() == 200);

        List responseList = new Gson().fromJson(response.getBody(), List.class);
        assertThat(responseList.size(), equalTo(2));
        Map photo1resp = (Map) responseList.get(0);
        assertThat(photo1resp.get("id"), equalTo(photo1.getId()));
        assertThat(photo1resp.get("fbUrl"), equalTo(photo1.getFbUrl()));
        assertThat(photo1resp.get("imageUrl"), equalTo(photo1.getImageUrl()));
        assertThat(photo1resp.get("albumName"), equalTo(photo1.getAlbumName()));

        Map photo2resp = (Map) responseList.get(1);
        assertThat(photo2resp.get("id"), equalTo(photo2.getId()));
        assertThat(photo2resp.get("fbUrl"), equalTo(photo2.getFbUrl()));
        assertThat(photo2resp.get("imageUrl"), equalTo(photo2.getImageUrl()));
        assertThat(photo2resp.get("albumName"), equalTo(photo2.getAlbumName()));
    }

    /**
     * Check getting photos of not-existing user
     *
     * @throws Exception
     */
    @Test
    public void getNonExistingUserPhotos() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString() + "/123/photos", String.class);
        assertThat("Response code is not 404", response.getStatusCodeValue() == 404);
    }

}

