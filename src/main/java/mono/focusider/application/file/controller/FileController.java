package mono.focusider.application.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mono.focusider.domain.file.dto.res.FileUploadResDto;
import mono.focusider.domain.file.service.FileService;
import mono.focusider.global.domain.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/file")
@Tag(name = "File", description = "File API")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @Operation(summary = "[유저, 어드민] 파일 업로드", description = "파일을 업로드하고 URL을 받는 API")
    @ApiResponse(responseCode = "201", description = "파일 업로드 성공", content = @Content(schema = @Schema(implementation = FileUploadResDto.class)))
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> fileUpload(@RequestPart("profileImage") MultipartFile file) {
        FileUploadResDto resDto = fileService.createFile(file);
        return SuccessResponse.ok(resDto);
    }
}
