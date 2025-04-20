package com.example.market.Services;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
public class Birt {

    private IReportEngine reportEngine;

    @PostConstruct
    protected void init() throws BirtException {
        EngineConfig config = new EngineConfig();
        Platform.startup(config);
        IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        reportEngine = factory.createReportEngine(config);
    }

    @PreDestroy
    public void destroy() {
        reportEngine.destroy();
        Platform.shutdown();
    }

    @Autowired
    private DataSource dataSource;

    public void generateOrdersPDF(String id, HttpServletResponse response, HttpServletRequest request) {

        IRunAndRenderTask task = null;

        try {
            IReportRunnable reportDesign = reportEngine.openReportDesign("C:\\Users\\Higashi\\workspace\\New\\orders.rptdesign");

            task = reportEngine.createRunAndRenderTask(reportDesign);

            Map<String, Object> reportParams = new HashMap<>();
            reportParams.put("NewParameter", id == null || "".equals(id) ? null : id);
            task.setParameterValues(reportParams);

            response.setContentType(reportEngine.getMIMEType("pdf"));

            PDFRenderOption pdfRenderOption = new PDFRenderOption();
            pdfRenderOption.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);

            task.setRenderOption(pdfRenderOption);
            task.getAppContext().put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT, request);
            task.getAppContext().put("OdaJDBCDriverPassInConnection", dataSource.getConnection());

            pdfRenderOption.setOutputStream(response.getOutputStream());
            task.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            task.close();
        }
    }

    public void generateProductsPDF(HttpServletResponse response, HttpServletRequest request) {

        IRunAndRenderTask task = null;

        try {
            IReportRunnable reportDesign = reportEngine.openReportDesign("C:\\Users\\Higashi\\workspace\\New\\products.rptdesign");

            task = reportEngine.createRunAndRenderTask(reportDesign);


            response.setContentType(reportEngine.getMIMEType("pdf"));

            PDFRenderOption pdfRenderOption = new PDFRenderOption();
            pdfRenderOption.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);

            task.setRenderOption(pdfRenderOption);
            task.getAppContext().put(EngineConstants.APPCONTEXT_PDF_RENDER_CONTEXT, request);
            task.getAppContext().put("OdaJDBCDriverPassInConnection", dataSource.getConnection());

            pdfRenderOption.setOutputStream(response.getOutputStream());
            task.run();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            task.close();
        }
    }
}

