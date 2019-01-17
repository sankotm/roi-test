package cz.roi.test.dto;

import java.util.List;

/**
 * Facebook response with paginated data
 */
public class PaginatedResult {
    public List<PhotoInfo> data; // should be extended to generic content
    public Paging paging;

    public static class Paging {
        public String previous;
        public String next;
    }
}
