package hei.school.soratra.repository;

import hei.school.soratra.repository.model.TextInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextInformationRepository extends JpaRepository<TextInformation, String> {}
