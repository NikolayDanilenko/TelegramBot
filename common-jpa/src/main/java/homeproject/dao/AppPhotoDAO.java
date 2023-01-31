package homeproject.dao;

import homeproject.entity.AppPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public interface AppPhotoDAO extends JpaRepository<AppPhoto,Long> {
}
