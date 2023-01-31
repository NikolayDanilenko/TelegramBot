package homeproject.dao;

import homeproject.entity.AppDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface AppDocDAO extends JpaRepository<AppDoc, Long> {
}
