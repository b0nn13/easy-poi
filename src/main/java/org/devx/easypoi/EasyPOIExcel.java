package org.devx.easypoi;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.devx.easypoi.domain.ColumnEP;
import org.devx.easypoi.domain.SheetEP;
import org.devx.easypoi.format.FormatEP;
import org.devx.easypoi.format.FormatEPFactory;

public class EasyPOIExcel
{
    
    public static ByteArrayOutputStream build(Object param) 
    {
        
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try
        {
            XSSFWorkbook workbook = buildWorkBook(param);
            workbook.write(output);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return output;
    }

    public static XSSFWorkbook buildWorkBook(Object param) throws IllegalAccessException
    {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Field[] paramFields = getAllDeclaredFields(param.getClass());
        for (Field paramField : paramFields)
        {
            XSSFSheet sheet = null;
            Object[] items = getItems(param, paramField);
            if (items == null) continue;
            int rownum = 0;
            boolean headerAdd = false;
            for (Object item : items)
            {
                if (sheet == null)
                {
                    String sheetName = getSheetName(paramField);
                    if (sheetName == null) continue;
                    sheet = workbook.createSheet(sheetName);
                }
                if (!headerAdd) {
                    Row header = sheet.createRow(rownum++);
                    generateHeader(item, header);    
                    headerAdd = true;
                }
                Row row = sheet.createRow(rownum++);
                generateRow(item, row, workbook);
                
            }
            sheet = null;
        }
        autoSizeColumns(workbook);
        return workbook;
    }

    private static String getSheetName(Field paramField)
    {
        if (paramField.isAnnotationPresent(SheetEP.class)) {
            return paramField.getAnnotation(SheetEP.class).value();
        }
        return null;
    }
    
    private static ColumnEP getColumnMetadata(Field paramField)
    {
        if (isColumn(paramField)) {
            return paramField.getAnnotation(ColumnEP.class);
        }
        return null;
    }

    private static boolean isColumn(Field paramField)
    {
        return paramField.isAnnotationPresent(ColumnEP.class);
    }

    private static Object[] getItems(Object param, Field paramField) throws IllegalAccessException
    {
        Object[] items = null;
        paramField.setAccessible(true);
        if (paramField.getType().isAssignableFrom(java.util.List.class))
        {
            items = ((Collection) paramField.get(param)).toArray();
        }
        else if (paramField.getType().isAssignableFrom(Set.class))
        {
            items = ((Set) paramField.get(param)).toArray();
        }
        return items;
    }

    private static void generateRow(Object item, Row row, Workbook workbook)
            throws IllegalAccessException
    {
        Field[] itemFields = getAllDeclaredFields(item.getClass());
        int cellnum = 0;
        for (Field itemField : itemFields)
        {
            if (isColumn(itemField))
            {
                Cell cell = row.createCell(cellnum++);
                Object value = getValueOf(item, itemField);
                if (value instanceof String)
                    cell.setCellValue((String) value);
                else if (value instanceof Number)
                    cell.setCellValue(((Number) value).doubleValue());
                else if (value instanceof Boolean)
                    cell.setCellValue(((Boolean) value));
                else if (value instanceof Date) {
                    cell.setCellValue(((Date) value));
                    cell.setCellStyle(getDateStyleFormat(workbook));
                }
                else if (value instanceof Calendar) {
                    cell.setCellValue(((Calendar) value));
                    cell.setCellStyle(getDateStyleFormat(workbook));
                }
            }
        }
    }

    private static Object getValueOf(Object item, Field itemField) throws IllegalArgumentException, IllegalAccessException
    {
        
        ColumnEP metadata = getColumnMetadata(itemField);
        itemField.setAccessible(true);
        Object value = itemField.get(item);
        FormatEP formatter = FormatEPFactory.getFormatter(metadata.format());
        if (formatter != null) {
            String defaultFormat = metadata.pattern();
            value = formatter.format(itemField.getName(), value, defaultFormat);
        }
        return value;
    }

    private static CellStyle getDateStyleFormat(Workbook workbook)
    {
        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/mm/yyyy HH:mm:ss"));
        return cellStyle;
    }
    
    private static void generateHeader(Object item, Row row)
            throws IllegalAccessException
    {
        Field[] itemFields = getAllDeclaredFields(item.getClass());
        int cellnum = 0;
        for (Field itemField : itemFields)
        {
            if (isColumn(itemField)) {
                ColumnEP columnMetadata = getColumnMetadata(itemField);
                String columnName = columnMetadata.value();
                if (columnName == null) columnName = "COLUMN_" +(cellnum + 1);
                Cell cell = row.createCell(cellnum++);
                itemField.setAccessible(true);
                cell.setCellValue((String) columnName);
            }
        }
    }
    
    public static void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(sheet.getFirstRowNum());
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }
    
    private static Field[] getAllDeclaredFields(Class<?> clazz)
    {
        List<Field> list = new ArrayList<Field>();
        Class<?> superClass = clazz;
        Field[] fields = superClass.getDeclaredFields();
        do
        {
            for (Field f : fields)
            {
                list.add(f);
            }
            superClass = superClass.getSuperclass();
            fields = superClass.getDeclaredFields();
        } while (fields.length > 0);
        
        return list.toArray(new Field[list.size()]);
    }
    
}
