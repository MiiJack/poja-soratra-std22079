package hei.school.soratra.repository.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "text_information")
public class TextInformation {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "text_bucket_key")
  private String textBucketKey;

  @Column(name = "original_bucket_key")
  private String originalBucketKey;

  @CreationTimestamp
  @Column(name = "creation_datetime", nullable = false)
  private Timestamp creationDatetime;

  @UpdateTimestamp
  @Column(name = "update_datetime", nullable = false)
  private Timestamp updateDatetime;
}
