#!/usr/bin/env bash

HOSTNAMES=("jenkins" "scmm" "registry"
  "fluxv1-petclinic-staging" "fluxv1-petclinic"
  "fluxv1-nginx-staging" "fluxv1-nginx"
  "fluxv2-petclinic-staging" "fluxv2-petclinic"
  "argo-petclinic-staging" "argo-petclinic"
  "argo-nginx"
)
  
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

function createHostNames() {
  local IP=127.0.0.1
  
  echo "Writing the following to /etc/hosts. ${IP}":
  echo "Hosts: " "${HOSTNAMES[@]}"

  for NAME in "${HOSTNAMES[@]}"
  do
       echo "${IP} ${NAME}" | sudo tee --append /etc/hosts
  done
}

function deleteHostNames() {
  echo "Deleting entries from /etc/hosts: " "${HOSTNAMES[@]}"

  for NAME in "${HOSTNAMES[@]}"; do
       sudo sed -i "/${NAME}/d" /etc/hosts
  done
}