package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TurretMovementTest {

    private Servo servoPos;

    public void init(HardwareMap HwMap){
        servoPos = HwMap.get(Servo.class,"servoPos");
    }

    public void setServoPos(double angle){
        servoPos.setPosition(angle);
    }


}
