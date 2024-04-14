package org.kps_group2.spring_mini_project.controller.fileController;

import lombok.AllArgsConstructor;
import org.kps_group2.spring_mini_project.model.fileModel.ApiResponse;
import org.kps_group2.spring_mini_project.model.fileModel.FileResponse;
import org.kps_group2.spring_mini_project.service.fileService.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam List<MultipartFile> files) throws IOException {
        List<FileResponse> fileResponses = new ArrayList<>();

        for (MultipartFile file: files){
            String fileName = fileService.saveFile(file);
            String fileUrl = "http://localhost:8080/" + fileName;
            fileResponses.add(new FileResponse(fileName, fileUrl, file.getContentType(), file.getSize()));
        }
        ApiResponse<List<FileResponse>> response = ApiResponse.<List<FileResponse>>builder()
                .message("successfully uploaded file")
                .status(HttpStatus.OK)
                .dateTime(LocalDateTime.now())
                .payload(fileResponses).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getFile(@RequestParam String fileName) throws IOException {
        Resource resource = fileService.getFileByFileName(fileName);
        MediaType mediaType;
        if (fileName.endsWith(".pdf")){mediaType = MediaType.APPLICATION_PDF;}
        else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")){
            mediaType = MediaType.IMAGE_PNG;}
        else {mediaType = MediaType.APPLICATION_OCTET_STREAM;}
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(mediaType).body(resource);
    }
}

