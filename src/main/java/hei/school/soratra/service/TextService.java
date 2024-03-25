package hei.school.soratra.service;

import hei.school.soratra.DTO.TextURL;
import hei.school.soratra.file.BucketComponent;
import hei.school.soratra.repository.TextInformationRepository;
import hei.school.soratra.repository.model.TextInformation;
import java.io.*;
import java.rmi.RemoteException;
import java.time.Duration;
import java.util.Scanner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TextService {
  private final BucketComponent bucketComponent;
  private final TextInformationRepository textInformationRepository;
  private final String TEXT_BUCKET_DIRECTORY = "text/";

  @Transactional
  public void uploadTextFile(File textFile, String textName) {
    String bucketKey = TEXT_BUCKET_DIRECTORY + textName;
    bucketComponent.upload(textFile, bucketKey);
    boolean isDeleted = textFile.delete();
    if (!isDeleted) {
      throw new RuntimeException("file " + bucketKey + " is not deleted.");
    }
  }

  @Transactional
  public TextURL uploadAndModifyText(String id, byte[] file) throws IOException {
    if (file == null) {
      throw new RemoteException("Text file is mandatory");
    }
    String fileEndSuffix = ".txt";
    String newFileName = id + fileEndSuffix;
    String modifiedFileName = id + "-kai" + fileEndSuffix;

    File originalFile = File.createTempFile(newFileName, fileEndSuffix);
    File modifiedTempTextFile = File.createTempFile(modifiedFileName, fileEndSuffix);
    writeFileFromByteArray(file, originalFile);
    File modifiedTextFile = modifyTextContent(originalFile, modifiedTempTextFile);

    uploadTextFile(originalFile, newFileName);
    uploadTextFile(modifiedTextFile, modifiedFileName);
    TextInformation toSave =
        TextInformation.builder()
            .id(id)
            .originalBucketKey(TEXT_BUCKET_DIRECTORY + newFileName)
            .textBucketKey(TEXT_BUCKET_DIRECTORY + modifiedFileName)
            .build();
    textInformationRepository.save(toSave);
    return getTextURL(id);
  }

  public TextURL getTextURL(String textInformationId) {
    TextInformation textInformation =
        textInformationRepository
            .findById(textInformationId)
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Text information with id " + textInformationId + " does not exist"));

    String originalTextURL =
        bucketComponent
            .presign(textInformation.getOriginalBucketKey(), Duration.ofHours(1))
            .toString();
    String modifiedTextURL =
        bucketComponent.presign(textInformation.getTextBucketKey(), Duration.ofHours(1)).toString();

    return TextURL.builder().original_url(originalTextURL).transformed_url(modifiedTextURL).build();
  }

  private File writeFileFromByteArray(byte[] bytes, File file) {
    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(bytes);
      return file;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private File modifyTextContent(File originalFile, File outputFile) {
    try {
      Scanner scanner = new Scanner(originalFile);
      StringBuilder sb = new StringBuilder();
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        sb.append(line.toUpperCase()).append(System.lineSeparator());
      }
      scanner.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return outputFile;
  }
}
