package vn.vnpt.tracebility_custom.util;


import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static final int STANDARD_WIDTH_EXCEL = 256;

    public static XSSFSheet createSheet(String nameFile) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        return workbook.createSheet(nameFile);
    }

    public static XSSFSheet createTitleColumns(XSSFSheet sheet, String[] titleColumns, int[] widthColumns, int positionTitle) {

        Row row = sheet.createRow(positionTitle);

        final int amountColumn = titleColumns.length;

        for (int i = 0; i < amountColumn; i++) {
            sheet.setColumnWidth(i, widthColumns[i] * STANDARD_WIDTH_EXCEL);
        }

        for (int i = 0; i < amountColumn; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(titleColumns[i]);
        }
        return sheet;
    }

    public static CellStyle setCellStyle(Workbook workbook, CellStyle cellStyle) {

        Font boldFont = workbook.createFont();
        boldFont.setFontName("Times New Roman");
        boldFont.setBold(true);
        boldFont.setFontHeightInPoints((short) 14);
        cellStyle.setFont(boldFont);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        return cellStyle;
    }

    public static CellStyle setAlignmentCentre(CellStyle cellStyle) {

        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    public static CellStyle setAlignmentLeft(CellStyle cellStyle) {

        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
    }

    public static void mergeAndBoder(XSSFSheet sheet, int fromRow, int toRow, int fromColumn, int toColumn) {

        CellRangeAddress cellRangeAddress = new CellRangeAddress(fromRow, toRow, fromColumn, toColumn);
        sheet.addMergedRegion(cellRangeAddress);
    }

    public static void mergeAndBoderBold(XSSFSheet sheet, int fromRow, int toRow, int fromColumn, int toColumn) {

        CellRangeAddress cellRangeAddress = new CellRangeAddress(fromRow, toRow, fromColumn, toColumn);
        sheet.addMergedRegion(cellRangeAddress);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);
    }

    public static Cell setContentToCell(XSSFSheet sheet, int rowIndex, int columnIndex, Object values) {


        CellReference cellReference = new CellReference(rowIndex, columnIndex);

        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }

        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            cell = row.createCell(columnIndex);
        }

        cell.setCellValue(ParseUtil.parseToString(values));

        return cell;
    }


}
