package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive; // Fixed package/class name spacing

@Autonomous(name = "Shoot and park", group = "Scrimmage")
public class TestAutonomous extends LinearOpMode {

    MecanumDrive drive = new MecanumDrive();

    @Override
    public void runOpMode() throws InterruptedException {
        drive.init(hardwareMap);

        waitForStart();

        if (opModeIsActive()) {
            // Your autonomous routine runs sequentially here:
            drive.driveOuttake(-0.88);
            sleep(3000);

            for (int i = 0; i < 3; i++) {
                drive.driveIntake(1.0);
                sleep(80); // Intake runs for 80ms
                drive.driveIntake(0.0);
                sleep(700); // Intake pauses for 700ms
            }

            drive.driveOuttake(0);
            drive.driveIntake(0);
            drive.drive(-0.1, 0.7, 0);
            sleep(700);
            drive.drive(0, 0, 0);
        }
    }
}
