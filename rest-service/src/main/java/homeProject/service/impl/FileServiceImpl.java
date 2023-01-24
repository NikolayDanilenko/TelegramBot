package homeProject.service.impl;

import homeProject.service.FileService;

import homeproject.dao.AppDocDAO;
import homeproject.dao.AppPhotoDAO;
import homeproject.entity.AppDoc;
import homeproject.entity.AppPhoto;
import homeproject.entity.BinaryContent;
import lombok.extern.log4j.Log4j;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;


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
        return null;
    }

}