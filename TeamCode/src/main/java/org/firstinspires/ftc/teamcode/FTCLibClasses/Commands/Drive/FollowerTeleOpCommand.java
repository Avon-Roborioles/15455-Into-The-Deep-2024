package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Drive;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;

public class FollowerTeleOpCommand extends CommandBase {

    private FollowerSubsystem followerSubsystem;
    private Follower follower;
    private GamepadEx gamepadEx;


    private int lockCount=0;

    public FollowerTeleOpCommand(FollowerSubsystem subsystem, Telemetry telemetry, GamepadEx gamepadEx){
        followerSubsystem = subsystem;
        this.gamepadEx = gamepadEx;
        follower = followerSubsystem.getFollower();
        addRequirements(followerSubsystem);
    }


    @Override
    public void initialize(){
        follower.startTeleopDrive();
    }


    @Override
    public void execute(){
        double heading = gamepadEx.getRightX()*.6;
        if (gamepadEx.getButton(GamepadKeys.Button.A)){
            followerSubsystem.resetHeading();
        }
//
//        if (Math.abs(follower.getPose().getHeading()%(Math.PI/4))<=Math.toRadians(5)){
//            heading = 0;
//            lockCount++;
//        }
//
//        if (lockCount>50){
//            heading = gamepadEx.getRightX();
//            lockCount = 0;
//        }

        follower.setTeleOpMovementVectors(
                gamepadEx.getLeftY(),
                -gamepadEx.getLeftX(),
                -heading*Math.abs(heading),
                false
        );
        follower.update();
    }

    @Override
    public void end(boolean b){
        follower.breakFollowing();
    }
}
