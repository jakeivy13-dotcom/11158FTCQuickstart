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


    @Override
    public void loop() {

        forward = gamepad1.left_stick_y;
        strafe = gamepad1.right_stick_x;
        rotate = (gamepad1.right_bumper ? 0:1 ) + (gamepad1.left_bumper ? 0:-1) ;




        drive.driveFieldRelative(forward,strafe,rotate);
        drive.driveOuttake(gamepad2.left_stick_y);
        drive.driveIntake(gamepad2.right_stick_y);





    }
}

