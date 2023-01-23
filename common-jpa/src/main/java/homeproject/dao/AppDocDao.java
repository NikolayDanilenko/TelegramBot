package homeproject.dao;

import homeproject.entity.AppDoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppDocDao extends JpaRepository<AppDoc, Long> {
}
