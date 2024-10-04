package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestRubberBandIntake {
    private ServoEx servo;
    private double servoSensitivity=.005;

    private ColorSensor colorSensor1;
    private ColorSensor colorSensor2;
    private Telemetry telemetry;
    private Gamepad gamepad;

    public TestRubberBandIntake(HardwareMap hMap, Gamepad gamepad, Telemetry telemetry){
        servo = new SimpleServo(hMap,"intakeServo",0,300);
        colorSensor1 = hMap.get(ColorSensor.class,"colorSensor");
        this.telemetry = telemetry;
        this.gamepad = gamepad;
    }

    public void runRBIntake(){
        double red = colorSensor1.red();
        double green = colorSensor1.green();
        double blue = colorSensor1.blue();



        telemetry.addData("Red",red);
        telemetry.addData("Blue", blue);
        telemetry.addData("Green", green);

        double max = Math.max(Math.max(red,blue),green);

        red/=max;
        blue/=max;
        green/=max;



        if(blue>red&&blue>green){
            telemetry.addLine("BLUE SAMPLE");
        } else if (red>blue&&red>green){
            telemetry.addLine("RED SAMPLE");
        } else if (Math.abs(red-green) < RobotConfig.IntakeConstants.colorSensorRedToGreenThreshold){
            telemetry.addLine("YELLOW SAMPLE");
        } else {
            telemetry.addLine("NO SAMPLE");
        }

        servo.setPosition(Range.clip(servo.getPosition()+gamepad.right_stick_y*servoSensitivity,0,1));
    }

}
