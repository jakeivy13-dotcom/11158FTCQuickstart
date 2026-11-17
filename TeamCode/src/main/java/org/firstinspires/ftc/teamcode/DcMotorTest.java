package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Autonomous (name = "Test: motor")
public class DcMotorTest extends OpMode {
    private DcMotor testMotor;
    DcMotor test;

    @Override
    public void init(){


        testMotor = hardwareMap.get(DcMotor.class,"motor1");


    }

    @Override
    public void loop() {

        testMotor.setPower(1);

    }
}
