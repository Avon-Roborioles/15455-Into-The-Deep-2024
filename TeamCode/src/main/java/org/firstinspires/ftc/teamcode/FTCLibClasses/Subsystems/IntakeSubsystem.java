package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.function.BooleanSupplier;

public class IntakeSubsystem extends SubsystemBase {

    private ColorSensor colorSensor1;
    private ColorSensor colorSensor2;

    private CRServo intakeServo;

    private ServoEx verticalServo;
    private IntakeHeight verticalServoTarget;

    private Motor extendMotor;
    private double motorStartPos;
    private ExtendPos extendPos = ExtendPos.IN;

    private AllianceColor allianceColor;

    private Telemetry telemetry;
    private BooleanSupplier doTelemetry;


    private String telemetryToReturn = "";

    private double combinedRedRaw = -1;
    private double combinedBlueRaw = -1;
    private double combinedGreenRaw = -1;

    private Gamepad gamepad;


    public IntakeSubsystem(HardwareMap hMap, AllianceColor alliance, Telemetry telemetry, BooleanSupplier doTelemetry, Gamepad gamepad){
        //colorSensor1 = hMap.get(ColorSensor.class, RobotConfig.IntakeConstants.colorSensor1Name);
        //colorSensor2 = hMap.get(ColorSensor.class, RobotConfig.IntakeConstants.colorSensor2Name);

        intakeServo = hMap.get(CRServo.class,RobotConfig.IntakeConstants.intakeServoName);
        this.allianceColor = alliance;

        verticalServo = new SimpleServo(
                hMap,
                RobotConfig.IntakeConstants.verticalServoName,
                RobotConfig.IntakeConstants.verticalServoMinDegrees,
                RobotConfig.IntakeConstants.verticalServoMaxDegrees
        );
        extendMotor = new MotorEx(hMap,RobotConfig.IntakeConstants.extendMotorName);
        extendMotor.setRunMode(Motor.RunMode.PositionControl);
        extendMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        extendMotor.setPositionCoefficient(RobotConfig.IntakeConstants.motorPCoefficient);


        motorStartPos = extendMotor.getCurrentPosition();
        this.telemetry = telemetry;
        this.doTelemetry = doTelemetry;
        this.gamepad = gamepad;
    }


    //This method doesn't run the bot. It's only for telemetry purposes.
    // DO NOT PUT LOGIC INSIDE THIS
    @Override
    public void periodic(){
        //if(doTelemetry.getAsBoolean()) {
            telemetry.addLine("============COLOR SENSORS============");
//            telemetry.addData("Combined Red Raw",colorSensor1.red()+colorSensor2.red());
//            telemetry.addData("Combined Blue Raw",colorSensor1.blue()+colorSensor2.blue());
//            telemetry.addData("Combined Green Raw", colorSensor1.green()+colorSensor2.green());

            telemetry.addLine("============Intake Servo============");
            telemetry.addData("Intake Servo Power",intakeServo.getPower());

            telemetry.addLine("============Vertical Servo============");
            telemetry.addData("Vertical Servo Position",verticalServo.getPosition());
            telemetry.addData("Vertical Servo Target",verticalServoTarget);

            telemetry.addLine("============Extend Motor============");
            telemetry.addData("Motor Position",extendMotor.getCurrentPosition());


       // }
    }

    public SampleState hasCorrectSample(){
        //It adds both sensor values and then takes the proportion of the colors.
        //We do this because the closer the sample is, the more color it receives
        //The proportion of the colors is what matters



//        double red = colorSensor1.red() + colorSensor2.red();
//        double blue = colorSensor1.blue() + colorSensor2.blue();
//        double green = colorSensor1.green() + colorSensor2.green();
//
//        double max = Math.max(Math.max(red,blue),green);
//
//        red/=max;
//        blue/=max;
//        green/=max;
//
//
//        telemetryToReturn = "Red: " +red+"\nBlue: " + blue+"\nGreen: " +green;
//
//
//        if(blue>red&&blue>green){
//            switch(allianceColor){
//                case RED:
//                    return SampleState.WRONG_SAMPLE;
//                case BLUE:
//                    return SampleState.CORRESPONDING_SAMPLE;
//            }
//        } else if (red>blue&&red>green){
//            switch(allianceColor){
//                case RED:
//                    return SampleState.CORRESPONDING_SAMPLE;
//                case BLUE:
//                    return SampleState.WRONG_SAMPLE;
//            }
//            return SampleState.WRONG_SAMPLE;
//        } else if (Math.abs(red-green) < RobotConfig.IntakeConstants.colorSensorRedToGreenThreshold){
//            return SampleState.YELLOW_SAMPLE;
//        }
//
//        return SampleState.NO_SAMPLE;


        if (gamepad.b){
            return SampleState.CORRESPONDING_SAMPLE;
        }

        return SampleState.NO_SAMPLE;
    }

