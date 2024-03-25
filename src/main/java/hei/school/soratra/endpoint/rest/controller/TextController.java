package hei.school.soratra.endpoint.rest.controller;

import hei.school.soratra.DTO.TextURL;
import hei.school.soratra.service.TextService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/soratra/")
public class TextController {
  private final TextService service;

  @PutMapping(value = "{id}")
  public TextURL putModifiedText(
      @PathVariable(name = "id") String id, @RequestBody(required = false) byte[] file) {
    try {
      return service.uploadAndModifyText(id, file);
    } catch (Exception e) {
      return null;
    }
  }
}
