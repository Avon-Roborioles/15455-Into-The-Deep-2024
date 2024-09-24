package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class ServoTest extends SubsystemBase {
    private SimpleServo servo;

    public ServoTest(HardwareMap hMap){
        servo = new SimpleServo(hMap,"testServo",0,300, AngleUnit.DEGREES);
    }
    public void setToPos(double pos){
        
        servo.setPosition(pos);
    }

    public double getPos(){
        return servo.getPosition();
    }
}
