package hei.school.soratra.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TextURL {
  private String original_url;
  private String transformed_url;
}
