package cz.roi.test.service;

import cz.roi.test.dto.PaginatedResult;
import cz.roi.test.dto.PhotoInfo;
import cz.roi.test.dto.UserInfo;
import cz.roi.test.model.Photo;
import cz.roi.test.model.User;
import cz.roi.test.repository.PhotoRepository;
import cz.roi.test.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    FacebookFetcher facebookFetcher;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    public String getUserInfo(String fbId, String accessToken) throws UnsupportedEncodingException {

        UserInfo userInfo = facebookFetcher.getUserInfo(fbId, accessToken);
        User user = updateUser(userInfo);

        PaginatedResult userPhotos = facebookFetcher.getPaginatedResult(fbId, accessToken);
        List<PhotoInfo> photoInfos = userPhotos.data;
        while (userPhotos.paging.next != null) {
            userPhotos = facebookFetcher.getPaginatedResult(userPhotos.paging.next);
            photoInfos.addAll(userPhotos.data);
        }
        photoInfos.forEach(photoInfo -> updatePhoto(photoInfo, user));
        return user.getId();

    }

    private User updateUser(UserInfo userInfo) {
        User user = userRepository.findById(userInfo.id).orElse(new User(userInfo.id));
        user.setName(userInfo.name);
        user.setGender(userInfo.gender);
        user.setProfilePictureUrl((userInfo.picture != null && userInfo.picture.data != null) ? userInfo.picture.data.url : null);
        logger.info("Saving " + user);
        userRepository.save(user);
        return user;
    }

    private void updatePhoto(PhotoInfo info, User user) {
        Photo photo = photoRepository.findById(info.id).orElse(new Photo(info.id));
        photo.setUser(user);
        photo.setFbUrl(info.link);
        photo.setAlbumName(info.album != null ? info.album.name : null);
        photo.setImageUrl(info.images!=null && info.images.size()>0 ? info.images.get(0).source : null);
        logger.info("Saving " + photo);
        photoRepository.save(photo);
    }
}
