package homeproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import homeproject.entity.RawData;

public interface RawDataDAO extends JpaRepository<RawData,Long> {
}
