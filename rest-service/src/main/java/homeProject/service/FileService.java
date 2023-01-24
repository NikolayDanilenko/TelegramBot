package homeProject.service;

import homeproject.entity.AppDoc;
import homeproject.entity.AppPhoto;
import homeproject.entity.BinaryContent;
import org.springframework.core.io.FileSystemResource;

public interface FileService {
    AppDoc getDocument(String id);
    AppPhoto getPhoto(String id);
    FileSystemResource getFileSystemResource (BinaryContent binaryContent);
}
