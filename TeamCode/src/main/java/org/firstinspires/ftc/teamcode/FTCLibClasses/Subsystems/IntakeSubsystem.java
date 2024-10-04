package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class IntakeSubsystem extends SubsystemBase {

    private ColorSensor colorSensor1;
    private ColorSensor colorSensor2;
    private CRServo servo;
    private AllianceColor allianceColor;
    private String telemetryToReturn = "";

    public IntakeSubsystem(HardwareMap hMap, AllianceColor alliance){
        colorSensor1 = hMap.get(ColorSensor.class, RobotConfig.IntakeConstants.colorSensor1Name);
        colorSensor2 = hMap.get(ColorSensor.class, RobotConfig.IntakeConstants.colorSensor2Name);

        servo = hMap.get(CRServo.class,RobotConfig.IntakeConstants.servoName);
        this.allianceColor = alliance;
    }

    public PixelState hasCorrectSample(){
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
                    return PixelState.WRONG_PIXEL;
                case BLUE:
                    return PixelState.CORRECT_PIXEL;
            }
        } else if (red>blue&&red>green){
            switch(allianceColor){
                case RED:
                    return PixelState.CORRECT_PIXEL;
                case BLUE:
                    return PixelState.WRONG_PIXEL;
            }
            return PixelState.WRONG_PIXEL;
        } else if (Math.abs(red-green) < RobotConfig.IntakeConstants.colorSensorRedToGreenThreshold){
            return PixelState.CORRECT_PIXEL;
        }

        return PixelState.NO_PIXEL;

    }

    public String getTelemetry(){
        return telemetryToReturn;
    }
    public enum PixelState{
        CORRECT_PIXEL,
        WRONG_PIXEL,
        NO_PIXEL
    }

}
