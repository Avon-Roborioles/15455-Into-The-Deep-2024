package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class IntakeSubsystem extends SubsystemBase {

    private ColorSensor colorSensor1;
    private ColorSensor colorSensor2;

    private CRServo intakeServo;

    private ServoEx verticalServo;
    private IntakeHeight verticalServoTarget;

    private Motor extendMotor;

    private AllianceColor allianceColor;


    private String telemetryToReturn = "";

    public IntakeSubsystem(HardwareMap hMap, AllianceColor alliance){
        colorSensor1 = hMap.get(ColorSensor.class, RobotConfig.IntakeConstants.colorSensor1Name);
        colorSensor2 = hMap.get(ColorSensor.class, RobotConfig.IntakeConstants.colorSensor2Name);

        intakeServo = hMap.get(CRServo.class,RobotConfig.IntakeConstants.intakeServoName);
        this.allianceColor = alliance;

        verticalServo = new SimpleServo(
                hMap,
                RobotConfig.IntakeConstants.verticalServoName,
                RobotConfig.IntakeConstants.verticalServoMinDegrees,
                RobotConfig.IntakeConstants.verticalServoMaxDegrees
        );
        extendMotor = new MotorEx(hMap,RobotConfig.IntakeConstants.extendMotorName);

    }

    public SampleState hasCorrectSample(){
        double red = colorSensor1.red() + colorSensor2.red();
        double blue = colorSensor1.blue() + colorSensor2.blue();
        double green = colorSensor1.green() + colorSensor2.green();

        double max = Math.max(Math.max(red,blue),green);

        red/=max;
        blue/=max;
        green/=max;

        telemetryToReturn = "Red: " +red+"\nBlue: " + blue+"\nGreen: " +green;


        if(blue>red&&blue>green){
            switch(allianceColor){
                case RED:
                    return SampleState.WRONG_SAMPLE;
                case BLUE:
                    return SampleState.CORRESPONDING_SAMPLE;
            }
        } else if (red>blue&&red>green){
            switch(allianceColor){
                case RED:
                    return SampleState.CORRESPONDING_SAMPLE;
                case BLUE:
                    return SampleState.WRONG_SAMPLE;
            }
            return SampleState.WRONG_SAMPLE;
        } else if (Math.abs(red-green) < RobotConfig.IntakeConstants.colorSensorRedToGreenThreshold){
            return SampleState.YELLOW_SAMPLE;
        }

        return SampleState.NO_SAMPLE;

    }

    public String getTelemetry(){
        return telemetryToReturn;
    }

    public void spinWheelsUp(){
        intakeServo.setPower(RobotConfig.IntakeConstants.intakeServoUpDirection *1);
    }

    public void spinWheelsDown(){
        intakeServo.setPower(RobotConfig.IntakeConstants.intakeServoUpDirection *-1);
    }
    public void stopIntake(){
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


    public enum IntakeHeight{
        UP,
        DOWN
    }

    public enum SampleState {
        CORRESPONDING_SAMPLE,
        WRONG_SAMPLE,
        YELLOW_SAMPLE,
        NO_SAMPLE
    }

}
