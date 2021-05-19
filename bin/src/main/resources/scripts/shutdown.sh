#! /bin/shell

#======================================================================
# 项目停服shell脚本
# 通过项目名称查找到PID
# 然后kill -9 pid
#
# author: geekidea
# date: 2018-12-2
#======================================================================

# 项目名称
APPLICATION="@project.name@"

# 项目启动jar包名称
APPLICATION_JAR="@build.finalName@.jar"

PID=$(ps -ef | grep "${APPLICATION_JAR}" | grep -v grep | awk '{ print $2 }')
if [[ -n "$PID" ]]
then
    echo kill  ${PID}
    kill -9 ${PID}
    echo ${APPLICATION} stopped successfully
else
    echo ${APPLICATION} is already stopped
fi