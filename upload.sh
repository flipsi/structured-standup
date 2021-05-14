#!/usr/bin/env bash

set -e


function upload_via_ssh() {
    [[ "${SRC}" == */ ]] && SRC="${SRC: : -1}" # remove trailing slash if there is one
    # scp -r "$SRC/." "$USER@$HOST:/$DST"
    rsync -ruL --delete --progress "$SRC/." "$USER@$HOST:$DST"
}


function upload_to_strato() {
    SRC="$1"
    echo "Uploading to Strato..."
    USER=$(pass strato/flipsi | grep ssh-user | cut -f2 -d' ')
    HOST=$(pass strato/flipsi | grep ssh-host | cut -f2 -d' ')
    DST="www/standup"
    upload_via_ssh
}

function prepare() {
    TMP_DOC_ROOT="/tmp/standup"
    mkdir -p "$TMP_DOC_ROOT"
    cp "./src/main/resources/index-fullopt.html" "$TMP_DOC_ROOT/index.html"
    cp "./src/main/resources/"*.css "$TMP_DOC_ROOT"
    cp "./target/scala-2.13/scalajs-bundler/main/laminarexamples-fastopt-bundle.js" "$TMP_DOC_ROOT"
    echo "$TMP_DOC_ROOT"
}

function main() {
    DIR=$(prepare)
    upload_to_strato "$DIR"
}

main
