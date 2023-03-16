package com.devper.reporter.grpc;

import com.devper.common.exception.JwtVerificationException;
import com.devper.reporter.model.Balance;
import com.devper.reporter.protobuf.ReportServiceGrpc;
import com.devper.reporter.protobuf.ReportServiceOuterClass;
import com.devper.reporter.service.ReportService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class ReportServiceGrpcImpl extends ReportServiceGrpc.ReportServiceImplBase {
    @Autowired
    private ReportService reportService;

    @Override
    @NewSpan("getBalance")
    public void balanceReport(ReportServiceOuterClass.BalanceRequest request, StreamObserver<ReportServiceOuterClass.UserBalance> responseObserver) {
        try {
            Balance balance = reportService.getCurrentBalanceByToken(request.getJwtToken());
            log.info("balance: {}", balance);
            ReportServiceOuterClass.UserBalance userBalance = ReportServiceOuterClass.UserBalance.newBuilder()
                    .setBalance(balance.getBalance() != null && balance.getBalance().compareTo(BigDecimal.ZERO) != 0 ? balance.getBalance().longValueExact() : 0L)
                    .setUsername(balance.getUsername())
                    .build();

            responseObserver.onNext(userBalance);
            responseObserver.onCompleted();
            log.info("Done! userBalance: {}", userBalance);
        } catch (JwtVerificationException e) {
            responseObserver.onError(
                    Status.PERMISSION_DENIED
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        } catch (Exception e) {
            responseObserver.onError(
                    Status.INTERNAL
                            .withDescription(e.getMessage())
                            .asRuntimeException()
            );
        }

    }
}
