package mono.focusider.application.attendance.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/attendance")
@Tag(name = "Attendance", description = "Attendance API")
@RequiredArgsConstructor
public class AttendanceController {

}
