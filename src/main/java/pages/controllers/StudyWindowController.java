package pages.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.MainApp;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import persons.current.CurrentChild;
import tools.*;
import tools.actions.Actions;
import tools.litary.Litary;

import tools.utils.LanguageTesseract;
import tools.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tools.windows.OpenNewWindow;

public class StudyWindowController {

    OpenNewWindow newWindow = null;
    Litary litary = new Litary();
    private Connection connection;

    boolean flag = false;

    @FXML
    private TextField scoreField;

    @FXML
    private Button backButton;

    @FXML
    private Text textResultRigt;

    // the FXML image view
    @FXML
    private ImageView currentFrame;

    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    // the OpenCV object that realizes the video capture
    private VideoCapture capture;
    // a flag to change the button behavior
    private boolean cameraActive = false;
    // the id of the camera to be used
    private static int cameraId = 0;

    Tesseract testTesseract = new Tesseract();


    @FXML
    private Text literalView;

    @FXML
    private Text textResultFalse;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Text score;

    @FXML
    void initialize() {

        progressBar.setProgress(0.0);
        LanguageTesseract.setLang("ENG");


        /**
         * При нажатии назад -> Переходим в гланое меню + Закриваем считывание
         */
        backButton.setOnAction(event -> {
            try {
                sendResult();
            } catch (SQLException e) {
                e.printStackTrace( );
            }
            Counter.countDown();
            Actions.goTo(newWindow, "/fxml/open.fxml");
            setClosed();
        });

        Counter.countDown();
        startCamera();
        literalView.setText(litary.getCurrentLitary());

    }

    public void sendResult() throws SQLException {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now= new Date(System.currentTimeMillis());
        connection = Connect.getConnection();
        Statement statement = connection.createStatement();
        //DateTimeFormatter.ofPattern(\"yyyy/MM/dd HH:mm:ss\").format(now)
        ResultSet resultSet = statement.executeQuery("INSERT INTO [dbo].[Результаты]([Результат],[Дата] ,[FKРебенка])VALUES(" + Counter.getCounter() + ", '" + formatter.format(now) + "'," + CurrentChild.getIdOfCurrentChild()+")");

        resultSet.close();
        statement.close();
    }

    @FXML
    protected void startCamera()
    {
        // тут устанавливаем освоюождение ресурсов видеофиксации поле закрытия окна
        MainApp.getStage().setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
            setClosed();
        });

        capture = new VideoCapture();

        if (!this.cameraActive)
        {
            // start the video capture
            this.capture.open(cameraId);

            // is the video stream available?
            if (this.capture.isOpened())
            {
                this.cameraActive = true;
                Tesseract.setUp(LanguageTesseract.getLang());

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = () -> {
                    // effectively grab and process a single frame
                    Mat frame = grabFrame();
                    Mat frame_to_Tesseract =  grabFrame();


                    //обработка
                    BufferedImage img = null;
                    try {
                        img = Utils.matToBufferedImage(frame_to_Tesseract);
                    } catch (IOException e) {
                        e.printStackTrace( );
                    }

                    textResultRigt.setText("");
                    textResultFalse.setText("");

                    if (flag)
                        literalView.setText(litary.getCurrentLitary());

                    // convert and show the frame
                    Image imageToShow = Utils.mat2Image(frame);
                    updateImageView(currentFrame, imageToShow);
                    if (testTesseract.searchText(img).contains(literalView.getText()) && !testTesseract.searchText(img).equals("")) {
                        litary.deleteCurrentLitary();
                        Counter.plusCounter();

                        textResultRigt.setText("Right");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace( );
                        }
                        flag = true;
                    }
                    else {
//                        textResultFalse.setText("Sorry, no... try again");
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace( );
//                        }
                        flag = false;
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

            }
            else
            {
                // log the error
                System.err.println("Impossible to open the camera connection...");
            }
        }
        else
        {
            // the camera is not active at this point
            this.cameraActive = false;

            // stop the timer
            this.stopAcquisition();
        }
    }

    private Mat grabFrame()
    {
        // init everything
        Mat frame = new Mat();

        // check if the capture is open
        if (this.capture.isOpened())
        {
            try
            {
                // read the current frame
                this.capture.read(frame);

                /*
                // if the frame is not empty, process it
                if (!frame.empty())
                {
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                }
                   */
            }
            catch (Exception e)
            {
                // log the error
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown())
        {
            try
            {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened())
        {
            // release the camera
            this.capture.release();
        }
    }

    private void updateImageView(ImageView view, Image image)
    {
        Utils.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    protected void setClosed()
    {
        this.stopAcquisition();
    }


}
