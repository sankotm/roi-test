package cz.roi.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "photo")
@NoArgsConstructor
public class Photo implements Serializable {

    @Id
    private String id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Column(name = "fb_url")
    private String fbUrl;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "reactions_summary")
    private String reactionsSummary;

    public Photo(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id.equals(photo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", fbUrl='" + fbUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", albumName='" + albumName + '\'' +
                ", reactionsSummary='" + reactionsSummary + '\'' +
                '}';
    }
}
