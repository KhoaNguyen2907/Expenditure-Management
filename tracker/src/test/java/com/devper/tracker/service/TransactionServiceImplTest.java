package com.devper.tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.devper.common.exception.NotFoundException;
import com.devper.common.utils.ProjectMapper;
import com.devper.tracker.dao.TransactionDAO;
import com.devper.tracker.model.Transaction;
import com.devper.tracker.model.TransactionType;
import com.devper.tracker.model.request.CreateTransactionRequest;
import com.devper.tracker.model.request.UpdateTransactionRequest;
import com.devper.tracker.model.response.TransactionInfo;
import com.devper.tracker.model.response.TransactionResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ReportService reportService;

    @Mock
    private TransactionDAO transactionDAO;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;


    @Test
    void testGetAll() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(this.transactionDAO.findAll()).thenReturn(transactionList);
        when(this.reportService.getCurrentBalance()).thenReturn(BigDecimal.valueOf(42L));
        TransactionResponse actualAll = this.transactionServiceImpl.getAll();
        assertEquals("TransactionResponse(transactionList=[], currentBalance=42)", actualAll.toString());
        assertEquals(transactionList, actualAll.getTransactionList());
        assertEquals("42", actualAll.getCurrentBalance().toString());
        verify(this.transactionDAO).findAll();
        verify(this.reportService).getCurrentBalance();
    }

    @Test
    void testGetAll2() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("transactionList: {}");
        transaction.setDescription("The characteristics of someone or something");

        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        when(this.transactionDAO.findAll()).thenReturn(transactionList);
        when(this.projectMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new NotFoundException("An error occurred"));
        assertThrows(NotFoundException.class, () -> this.transactionServiceImpl.getAll());
        verify(this.transactionDAO).findAll();
        verify(this.projectMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    void testGetAll3() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transaction.setAmount(valueOfResult);
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("transactionList: {}");
        transaction.setDescription("The characteristics of someone or something");

        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        when(this.transactionDAO.findAll()).thenReturn(transactionList);
        when(this.reportService.getCurrentBalance()).thenReturn(BigDecimal.valueOf(42L));
        when(this.projectMapper.map((Object) any(), (Class<Object>) any())).thenReturn(new TransactionInfo());
        TransactionResponse actualAll = this.transactionServiceImpl.getAll();
        BigDecimal currentBalance = actualAll.getCurrentBalance();
        assertEquals(valueOfResult, currentBalance);
        assertEquals(1, actualAll.getTransactionList().size());
        assertEquals("42", currentBalance.toString());
        verify(this.transactionDAO).findAll();
        verify(this.reportService).getCurrentBalance();
        verify(this.projectMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    void testGetAll4() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transaction.setAmount(valueOfResult);
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("transactionList: {}");
        transaction.setDescription("The characteristics of someone or something");

        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID());
        transaction1.setType(TransactionType.INCOME);
        transaction1.setUsername("janedoe");
        transaction1.setAmount(BigDecimal.valueOf(42L));
        transaction1.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction1.setCategory("transactionList: {}");
        transaction1.setDescription("The characteristics of someone or something");

        ArrayList<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction);
        when(this.transactionDAO.findAll()).thenReturn(transactionList);
        when(this.reportService.getCurrentBalance()).thenReturn(BigDecimal.valueOf(42L));
        when(this.projectMapper.map((Object) any(), (Class<Object>) any())).thenReturn(new TransactionInfo());
        TransactionResponse actualAll = this.transactionServiceImpl.getAll();
        BigDecimal currentBalance = actualAll.getCurrentBalance();
        assertEquals(valueOfResult, currentBalance);
        assertEquals(2, actualAll.getTransactionList().size());
        assertEquals("42", currentBalance.toString());
        verify(this.transactionDAO).findAll();
        verify(this.reportService).getCurrentBalance();
        verify(this.projectMapper, atLeast(1)).map((Object) any(), (Class<Object>) any());
    }

    @Test
    void testGet() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("Category");
        transaction.setDescription("The characteristics of someone or something");
        Optional<Transaction> ofResult = Optional.of(transaction);
        when(this.transactionDAO.findById((UUID) any())).thenReturn(ofResult);
        when(this.projectMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new NotFoundException("An error occurred"));
        assertThrows(NotFoundException.class, () -> this.transactionServiceImpl.get(UUID.randomUUID()));
        verify(this.transactionDAO).findById((UUID) any());
        verify(this.projectMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    void testGet2() {
        when(this.transactionDAO.findById((UUID) any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> this.transactionServiceImpl.get(UUID.randomUUID()));
        verify(this.transactionDAO).findById((UUID) any());
    }

    @Test
    void testGet3() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("Category");
        transaction.setDescription("The characteristics of someone or something");
        Optional<Transaction> ofResult = Optional.of(transaction);
        when(this.transactionDAO.findById((UUID) any())).thenReturn(ofResult);
        TransactionInfo transactionInfo = new TransactionInfo();
        when(this.projectMapper.map((Object) any(), (Class<Object>) any())).thenReturn(transactionInfo);
        assertSame(transactionInfo, this.transactionServiceImpl.get(UUID.randomUUID()));
        verify(this.transactionDAO).findById((UUID) any());
        verify(this.projectMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    void testDelete() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("Category");
        transaction.setDescription("The characteristics of someone or something");
        Optional<Transaction> ofResult = Optional.of(transaction);
        doNothing().when(this.transactionDAO).deleteById((UUID) any());
        when(this.transactionDAO.findById((UUID) any())).thenReturn(ofResult);
        this.transactionServiceImpl.delete(UUID.randomUUID());
        verify(this.transactionDAO).deleteById((UUID) any());
        verify(this.transactionDAO).findById((UUID) any());
    }

    @Test
    void testDelete2() {
        when(this.transactionDAO.findById((UUID) any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> this.transactionServiceImpl.delete(UUID.randomUUID()));
        verify(this.transactionDAO).findById((UUID) any());
    }

    @Test
    void testCreate() {
        when(this.projectMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new NotFoundException("An error occurred"));
        assertThrows(NotFoundException.class, () -> this.transactionServiceImpl.create(new CreateTransactionRequest()));
        verify(this.projectMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    void testCreate2() {
        when(this.transactionDAO.save((Transaction) any())).thenThrow(new NotFoundException("An error occurred"));

        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("Category");
        transaction.setDescription("The characteristics of someone or something");
        when(this.projectMapper.map((Object) any(), (Class<Object>) any())).thenReturn(transaction);

        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
        createTransactionRequest.setType("Type");
        assertThrows(NotFoundException.class, () -> this.transactionServiceImpl.create(createTransactionRequest));
        verify(this.transactionDAO).save((Transaction) any());
        verify(this.projectMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    void testUpdate() {
        when(this.transactionDAO.findById((UUID) any())).thenReturn(Optional.empty());
        UUID id = UUID.randomUUID();
        assertThrows(NotFoundException.class, () -> this.transactionServiceImpl.update(id, new UpdateTransactionRequest()));
        verify(this.transactionDAO).findById((UUID) any());
    }

    @Test
    void testUpdate2() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("Category");
        transaction.setDescription("The characteristics of someone or something");
        Optional<Transaction> ofResult = Optional.of(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID());
        transaction1.setType(TransactionType.INCOME);
        transaction1.setUsername("janedoe");
        transaction1.setAmount(BigDecimal.valueOf(42L));
        transaction1.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction1.setCategory("Category");
        transaction1.setDescription("The characteristics of someone or something");
        when(this.transactionDAO.save((Transaction) any())).thenReturn(transaction1);
        when(this.transactionDAO.findById((UUID) any())).thenReturn(ofResult);
        when(this.projectMapper.map((Object) any(), (Class<Object>) any()))
                .thenThrow(new NotFoundException("An error occurred"));
        doNothing().when(this.projectMapper).map((Object) any(), (Object) any());
        UUID id = UUID.randomUUID();

        UpdateTransactionRequest updateTransactionRequest = new UpdateTransactionRequest();
        updateTransactionRequest.setType("Type");
        assertThrows(NotFoundException.class, () -> this.transactionServiceImpl.update(id, updateTransactionRequest));
        verify(this.transactionDAO).findById((UUID) any());
        verify(this.transactionDAO).save((Transaction) any());
        verify(this.projectMapper).map((Object) any(), (Object) any());
        verify(this.projectMapper).map((Object) any(), (Class<Object>) any());
    }

    @Test
    void testUpdate3() {
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setType(TransactionType.INCOME);
        transaction.setUsername("janedoe");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction.setCategory("Category");
        transaction.setDescription("The characteristics of someone or something");
        Optional<Transaction> ofResult = Optional.of(transaction);

        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID());
        transaction1.setType(TransactionType.INCOME);
        transaction1.setUsername("janedoe");
        transaction1.setAmount(BigDecimal.valueOf(42L));
        transaction1.setDate(LocalDateTime.of(1, 1, 1, 1, 1));
        transaction1.setCategory("Category");
        transaction1.setDescription("The characteristics of someone or something");
        when(this.transactionDAO.save((Transaction) any())).thenReturn(transaction1);
        when(this.transactionDAO.findById((UUID) any())).thenReturn(ofResult);
        TransactionInfo transactionInfo = new TransactionInfo();
        when(this.projectMapper.map((Object) any(), (Class<Object>) any())).thenReturn(transactionInfo);
        doNothing().when(this.projectMapper).map((Object) any(), (Object) any());
        UUID id = UUID.randomUUID();

        UpdateTransactionRequest updateTransactionRequest = new UpdateTransactionRequest();
        updateTransactionRequest.setType("Type");
        assertSame(transactionInfo, this.transactionServiceImpl.update(id, updateTransactionRequest));
        verify(this.transactionDAO).findById((UUID) any());
        verify(this.transactionDAO).save((Transaction) any());
        verify(this.projectMapper).map((Object) any(), (Object) any());
        verify(this.projectMapper).map((Object) any(), (Class<Object>) any());
    }
}

