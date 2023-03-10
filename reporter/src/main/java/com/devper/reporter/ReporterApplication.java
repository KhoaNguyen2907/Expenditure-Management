package com.devper.reporter;

import com.devper.reporter.dao.ReportDAO;
import com.devper.reporter.model.DayReport;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

@SpringBootApplication
@ComponentScan(basePackages = {"com.devper.common", "com.devper.reporter"})
public class ReporterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReporterApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(ReportDAO reportDAO) {
        return args -> {
            DayReport report = DayReport.builder().date(LocalDate.now()).build();
            reportDAO.insert(report);
        };
    }
}
