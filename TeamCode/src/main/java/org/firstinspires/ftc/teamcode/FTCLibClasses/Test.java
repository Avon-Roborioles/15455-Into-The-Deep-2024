package org.firstinspires.ftc.teamcode.FTCLibClasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Test {

    private Servo servo;

    public Test(HardwareMap hMap){
        servo = hMap.get(Servo.class,"testServo");

    }

    //Sets range from 0 to 1
    public void setBigRange(){
        servo.scaleRange(0,1);
    }

    //Sets range from .25 to .75
    public void setSmallRange(){
        servo.scaleRange(.25,.75);
    }

    public void setServoPos(double pos){
        servo.setPosition(pos);
    }

    public double getServoPos(){
        return servo.getPosition();
    }
}
