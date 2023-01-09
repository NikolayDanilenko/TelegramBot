package homeproject.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import homeproject.dao.AppUserDAO;
import homeproject.dao.RawDataDAO;
import homeproject.entity.AppUser;
import homeproject.entity.RawData;
import homeproject.service.MainService;
import homeproject.service.ProducerService;

import static homeproject.service.enums.ServiceCommands.CANSEL;
import static homeproject.entity.enums.UserState.BASIC_STATE;
import static homeproject.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;


@Service
@Log4j
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;

    private final AppUserDAO appUserDAO;

    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService, AppUserDAO appUserDAO) {
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var userState = appUser.getState();
        var text = update.getMessage().getText();
        var output = "";

        if (CANSEL.equals(text)){
            output = canselProcess(appUser);
        }else if(BASIC_STATE.equals(userState)){
            output= processServiceCommand(appUser, text);
        }else if(WAIT_FOR_EMAIL_STATE.equals(userState)){
            //TODO
        }else{
            log.error("Unknown user state: " + userState);
            output = "Неизвестная ошибка! Введите /cansel и попробуйте снова!";
        }


        var chatId = update.getMessage().getChatId();
        sendAnswer(output,chatId);

    }

    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if(isNotAllowToSendContent(chatId, appUser)){
            return ;
        }
        var answer = "Фото успешно загружено! Ссылка на скачивание: http://homeproject.ru/get-photo/1337";
        sendAnswer(answer,chatId);
    }

    private boolean isNotAllowToSendContent(Long chatId, AppUser appUser) {
        var userState = appUser.getState();
        if(!appUser.getIsActive()){
            var error = "Зарегистрируйтесь или активируйте свою учетную запись для загрузки контента.";
            sendAnswer(error, chatId);
            return true;
        }else if(!BASIC_STATE.equals(userState)){
            var error = "Отмените текщую команду с помощью /cansel";
            sendAnswer(error, chatId);
            return true;
        }
        return false;
    }

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if(isNotAllowToSendContent(chatId, appUser)){
            return ;
        }
        var answer = "Документ успешно загружен! Ссылка на скачивание: http://homeproject.ru/get-doc/1337";
        sendAnswer(answer,chatId);

    }

    private void sendAnswer(String output, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);
        producerService.producerAnswer(sendMessage);
    }

    private String processServiceCommand(AppUser appUser, String text) {
        return "";
    }

    private String canselProcess(AppUser appUser) {

        appUser.setState(BASIC_STATE);
        appUserDAO.save(appUser);
        return "Команда отменена";
    }

    private AppUser findOrSaveAppUser(Update update){
        User telegramUser = update.getMessage().getFrom();
        AppUser persistentAppUser = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());
        if(persistentAppUser == null){
            AppUser transientAppUser = AppUser.builder()
                    .telegramUserId((telegramUser.getId()))
                    .username(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .isActive(true)
                    .state(BASIC_STATE)
                    .build();

            return appUserDAO.save(transientAppUser);
        }
        return persistentAppUser;
    }
    private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .event(update)
                .build();

        rawDataDAO.save(rawData);
    }
}
