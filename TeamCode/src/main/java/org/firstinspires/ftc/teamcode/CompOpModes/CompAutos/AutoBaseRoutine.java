package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import com.arcrobotics.ftclib.command.CommandGroupBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.AprilTagReader;
import org.firstinspires.ftc.teamcode.CompOpModes.RobotOpMode;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.ArmDownCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.ArmHighDunkCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.ExtendIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeDown;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeUp;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.PassIntoBucket;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.RetractIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.SpinIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.LiftDownCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.LiftHighBasketCommand;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ExtendMotorSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.SpinIntakeSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.VerticalIntakeSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;


abstract class AutoBaseRoutine extends RobotOpMode {





    @Override
    public void createLogic(){
        specificInit();
    }

    abstract public void specificInit();

}
