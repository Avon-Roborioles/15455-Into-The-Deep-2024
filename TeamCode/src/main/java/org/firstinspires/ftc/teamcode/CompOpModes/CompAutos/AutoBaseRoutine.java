package org.firstinspires.ftc.teamcode.CompOpModes.CompAutos;

import org.firstinspires.ftc.teamcode.CompOpModes.RobotOpMode;


abstract class AutoBaseRoutine extends RobotOpMode {

    @Override
    public void createLogic(){
        specificInit();
    }

    abstract public void specificInit();

}
