package homeproject.dao;

import homeproject.entity.BinaryContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinaryContentDao extends JpaRepository<BinaryContent,Long> {
}
