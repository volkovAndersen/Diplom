package pages.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import main.MainApp;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import tools.*;
import tools.actions.Actions;
import tools.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tools.windows.OpenNewWindow;

public class StudyWindowController {

    OpenNewWindow newWindow = null;

    @FXML
    private Button backButton;


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
    Binary binaryTest = new Binary();
    BinaryOpenCV binaryOpenCV = new BinaryOpenCV();
    Tesseract testTesseract = new Tesseract();

    Grey grey = new Grey();

    @FXML
    void initialize() {
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'studyWindow.fxml'.";
        assert currentFrame != null : "fx:id=\"currentFrame\" was not injected: check your FXML file 'studyWindow.fxml'.";

        /**
         * При нажатии назад -> Переходим в гланое меню + Закриваем считывание
         */
        backButton.setOnAction(event -> {
            Actions.goTo(newWindow, "/fxml/open.fxml");
            setClosed();
        });

        startCamera();
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
                Tesseract.setUp();
                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = () -> {
                    // effectively grab and process a single frame
                    Mat frame = grabFrame();
                    Mat frame_to_Tesseract =  grabFrame();

//                    Imgproc.cvtColor(frame_to_Tesseract, frame_to_Tesseract, Imgproc.COLOR_BGR2GRAY);
//                    Imgproc.cvtColor(frame_to_Tesseract, frame_to_Tesseract, Imgproc.THRESH_BINARY);
//                    BufferedImage img_OpenCV = ConvertToBufferImage.mat2BufImg(frame_to_Tesseract);
//                    testTesseract.searchText(img_OpenCV);

                    //обработка
                    BufferedImage img = null;
                    try {
                        img = Utils.matToBufferedImage(frame_to_Tesseract);
                    } catch (IOException e) {
                        e.printStackTrace( );
                    }
                    //ConvertToBufferImage.mat2BufImg(frame_to_Tesseract);
                    // BufferedImage greyBuff = grey.toGrey(img);
                    //BufferedImage binaryBuff = binaryTest.binaryTest(img);


                    // convert and show the frame
                    Image imageToShow = Utils.mat2Image(frame);
                    updateImageView(currentFrame, imageToShow);
                    testTesseract.searchText(img);
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
