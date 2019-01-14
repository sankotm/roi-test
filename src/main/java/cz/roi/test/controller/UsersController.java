package cz.roi.test.controller;

import cz.roi.test.dto.FbDownloadRq;
import cz.roi.test.dto.GenericResponse;
import cz.roi.test.exception.ResourceNotFoundException;
import cz.roi.test.model.Photo;
import cz.roi.test.model.User;
import cz.roi.test.repository.PhotoRepository;
import cz.roi.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    // POST /users - request body containing JSON with user FB ID and access token
    @PostMapping
    public GenericResponse getUserInfos(@Valid @RequestBody FbDownloadRq fbDownloadRq) {
        // todo get infos
        System.out.println(fbDownloadRq);
        return new GenericResponse(true, null, "TODO get user information from FB");
    }

    //DELETE /users/{user_fb_id} - deletes the user and his photos from application DB (don’t delete photos on Facebook)
    @DeleteMapping("/{id}")
    public GenericResponse deleteUser(@PathVariable(value = "id") String fbId) {
        // todo delete user and his photos
        return new GenericResponse(true, null, "TODO delete user [" + fbId + "] information");
    }

    //GET /users/{user_fb_id} - responding with the user details (FB ID, name, gender, profile picture URL)
    @GetMapping("/{id}")
    public User getUser(@PathVariable(value = "id") String fbId) {
        return userRepository.findById(fbId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "fbId", fbId));
    }


    //GET /users/{user_fb_id}/photos - responding with list of photos (each with: URL on FB, URL of image file, album name (if any), numbers of reactions grouped by type); can optionally support sorting too (for example by sum of reactions)
    @GetMapping("/{id}/photos")
    public List<Photo> getUserPhotos(@PathVariable(value = "id") String fbId) {
        return photoRepository.findAllByUserId(fbId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "fbId", fbId));
    }

}
