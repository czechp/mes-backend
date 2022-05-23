package pl.bispol.mesbispolserver.report;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;
import pl.bispol.mesbispolserver.report.dto.ReportQueryDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
class ReportSpreadSheetGenerator {
    private static final String REPORTS_DIRECTORY = "reports";
    private static final String REPORTS_SHEET_NAME = "RAPORTY";
    private final List<HeaderCell> headerCells = Arrays.asList(
            new HeaderCell(0, "Linia"),
            new HeaderCell(1, "Data"),
            new HeaderCell(2, "Operator"),
            new HeaderCell(3, "Zmiana"),
            new HeaderCell(4, "Id"),
            new HeaderCell(5, "Produkt"),
            new HeaderCell(6, "Planowana\n wydajność\n [szt/h]"),
            new HeaderCell(7, "Rzeczywista\n wydajność\n [szt/h]"),
            new HeaderCell(8, "Planowana\n wydajność\n [szt/zmiane]"),
            new HeaderCell(9, "Wykonane\n (dobre \ni złe) [szt.]"),
            new HeaderCell(10, "Wykonane\n (dobre) \n[szt.]"),
            new HeaderCell(11, "%\nnormy \n[%]"),
            new HeaderCell(12, "OEE \n[%]"),
            new HeaderCell(13, "Czas pracy \n[h]"),
            new HeaderCell(14, "Rzeczywisty\nczas\npracy \n[h]"),
            new HeaderCell(15, "Suma\nawarii \n[h]")
    );
    private int currentRow;
    private int currentCell;

    String generateReportsSpreadSheet(List<ReportQueryDto> reports, String fileName) throws IOException {
        this.currentRow = 0;
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(REPORTS_SHEET_NAME);
        XSSFDataFormat dataFormat = workbook.createDataFormat();
        createHeaders(sheet, workbook);
        populateSpreadSheet(sheet, reports, dataFormat);
        styleColumns(sheet);
        return saveSpreadSheet(workbook, fileName);
    }

    private void styleColumns(XSSFSheet sheet) {
        headerCells.forEach(h -> {
            sheet.autoSizeColumn(h.getColNumber());
        });
    }

    private void populateSpreadSheet(XSSFSheet sheet, List<ReportQueryDto> reports, XSSFDataFormat dataFormat) {
        reports.forEach(report -> {
            this.currentCell = 0;
            XSSFRow row = sheet.createRow(this.currentRow);
            createCellString(row, report.getLineName());
            createCellString(row, report.getCreationDate().toLocalDate().toString());
            createCellString(row, report.getCreateOperator());
            createCellString(row, report.getReportWorkShift().toString());
            createCellString(row, "");
            createCellString(row, report.getProductName());
            createCellInt(row, report.getReportStatisticsDto().getExpectedProductionPerHour());
            createCellInt(row, report.getReportStatisticsDto().getCurrentProductionPerHour());
            createCellInt(row, (int) report.getTargetAmount());
            createCellInt(row, (int) report.getAmount());
            createCellInt(row, (int) (report.getAmount() - report.getTrashAmount()));
            createCellDouble(row, report.getReportStatisticsDto().getCurrentProductionPercent(), dataFormat);
            createCellDouble(row, report.getReportStatisticsDto().getOee(), dataFormat);
            createCellFloat(row, report.getReportStatisticsDto().getWorkingTime().getHoursDecimal(), dataFormat);
            createCellFloat(row, report.getReportStatisticsDto().getWorkingTimeWithoutStop().getHoursDecimal(), dataFormat);
            createCellFloat(row, (report.getReportStatisticsDto().getWorkingTime().getHoursDecimal()) - report.getReportStatisticsDto().getWorkingTimeWithoutStop().getHoursDecimal(), dataFormat);
            this.currentRow++;
        });
    }

    private void createCellString(XSSFRow row, String value) {
        XSSFCell cell = row.createCell(this.currentCell);
        cell.setCellValue(value);
        this.currentCell++;
    }


    private void createCellFloat(XSSFRow row, float value, XSSFDataFormat dataFormat) {
        XSSFCell cell = row.createCell(this.currentCell);
        setCellDecimalPlaces(cell, dataFormat);
        cell.setCellValue(value);
        this.currentCell++;
    }


    private void createCellDouble(XSSFRow row, double value, XSSFDataFormat dataFormat) {
        XSSFCell cell = row.createCell(this.currentCell);
        setCellDecimalPlaces(cell, dataFormat);
        cell.setCellValue(value);
        this.currentCell++;
    }

    private void createCellInt(XSSFRow row, int value) {
        XSSFCell cell = row.createCell(this.currentCell);
        cell.setCellValue(value);
        this.currentCell++;
    }


    private void setCellDecimalPlaces(XSSFCell cell, XSSFDataFormat dataFormat) {
        XSSFCellStyle cellStyle = cell.getCellStyle();
        cellStyle.setDataFormat(dataFormat.getFormat("00.00"));
        cell.setCellStyle(cellStyle);
    }


    private String saveSpreadSheet(XSSFWorkbook workbook, String fileName) throws IOException {
        String fullFileName = REPORTS_DIRECTORY + "/" + fileName + ".xlsx";
        FileOutputStream fileOutputStream = new FileOutputStream(fullFileName);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        return fullFileName;
    }

    private void createHeaders(XSSFSheet sheet, XSSFWorkbook workbook) {
        XSSFRow headers = sheet.createRow(this.currentRow);
        headers.setHeight((short) 1000);
        this.currentRow++;
        headerCells.forEach(header -> {
            XSSFCell headerCell = headers.createCell(header.colNumber);
            headerCell.setCellValue(header.getColDescription());
            headerCell.setCellStyle(createHeaderStyle(workbook));
        });
    }

    private CellStyle createHeaderStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    void clearDirectories() throws IOException {
        createDirectory();
        Files.list(Path.of(REPORTS_DIRECTORY)).forEach(file -> {
            try {
                Files.delete(Path.of(String.valueOf(file)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void createDirectory() throws IOException {
        Path directoryPath = Path.of(REPORTS_DIRECTORY);
        if (!Files.exists(directoryPath))
            Files.createDirectory(directoryPath);
    }

    class HeaderCell {
        int colNumber;
        String colDescription;

        HeaderCell(int colNumber, String colDescription) {
            this.colNumber = colNumber;
            this.colDescription = colDescription;
        }

        int getColNumber() {
            return colNumber;
        }

        void setColNumber(int colNumber) {
            this.colNumber = colNumber;
        }

        String getColDescription() {
            return colDescription;
        }

        void setColDescription(String colDescription) {
            this.colDescription = colDescription;
        }

        @Override
        public String toString() {
            return "HeaderCell{" + "colNumber=" + colNumber + ", colDescription='" + colDescription + '\'' + '}';
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HeaderCell that = (HeaderCell) o;
            return colNumber == that.colNumber && Objects.equals(colDescription, that.colDescription);
        }

        @Override
        public int hashCode() {
            return Objects.hash(colNumber, colDescription);
        }
    }

}
