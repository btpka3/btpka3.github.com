package me.test.sto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

public class Sto implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Sto.class);

    private static final String STO_QUERY_FORM_URL = "http://q.sto.cn/track.aspx";

    private final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_24);
    private final String expressNo;
    private final Map<String, List<Event>> resultSet;

    public Sto(Map<String, List<Event>> resultSet, String expressNo) {
        this.resultSet = resultSet;
        this.expressNo = expressNo;
    }

    @Override
    public void run() {
        try {
            List<Event> traceInfo = getTraceInfo();
            resultSet.put(expressNo, traceInfo);
            logger.debug("[{}] : ok ", expressNo);
        } catch (Exception e) {
            logger.error("[" + expressNo + "] : error ", e);
        } finally {
            webClient.closeAllWindows();
        }

    }

    public List<Event> getTraceInfo() throws FailingHttpStatusCodeException,
            MalformedURLException, IOException {

        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.closeAllWindows();

        final HtmlPage queryFormPage = webClient.getPage(STO_QUERY_FORM_URL);
        HtmlTextArea input = (HtmlTextArea) queryFormPage.getElementById("wen");
        input.click();
        input.setText(expressNo);
        HtmlSubmitInput queryBtn = (HtmlSubmitInput) queryFormPage.getElementById("btnQuery");
        HtmlPage resultPage = queryBtn.click();

        Document doc = Jsoup.parse(resultPage.asXml());
        Elements rows = doc.select("table.tab_result tr");
        rows.remove(0);

        List<Event> resultList = new ArrayList<Event>(10);
        for (Element row : rows) {
            String time = row.child(0).text();
            String event = row.child(1).text();

            resultList.add(new Event(expressNo, time, event));
        }

        return resultList;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Usage : java me.test.sto.Sto <expressNoListFile>");
            System.exit(1);
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            System.err.println("expressNoListFile not exist. " + file.getAbsolutePath());
            System.exit(2);
        }
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 10, TimeUnit.SECONDS, queue);

        long startTime = System.currentTimeMillis();
        logger.info("查询 —— 开始");
        Scanner scanner = new Scanner(new FileInputStream(file));
        Collection<Future<?>> futures = new LinkedList<Future<?>>();
        Map<String, List<Event>> resultSet = new LinkedHashMap<String, List<Event>>();
        try {
            while (scanner.hasNextLine()) {
                String expressNo = StringUtils.trimToNull(scanner.nextLine());
                if (expressNo == null) {
                    continue;
                }
                futures.add(executor.submit(new Sto(resultSet, expressNo)));
            }
        } finally {
            scanner.close();
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("查询 —— 结束, 耗时 :{} ms", endTime - startTime);

        exportXlsx(resultSet);
    }

    public static void exportXlsx(Map<String, List<Event>> resultSet) throws FileNotFoundException, IOException {
        long startTime = System.currentTimeMillis();
        logger.info("导出 —— 开始");

        final int cols = 3;
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sh = wb.createSheet();

        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        titleStyle.setFillPattern(XSSFCellStyle.FINE_DOTS);

        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);

        Row row;
        Cell cell;

        // 行
        int i = 0;
        // 列
        int j = 0;

        row = sh.createRow(i);

        cell = row.createCell(j);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("STO 快递单号");

        j++;
        cell = row.createCell(j);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("时间");

        j++;
        cell = row.createCell(j);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("事件");

        for (Entry<String, List<Event>> entry : resultSet.entrySet()) {
            String expressNo = entry.getKey();
            List<Event> resultList = entry.getValue();

            for (Event event : resultList) {
                i++;
                row = sh.createRow(i);

                j = 0;
                cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(expressNo);

                j++;
                cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(StringUtils.trimToNull(event.time));

                j++;
                cell = row.createCell(j);
                cell.setCellStyle(cellStyle);
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(StringUtils.trimToNull(event.event));
            }

        }

        for (int col = 0; col < cols; col++) {
            sh.autoSizeColumn(col);
        }

        wb.write(new FileOutputStream("sto.xlsx"));
        wb.close();
        long endTime = System.currentTimeMillis();
        logger.info("导出 —— 结束, 耗时 :{} ms", endTime - startTime);
    }

    public static class Event {
        public String expressNo;
        public String time;
        public String event;

        public Event() {
        }

        public Event(String expressNo, String time, String event) {
            this.expressNo = expressNo;
            this.time = time;
            this.event = event;
        }
    }
}
