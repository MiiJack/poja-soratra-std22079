package hei.school.soratra.repository.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "text_information")
public class TextInformation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "text_bucket_key")
  private String textBucketKey;

  @Column(name = "original_bucket_key")
  private String originalBucketKey;

  @Column(name = "creation_datetime", nullable = false)
  private OffsetDateTime creationDatetime;

  @Column(name = "update_datetime", nullable = false)
  private OffsetDateTime updateDatetime;
}
