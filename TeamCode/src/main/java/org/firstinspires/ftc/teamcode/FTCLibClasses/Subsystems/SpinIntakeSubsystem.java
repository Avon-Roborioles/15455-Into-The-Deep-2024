package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.function.BooleanSupplier;

public class SpinIntakeSubsystem extends SubsystemBase {

    private ColorSensor colorSensor1;
    private ColorSensor colorSensor2;

    private CRServo intakeServo;



    private Motor extendMotor;

    private AllianceColor allianceColor;

    private Telemetry telemetry;
    private BooleanSupplier doTelemetry;

    private Bot curBot = Bot.COMP;


    public SpinIntakeSubsystem(HardwareMap hMap, AllianceColor alliance, Telemetry telemetry){
        colorSensor1 = hMap.get(ColorSensor.class, RobotConfig.IntakeConstants.colorSensor1Name);
        colorSensor2 = hMap.get(ColorSensor.class, RobotConfig.IntakeConstants.colorSensor2Name);

        intakeServo = hMap.get(CRServo.class,RobotConfig.IntakeConstants.intakeServoName);
        this.allianceColor = alliance;
        this.telemetry = telemetry;
        intakeServo.setPower(0);
    }

    public void setBot(Bot bot){
        curBot = bot;
    }


    //This method doesn't run the bot. It's only for telemetry purposes.
    // DO NOT PUT LOGIC INSIDE THIS
    @Override
    public void periodic(){
        //if(doTelemetry.getAsBoolean()) {
        telemetry.addLine("============COLOR SENSORS============");
        telemetry.addData("Combined Red Raw",colorSensor1.red()+colorSensor2.red());
        telemetry.addData("Combined Blue Raw",colorSensor1.blue()+colorSensor2.blue());
        telemetry.addData("Combined Green Raw", colorSensor1.green()+colorSensor2.green());
        telemetry.addData("SAMPLE STATE",hasCorrectSample());

        telemetry.addLine("============Intake Servo============");
        telemetry.addData("Intake Servo Power",intakeServo.getPower());
    }

    public SampleState hasCorrectSample(){
        //It adds both sensor values and then takes the proportion of the colors.
        //We do this because the closer the sample is, the more color it receives
        //The proportion of the colors is what matters
        double c1Red = colorSensor1.red();
        double c1Blue = colorSensor1.blue();
        double c1Green = colorSensor1.green();

        double c2Red = colorSensor2.red();
        double c2Blue = colorSensor2.blue();
        double c2Green = colorSensor2.green();

        double red = c1Red + c2Red;
        double blue = c1Blue + c2Blue;
        double green = c1Green + c2Green;
        return calculateSampleColor(red,green,blue);
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


    private SampleState calculateSampleColor(double red, double green, double blue){
        double max = Math.max(Math.max(red,blue),green);

        red/=max;
        blue/=max;
        green/=max;

        double redToGreenThreshold=.3;

        if (curBot == Bot.COMP){
            redToGreenThreshold = RobotConfig.IntakeConstants.colorSensorRedToGreenThreshold;
        }

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
        } else if (Math.abs(red-green) < redToGreenThreshold){
            return SampleState.YELLOW_SAMPLE;
        }
        return SampleState.NO_SAMPLE;
    }


    public enum SampleState {
        CORRESPONDING_SAMPLE("Corresponding Sample",true),
        WRONG_SAMPLE("Wrong Sample",false),
        YELLOW_SAMPLE("Yellow Sample",true),
        NO_SAMPLE("No Sample",false);

        private String name;
        public boolean correctSample;

        SampleState(String name){
            this.name = name;
        }
        SampleState(String name, boolean b){
            this.name = name;
            this.correctSample =b;
        }
        @Override


        public String toString(){
            return name;
        }

        public boolean equals(SampleState other){
            return this.toString().equals(other.toString());
        }
    }
}
