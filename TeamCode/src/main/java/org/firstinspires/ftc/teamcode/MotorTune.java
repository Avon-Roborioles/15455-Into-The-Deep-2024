package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MotorTune {

    private Motor motor;
    private int startPos;
    private Telemetry telemetry;
    private Gamepad gamepad;
    public MotorTune(HardwareMap hMap, String str, Gamepad gamepad, Telemetry telemetry){
        this.motor = new Motor(hMap,str);
        startPos = motor.getCurrentPosition();
        motor.setRunMode(Motor.RunMode.PositionControl);
        this.gamepad = gamepad;
    }
    public void run(){
        motor.setTargetPosition((int)(motor.getCurrentPosition()+gamepad.right_stick_y*10));
        motor.set(1);
        telemetry.addData("Motor Pos",motor.getCurrentPosition());
    }

}
