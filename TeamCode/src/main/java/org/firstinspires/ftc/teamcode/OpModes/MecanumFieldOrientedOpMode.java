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
    double turretTurnSpeed;


    @Override
    public void loop() {


        rotate = (gamepad1.right_bumper ? 0:1) + (gamepad1.left_bumper ? 0:-1);

        turretTurnSpeed = (gamepad2.dpad_up ? 0:1) + (gamepad2.dpad_down ? 0:-1);

        forward = (-gamepad1.left_stick_y);

        strafe = (gamepad1.right_stick_x);





        drive.drive(strafe, forward,rotate);
        drive.driveOuttake(gamepad2.left_stick_y);
        drive.driveIntake(gamepad2.right_stick_y);
        drive.driveContServo(turretTurnSpeed);

        telemetry.clearAll();
        telemetry.addLine( drive.toString() );
        telemetry.update();



    }
}

