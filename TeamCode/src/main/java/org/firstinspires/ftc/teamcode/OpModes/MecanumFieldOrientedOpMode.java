package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Mechanisms.MecanumDrive;

@TeleOp(name = "Mecanum Field Oriented")
public class MecanumFieldOrientedOpMode extends OpMode {

    MecanumDrive drive = new MecanumDrive();

    double forward,strafe,rotate;
    double defaltPos;



    @Override
    public void init() {
        drive.init(hardwareMap);

        boolean servoGateState = true;

    }
    double r = .80;
    boolean servoGateState = true;

    double speed;


    @Override
    public void loop() {


        rotate = (gamepad1.right_bumper ? 0:1) + (gamepad1.left_bumper ? 0:-1);

        speed = (gamepad2.dpad_up ? 0:1) + (gamepad2.dpad_down ? 0:-1);

        forward = (-gamepad1.left_stick_y);

        strafe = (gamepad1.right_stick_x);

        if (gamepad2.leftBumperWasPressed()){
            r -= .02;
        }
        if (gamepad2.rightBumperWasPressed()){
            r += .02;
        }
        /*if (servoGateState && gamepad2.aWasPressed()){
            defaltPos = drive.getServoPos();
            drive.setPosServo(defaltPos+5);
            servoGateState = false;
        }if (!servoGateState && gamepad2.aWasPressed()) {
            drive.setPosServo(defaltPos);
            servoGateState = true;
        }*/




        drive.drive(strafe, forward,rotate);
        drive.driveOuttake(r*(gamepad2.left_stick_y));
        drive.driveIntake(-gamepad2.right_stick_y);
        drive.setContServo(speed);

        telemetry.clearAll();
        telemetry.addData("Outtake speed", r);
        telemetry.update();



    }
}

