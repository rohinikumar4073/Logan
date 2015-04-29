/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.utiliies;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import pl.otros.logview.importer.LogImporterUsingParser;
import pl.otros.logview.parser.ParsingContext;
import pl.otros.logview.reader.ProxyLogDataCollector;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableView;
import pl.otros.logview.LogData;
import pl.otros.logview.importer.InitializationException;
import pl.otros.logview.parser.log4j.Log4jPatternMultilineLogParser;

/**
 *
 * @author Admin
 */
public class Filehandler {
    
 /*   public static String defaultParsePattern="C:\\Users\\Administrator\\Documents\\NetBeansProjects\\Logon\\src\\logan\\utiliies\\default.txt";
    public static String customTypeOne="C:\\Users\\Administrator\\Documents\\NetBeansProjects\\Logon\\src\\logan\\utiliies\\customtype1.txt";
    public static String tempStorageFile="C:\\Users\\Administrator\\Documents\\tempfiles.log";
;*/
   
    public static String defaultParsePattern= "\\default.txt";
    public static String customTypeOne="\\customtype1.txt";
    public static String tempStorageFile="\\tempfiles.log";
    public static int counter = 0;

    private TableView tableView;

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    public LogData[] readFile(File file) throws FileNotFoundException, IOException {
        LogData[] logdata = null;
        try {

            Properties p = new Properties();
        File f = new File("./");
            String filePath=f.getCanonicalPath();
            filePath= filePath.concat("\\src\\logan\\resources\\patterns").concat(Filehandler.customTypeOne );
            FileInputStream fileInputStream = new FileInputStream(new File(filePath));
            try {
                p.load(fileInputStream);
            } catch (IOException ex) {
                Logger.getLogger(Filehandler.class.getName()).log(Level.SEVERE, null, ex);
            }

            Log4jPatternMultilineLogParser logParser = new Log4jPatternMultilineLogParser();
            InputStream in = new FileInputStream(file);
            LogImporterUsingParser importerUsingParser = new LogImporterUsingParser(logParser);
            ParsingContext context = new ParsingContext();
            importerUsingParser.init(p);
            importerUsingParser.initParsingContext(context);

            // when
            ProxyLogDataCollector dataCollector = new ProxyLogDataCollector();
            importerUsingParser.importLogs(in, dataCollector, context);  // then
            logdata = dataCollector.getLogData();
            System.out.println(logdata.length);
        } catch (InitializationException ex) {
            Logger.getLogger(Filehandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return logdata;
    }

}
