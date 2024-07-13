package com.java.spring.batch.statement.batch;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.java.spring.batch.statement.entity.CustomerAccount;
import com.java.spring.batch.statement.repository.CustomerAccountRepository;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class PdfProcessTasklet implements Tasklet {

  // to perform a single task within a step .Consist of several steps
  // Generates Database records into PDF in table format.
  private final CustomerAccountRepository customerRepository;

  public PdfProcessTasklet(CustomerAccountRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
      throws Exception {

    log.info(
        "Processing tasklet to generating PDF -exec id :{}",
        stepContribution.getStepExecution().getId());

    String pdfFilePathToStore = "/home/shubham/Documents/itextPDF/Customers-list.pdf";
    OutputStream outputStream = new FileOutputStream(pdfFilePathToStore);

    PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
    Document document = new Document(pdfDocument);

    Color fontColor = new DeviceRgb(0, 0, 0); // Black

    float[] columnWidths = {50f, 100f, 100f, 150f, 70f, 100f, 80f, 100f};

    Table table = new Table(UnitValue.createPointArray(columnWidths));

    String[] headers = {
      "ID", "First Name", "Last Name", "Email", "Gender", "Contact No", "Country", "DOB"
    };

    for (String header : headers) {
      Cell cell = new Cell().add(new Paragraph(header));
      cell.setFontColor(fontColor).setFontSize(5).setTextAlignment(TextAlignment.CENTER);
      table.addHeaderCell(cell);
    }

    List<CustomerAccount> list = customerRepository.findAll();
    List<CustomerAccount> filteredCustomers =
        list.stream().filter(c -> c.getGender().equals("Female")).toList();

    for (CustomerAccount customer : filteredCustomers) {
      addCell(table, String.valueOf(customer.getId()));
      addCell(table, customer.getAccountName());
      addCell(table, customer.getAddress());
      addCell(table, customer.getDate().toString());
      addCell(table, customer.getAccountNumber());
      addCell(table, customer.getAccountDescription());

      addCell(table, customer.getBranch());
      addCell(table, customer.getCifNo());
      addCell(table, customer.getIfsCode());
      addCell(table, customer.getMicrCode());
      addCell(table, String.valueOf(customer.getBalance()));
      addCell(table, customer.getEmail());

      addCell(table, customer.getGender());
      addCell(table, customer.getContactNo());
      addCell(table, customer.getCountry());
      addCell(table, customer.getDob());
    }

    document.add(table);
    document.close();
    log.info("RepeatStatus.FINISHED {}", RepeatStatus.FINISHED);
    return RepeatStatus.FINISHED;
  }

  private void addCell(Table table, String value) {
    Cell cell = new Cell().add(new Paragraph(value));
    cell.setFontColor(new DeviceRgb(0, 0, 0)).setFontSize(5);
    table.addCell(cell);
  }
}
