package cz.roi.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * User entity
 */
@Data
@Entity
@Table(name = "fb_user")
@NoArgsConstructor
public class User implements Serializable {

    @Id
    private String id;

    private String name;

    private String gender;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Photo> photos;

    public User(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
