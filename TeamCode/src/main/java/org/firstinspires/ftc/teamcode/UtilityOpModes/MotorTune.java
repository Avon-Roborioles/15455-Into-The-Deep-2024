package org.firstinspires.ftc.teamcode.UtilityOpModes;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.DoubleSupplier;

@TeleOp(name = "Hardware Tune",group = "Utility")
public class MotorTune {

    private Motor motor;
    private int startPos;
    private Telemetry telemetry;
    private Gamepad gamepad;
    private DoubleSupplier getPos;

    private Servo servo;


    public MotorTune(HardwareMap hMap, String str, Gamepad gamepad, Telemetry telemetry, DoubleSupplier getPos){
        servo = hMap.get(Servo.class,str);

        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }


    public void run(){

        double pos = servo.getPosition() - gamepad.right_stick_y*.0005;
        pos = Range.clip(pos,0,1);
        servo.setPosition(pos);
        telemetry.addData("Servo Pos",servo.getPosition());
    }

}
