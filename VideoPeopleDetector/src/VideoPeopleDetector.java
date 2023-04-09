import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.highgui.HighGui.destroyAllWindows;
import static org.opencv.videoio.VideoWriter.fourcc;

public class VideoPeopleDetector {
    public static void main(String[] args) {

        // Load the OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Open the input video file
        String filename = "video.mp4";
        VideoCapture capture = new VideoCapture(filename);
        if (!capture.isOpened()) {
            System.out.println("Could not open video file: " + filename);
            System.exit(1);
        }

        // Create the output video file
        int codec = fourcc('X', '2', '6', '4');
        Size frameSize = new Size((int) capture.get(3), (int) capture.get(4));
        VideoWriter writer = new VideoWriter("output.mp4", codec, capture.get(5), frameSize, true);

        // Load the cascade classifier for detecting people
        CascadeClassifier classifier = new CascadeClassifier("haarcascade_fullbody.xml");

        // Process each frame in the input video
        Mat frame = new Mat();
        int count = 0;
        while (capture.read(frame)) {

            // Convert the frame to grayscale
            Mat gray = new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);

            // Detect people in the grayscale frame
            MatOfRect people = new MatOfRect();
            classifier.detectMultiScale(gray, people);

            // Draw rectangles around the detected people
            for (Rect rect : people.toArray()) {
                Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
            }

            // Write the processed frame to the output video file
            writer.write(frame);
            count++;

            // Display the processed frame in a window
            imshow("Video", frame);
            waitKey(1);
        }

        // Release the input and output video files
        capture.release();
        writer.release();
        destroyAllWindows();

        // Print the number of frames processed
        System.out.println("Processed " + count + " frames.");

    }
}
