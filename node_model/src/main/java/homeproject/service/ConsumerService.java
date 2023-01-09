package homeproject.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface ConsumerService {
    void consumeTextMessage (Update update);
    void consumeDocMessage (Update update);
    void consumePhotoMessage (Update update);
}
