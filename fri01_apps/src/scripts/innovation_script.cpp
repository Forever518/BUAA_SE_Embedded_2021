#include "8_innovation_script.h"
CInnovationScript::CInnovationScript()
{
    
}

CInnovationScript::~CInnovationScript()
{

}

void CInnovationScript::Queue()
{
    stAct newAct;

    newAct.nAct = ACT_SPEAK;
    newAct.strTarget = "请标定机器人初始位置,20秒后启动流程";
    newAct.nDuration = 20;
    arAct.push_back(newAct);

    newAct.nAct = ACT_GOTO;
    newAct.strTarget = "kitchen";
    arAct.push_back(newAct);

    newAct.nAct = ACT_SPEAK;
    newAct.strTarget = "开始抓取饮料";
    newAct.nDuration = 5;
    arAct.push_back(newAct);

    newAct.nAct = ACT_GRAB;
    newAct.strTarget = "";
    newAct.nDuration = 5;
    arAct.push_back(newAct);

    newAct.nAct = ACT_SPEAK;
    newAct.strTarget = "饮料抓取完毕";
    newAct.nDuration = 2;
    arAct.push_back(newAct);

    newAct.nAct = ACT_GOTO;
    newAct.strTarget = "master";
    arAct.push_back(newAct);

    newAct.nAct = ACT_SPEAK;
    newAct.strTarget = "饮料已取回,主人请慢用";
    newAct.nDuration = 5;
    arAct.push_back(newAct);

    newAct.nAct = ACT_PASS;
    newAct.strTarget = "";
    newAct.nDuration = 5;
    arAct.push_back(newAct);

    newAct.nAct = ACT_SPEAK;
    newAct.strTarget = "任务完成";
    newAct.nDuration = 5;
    arAct.push_back(newAct);

}