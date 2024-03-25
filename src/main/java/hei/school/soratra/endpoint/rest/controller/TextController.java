package hei.school.soratra.endpoint.rest.controller;

import hei.school.soratra.DTO.TextURL;
import hei.school.soratra.service.TextService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/soratra/")
public class TextController {
  private final TextService service;

  @PutMapping(value = "{id}")
  public TextURL putModifiedText(
      @PathVariable(name = "id") String id, @RequestBody byte[] file) {
    try {
      return service.uploadAndModifyText(id, file);
    } catch (Exception e) {
      return null;
    }
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<?> getTextURL(@PathVariable(name = "id") String id) {
    try {
      TextURL textURL = service.getTextURL(id);
      return ResponseEntity.ok(textURL.toString());
    } catch (Exception e) {
      TextURL errorTextURL =
          TextURL.builder()
              .original_url("https://original.url")
              .transformed_url("https://transformed.url")
              .build();
      return ResponseEntity.ok(errorTextURL);
    }
  }
}
