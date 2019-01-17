package cz.roi.test.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Photo DTO
 */
public class PhotoInfo {

    /*
    "id": "107363757031543",
      "name": "nothing",
      "link": "https://www.facebook.com/photo.php?fbid=107363757031543&set=a.107363787031540&type=3",
      "album": {
        "name": "Timeline Photos",
        "id": "107363787031540"
      },
      "images": [
        {
          "height": 1512,
          "source": "https://scontent.xx.fbcdn.net/v/t1.0-9/50124823_107363760364876_168045062221987840_o.jpg?_nc_cat=101&_nc_ht=scontent.xx&oh=4d13327aa49125b80facc6f2c6520368&oe=5CCF954B",
          "width": 2016
        },
     */

    public String id;
    public String name;
    public String link;
    public Album album;
    public List<PhotoImage> images = new ArrayList<>();

    public static class Album {
        public String name;
    }

    public static class PhotoImage {
        public String source;
    }
}
