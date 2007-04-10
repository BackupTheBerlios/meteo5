#!/bin/bash
#       pkg_install.sh       Install or reinstall the current package
#
#       D.Deveaux-G.Moguerou
#       $Id: pkg_install.sh 33 2006-10-04 20:26:34Z deveaux $
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
#  Read information in the 'help*()` functions below
#
#   Global settings and variables ------------------------------------------
#
#   List of files to adjust
ver=`cat Version`
pkgname="STclass--${ver}"
fileslist="./etc/setup.bash ./etc/setup.tcsh"
if [ -w ".outil/${pkgname}.tcsh" ]; then
   fileslist="${fileslist} .outil/${pkgname}.tcsh .outil/${pkgname}.bash"
fi

#   Two arrays to define optional parameters and parameters help
optpar="bdir jhome"
optparhelp=('base install directory (supply only to override current dir)'\
            'java version identification')

#   To store parameters values and a basedir definition indicator
parvalues=''
bdirdefined=0
            
#
# Script local functions ---------------------------------------------------
#

function help() {
# Help function launched by '-h' or '--help' option and error
#
    echo "  
  This script installs or reinstalls the current package, adjusting
  automaticaly the package base directory on the current directory and
  other user modifiable parameters; the command is very simple:
  	./pkg_install.sh -h|--help	to display this message
	./pkg_install.sh -l|--list	to show modifiable parameters list
	./pkg_install.sh [<param>=<value>]* to install the package
              where <param> may be one of these keyword: $optpar
"  
} # --------------------------------------------------------------- help()

function verb_msg() {
#
    echo -e "\t- $msg"
} # ----------------------------------------------------------- verb_msg()

function error() {
#
    echo "ERROR: $1"
    help
    exit 1
} # -------------------------------------------------------------- error()

function list() {
# List authorized parameters names for installation
#
    echo "
    List of adjustable parameters of this install: ${#optpar}"
    i=0
    for id in ${optpar}; do
        echo -e "\t${id}:\t${optparhelp[$i]}"
        i=$(expr $i + 1)
    done
    echo ""
} # --------------------------------------------------------------- list()

function getbasedir() {
#  Get the base directory of the installation ; returns the 'basedir'
#  variable set
#
    local com="$0"
    while [ -L "$com" ]; do
        local link=$(readlink $com)
        local isabs=$(echo $link | grep -c '^/')
        if [ ${isabs} -ne 0 ]; then
            com=$link
        else
            com="${PWD}/${link}"
        fi
    done
    basedir=$(dirname $com)
    if [ -z $basedir -o "$basedir" == '.' ]; then
        basedir=$PWD
    else
        basedir=`(cd $basedir; pwd)`
    fi
    basedir=$(dirname $basedir)
    echo -e "\t- base package directory is: '${basedir}'"
} # --------------------------------------------------------- getbasedir()

function verifparam() {
#  Control if required substitutions are authorized
#
    local cpar
    echo ${parvalues}
    for cpar in ${parvalues} ; do
        #  extract the identifier and the value of the parameter
        local root=$(echo "$cpar" | cut -d '=' -f 1)
        #  verify if parameter can be modified
        if [ "$root" == "bdir" ]; then
	    bdirdefined=1;
	    basedir=$(echo "$cpar" | cut -d '=' -f 2)
	fi
        local found=0
        for parid in ${optpar}; do
            if [ "$root" == "$parid" ]; then
                found=1
                break
            fi
        done
        if [ ${found} -ne 1 ]; then 
            echo -e "\tparameter '${root}' not authorized"
            return 1 
        fi
        echo -e "\t- '${cpar}' substitution registered"
    done
    return 0
} # -------------------------------------------------------- verifparaml()

function trtfile() {
#  Make a file modification, substituing all parameters in it
#
    local curfile=$1
    local cpar
    local script=""
    for cpar in ${parvalues} ; do
        #  extract the identifier and build the substitution script
        local root=$(echo "$cpar" | cut -d '=' -f 1)
        echo "s:${root}=.*\$:${cpar}:g" >> script${$}.sed
    done
    echo
    #  make the substitution
    mv $curfile "tmp$$"
    sed -f script${$}.sed "tmp$$" > $curfile
    rm "tmp$$" script${$}.sed
    echo -e "\t- file '${curfile}' adjusted"
} # ------------------------------------------------------------ trtfile()

#
#   Beginning of the script ------------------------------------------------
#
#   Option analysis
#
if [ "Z$1" == "Z-h" -o  "Z$1" == "Z--help" ]; then
    help
    exit 0
fi
if [ "Z$1" == "Z-l" -o  "Z$1" == "Z--list" ]; then
    list
    exit 0
fi
if [ `echo "$1" | grep -c '^-'` != 0 ]; then
    error "Unrecognized option: $1"
    help
    exit 0
fi

echo ""
echo -e "  Post-installation of '${pkgname}' package\n"

#   Parameters definition verification
parvalues="$*"
verifparam
if [ $? -ne 0 ]; then 
   error "bad parameter definition - exit"
fi

#   Get the base directory of the package
if [ $bdirdefined -ne 1 ]; then
    getbasedir
    parvalues="bdir=${basedir} ${parvalues}"
fi

#   Files modification
cd ${basedir}
pwd
for f in $fileslist; do
    trtfile $f 
done
echo "
   Post-installation finished.
   You can re-run this script if you put this package in an other location
   or if you modify configuration parameters.
   
   Use 'source <stclass_path>/etc/setup.(bash|tcsh)' to initialize our working 
   environment; a good way is to define an alias 'init_stclass' that launch this
   command in our .profile or .cshrc file.
   "
