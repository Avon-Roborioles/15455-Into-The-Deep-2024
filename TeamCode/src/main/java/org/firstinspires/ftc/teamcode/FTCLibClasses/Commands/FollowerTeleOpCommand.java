package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.RamseteCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

public class FollowerTeleOpCommand extends CommandBase {

    private FollowerSubsystem followerSubsystem;
    private Follower follower;
    private GamepadEx gamepadEx;
    private IMU imu;

    public FollowerTeleOpCommand(FollowerSubsystem subsystem, Telemetry telemetry, GamepadEx gamepadEx, IMU imu){
        followerSubsystem = subsystem;
        this.gamepadEx = gamepadEx;
        this.imu = imu;
        follower = followerSubsystem.getFollower();
    }


    @Override
    public void initialize(){
        follower.startTeleopDrive();
    }


    @Override
    public void execute(){
        follower.setTeleOpMovementVectors(
                gamepadEx.getLeftY(),
                gamepadEx.getLeftX(),
                gamepadEx.getRightX(),
                true
        );
        follower.update();
    }

    @Override
    public void end(boolean b){
        follower.breakFollowing();
    }
}
