package com.management.web.controller;

import com.management.chatbot.domain.Member;
import com.management.web.service.GiftcardService;
import com.management.chatbot.service.MemberService;
import com.management.web.service.dto.GiftcardDto;
import com.management.chatbot.service.dto.MemberResponseDto;
import com.management.web.service.dto.GiftcardMemberDto;
import com.management.web.service.dto.OrderSaveDto;
import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GiftcardController {

    private final GiftcardService giftcardService;
    private final MemberService memberService;

    @GetMapping("/shop/{kakaoId}")
    public ResponseEntity<HashMap<String, Object>> giftcardList(@PathVariable("kakaoId") String kakaoId) {
        GiftcardMemberDto member = new GiftcardMemberDto(memberService.findByKakaoId(kakaoId));

        HashMap<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        List<GiftcardDto> giftcardDtoList = giftcardService.findBySaleYNOrderByPriceAsc(true);

        HashMap<Long, List<GiftcardDto>> giftcards = new HashMap<>();
        for (GiftcardDto giftcardDto : giftcardDtoList) {
            Long price = giftcardDto.getPrice() / 1000 * 1000;
            if (!giftcards.containsKey(price)) {
                giftcards.put(price, new ArrayList<>());
            }
            giftcards.get(price).add(giftcardDto);
        }

        response.put("member", member);
        response.put("giftcards", giftcards);

        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/order/{giftcardId}/{kakaoId}")
    public ResponseEntity<HashMap<String, Object>> orderDetails(@PathVariable("giftcardId") Long giftcardId, @PathVariable("kakaoId") String kakaoId) {
        GiftcardMemberDto member = new GiftcardMemberDto(memberService.findByKakaoId(kakaoId));
        GiftcardDto giftcard = giftcardService.findById(giftcardId);

        HashMap<String, Object> response = new HashMap<>();
        HttpStatus status = HttpStatus.OK;

        response.put("member", member);
        response.put("giftcard", giftcardService.findById(giftcardId));

        return new ResponseEntity<>(response, status);
    }

    @PostMapping("/order")
    public ResponseEntity<HashMap<String, Object>> orderAdd(@RequestBody OrderSaveDto orderSaveDto) {
        HashMap<String, Object> response = new HashMap<>();

        Long price = giftcardService.findById(orderSaveDto.getGiftcardId()).getPrice();
        Member member = giftcardService.addOrder(orderSaveDto, price);

        HttpStatus status = HttpStatus.OK;

//        orderListSaveToExcel(member);

        return new ResponseEntity<>(response, status);
    }

//    public void orderListSaveToExcel(Member member) {
//        // 기존에 존재하는 엑셀 파일 경로
//        String path = "./existing_excel_file.xlsx";
//
//        try {
//            FileInputStream file = new FileInputStream(new File(path));
//            Path filePath = Paths.get(path);
//
//            if (Files.exists(filePath)) {
//                // 파일이 존재하지 않으면 새로운 엑셀 파일 생성
//                Workbook workbook = new HSSFWorkbook();
//                Sheet sheet = workbook.createSheet("Member Data");
//
//                // 헤더 생성
//                Row headerRow = sheet.createRow(0);
//                headerRow.createCell(0).setCellValue("Challenge ID");
//                headerRow.createCell(1).setCellValue("Certification Count");
//                headerRow.createCell(2).setCellValue("Start Date");
//                headerRow.createCell(3).setCellValue("End Date");
//
//                // 파일로 저장
//                FileOutputStream fos = new FileOutputStream(new File(path));
//                workbook.write(fos);
//
//                // 리소스 해제
//                workbook.close();
//                fos.close();
//            }
//
//            Workbook workbook = new HSSFWorkbook(file);
//            Sheet sheet = workbook.getSheetAt(0); // 첫 번째 시트 가져오기
//
//            int lastRowIndex = sheet.getLastRowNum(); // 마지막 행 인덱스 가져오기
//            Row row = sheet.createRow(lastRowIndex + 1); // 마지막 행 다음에 새로운 행 생성
//
//            // member 데이터를 row data로 저장
//            row.createCell(0).setCellValue(member.getUsername());
//            row.createCell(1).setCellValue(member.getSavedMoney());
//            row.createCell(2).setCellValue(member.getPhoneNumber());
//            row.createCell(3).setCellValue(member.getId().toString());
//
//            // 엑셀 파일로 저장
//            FileOutputStream fos = new FileOutputStream(new File(path));
//            workbook.write(fos);
//
//            // 리소스 해제
//            workbook.close();
//            fos.close();
//            file.close();
//
//            System.out.println("Row data 저장 완료!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}