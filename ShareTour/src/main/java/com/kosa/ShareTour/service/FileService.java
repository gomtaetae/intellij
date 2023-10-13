package com.kosa.ShareTour.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@Log
public class FileService {
    public String uploadFile(String uploadPath, String originalFileName, byte[] fileDate) throws Exception{
        UUID uuid = UUID.randomUUID();      //UUID는 서로다른 개체들을 구별하기위해 이름을 부여하기위해 사용
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String saveFileName = uuid.toString() + extension;  //UUID로 받은 값과 원래 파일의 이름의 확장자를 조합해서 저장될 파일 이름을 만듬
        String fileUpLoadFullUrl = uploadPath + "/" + saveFileName;

        FileOutputStream fos = new FileOutputStream(fileUpLoadFullUrl);
        //FileOutputStream은 바이트 단위의 출력을 내보내는 클래스, 파일이 저장될 위치와 이름을 넘겨 파일 출력 스트림 생성
        fos.write(fileDate);
        //fileDate를 파일 출력 스트림에 입력
        fos.close();
        return saveFileName;
        //업로드된 파일이름을 반환
    }

    public void deleteFile(String filePath) throws Exception {
        File deletFile = new File(filePath);

        if(deletFile.exists()) {
            deletFile.delete();
            log.info("파일을 삭제하였습니다.");
        } else {
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
