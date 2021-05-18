#! /bin/shell

#======================================================================
# 项目重启shell脚本
# 先调用shutdown.sh停服
# 然后调用startup.sh启动服务
#
# author: geekidea
# date: 2018-12-2
#======================================================================

# 项目名称
APPLICATION="@project.name@"

# 项目启动jar包名称
APPLICATION_JAR="@build.finalName@.jar"

#项目路径
script_home=$(dirname $(readlink -f $0))

# 停服
echo stop ${APPLICATION} Application...
sh ${script_home}/shutdown.sh

# 启动服务
echo start ${APPLICATION} Application...
sh ${script_home}/startup.sh