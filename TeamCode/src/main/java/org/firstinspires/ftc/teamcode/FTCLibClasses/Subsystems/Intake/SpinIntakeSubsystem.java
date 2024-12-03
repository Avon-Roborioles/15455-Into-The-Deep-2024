package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.Intake;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class SpinIntakeSubsystem extends SubsystemBase {

    private final ColorSensor colorSensor1;
    private final ColorSensor colorSensor2;

    private final CRServo intakeServo;


    private final AllianceColor allianceColor;

    private final Telemetry telemetry;

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
        double c1Red = colorSensor1.red();
        double c1Blue = colorSensor1.blue();
        double c1Green = colorSensor1.green();

        double c2Red = colorSensor2.red();
        double c2Blue = colorSensor2.blue();
        double c2Green = colorSensor2.green();

        double red = c1Red + c2Red;
        double blue = c1Blue + c2Blue;
        double green = c1Green + c2Green;

        double[] percents = getPercents(red,blue, green);
        red = percents[0];
        green=percents[1];
        blue=percents[2];



        //if(doTelemetry.getAsBoolean()) {
        telemetry.addLine("============COLOR SENSORS============");
        telemetry.addData("Percent Red",red);
        telemetry.addData("Percent Blue Raw",blue);
        telemetry.addData("Percent Green Raw", green);
        telemetry.addData("Red Raw",c1Red+c2Red);
        telemetry.addData("Blue Raw",c1Blue+c2Blue);
        telemetry.addData("Green Raw",c1Green+c2Green);
        telemetry.addData("SAMPLE STATE", getSampleState());

        telemetry.addLine("============Intake Servo============");
        telemetry.addData("Intake Servo Power",intakeServo.getPower());
    }

    public SampleState getSampleState(){
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


    public boolean hasCorrectSample(){
        double c1Red = colorSensor1.red();
        double c1Blue = colorSensor1.blue();
        double c1Green = colorSensor1.green();

        double c2Red = colorSensor2.red();
        double c2Blue = colorSensor2.blue();
        double c2Green = colorSensor2.green();

        SampleState c1Color = calculateSampleColor(c1Red,c1Green,c1Blue);
        SampleState c2Color = calculateSampleColor(c2Red,c2Green,c2Blue);

        if (!c1Color.correctSample||!c1Color.correctSample){
            return false;
        }
        return true;
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


    public double[] getPercents(double red, double blue, double green){
        double max = red+blue+green;
        double[] toRet = new double[3];
        toRet[0] = red/max;
        toRet[1] = green/max;
        toRet[2] = blue/max;
        return toRet;
    }


    private SampleState calculateSampleColor(double red, double green, double blue){
        double[] percents = getPercents(red,blue, green);
        red = percents[0];
        green=percents[1];
        blue=percents[2];

        if (blue>.42){
            switch(allianceColor){
                case RED:
                    return SampleState.WRONG_SAMPLE;
                case BLUE:
                    return SampleState.CORRESPONDING_SAMPLE;
            }
        } else if (.26<red){
            if (green<.40) {
                switch (allianceColor) {
                    case RED:
                        return SampleState.CORRESPONDING_SAMPLE;
                    case BLUE:
                        return SampleState.WRONG_SAMPLE;
                }
            } else {
                return SampleState.YELLOW_SAMPLE;

            }
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
