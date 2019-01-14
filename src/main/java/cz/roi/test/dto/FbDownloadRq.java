package cz.roi.test.dto;

/**
 * Request to get FB information about one user
 */
public class FbDownloadRq {
    public String fbId;
    public String accessToken;

    @Override
    public String toString() {
        return "FbDownloadRq{" +
                "fbId='" + fbId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
