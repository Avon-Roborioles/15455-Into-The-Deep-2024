package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.ArmDownCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.ArmHighDunkCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.FollowerTeleOpCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.ExtendIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeDown;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeUp;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.PassIntoBucket;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.RetractIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.SpinIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.LiftDownCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.LiftHighBasketCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ExtendMotorSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.SpinIntakeSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.VerticalIntakeSubsystem;

public class Robot {
    public GamepadEx drivePad;
    public GamepadEx gamepadEx2;

    public DriveSubsystem driveSubsystem;
    public TeleOpDriveCommand driveCommand;
    public FollowerSubsystem followerSubsystem;
    public FollowerTeleOpCommand followerTeleOpCommand;


    public SpinIntakeSubsystem spinIntakeSubsystem;
    public VerticalIntakeSubsystem verticalIntakeSubsystem;
    public ExtendMotorSubsystem extendMotorSubsystem;

    public ExtendIntake extendIntake;
    public SpinIntake spinIntake;
    public RetractIntake retractIntake;
    public PassIntoBucket passIntoBucket;
    public MoveIntakeUp moveIntakeUp;
    public MoveIntakeDown moveIntakeDown;


    public LiftSubsystem liftSubsystem;
    public LiftHighBasketCommand liftCommand;
    public LiftDownCommand liftDownCommand;


    public ArmSubsystem armSubsystem;
    public ArmHighDunkCommand armCommand;
    public ArmDownCommand armDownCommand;

    public Robot(HardwareMap hMap, GamepadEx drivePad, GamepadEx gamepadEx2, Telemetry telemetry,AllianceColor color){
        this.drivePad = drivePad;
        this.gamepadEx2 = gamepadEx2;


        /*
        ====================Intake====================
         */
        verticalIntakeSubsystem = new VerticalIntakeSubsystem(hMap,telemetry);
        spinIntakeSubsystem = new SpinIntakeSubsystem(hMap,color,telemetry);
        extendMotorSubsystem = new ExtendMotorSubsystem(hMap,telemetry);

        extendIntake = new ExtendIntake(extendMotorSubsystem);
        retractIntake = new RetractIntake(extendMotorSubsystem);
        spinIntake = new SpinIntake(spinIntakeSubsystem);
        passIntoBucket = new PassIntoBucket(spinIntakeSubsystem);
        moveIntakeDown = new MoveIntakeDown(verticalIntakeSubsystem);
        moveIntakeUp = new MoveIntakeUp(verticalIntakeSubsystem);


        /*
        ====================Lift/Dunk====================
         */
        liftSubsystem = new LiftSubsystem(hMap,telemetry);
        armSubsystem = new ArmSubsystem(hMap,telemetry);

        liftCommand = new LiftHighBasketCommand(liftSubsystem);
        liftDownCommand = new LiftDownCommand(liftSubsystem);
        armCommand = new ArmHighDunkCommand(armSubsystem);
        armDownCommand = new ArmDownCommand(armSubsystem);

        /*
        ====================Drive====================
         */
        IMU imu = hMap.get(IMU.class,"imu");
        driveSubsystem = new DriveSubsystem(hMap,drivePad,imu);
        driveCommand = new TeleOpDriveCommand(driveSubsystem);

        followerSubsystem = new FollowerSubsystem(hMap);
        followerTeleOpCommand = new FollowerTeleOpCommand(followerSubsystem,telemetry,drivePad);
        CommandGroupBase.clearGroupedCommands();
    }


    public void setBot(Bot bot){
        driveSubsystem.setBot(bot);
        spinIntakeSubsystem.setBot(bot);
        verticalIntakeSubsystem.setBotType(bot);
        followerSubsystem.getFollower().setBot(bot);
    }
}







