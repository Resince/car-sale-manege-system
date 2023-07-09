package utils;

import entity.Car;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ExcelReader {
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    private static final Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类

    /**
     * 根据文件名后缀名类型获得对应的工作簿对象
     * @param inputStream 读取文件的输入流
     * @param fileType 文件后缀名类型（xls/xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    private static Workbook getWorkbook(InputStream inputStream, String fileType){
        try {
            Workbook workbook = null;
            if (fileType.equalsIgnoreCase(XLS)) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (fileType.equalsIgnoreCase(XLSX)) {
                workbook = new XSSFWorkbook(inputStream);
            }
            return workbook;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败返回null
     */
    public static List<Car> readExcel(String fileName){
        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.warning("指定的Excel文件不存在！");
                return null;
            }
            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);
            // 读取excel中的数据
            if (workbook != null) {
                return parseExcel(workbook);
            } else return null;
        } catch (Exception e) {
            logger.warning("解析Excel失败，文件名：" + fileName + "错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.warning("关闭数据流出错！错误信息：" + e.getMessage());
            }
        }
    }

    /**
     *解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<Car> parseExcel(Workbook workbook) {
        List<Car> resultDataList = new ArrayList<>();

        // 解析sheer
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum ++ ) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 检验sheet是否合法
            if (sheet == null) {
                continue;
            }
            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRw = sheet.getRow(firstRowNum);
            if (null == firstRw) {
                logger.warning("解析Excel失败，在第一行没有读取到任何数据！");
            }
            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum ++ ) {
                Row row = sheet.getRow(rowNum);
                if (null == row) {
                    continue;
                }
                Car resultData = convertRowToData(row);
                if (null == resultData) {
                    logger.warning("第" + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }
        return resultDataList;
    }



    /**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法
//                DecimalFormat df = new DecimalFormat("###,###.000");
//                returnValue = df.format(doubleValue);

                returnValue = doubleValue.toString();
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                boolean booleanValue = cell.getBooleanCellValue();
                returnValue = Boolean.toString(booleanValue);
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    /**
     * 提取每一行中的数据，构造成一个结果数据对象
     * <p>
     * 当该行中有单元格的数据为空或不合法时，忽略该行数据
     *
     * @param row 行数
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static Car convertRowToData(Row row) {
        Car resultData = new Car();

        Cell cell;
        int cellNum = 0;

        // 获取价格
        cell = row.getCell(cellNum++);
        String price = convertCellValueToString(cell);
        if (null == price || "".equals(price)) {
            // 价格为空
            resultData.setPrice(null);
        } else {
            resultData.setPrice(Double.parseDouble(price));
        }

        // 获取类型
        cell = row.getCell(cellNum++);
        String series = convertCellValueToString(cell);
        resultData.setSeries(series);

        // 获取powerType
        cell = row.getCell(cellNum++);
        String powerType = convertCellValueToString(cell);
        resultData.setPowerType(powerType);

        // 获取品牌
        cell = row.getCell(cellNum++);
        String brand = convertCellValueToString(cell);
        resultData.setBrand(brand);

        // 获取series
        cell = row.getCell(cellNum++);
        String type = convertCellValueToString(cell);
        resultData.setType(type);

        // 获取number
        cell = row.getCell(cellNum++);
        String number = convertCellValueToString(cell);
        if (null == price || "".equals(price)) {
            resultData.setNumber(0);
        } else {
            resultData.setNumber(Integer.parseInt(number.split("\\.")[0]));
        }

        return resultData;
    }


}