    public void spinWheelsUp(){
        intakeServo.setPower(RobotConfig.IntakeConstants.intakeServoUpDirection *1);
    }

    public void spinWheelsDown(){
        intakeServo.setPower(RobotConfig.IntakeConstants.intakeServoUpDirection *-1);
    }

    public void stopIntakeWheels(){
        intakeServo.setPower(0);
    }

    public void moveIntakeUp(){
        verticalServo.setPosition(RobotConfig.IntakeConstants.verticalServoUpPosition);
        verticalServoTarget = IntakeHeight.UP;
    }

    public void moveIntakeDown(){
        verticalServo.setPosition(RobotConfig.IntakeConstants.verticalServoDownPosition);
        verticalServoTarget = IntakeHeight.DOWN;
    }

    public boolean isVerticalMotionDone(){
        switch (verticalServoTarget){
            case UP:
                return verticalServo.getPosition() == RobotConfig.IntakeConstants.verticalServoUpPosition;
            case DOWN:
                return verticalServo.getPosition() == RobotConfig.IntakeConstants.verticalServoDownPosition;
        }
        return false;
    }

    public void extendMotorOutFully(){
        extendMotor.setRunMode(Motor.RunMode.PositionControl);
        extendMotor.setTargetPosition(RobotConfig.IntakeConstants.motorMaxPosition);
        extendMotor.set(RobotConfig.IntakeConstants.motorExtendSpeed);
        extendPos = ExtendPos.OUT;
    }

    public void retractMotorFully(){
        extendMotor.setRunMode(Motor.RunMode.PositionControl);
        extendMotor.setTargetPosition(RobotConfig.IntakeConstants.motorMinPosition);
        extendMotor.set(RobotConfig.IntakeConstants.motorSlowRetractionRawPower);
        extendPos = ExtendPos.OUT;
    }


    public boolean isExtendAtClearPos(){
        return (extendMotor.getCurrentPosition()< RobotConfig.IntakeConstants.intakeClearBucketPos+ RobotConfig.IntakeConstants.motorDegreeOfError)
                ||(extendMotor.getCurrentPosition()>RobotConfig.IntakeConstants.intakeClearBucketPos - RobotConfig.IntakeConstants.motorDegreeOfError);
    }

    public void slowRetractMotor(){
        extendMotor.setRunMode(Motor.RunMode.RawPower);
        extendMotor.set(RobotConfig.IntakeConstants.motorSlowRetractionRawPower);

    }
    public void stopExtend(){
        extendMotor.set(0);
    }

    public boolean extendFinished(){
        switch (extendPos){
            case IN:
                return extendMotor.getCurrentPosition()<
                        RobotConfig.IntakeConstants.motorMinPosition+RobotConfig.IntakeConstants.motorDegreeOfError;
            case OUT:
                return extendMotor.getCurrentPosition()>
                        RobotConfig.IntakeConstants.motorMaxPosition-RobotConfig.IntakeConstants.motorDegreeOfError;

        }
        return extendMotor.atTargetPosition();
    }

    private double getAdjustedMotorPos(){
        return extendMotor.getCurrentPosition();
    }

    public boolean intakeClearedBucket(){
        return extendMotor.getCurrentPosition()>= RobotConfig.IntakeConstants.intakeClearBucketPos;
    }

    public enum IntakeHeight{
        UP,
        DOWN
    }
    public enum ExtendPos{
        OUT,
        CLEAR,
        IN
    }
    public enum SampleState {
        CORRESPONDING_SAMPLE("Corresponding Sample"),
        WRONG_SAMPLE("Wrong Sample"),
        YELLOW_SAMPLE("Yellow Sample"),
        NO_SAMPLE("No Sample");

        private String name;
        SampleState(String name){
            this.name = name;
        }
        @Override
        public String toString(){
            return name;
        }
    }
}
