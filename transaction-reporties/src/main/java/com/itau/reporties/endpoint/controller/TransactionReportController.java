package com.itau.reporties.endpoint.controller;

import com.itau.reporties.model.Transaction;
import com.itau.reporties.service.TransactionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vinicius.montouro
 */
@RestController
@RequestMapping("v1/transacao/relatorio")
@Log4j2
@Api(value = "Endpoints para 'Relatório de transações' ")
public class TransactionReportController {
    @Autowired
    private TransactionService transactionService;

    @SneakyThrows
    @GetMapping("/pdf")
    @ApiOperation(value = "Exportar pdf de relatório")
    public void exportPdf(HttpServletResponse response){
        List<Transaction> transactions = (List<Transaction>) transactionService.findAll();
        String sourceFileName = ResourceUtils
                .getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "report.jasper").getAbsolutePath();
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(transactions);
        Map parameters = new HashMap();
        JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, beanColDataSource);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "inline; filename=relatorio.pdf;");
    }
}
