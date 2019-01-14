package cz.roi.test.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
//@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
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

        @OneToMany(mappedBy = "user")
        private Set<Photo> photos;

//        @Column(nullable = false, updatable = false)
//        @Temporal(TemporalType.TIMESTAMP)
//        @CreatedDate
//        private Date createdAt;
//
//        @Column(nullable = false)
//        @Temporal(TemporalType.TIMESTAMP)
//        @LastModifiedDate
//        private Date updatedAt;
}
