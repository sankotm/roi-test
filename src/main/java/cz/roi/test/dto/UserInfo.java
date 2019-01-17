package cz.roi.test.dto;

/**
 * User DTO
 */
public class UserInfo {
    public String id;
    public String name;
    public String gender;
    public Picture picture;

    public static class Picture {
        public PictureData data;
    }

    public static class PictureData {
        public Integer height;
        public Integer width;
        public Boolean is_silhouette;
        public String url;
    }
}
