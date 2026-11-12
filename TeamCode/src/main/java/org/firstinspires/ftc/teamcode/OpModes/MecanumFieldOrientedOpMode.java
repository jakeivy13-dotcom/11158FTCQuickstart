package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;

@TeleOp(name = "Mecanum Field Oriented")
public class MecanumFieldOrientedOpMode extends OpMode {

    MecanumDrive drive = new MecanumDrive();

    double forward,strafe,rotate;

    @Override
    public void init() {
        drive.init(hardwareMap);

    }
    double r = 1;


    @Override
    public void loop() {

        forward = gamepad1.left_stick_y;
        strafe = gamepad1.right_stick_x;
        rotate = (gamepad1.right_bumper ? 0:1 ) + (gamepad1.left_bumper ? 0:-1) ;

        double s = gamepad2.left_stick_x;
        double t = gamepad2.right_stick_y;

        if (gamepad2.leftBumperWasPressed()){
            r -= .1;
        }
        if (gamepad2.rightBumperWasPressed()){
            r += .1;
        }



        drive.driveFieldRelative(forward,strafe,rotate);
        drive.x(s);
        drive.i(t*r);

        telemetry.clearAll();
        telemetry.addData("Intake Speed( Bumpers to change the speed) (Right Joystick to move it)", r);
        telemetry.update();



    }
}

