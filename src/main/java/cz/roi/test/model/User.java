package cz.roi.test.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "fb_user")
public class User implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private String id;

        @NotBlank
        private String name;

        @NotBlank
        private String gender;

        @Column(name = "profile_picture_url")
        private String profilePictureUrl;

        @JsonIgnore
        @OneToMany(mappedBy = "user")
        private Set<Photo> photos;

}
