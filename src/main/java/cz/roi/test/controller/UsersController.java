package cz.roi.test.controller;

import cz.roi.test.dto.FbDownloadRq;
import cz.roi.test.dto.GenericResponse;
import cz.roi.test.exception.ResourceNotFoundException;
import cz.roi.test.model.Photo;
import cz.roi.test.model.User;
import cz.roi.test.repository.PhotoRepository;
import cz.roi.test.repository.UserRepository;
import cz.roi.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Endpoint to work with user and photo information
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    UserService userService;

    /**
     * Fetches information about user and his photos form Facebook
     *
     * @param fbDownloadRq
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping
    public GenericResponse getUserInfos(@Valid @RequestBody FbDownloadRq fbDownloadRq) throws UnsupportedEncodingException {
        String userId = userService.getUserInfo(fbDownloadRq.fbId, fbDownloadRq.accessToken);
        return new GenericResponse(HttpStatus.OK, "All good. User ID: " + userId);
    }

    /**
     * Deletes the user and his photos from application DB (don’t delete photos on Facebook)
     *
     * @param fbId
     * @return
     */
    @DeleteMapping("/{id}")
    public GenericResponse deleteUser(@PathVariable(value = "id") String fbId) {
        User user = userRepository.findById(fbId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "fbId", fbId));

        // photos are cascaded on DB level so they will get deleted together with user
        userRepository.delete(user);
        return new GenericResponse(HttpStatus.OK, "User [" + fbId + "] deleted");
    }

    /**
     * User details (FB ID, name, gender, profile picture URL)
     *
     * @param fbId
     * @return
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") String fbId) {
        return userRepository.findById(fbId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "fbId", fbId));
    }


    /**
     * List of photos (each with: URL on FB, URL of image file, album name (if any)
     *
     * @param fbId
     * @return
     */
    @GetMapping("/{id}/photos")
    public List<Photo> getUserPhotos(@PathVariable(value = "id") String fbId) {
        // lookup user separately, so that exception is thrown when user does not exist
        userRepository.findById(fbId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "fbId", fbId));
        return photoRepository.findAllByUserId(fbId).orElse(new ArrayList<>());
    }

}
