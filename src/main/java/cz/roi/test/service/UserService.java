package cz.roi.test.service;

import cz.roi.test.dto.UserInfo;
import cz.roi.test.model.User;
import cz.roi.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    FacebookFetcher facebookFetcher;

    @Autowired
    UserRepository userRepository;

    // todo return something
    public void getUserInfo(String fbId, String accessToken) {
        UserInfo userInfo = facebookFetcher.getUserInfo(fbId, accessToken);
        updateUser(userInfo);

//        Map userPhotos = facebookFetcher.getUserPhotos(fbId, accessToken);
//        updatePhotos;
//        if (userPhotos.get("next") != null) {
            // todo iterate over photos
            // updatePhotos;
//        }
    }

    private void updateUser(UserInfo userInfo) {
        User user = userRepository.findById(userInfo.id).orElse(new User());
        if (user.getId() == null) {
            user.setId(userInfo.id);
        }
        user.setName(userInfo.name);
        user.setGender(userInfo.gender);
        user.setProfilePictureUrl((userInfo.picture != null && userInfo.picture.data != null) ? userInfo.picture.data.url : null);
        userRepository.save(user);
        System.out.println("Saved "+user);
    }
}
