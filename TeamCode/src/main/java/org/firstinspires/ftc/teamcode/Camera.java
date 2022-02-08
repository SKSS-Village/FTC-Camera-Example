package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaCurrentGame;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TfodCurrentGame;

@TeleOp
public class Camera extends LinearOpMode {

    private VuforiaCurrentGame vuforiaFreightFrenzy;
    private TfodCurrentGame tfodFreightFrenzy;

    Recognition recognition;

    @Override
    public void runOpMode() {
        List<Recognition> recognitions;
        int index;

        vuforiaFreightFrenzy = new VuforiaCurrentGame();
        tfodFreightFrenzy = new TfodCurrentGame();

        vuforiaFreightFrenzy.initialize(
                "", // vuforiaLicenseKey
                VuforiaLocalizer.CameraDirection.BACK, // cameraDirection
                false, // useExtendedTracking
                false, // enableCameraMonitoring
                VuforiaLocalizer.Parameters.CameraMonitorFeedback.NONE, // cameraMonitorFeedback
                0, // dx
                0, // dy
                0, // dz
                AxesOrder.XYZ, // axesOrder
                0, // firstAngle
                -90, // secondAngle
                0, // thirdAngle
                true); // useCompetitionFieldTargetLocations
        tfodFreightFrenzy.initialize(vuforiaFreightFrenzy, (float) 0.7, true, true);
        tfodFreightFrenzy.activate();
        tfodFreightFrenzy.setZoom(2.5, 16 / 9);
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
        if (opModeIsActive()) {
            while (opModeIsActive()) {
                recognitions = tfodFreightFrenzy.getRecognitions();
                if (recognitions.size() == 0) {
                    telemetry.addData("TFOD", "No items detected.");
                } else {
                    index = 0;
                    // Iterate through list and call a function to
                    // display info for each recognized object.
                    for (Recognition recognition_item : recognitions) {
                        recognition = recognition_item;
                        // Display info.
                        displayInfo(index);
                        // Increment index.
                        index = index + 1;
                    }
                }
                telemetry.update();
            }
        }
        // Deactivate TFOD.
        tfodFreightFrenzy.deactivate();

        vuforiaFreightFrenzy.close();
        tfodFreightFrenzy.close();
    }

    /**
     * Display info (using telemetry) for a recognized object.
     */
    private void displayInfo(int i) {
        double Left;
        double Top;
        double Right;
        double Bottom;

        Left = Double.parseDouble(JavaUtil.formatNumber(recognition.getLeft(), 0));
        Top = Double.parseDouble(JavaUtil.formatNumber(recognition.getTop(), 0));
        Right = Double.parseDouble(JavaUtil.formatNumber(recognition.getRight(), 0));
        Bottom = Double.parseDouble(JavaUtil.formatNumber(recognition.getBottom(), 0));


        telemetry.addData("label " + i, recognition.getLabel());
        telemetry.addData("Left, Top " + i, Left + ", " + Top);
        telemetry.addData("Right, Bottom " + i, Right + ", " + Bottom);
        if (Top > 450 && Top < 510) {
            telemetry.addData("Level", "1");
        } else if (Top > 240 && Top < 280) {
            telemetry.addData("Level", "2");
        } else if (Top > 200 && Top < 240) {
            telemetry.addData("Level", "3");
        } else {
            telemetry.addData("Level", "Unclassified");
        }

    }
}