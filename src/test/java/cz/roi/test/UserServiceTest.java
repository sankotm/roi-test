package cz.roi.test;

import cz.roi.test.dto.PaginatedResult;
import cz.roi.test.dto.PhotoInfo;
import cz.roi.test.dto.UserInfo;
import cz.roi.test.model.User;
import cz.roi.test.repository.PhotoRepository;
import cz.roi.test.repository.UserRepository;
import cz.roi.test.service.FacebookFetcher;
import cz.roi.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService = new UserService();

    @Mock
    FacebookFetcher facebookFetcher;

    @Mock
    UserRepository userRepository;

    @Mock
    PhotoRepository photoRepository;

    /**
     * Test processing of user with no photos
     * @throws UnsupportedEncodingException
     */
    @Test
    public void getUserNoPhotos() throws UnsupportedEncodingException {
        UserInfo userInfo = new UserInfo();
        userInfo.id = "111";
        User user = new User(userInfo.id);
        PaginatedResult noPhotos = new PaginatedResult();
        noPhotos.data = new ArrayList<>();
        noPhotos.paging = new PaginatedResult.Paging();

        when(facebookFetcher.getUserInfo(Mockito.any(), Mockito.any())).thenReturn(userInfo);
        when(facebookFetcher.getPaginatedResult(Mockito.any(), Mockito.any())).thenReturn(noPhotos);
        when(userRepository.save(Mockito.any())).thenReturn(user);

        String userId = userService.getUserInfo("", "");

        assert userId.equals("111");
        verify(userRepository, times(1)).save(Mockito.any());
    }

    /**
     * Test processing of user with one page of photos
     * @throws UnsupportedEncodingException
     */
    @Test
    public void getUserOnePageOfPhotos() throws UnsupportedEncodingException {
        UserInfo userInfo = new UserInfo();
        userInfo.id = "111";
        User user = new User(userInfo.id);
        PaginatedResult onePage = new PaginatedResult();
        onePage.data = new ArrayList<>();
        onePage.data.add(new PhotoInfo());
        onePage.paging = new PaginatedResult.Paging();

        when(facebookFetcher.getUserInfo(Mockito.any(), Mockito.any())).thenReturn(userInfo);
        when(facebookFetcher.getPaginatedResult(Mockito.any(), Mockito.any())).thenReturn(onePage);
        when(userRepository.save(Mockito.any())).thenReturn(user);
        when(photoRepository.save(Mockito.any())).thenReturn(null);

        String userId = userService.getUserInfo("", "");

        assert userId.equals("111");
        verify(userRepository, times(1)).save(Mockito.any());
        verify(photoRepository, times(1)).save(Mockito.any());

    }

    /**
     * Test processing of user with two pages of photos
     * @throws UnsupportedEncodingException
     */
    @Test
    public void getUserTwoPagesOfPhotos() throws UnsupportedEncodingException {
        UserInfo userInfo = new UserInfo();
        userInfo.id = "111";
        User user = new User(userInfo.id);
        PaginatedResult pageOne = new PaginatedResult();
        pageOne.data = new ArrayList<>();
        pageOne.data.add(new PhotoInfo());
        pageOne.paging = new PaginatedResult.Paging();
        pageOne.paging.next = "url to next one";

        PaginatedResult pageTwo = new PaginatedResult();
        pageTwo.data = new ArrayList<>();
        pageTwo.data.add(new PhotoInfo());
        pageTwo.paging = new PaginatedResult.Paging();

        when(facebookFetcher.getUserInfo(Mockito.any(), Mockito.any())).thenReturn(userInfo);
        when(facebookFetcher.getPaginatedResult(Mockito.any(), Mockito.any())).thenReturn(pageOne);
        when(facebookFetcher.getPaginatedResult("url to next one")).thenReturn(pageTwo);
        when(userRepository.save(Mockito.any())).thenReturn(user);
        when(photoRepository.save(Mockito.any())).thenReturn(null);

        String userId = userService.getUserInfo("", "");

        assert userId.equals("111");
        verify(userRepository, times(1)).save(Mockito.any());
        verify(photoRepository, times(2)).save(Mockito.any());

    }

}