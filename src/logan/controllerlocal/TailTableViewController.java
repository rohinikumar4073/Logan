package logan.controllerlocal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import logan.Main;
import logan.model.LoggerData;
import logan.utiliies.Filehandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;
import pl.otros.logview.LogData;

public class  TailTableViewController extends BasicController implements Initializable {

    private File file;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<LoggerData> logDataTable;
    @FXML
    private TableColumn<LoggerData, String> dateColumn;
    @FXML
    private TableColumn<LoggerData, String> threadColumn;

    @FXML
    private Label date;
    @FXML
    private Label thread;
    @FXML
    private Label priority;
    @FXML
    private Label category;
    @FXML
    private Label message;
    @FXML
    private Label className;
    @FXML
    private Label lineNumber;
    @FXML
    private Label methodName;

    public static int noOfLines;

    @FXML
    public void listenData(ActionEvent event) throws IOException {
        listenData();
    }

    public class ShowLinesListener extends TailerListenerAdapter {

        @Override
        public void handle(String line) {
            arrayList.add(line);
        }
    }
    ArrayList<String> arrayList = new ArrayList<String>();

    public void listenFile(File file) {

        TailerListener listener = new ShowLinesListener();

        Tailer tailer = new Tailer(file, listener, 1000);
        tailer.run();

        System.out.println("Tailer Started");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void setApp(Main application, File file) throws FileNotFoundException {
        setApplication(application);
        setStage(application.getStage());
        try {
            createTableView(file);
        } catch (IOException ex) {
            Logger.getLogger(TailTableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setApp(Main application, File file, String str) throws FileNotFoundException {
        setApplication(application);
        setStage(application.getStage());
        createTableView(file, str);
    }

    public void setApp(Main application) {
        setApplication(application);
        setStage(application.getStage());
    }

 private void listenData() throws IOException {
        File f = new File("./");

        String filePath=f.getCanonicalPath();
      filePath= filePath.concat("\\src\\logan\\resources").concat(Filehandler.tempStorageFile);
        System.out.println(filePath);
        File fileouput = new File(filePath);

        // if file doesnt exists, then create it
        if (!fileouput.exists()) {
            fileouput.createNewFile();
        }
                 System.out.println("chekcpoint 0");

        FileWriter fw = new FileWriter(fileouput.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
                 System.out.println(file);
                 System.out.println(file.getAbsolutePath());

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int lines = 0;
        String sCurrentLine;
                 System.out.println("chekcpoint 1");

        while ((sCurrentLine = reader.readLine()) != null) {
            lines++;
            if (lines >= noOfLines) {
                bw.write(sCurrentLine);
                                bw.write("\n");

                System.out.println(sCurrentLine);
            }
        }
        reader.close();
        bw.close();
                 System.out.println("chekcpoint 2");

        LogData[] logDatas= new Filehandler().readFile(fileouput);
       final ObservableList<LoggerData> data = createObservableArrayList(logDatas, FXCollections.observableArrayList());
       System.out.println("chekcpoint 4");
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<LoggerData> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(logData -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (logData.getPriority().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (logData.getMessage().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (logData.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (logData.getClassName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        logDataTable.setItems(filteredData);
                 System.out.println("chekcpoint last");
     
            }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
exec.scheduleAtFixedRate(new Runnable() {
  @Override
  public void run() {
      try {
          System.out.println("Log");
          if(file!=null)
          listenData();
      } catch (IOException ex) {
          Logger.getLogger(TailTableViewController.class.getName()).log(Level.SEVERE, null, ex);
      }
     
  }

           
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void createTableView(File file, String str) throws FileNotFoundException {

        TableView tableView = new TableView();
        TableColumn timeStamp = new TableColumn("Time Stamp");
        TableColumn priority = new TableColumn("Priority");
        TableColumn thread = new TableColumn("Thread");
        TableColumn message = new TableColumn("Message");
        TableColumn className = new TableColumn("ClassName");
        TableColumn method = new TableColumn("Method");
        TableColumn lineNumber = new TableColumn("Line Number");
        timeStamp.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("date"));
        priority.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("priority"));
        thread.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("threadId"));
        message.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("message"));
        className.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("className"));
        method.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("method"));
        lineNumber.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("lineNumber"));
        getVbox().getChildren().add(tableView);
        tableView.getColumns().addAll(timeStamp, priority, thread, message, className, method, lineNumber);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int lines = 0;
        try {
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();

        } catch (IOException ex) {
            Logger.getLogger(LogDataTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        noOfLines = lines;
        //S  new Filehandler().listenFile(file,tableView);
        this.logDataTable = tableView;
        this.file = file;

    }

}
