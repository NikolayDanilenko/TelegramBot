package homeproject.service;

import homeproject.entity.AppDoc;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {
    AppDoc processDoc (Message externalMessage);
}
