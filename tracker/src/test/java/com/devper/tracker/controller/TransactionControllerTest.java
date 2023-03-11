package com.devper.tracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devper.common.model.response.ResponseWrapper;
import com.devper.common.utils.ProjectMapper;
import com.devper.tracker.dao.TransactionDAO;
import com.devper.tracker.model.Transaction;
import com.devper.tracker.model.request.CreateTransactionRequest;
import com.devper.tracker.model.request.UpdateTransactionRequest;
import com.devper.tracker.model.response.TransactionInfo;
import com.devper.tracker.model.response.TransactionResponse;
import com.devper.tracker.service.ReportService;
import com.devper.tracker.service.TransactionService;
import com.devper.tracker.service.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TransactionController.class})
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {
    @Autowired
    private TransactionController transactionController;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testGet() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/transactions/*");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testGetAll() {
        TransactionDAO transactionDAO = mock(TransactionDAO.class);
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(transactionDAO.findAll()).thenReturn(transactionList);
        ReportService reportService = mock(ReportService.class);
        when(reportService.getCurrentBalance()).thenReturn(BigDecimal.valueOf(42L));
        ResponseEntity<ResponseWrapper> actualAll = (new TransactionController(
                new TransactionServiceImpl(transactionDAO, new ProjectMapper(), reportService))).getAll();
        assertTrue(actualAll.getHeaders().isEmpty());
        assertTrue(actualAll.hasBody());
        assertEquals(HttpStatus.OK, actualAll.getStatusCode());
        ResponseWrapper body = actualAll.getBody();
        assertNull(body.getErrors());
        Object content = body.getContent();
        assertTrue(content instanceof TransactionResponse);
        assertFalse(body.isHasErrors());
        assertEquals(200, body.getStatus());
        assertEquals(transactionList, ((TransactionResponse) content).getTransactionList());
        assertEquals("TransactionResponse(transactionList=[], currentBalance=42)", content.toString());
        assertEquals("42", ((TransactionResponse) content).getCurrentBalance().toString());
        verify(transactionDAO).findAll();
        verify(reportService).getCurrentBalance();
    }

    @Test
    void testDelete() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/transactions/*");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testCreate() {
        TransactionInfo transactionInfo = new TransactionInfo();
        when(transactionService.create((CreateTransactionRequest) any())).thenReturn(transactionInfo);
        TransactionController transactionController = new TransactionController(transactionService);
        ResponseEntity<ResponseWrapper> actualCreateResult = transactionController.create(new CreateTransactionRequest());
        assertTrue(actualCreateResult.getHeaders().isEmpty());
        assertTrue(actualCreateResult.hasBody());
        assertEquals(HttpStatus.CREATED, actualCreateResult.getStatusCode());
        ResponseWrapper body = actualCreateResult.getBody();
        assertNull(body.getErrors());
        assertSame(transactionInfo, body.getContent());
        assertFalse(body.isHasErrors());
        assertEquals(201, body.getStatus());
        verify(transactionService).create((CreateTransactionRequest) any());
    }

    @Test
    void testUpdate() throws Exception {
        UpdateTransactionRequest updateTransactionRequest = new UpdateTransactionRequest();
        updateTransactionRequest.setAmount(BigDecimal.valueOf(42L));
        updateTransactionRequest.setCategory("Category");
        updateTransactionRequest.setDescription("The characteristics of someone or something");
        updateTransactionRequest.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(updateTransactionRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/transactions/*")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.transactionController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }
}

