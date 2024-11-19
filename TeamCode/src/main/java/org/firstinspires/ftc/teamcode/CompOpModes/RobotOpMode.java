package org.firstinspires.ftc.teamcode.CompOpModes;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.Robot;

public abstract class RobotOpMode extends OpMode {

    protected Robot robot;
    protected GamepadEx drivePad;
    protected GamepadEx gamepadEx2;

    protected Bot curBot = Bot.COMP;

    protected AllianceColor allianceColor;

    protected String telemetryToAdd = "";



    public abstract void createLogic();

    public abstract void setAllianceColor();

    @Override
    public void init(){
        setAllianceColor();
        drivePad = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        robot = new Robot(hardwareMap,drivePad,gamepadEx2,telemetry,allianceColor);
        createLogic();
    }

    @Override
    public void init_loop(){
        if (gamepad1.b) {
            curBot = Bot.COMP;
        } else if (gamepad1.a){
            curBot = Bot.PRACTICE;
        }
        switch (curBot) {
            case COMP:
                telemetry.addLine("Initiating Comp Bot Opmode");
                break;
            case PRACTICE:
                telemetry.addLine("Initiating Practice Bot Opmode");
                break;
        }
        telemetry.addLine("Press b to initiate the comp opmode and a to initiate the practice bot opmode");
        telemetry.update();
    }

    @Override
    public void start(){
        robot.setBot(curBot);
    }

    @Override
    public final void loop(){
        drivePad.readButtons();
        gamepadEx2.readButtons();

        telemetry.addLine(getTelemetry());
        telemetry.addLine(telemetryToAdd);

        CommandScheduler.getInstance().run();
    }


    public String getTelemetry(){return "";};


    @Override
    public void stop(){
        CommandScheduler.getInstance().reset();
    }
}
