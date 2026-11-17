package org.firstinspires.ftc.teamcode.Mechanisms;

import com.pedropathing.tuning.autotune.Display;

public class EncoderConverter {
    double gearRatio;
    double encoderRev;

    double WheelCircumMM;

    EncoderConverter(double gearRatio, double encoderRes, double wheelCircumMM){
        this.gearRatio = gearRatio;
        this.encoderRev = encoderRes;
        this.WheelCircumMM = wheelCircumMM;
    }

    double ticksPerMM = (encoderRev/WheelCircumMM) * gearRatio;

    double ticksPerDegree = (encoderRev/360)*gearRatio;







}
