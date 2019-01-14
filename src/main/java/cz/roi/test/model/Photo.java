package cz.roi.test.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "photo")
public class Photo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

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

}
