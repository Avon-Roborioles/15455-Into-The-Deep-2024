package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HardwareDevicePlus {
    private HardwareDevice device;
    private double sensitivity;
    private Telemetry telemetry;

    public HardwareDevicePlus(HardwareDevice device, Telemetry telemetry){
        this.device = device;
        if (device instanceof DcMotor){
            sensitivity =10;
        } else if(device instanceof Servo){
            sensitivity = .001;
        } else if (device instanceof CRServo){
            //We'll change the power to CR Servos as our variable
            sensitivity =.05;
        }

        this.telemetry =telemetry;
    }

    public void changePos(double incBy){
        double adjustedIncBy = incBy*sensitivity;

        if(device instanceof DcMotor){
            telemetry.addLine(device.getConnectionInfo());
        } else {
            telemetry.addLine("Not a motor");
        }
    }
}
