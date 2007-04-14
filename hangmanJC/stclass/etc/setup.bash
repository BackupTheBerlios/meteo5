#!source
#
#	etc/setup.bash
#
#	init the STclass preprocessor and java testing framework.
#
#  In standard installation, this script should not be modified by hand
#
#	$Id: setup.bash 25 2006-09-26 16:12:00Z deveaux $
#
#  Copyright (c) 2000-06 - Daniel Deveaux - U.B.S. - VALORIA
#
# =========================================================================
#  This file is part of STclass.
#
#  STclass is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License.
#
#  STclass is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with STclass; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
# =========================================================================
#
    #
    #  Settings adjusted by pkg_install.sh
    #
    jhome=/usr/lib/jvm/java-1.5.0-sun
    bdir=/home/tanuki/workspace/hangmanJC/stclass
    ver=$(cat ${bdir}/etc/Version)
    

function add_jars() {
# create the jars section in classpath; return the 'cjars' variable
#
	jar_dir="${bdir}/lib/stclass/jar"
	cjars=''
	for id in $jar_dir/*.jar; do
		cjars="${cjars}:${id}"
	done
	cjars=`echo ${cjars} | cut -c 2-`
} # --------------------------------------------------------------- add_jars()

    echo ""
    echo "  *** STclass-$ver framework activation ***"
    echo ""

    #
    # export basedir of stclass as STCLASS_HOME
    #
    export STCLASS_HOME=${bdir}
    
    #
    #   Access to java (decomment the test if you want to force java version)
    #
    if [ "${JAVA_HOME:-none}" == "none" ]; then
    	export JAVA_HOME=$jhome
    fi

    #
    #  Java and STclass initialization
    #
    echo "  Current java environment is ${JAVA_HOME}"
    echo ""
    echo -n "  STclass runs with " 
    ${JAVA_HOME}/bin/java -version
    
    #
    #  Adjust the command path
    #
    if [ -z $(echo $PATH | grep "${JAVA_HOME}/bin") ]; then
        export PATH=${JAVA_HOME}/bin:$PATH
    fi
    if [ -z $(echo $PATH | grep "${STCLASS_HOME}/bin") ]; then
        export PATH=${STCLASS_HOME}/bin:$PATH
    fi
    echo ""
    echo "  STclass Home: $STCLASS_HOME"
    echo ""
    echo "  Command Path: $PATH"

    add_jars

    local="."
    if [ -d "../build/class" ];  then local="${local}:../build/class"; fi
    if [ -d "./Class" ];  then local="${local}:./Class"; fi
    if [ -d "../Class" ];  then local="${local}:../Class"; fi
    if [ -d "${HOME}/lib/java" ]; then
    	local="${local}:${HOME}/lib/java"
    fi
    export CLASSPATH="${local}:${cjars}"
    echo ""
    echo "  ClassPath: $CLASSPATH"
    echo ""


