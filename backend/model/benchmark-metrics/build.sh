#!/bin/bash

# Generate commit ID
commit_id="${BUILD_ID}-$(git rev-parse --short HEAD)"
echo "$commit_id" > commit_id.txt

# Define ACR details
acr_login_server="acrulcacin001.azurecr.io"
image_tag="${acr_login_server}/${image_name}:${commit_id}"

# Use injected Jenkins credentials (set via Bindings in Jenkins UI)
docker build -t "$image_tag" .

echo "$acr_pass" | docker login "$acr_login_server" -u "$acr_user" --password-stdin

docker push "$image_tag"
