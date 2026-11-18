package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import java.util.HashMap;
import java.util.Map;


@TeleOp
public class FlyWheelTuning extends OpMode {
    public DcMotorEx flywheelMotor;
    public DcMotor intake;

    public double highVelocity = 2400;
    public double lowVelocity = 1200;
    double curTargetVelocity = highVelocity;
    boolean isCurTargetVelocityHigh = true;

    private double F = 11.7;
    private double P = 0;

    double[] stepSizes = {100.0, 10.0, 1.0, 0.1, 0.0001};
    int stepIndex = 1;


    PIDFCoefficients pidfCoefficients = new PIDFCoefficients(P, 0, 0, F);


    @Override
    public void init() {
        flywheelMotor = hardwareMap.get(DcMotorEx.class, "outtake");
        flywheelMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        intake = hardwareMap.dcMotor.get("intake");

        intake.setDirection(DcMotor.Direction.REVERSE);
        flywheelMotor.setDirection(DcMotor.Direction.REVERSE);

        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        telemetry.addLine("Init complete");


    }

    @Override
    public void loop() {
        //get gamepad commands
        //set target velocity

        if (gamepad1.yWasPressed()) {
            if (isCurTargetVelocityHigh) {
                curTargetVelocity = lowVelocity;
                isCurTargetVelocityHigh = false;
            } else {
                curTargetVelocity = highVelocity;
                isCurTargetVelocityHigh = true;
            }
        }

        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }
        if (gamepad1.aWasPressed()) {
            curTargetVelocity = 0;
            isCurTargetVelocityHigh = false;
        }

        if (gamepad1.dpadLeftWasPressed()) {
            F -= stepSizes[stepIndex];
            pidfCoefficients.f = F;
            flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        }

        if (gamepad1.dpadRightWasPressed()) {
            F += stepSizes[stepIndex];
            pidfCoefficients.f = F;
            flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        }

        if (gamepad1.dpadUpWasPressed()) {
            P += stepSizes[stepIndex];
            pidfCoefficients.p = P;
            flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        }

        if (gamepad1.dpadDownWasPressed()) {
            P -= stepSizes[stepIndex];
            pidfCoefficients.p = P;
            flywheelMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfCoefficients);
        }
        if (gamepad1.leftBumperWasPressed()) {
            if (isCurTargetVelocityHigh) {
                highVelocity -= stepSizes[stepIndex];
            } else {
                lowVelocity -= stepSizes[stepIndex];
            }
        }
        if (gamepad1.rightBumperWasPressed()) {
            if (isCurTargetVelocityHigh) {
                highVelocity += stepSizes[stepIndex];
            } else {
                lowVelocity += stepSizes[stepIndex];
            }
        }

        intake.setPower(gamepad1.left_stick_y * 1);
        flywheelMotor.setVelocity(curTargetVelocity);

        double curVelocity = flywheelMotor.getVelocity();
        double error = curTargetVelocity - curVelocity;

        //SO VELOCITY and RPM are different, the max velocity is apparently is 2,800.
        //Set telemetry
        telemetry.addLine("Controller Schemes tends to be Y=Up; A=Down; B=Right; X=Left  ");
        telemetry.addData("Target Velocity", curTargetVelocity);
        telemetry.addData("Current Velocity", "%.2f", curVelocity);
        telemetry.addData("Error", "%.2f", error);
        telemetry.addLine("Y to change from high velocity to low velocity & vice versa, A to turn off");
        telemetry.addLine("-----------------");

        telemetry.addData("HighVelocity", highVelocity);
        telemetry.addData("LowVelocity", lowVelocity);
        telemetry.addLine("Use Bumpers to decrease/increase high and low velocities");
        telemetry.addLine("-----------------");

        telemetry.addData("Tuning P", "%.4f (D-Pad U/D)", P);
        telemetry.addData("Tuning F", "%.4f (D-Pad L/R)", F);
        telemetry.addData("Step Size", "%.4f (B Button)", stepSizes[stepIndex]);

        telemetry.update();

    }
}
