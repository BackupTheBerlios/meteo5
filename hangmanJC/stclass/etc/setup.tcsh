#!source
#
#	etc/setup.tcsh
#
#	init the STclass preprocessor and java testing framework.
#                                              
#  In standard installation, this script should not be modified by hand
#
#       $Id: setup.tcsh 25 2006-09-26 16:12:00Z deveaux $
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
    
    set jhome=/usr/lib/jvm/java-1.5.0-sun
    set bdir=/home/tanuki/workspace/hangman/stclass
    set ver=`cat ${bdir}/etc/Version`

    echo ""
    echo "  STclass-$ver framework activation"
    echo ""

    #
    # export basedir of stclass as STCLASS_HOME
    #
    setenv STCLASS_HOME ${bdir}
    
    #
    #   Access to java (decomment the test if you want to force java version)
    #

    if ( ! ${?JAVA_HOME} ) then
	setenv JAVA_HOME $jhome
    endif


    #
    #  Java and STclass initialization
    #
    echo "  Current java environment is $JAVA_HOME"
    echo ""
    echo -n "  STclass runs with " 
    ${JAVA_HOME}/bin/java -version

    #
    #  Adjust the command path
    #
    if ( `echo $PATH | grep -c "${JAVA_HOME}/bin"` == 0 ) then
        setenv PATH ${JAVA_HOME}/bin:$PATH
    endif
    if ( `echo $PATH | grep -c "${STCLASS_HOME}/bin"` == 0 ) then
        setenv PATH ${STCLASS_HOME}/bin:$PATH
    endif
    echo ""
    echo "  STclass Home: $STCLASS_HOME"
    echo ""
    echo "  Command Path: $PATH"

    #
    # Add jar files in the classpath
    #
    set jar_dir="${STCLASS_HOME}/lib/stclass/jar"
    set cjars=''
    foreach id ($jar_dir/*.jar) 
        set cjars="${cjars}:${id}"
    end
    set cjars=`echo ${cjars} | cut -c 2-`
	
    set local="."
    if ( -d "./build/class") set local="${local}:./build/class"
    if ( -d "../build/class") set local="${local}:../build/class"
    if ( -d "./Class") set local="${local}:./Class"
    if ( -d "../Class") set local="${local}:../Class"
    if ( -d "${HOME}/lib/java") then
	set local = "${local}:${HOME}/lib/java"
    endif
    setenv CLASSPATH "${local}:${cjars}"
    echo ""
    echo "  ClassPath: $CLASSPATH"
    echo ""
    unset bdir, local, cjars
