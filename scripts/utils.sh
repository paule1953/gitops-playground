#!/usr/bin/env bash

HOSTNAMES=("jenkins" "scmm" "registry" "argo"
  "fluxv1-petclinic-plain-staging" "fluxv1-plain-petclinic"
  "fluxv1-nginx-staging" "fluxv1-nginx"
  "fluxv2-petclinic-plain-staging" "fluxv2-petclinic-plain"
  "argo-petclinic-plain-staging" "argo-petclinic-plain"
  "argo-nginx"
)
HOSTNAMES_CREATED=''
  
function confirm() {
  # shellcheck disable=SC2145
  # - the line break between args is intended here!
  printf "%s\n" "${@:-Are you sure? [y/N]} "
  
  read -r response
  case "$response" in
  [yY][eE][sS] | [yY])
    true
    ;;
  *)
    false
    ;;
  esac
}

function spinner() {
    local info="$1"
    local pid=$!
    local delay=0.1
    local spinstr='|/-\'
    while kill -0 $pid 2> /dev/null; do
        local temp=${spinstr#?}
        printf " [%c]  $info" "$spinstr"
        local spinstr=$temp${spinstr%"$temp"}
        sleep $delay
        local reset="\b\b\b\b\b"
        for ((i=1; i<=$(echo $info | wc -c); i++)); do
            reset+="\b"
        done
        printf $reset
    done
    echo " [ok] $info"
}

function hostname() {
    name=$1
    
    if [[ ! -z "$HOSTNAMES_CREATED" ]]; then 
      echo $name
      return
    fi
    
    case $name in
      registry)
        echo localhost:8999
        ;;
      jenkins)
        echo localhost:9090
        ;;
      scmm)
        echo localhost:9091
        ;;
      argo)
        echo localhost:9092
        ;;
      fluxv1-petclinic-plain-staging)
        echo localhost:9000
        ;;
      fluxv1-plain-petclinic)
        echo localhost:9001
        ;;
      fluxv1-nginx-staging)
        echo localhost:9002
        ;;
      fluxv1-nginx)
        echo localhost:9003
        ;;
      fluxv2-petclinic-plain-staging)
        echo localhost:9010
        ;;
      fluxv2-petclinic-plain)
        echo localhost:9011
        ;;
      argo-petclinic-plain-staging)
        echo localhost:9020
        ;;
      argo-petclinic-plain)
        echo localhost:9021
        ;;
      argo-nginx)
        echo localhost:9022
        ;;
      *)
        echo name
        ;;
    esac
}


function createHostNames() {
  local IP=127.0.0.1
  
  echo "Writing the following to /etc/hosts. ${IP}":
  echo "Hosts: " "${HOSTNAMES[@]}"

  for NAME in "${HOSTNAMES[@]}"
  do
       echo "${IP} ${NAME}" | sudo tee --append /etc/hosts
  done
  
  HOSTNAMES_CREATED=true
}

function deleteHostNames() {
  echo "Deleting entries from /etc/hosts: " "${HOSTNAMES[@]}"

  for NAME in "${HOSTNAMES[@]}"; do
       sudo sed -i "/${NAME}/d" /etc/hosts
  done
}