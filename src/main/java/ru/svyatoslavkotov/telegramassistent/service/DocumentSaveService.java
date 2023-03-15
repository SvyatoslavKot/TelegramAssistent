package ru.svyatoslavkotov.telegramassistent.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.svyatoslavkotov.telegramassistent.config.BotConfig;
import ru.svyatoslavkotov.telegramassistent.model.AppDocument;
import ru.svyatoslavkotov.telegramassistent.model.BinaryContent;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class DocumentSaveService {

    @Autowired
    private BotConfig botConfig;
    @Autowired
    RestTemplate restTemplate;

    private final String TELEGRAM_API = "https://api.telegram.org/bot";
    private final String GET_FILE_COMMAND = "/getFile?file_id=";
    private final String TELEGRAM_SERVICE_FILE = "https://api.telegram.org/file/bot";
    private final String DOCUMENT_PATH ="";
    private final String DOCUMENT_NAME = "School_Scheduled.docx";

    public AppDocument processDoc(Message telegramMessage) {
        String fileId = telegramMessage.getDocument().getFileId();
        ResponseEntity<String> response = getFilePath(fileId);
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            String filePath = String.valueOf(jsonObject.getJSONObject("result").getString("file_path"));
            byte[] fileInByte = downloadFile(filePath);
            saveDocument(DOCUMENT_PATH + DOCUMENT_NAME, fileInByte);

            BinaryContent transientBinaryContent = BinaryContent.builder()
                    .fileAsArrayOfBytes(fileInByte)
                    .build();
            Document document = telegramMessage.getDocument();
            return AppDocument.builder().telegramFileId(document.getFileId())
                    .docName(document.getFileName())
                    .binaryContent(transientBinaryContent)
                    .mineType(document.getMimeType())
                    .fileSize(document.getFileSize())
                    .build();
        }else {
            throw new RuntimeException("Bad response from telegram service: " + response);
        }

    }

    private ResponseEntity<String> getFilePath(String fileId) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = TELEGRAM_API + botConfig.getToken() + GET_FILE_COMMAND + fileId;
        return restTemplate.exchange(url,
                HttpMethod.GET,
                request,
                String.class);
    }

    private byte[] downloadFile(String filePath) {
        URL urlObj = null;
        String url = TELEGRAM_SERVICE_FILE + botConfig.getToken() + "/" + filePath;
        try{
            urlObj = new URL(url);
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try(InputStream is = urlObj.openStream()){
            BufferedInputStream bis = new BufferedInputStream(is);
            return bis.readAllBytes();
        } catch (IOException e) {
            throw  new RuntimeException(urlObj.toExternalForm(), e);
        }
    }

    public boolean saveDocument(String pathName, byte[] documentBytes) {
        if (documentBytes.length > 0) {
            try{
                String pathname = pathName;
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(pathname)));
                stream.write(documentBytes);
                stream.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }
}
