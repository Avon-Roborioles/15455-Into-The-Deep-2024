package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Test;

import com.arcrobotics.ftclib.command.FunctionalCommand;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;

public class SubmersibleToBasket extends SequentialCommandGroup {

    public SubmersibleToBasket(DriveSubsystem drive){
        addCommands(
                new WaitCommand(100).deadlineWith(
                        new FunctionalCommand(
                                () ->{},
                                drive::goBackwardsAuto,
                                (Boolean b) -> {},
                                () ->{return false;}
                        )
                ),
                new ScheduleCommand(
                        new WaitCommand(500).deadlineWith(

                                new FunctionalCommand(
                                    () -> {},
                                    drive::goLeftAuto,
                                    (Boolean b) -> {},
                                    () -> {return  false;}
                                )

                        )
                )
        );
    }
}
