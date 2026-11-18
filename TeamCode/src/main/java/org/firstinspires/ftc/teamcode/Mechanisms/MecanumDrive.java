
        package org.firstinspires.ftc.teamcode.Mechanisms;




import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumDrive {
    private DcMotor frontLeftMotor, backLeftMotor,frontRightMotor,backRightMotor, outtakeMotor, intakeMotor ;
    private IMU imu;
    private DcMotorEx encoder;

    private Servo servo;

    private CRServo turretServo;
    ElapsedTime runtime = new ElapsedTime();





    public void init(HardwareMap hwMap){

        //Connected to Port 3
        frontLeftMotor = hwMap.get(DcMotor.class,"FLmotor");
        //Connected to Port 1
        backLeftMotor = hwMap.get(DcMotor.class,"BLmotor");
        //Connected to Port 2
        frontRightMotor = hwMap.get(DcMotor.class,"FRmotor");
        //Connected to Port 0
        backRightMotor = hwMap.get(DcMotor.class,"BRmotor");

        outtakeMotor = hwMap.get(DcMotor.class,"outtake");

        intakeMotor = hwMap.get(DcMotor.class,"intake");

        servo = hwMap.get(Servo.class,"servo");

        turretServo = hwMap.get(CRServo.class,"turretservo");

        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);




        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        imu = hwMap.get(IMU.class,"Imu");

        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.RIGHT);

        imu.initialize(new IMU.Parameters(RevOrientation ));

    }

    // Methods---------------------------------------------------

    public void sleep(double time) {
        runtime.reset();
        while (runtime.milliseconds() < time) {
            runtime.milliseconds();
        }
    }

    public void drive(double forward, double strafe, double rotate){

        double frontLeftPower = forward + strafe - rotate;
        double backLeftPower = forward - strafe + rotate;
        double frontRightPower = forward - strafe - rotate;
        double backRightPower = forward + strafe + rotate;

        double maxPower = 1;
        double maxSpeed = 1;

        maxPower = Math.max(maxPower, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        frontLeftMotor.setPower(maxSpeed * (frontLeftPower/maxPower));
        backLeftMotor.setPower(maxSpeed * (backLeftPower/maxPower));
        frontRightMotor.setPower(maxSpeed * (frontRightPower/maxPower));
        backRightMotor.setPower(maxSpeed * (backRightPower/maxPower));

    }

    public void driveFieldRelative(double forward, double strafe, double rotate){

        double theta = Math.atan2(forward,strafe);
        double r = Math.hypot(forward,strafe);

        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        double newForward = r * Math.sin(theta ); //
        double newStrafe = r * Math.cos(theta);

        this.drive(newForward,newStrafe,rotate);

    }

    public void driveOuttake(double power){
        outtakeMotor.setPower(power);
        }
    public void driveIntake(double power){
            intakeMotor.setPower(power);
        }
    public void setPosServo(double position){
        servo.setPosition(position);
        }
    public void driveContServo(double power){
        turretServo.setPower(power);
    }

    public double getServoPos(){ return servo.getPosition(); }

    public String toString(){
        try {
            return String.format(
                    "=== MecanumDrive Status ===\n" +
                            "--- Drive Motors (Port | Pwr | Pos | Busy?) ---\n" +
                            "FL (Port %d): Pwr=%.2f | Pos=%d | Busy=%b \n" +
                            "BL (Port %d): Pwr=%.2f | Pos=%d | Busy=%b \n" +
                            "FR (Port %d): Pwr=%.2f | Pos=%d | Busy=%b \n" +
                            "BR (Port %d): Pwr=%.2f | Pos=%d | Busy=%b \n" +
                            "--- Other Motors and Servos ---\n" +
                            "Intake  (Port %d): Pwr=%.2f | Busy=%b \n" +
                            "Outtake (Port %d): Pwr=%.2f | Busy=%b \n" +
                            "Servo   (Port %d): Pos=%.2f | \n",
                    frontLeftMotor.getPortNumber(), frontLeftMotor.getPower(), frontLeftMotor.getCurrentPosition(), frontLeftMotor.isBusy(),
                    backLeftMotor.getPortNumber(), backLeftMotor.getPower(), backLeftMotor.getCurrentPosition(), backLeftMotor.isBusy(),
                    frontRightMotor.getPortNumber(), frontRightMotor.getPower(), frontRightMotor.getCurrentPosition(), frontRightMotor.isBusy(),
                    backRightMotor.getPortNumber(), backRightMotor.getPower(), backRightMotor.getCurrentPosition(), backRightMotor.isBusy(),
                    intakeMotor.getPortNumber(), intakeMotor.getPower(), intakeMotor.isBusy(),
                    outtakeMotor.getPortNumber(), outtakeMotor.getPower(), outtakeMotor.isBusy(),
                    servo.getPortNumber(), servo.getPosition()
            );
        } catch (Exception e) {
            return String.format("Error: %S", e );
        }
    }
}




