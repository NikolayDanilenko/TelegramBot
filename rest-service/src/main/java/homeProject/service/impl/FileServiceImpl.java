package homeProject.service.impl;

import homeProject.service.FileService;
import homeproject.dao.AppDocDAO;
import homeproject.dao.AppPhotoDAO;
import homeproject.entity.AppDoc;
import homeproject.entity.AppPhoto;
import homeproject.entity.BinaryContent;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Log4j
@Service
public class FileServiceImpl implements FileService {
    private final AppDocDAO appDocDAO;
    private final AppPhotoDAO appPhotoDAO;

    public FileServiceImpl(AppDocDAO appDocDAO, AppPhotoDAO appPhotoDAO) {
        this.appDocDAO = appDocDAO;
        this.appPhotoDAO = appPhotoDAO;
    }

    @Override
    public AppDoc getDocument(String docId) {
        var id = Long.parseLong(docId);

        return appDocDAO.findById(id).orElse(null);
    }

    @Override
    public AppPhoto getPhoto(String photoId) {
        var id = Long.parseLong(photoId);

        return appPhotoDAO.findById(id).orElse(null);
    }

    @Override
    public FileSystemResource getFileSystemResource(BinaryContent binaryContent) {
        try {
            File temp = File.createTempFile("tempFile", ".bin");
            temp.deleteOnExit();
            FileUtils.writeByteArrayToFile(temp, binaryContent.getFileAsArrayOfBytes());
            return new FileSystemResource(temp);
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }
}
