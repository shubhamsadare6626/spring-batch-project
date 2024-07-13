package com.java.spring.batch.statement.batch;

import com.itextpdf.html2pdf.HtmlConverter;
import com.java.spring.batch.statement.entity.CustomerAccount;
import com.java.spring.batch.statement.entity.CustomerLogging;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PdfItemProcessor implements ItemProcessor<CustomerAccount, CustomerLogging> {

  private static final String PATH = "/home/shubham/Documents/itextPDF/";

  @Override
  public CustomerLogging process(CustomerAccount customer) throws Exception {
    Map<String, String> templateParams = new LinkedHashMap<>();
    templateParams.put("id", String.valueOf(customer.getId()));
    templateParams.put("accountName", customer.getAccountName());
    templateParams.put("accountDescription", customer.getAccountDescription());
    templateParams.put("accountNumber", customer.getAccountNumber());
    templateParams.put("address", customer.getAddress());
    templateParams.put("branch", customer.getBranch());
    templateParams.put("cifNo", customer.getCifNo());
    templateParams.put("ifsCode", customer.getIfsCode());
    templateParams.put("micrCode", customer.getMicrCode());
    templateParams.put("balance", String.valueOf(customer.getBalance()));
    templateParams.put("date", customer.getDate().toString());
    templateParams.put("email", customer.getEmail());
    templateParams.put("contactNo", customer.getContactNo());
    templateParams.put("generatedAt", String.valueOf(LocalDateTime.now()));

    mapTemplateParameters(PATH + "input/statement-template.html", templateParams, customer);

    return CustomerLogging.builder()
        .id(customer.getId())
        .email(customer.getEmail())
        .generatedAt(LocalDateTime.now())
        .targetLocation(PATH + "output/" + customer.getId() + "-record.pdf")
        .success(true)
        .build();
  }

  private static String mapTemplateParameters(
      String template, Map<String, String> templateParameters, CustomerAccount customer) {
    FileOutputStream fOut = null;

    String html = convertHtmlToString(template);
    String fileName =
        "Statement_"
            + LocalDate.now().getYear()
            + LocalDate.now().getMonth()
            + "_"
            + customer.getId()
            + ".pdf";
    try {
      for (Map.Entry<String, String> entry : templateParameters.entrySet()) {
        html = html.replace("${" + entry.getKey() + "}", entry.getValue());
      }
      File file = null;
      file = new File(PATH + "output/", fileName);

      fOut = new FileOutputStream(file);
      HtmlConverter.convertToPdf(html, fOut);
    } catch (Exception e) {
      log.error("Error {}", e.getMessage(), e);
    } finally {
      if (fOut != null) {
        try {
          fOut.close();
        } catch (IOException e) {
          log.error("Error at File close-{}", e.getMessage(), e);
        }
      }
    }
    return fileName;
  }

  private static String convertHtmlToString(String filePath) {
    StringBuilder builder = new StringBuilder();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
      String html;
      while ((html = bufferedReader.readLine()) != null) {
        builder.append(html);
      }
      bufferedReader.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return builder.toString();
  }
}
