package homeproject.service;

import homeproject.entity.AppDoc;
import homeproject.entity.AppPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {
    AppDoc processDoc (Message telegramMessage);
    AppPhoto processPhoto (Message telegramMessage);
}
