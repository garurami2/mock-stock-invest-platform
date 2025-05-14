package com.ach.stock.db;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceShutdown {

    private final DataSource dataSource;

    public DataSourceShutdown(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PreDestroy
    public void closeDataSource() throws Exception {
        try {
//            BlockSource blockSource = BlockSource.getDefaultBlockSource();
//            blockSource.close(); // BlockReleaser 스레드 종료 유도
//            System.out.println("Oracle BlockSource closed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
