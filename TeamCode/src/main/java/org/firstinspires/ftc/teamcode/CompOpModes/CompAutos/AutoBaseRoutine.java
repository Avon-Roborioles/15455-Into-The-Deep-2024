package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import static java.lang.Math.PI;

import android.util.Size;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.ProxyScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.AprilTagReader;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.ExtendIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeDown;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.MoveIntakeUp;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.PassIntoBucket;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.RetractIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake.SpinIntake;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.FollowerSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.NoAprilTagFoundException;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.PedroPathAutoCommand;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


abstract class AutoBaseRoutine extends OpMode {

    private Follower follower;
    private FollowerSubsystem followerSubsystem;
    protected AllianceColor allianceColor;

    private AprilTagReader aprilTagReader;

    @Override
    public void init(){
        setAllianceColor();

//        AprilTagProcessor aprilTagProcessor= new AprilTagProcessor.Builder()
//                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
////                .setLensIntrinsics()
//                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
//                .build();
//
//        VisionPortal visionPortal = new VisionPortal.Builder()
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
//                .addProcessor(aprilTagProcessor)
//                .setCameraResolution(new Size(640,480))
//                .setStreamFormat(VisionPortal.StreamFormat.YUY2)
//                .setAutoStopLiveView(true)
//                .build();
//        aprilTagReader = new AprilTagReader(aprilTagProcessor,telemetry);

        Pose startPose;
        follower = new Follower(hardwareMap);
        followerSubsystem =new FollowerSubsystem(follower);

        followerSubsystem.setTelemetry(telemetry);
        try{
            startPose = aprilTagReader.readTag();
            follower.setPose(
                    startPose
            );
        } catch (Exception e){
            follower.setPose(
                    new Pose(0,0,Math.toRadians(270))
            );
        }
        telemetry.update();



        Path goRight =  new Path.PathBuilder(
                new BezierLine(
                         new Point(follower.getPose()),
                         new Point(
                                 new Pose(-11.4,-15.29,Math.toRadians(270))
                         )
                )

        )
                .setConstantHeadingInterpolation(PI/2)
                .build();
//        new Path(
//                new BezierLine(
//                        new Point(follower.getPose()),
//                        new Point(
//                                new Pose(13.9,17.17,Math.toRadians(90))
//                        )
//                )
//        );
        goRight.setConstantHeadingInterpolation(3*PI/2);

        IntakeSubsystem intake = new IntakeSubsystem(hardwareMap,allianceColor,telemetry,() ->false,gamepad1);
        SpinIntake spinIntake = new SpinIntake(intake);
        ExtendIntake extendIntake = new ExtendIntake(intake);
        RetractIntake retractIntake = new RetractIntake(intake);
        PassIntoBucket passIntoBucket = new PassIntoBucket(intake);
        MoveIntakeUp moveIntakeUp = new MoveIntakeUp(intake);
        MoveIntakeDown moveIntakeDown = new MoveIntakeDown(intake);
        SequentialCommandGroup groupExtendIntake = new SequentialCommandGroup(
                extendIntake,
                new ParallelCommandGroup(
                        moveIntakeDown,
                        new ProxyScheduleCommand(spinIntake)
                )
        );



        PedroPathAutoCommand goRightCommand = new PedroPathAutoCommand(followerSubsystem,goRight);
        SequentialCommandGroup moveAndIntake = new SequentialCommandGroup(
                goRightCommand,
                groupExtendIntake
        );
        moveAndIntake.schedule();
    }
    @Override
    public void loop(){
        CommandScheduler.getInstance().run();
    }

    abstract public void setAllianceColor();
}
