package com.devper.authenticator.grpc;

import com.devper.reporter.protobuf.ReportServiceGrpc;
import com.devper.reporter.protobuf.ReportServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.cloud.sleuth.annotation.NewSpan;

public class ReportClientGrpc {

    private ReportServiceGrpc.ReportServiceBlockingStub blockingStub;
    private ManagedChannel channel;

    public ReportClientGrpc(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = ReportServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
    }

    @NewSpan("getBalance")
    public ReportServiceOuterClass.UserBalance getBalance(String jwtToken) {
        // create a request message
        ReportServiceOuterClass.BalanceRequest request = ReportServiceOuterClass.BalanceRequest.newBuilder()
                .setJwtToken(jwtToken)
                .build();

        // send the request and get the response
        ReportServiceOuterClass.UserBalance response = blockingStub.balanceReport(request);

        // return the balance
        return response;
    }


}
