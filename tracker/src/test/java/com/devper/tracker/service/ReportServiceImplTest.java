package com.devper.tracker.service;

import com.devper.clients.reporter.ReporterClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private ReporterClient reporterClient;

    @InjectMocks
    private ReportServiceImpl reportService;


//    @Test
//    public void testGetCurrentBalance() {
//        // given
//        Authentication authentication = new UsernamePasswordAuthenticationToken("test_user", "test_token");
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        ResponseEntity<ResponseWrapper> response = new ResponseEntity<>(ResponseWrapper.builder().content(100).build(), HttpStatus.OK);
//        when(reporterClient.getCurrentBalance("Bearer test_token")).thenReturn(response);
//
//        // when
//        BigDecimal balance = reportService.getCurrentBalance();
//
//        // then
//        assertEquals(BigDecimal.valueOf(100), balance);
//    }
}
