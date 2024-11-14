package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;

public class ExtendMotorSubsystem extends SubsystemBase {
    private Motor extendMotor;
    private ExtendPos extendPos = ExtendPos.IN;

    private Telemetry telemetry;

    public ExtendMotorSubsystem(HardwareMap hMap, Telemetry telemetry){
        extendMotor = new MotorEx(hMap, RobotConfig.IntakeConstants.extendMotorName);
        extendMotor.setRunMode(Motor.RunMode.PositionControl);
        extendMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        extendMotor.setPositionCoefficient(RobotConfig.IntakeConstants.motorPCoefficient);

        this.telemetry = telemetry;
    }

    public void periodic(){
        telemetry.addLine("============Extend Motor============");
        telemetry.addData("Motor Position",extendMotor.getCurrentPosition());
        telemetry.addData("Extend Target",extendPos);
    }

    public void extendMotorOutFully(){
        extendMotor.setRunMode(Motor.RunMode.PositionControl);
        extendMotor.setTargetPosition(RobotConfig.IntakeConstants.motorMaxPosition);
        extendMotor.set(RobotConfig.IntakeConstants.motorExtendSpeed);
        extendPos = ExtendPos.OUT;
    }

    public void retractMotorFully(){
        extendMotor.setRunMode(Motor.RunMode.PositionControl);
        extendMotor.setTargetPosition(RobotConfig.IntakeConstants.motorMinPosition);
        extendMotor.set(RobotConfig.IntakeConstants.motorRetractSpeed);
        extendPos = ExtendPos.IN;
    }


    public boolean isExtendAtClearPos(){
        return (extendMotor.getCurrentPosition()< RobotConfig.IntakeConstants.intakeClearBucketPos+ RobotConfig.IntakeConstants.motorDegreeOfError)
                ||(extendMotor.getCurrentPosition()>RobotConfig.IntakeConstants.intakeClearBucketPos - RobotConfig.IntakeConstants.motorDegreeOfError);
    }

    public void slowRetractMotor(){
        extendMotor.setRunMode(Motor.RunMode.RawPower);
        extendMotor.set(RobotConfig.IntakeConstants.motorSlowRetractionRawPower);

    }
    public void stopExtend(){
        extendMotor.set(0);
    }

    public boolean extendFinished(){
        switch (extendPos){
            case IN:
                return extendMotor.getCurrentPosition()<
                        RobotConfig.IntakeConstants.motorMinPosition+RobotConfig.IntakeConstants.motorDegreeOfError;
            case OUT:
                return extendMotor.getCurrentPosition()>
                        RobotConfig.IntakeConstants.motorMaxPosition-RobotConfig.IntakeConstants.motorDegreeOfError;

        }
        return extendMotor.atTargetPosition();
    }

    public enum ExtendPos{
        OUT("OUT"),
        CLEAR("CLEAR"),
        IN("IN");
        private String string;
        ExtendPos (String desc){
            this.string = desc;
        }

        public String toString(){
            return string;
        }
    }
}
