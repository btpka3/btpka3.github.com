#!bin/bash

dir=$(cd `dirname $0`;pwd)
  echo "------dir=${dir}"


mylog(){
  level=$1
  case $1 in
    info)      c=2   ;;
    error)  c=1   ;;
    warn) c=3   ;;
    *)      level="noop";;
  esac
  shift
  if [[ "${level}" = "noop" ]]
      then
      echo -e "$@"
    else
     echo -e "$(tput setaf $c)$@$(tput op)"
  fi
}